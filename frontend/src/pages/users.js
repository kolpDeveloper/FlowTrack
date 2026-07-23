import { usersApi } from "../services/api.js";
import { icon } from "../components/icons.js";
import { toast } from "../components/toast.js";

let searchTimer;

export function renderUsersPage() {
  const container = document.getElementById("page-content");
  container.innerHTML = `
    <section class="page-heading page-heading--split"><div><span class="eyebrow">Directory</span><h1>People</h1><p>Manage the user records available to your FlowTrack workspace.</p></div><button class="button button--primary" id="show-user-form">${icon("plus")}<span>Add person</span></button></section>
    <section class="notice-card"><span>${icon("warning")}</span><p>This screen calls the protected user API with your bearer token. If the current gateway identity configuration is not enabled, the error state below explains that no user data was changed.</p></section>
    <section class="content-card directory-card">
      <div class="content-card__header content-card__header--directory"><div><h2>People directory</h2><p id="directory-count">Loading records…</p></div><label class="search-field"><span class="sr-only">Search people</span>${icon("search")}<input id="user-search" placeholder="Search by username" autocomplete="off" /></label></div>
      <div id="users-content"><div class="table-loading"><span class="spinner"></span> Loading people…</div></div>
    </section>
    <div class="modal-backdrop" id="user-modal" hidden><section class="modal" role="dialog" aria-modal="true" aria-labelledby="user-modal-title"><button class="icon-button modal__close" data-close-user type="button" aria-label="Close">${icon("close")}</button><span class="eyebrow">New user</span><h2 id="user-modal-title">Add a person</h2><p>Create a user using the existing registration endpoint.</p><form id="user-form" class="form-stack form-stack--compact" novalidate><label class="field"><span>Username</span><input name="username" minlength="2" maxlength="30" required placeholder="Username" /></label><label class="field"><span>Email address</span><input name="email" type="email" required placeholder="name@example.com" /></label><label class="field"><span>Temporary password</span><input name="password" type="password" minlength="8" required placeholder="At least 8 characters" /></label><p id="user-error" class="form-error" hidden></p><button class="button button--primary button--full" type="submit">Create person</button></form></section></div>`;

  container.querySelector("#show-user-form").addEventListener("click", openUserModal);
  container.querySelector("[data-close-user]").addEventListener("click", closeUserModal);
  container.querySelector("#user-modal").addEventListener("click", (event) => { if (event.target.id === "user-modal") closeUserModal(); });
  container.querySelector("#user-form").addEventListener("submit", createUser);
  container.querySelector("#user-search").addEventListener("input", (event) => {
    window.clearTimeout(searchTimer);
    searchTimer = window.setTimeout(() => loadUsers(event.target.value), 300);
  });
  loadUsers();
}

async function loadUsers(query = "") {
  const content = document.getElementById("users-content");
  const count = document.getElementById("directory-count");
  if (!content || !count) return;
  content.innerHTML = '<div class="table-loading"><span class="spinner"></span> Loading people…</div>';
  try {
    const page = await usersApi.list(query);
    count.textContent = `${Number(page.totalElements || 0).toLocaleString()} ${page.totalElements === 1 ? "person" : "people"}`;
    renderUsers(page.content || []);
  } catch (cause) {
    count.textContent = "Directory unavailable";
    content.innerHTML = `<div class="empty-state empty-state--error"><span class="empty-state__icon">${icon("warning")}</span><h3>People couldn’t be loaded</h3><p>${escapeHtml(cause.message)}</p><button class="button button--secondary" id="retry-users">Try again</button></div>`;
    content.querySelector("#retry-users")?.addEventListener("click", () => loadUsers(query));
  }
}

function renderUsers(users) {
  const content = document.getElementById("users-content");
  if (!users.length) {
    content.innerHTML = `<div class="empty-state"><span class="empty-state__icon">${icon("users")}</span><h3>No people found</h3><p>Try a different search or create the first user record.</p></div>`;
    return;
  }
  content.innerHTML = `<div class="people-list">${users.map((user) => `<article class="person-row"><span class="avatar avatar--large">${escapeHtml(user.username?.slice(0, 1) || "U")}</span><div class="person-row__identity"><strong>${escapeHtml(user.username || "Unnamed user")}</strong><span>${escapeHtml(user.email || "No email address")}</span></div><span class="tag tag--muted">User record</span></article>`).join("")}</div><p class="inline-hint">The user-list response does not include user UUIDs, so rename and delete are intentionally unavailable until the backend exposes IDs.</p>`;
}

async function createUser(event) {
  event.preventDefault();
  const form = event.currentTarget;
  if (!form.reportValidity()) return;
  const error = form.querySelector("#user-error");
  const button = form.querySelector("button[type='submit']");
  button.disabled = true;
  error.hidden = true;
  try {
    await usersApi.create(Object.fromEntries(new FormData(form)));
    toast("User created successfully.");
    closeUserModal();
    loadUsers(document.getElementById("user-search")?.value || "");
  } catch (cause) {
    error.textContent = cause.message;
    error.hidden = false;
  } finally {
    button.disabled = false;
  }
}

function openUserModal() {
  const modal = document.getElementById("user-modal");
  modal.hidden = false;
  modal.querySelector("input[name='username']").focus();
}

function closeUserModal() {
  document.getElementById("user-modal").hidden = true;
}

function escapeHtml(value) {
  return String(value).replace(/[&<>'"]/g, (character) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", "'": "&#039;", '"': "&quot;" }[character]));
}
