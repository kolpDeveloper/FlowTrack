import { API_BASE_URL } from "../config.js";
import { sessionStore } from "./session.js";

export class ApiError extends Error {
  constructor(message, status, payload) {
    super(message);
    this.name = "ApiError";
    this.status = status;
    this.payload = payload;
  }
}

async function readResponse(response) {
  const contentType = response.headers.get("content-type") || "";
  if (response.status === 204 || response.status === 202 || !contentType) return null;

  try {
    return contentType.includes("application/json") ? await response.json() : await response.text();
  } catch {
    return null;
  }
}

function getErrorMessage(payload, fallback) {
  if (typeof payload === "string" && payload.trim()) return payload;
  if (payload && typeof payload === "object") {
    return payload.message || payload.error || payload.detail || fallback;
  }
  return fallback;
}

async function renewSession(refreshToken) {
  const response = await fetch(`${API_BASE_URL}/auth/refresh`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ refreshToken }),
  });
  const payload = await readResponse(response);
  if (!response.ok || !payload?.access_token) return null;

  sessionStore.setSession(payload);
  return payload.access_token;
}

/** Executes a real backend request and converts Spring error responses to a usable error. */
export async function request(path, options = {}) {
  const { method = "GET", body, authenticated = false, retry = true, headers = {} } = options;
  const session = sessionStore.getSession();
  const requestHeaders = { Accept: "application/json", ...headers };

  if (body !== undefined) requestHeaders["Content-Type"] = "application/json";
  if (authenticated && session?.access_token) requestHeaders.Authorization = `Bearer ${session.access_token}`;

  let response;
  try {
    response = await fetch(`${API_BASE_URL}${path}`, {
      method,
      headers: requestHeaders,
      body: body === undefined ? undefined : JSON.stringify(body),
    });
  } catch {
    throw new ApiError("Unable to reach FlowTrack. Check that the services are running.", 0, null);
  }

  if (response.status === 401 && authenticated && retry && session?.refreshToken) {
    const accessToken = await renewSession(session.refreshToken);
    if (accessToken) return request(path, { ...options, retry: false });
    sessionStore.clearSession();
    throw new ApiError("Your session has expired. Please sign in again.", 401, null);
  }

  const payload = await readResponse(response);
  if (!response.ok) {
    throw new ApiError(getErrorMessage(payload, `Request failed (${response.status}).`), response.status, payload);
  }
  return payload;
}

export const authApi = {
  login: (credentials) => request("/auth/login", { method: "POST", body: credentials }),
  register: (registration) => request("/auth/registration", { method: "POST", body: registration }),
  refresh: (refreshToken) => request("/auth/refresh", { method: "POST", body: { refreshToken } }),
  revoke: (refreshToken) => request("/auth/revoke", { method: "POST", body: { refreshToken }, authenticated: true }),
};

export const financeApi = {
  list: (userId) => request(`/api/value/user/${encodeURIComponent(userId)}`, { authenticated: true }),
  total: () => request("/api/value/sum", { authenticated: true }),
  create: (entry) => request("/api/value", { method: "POST", body: entry, authenticated: true }),
  remove: (entryId) => request(`/api/value/${encodeURIComponent(entryId)}`, { method: "DELETE", authenticated: true }),
};

export const usersApi = {
  list: (prefixName = "", page = 0, size = 20) => request(`/api/user?prefix_name=${encodeURIComponent(prefixName)}&page=${page}&size=${size}`, { authenticated: true }),
  create: (user) => request("/api/user", { method: "POST", body: user, authenticated: true }),
  rename: (userId, username) => request(`/api/user/${encodeURIComponent(userId)}`, { method: "PATCH", body: { username }, authenticated: true }),
  remove: (userId) => request(`/api/user/${encodeURIComponent(userId)}`, { method: "DELETE", authenticated: true }),
  finances: (userId) => request(`/api/user/${encodeURIComponent(userId)}/finances`, { authenticated: true }),
  listEmails: (page = 0, size = 20) => request(`/api/internal/user/emails?page=${page}&size=${size}`, { authenticated: true }),
};

export const messagesApi = {
  sendBulk: (recipients, subject, body) => request("/api/admin/send/bulk", {
    method: "POST",
    authenticated: true,
    // The controller currently declares Spring Data Page<String>, so preserve that contract.
    body: { to: { content: recipients, totalElements: recipients.length, totalPages: 1, number: 0, size: recipients.length }, subject, body },
  }),
};
