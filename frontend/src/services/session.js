import { SESSION_STORAGE_KEY, WORKSPACE_STORAGE_KEY } from "../config.js";
import { emptyWorkspace } from "../models/index.js";

let session = readJson(SESSION_STORAGE_KEY);
let workspace = { ...emptyWorkspace, ...readJson(WORKSPACE_STORAGE_KEY) };
const listeners = new Set();

function readJson(key) {
  try {
    return JSON.parse(sessionStorage.getItem(key) || "null");
  } catch {
    return null;
  }
}

function notify() {
  listeners.forEach((listener) => listener({ session, workspace }));
}

export const sessionStore = {
  getSession: () => session,
  getWorkspace: () => workspace,
  setSession(nextSession) {
    session = nextSession;
    sessionStorage.setItem(SESSION_STORAGE_KEY, JSON.stringify(nextSession));
    notify();
  },
  clearSession() {
    session = null;
    sessionStorage.removeItem(SESSION_STORAGE_KEY);
    notify();
  },
  setWorkspace(nextWorkspace) {
    workspace = { ...workspace, ...nextWorkspace };
    sessionStorage.setItem(WORKSPACE_STORAGE_KEY, JSON.stringify(workspace));
    notify();
  },
  subscribe(listener) {
    listeners.add(listener);
    return () => listeners.delete(listener);
  },
};
