import { financeApi } from "../services/api.js";
import { sessionStore } from "../services/session.js";
import { icon } from "../components/icons.js";
import { confirmDialog } from "../components/dialog.js";
import { toast } from "../components/toast.js";

export function renderDashboard() {
  const container = document.getElementById("page-content");
  const workspace = sessionStore.getWorkspace();
  container.innerHTML = `
    <section class="page-heading page-heading--split">
      <div><span class="eyebrow">Your workspace</span><h1>Overview</h1><p>See the entries that move your financial picture forward.</p></div>
      <button class="button button--primary" id="open-entry-modal">${icon("plus")}<span>Add entry</span></button>
    </section>
    <section class="connection-card ${workspace.userId ? "is-connected" : ""}" id="workspace-card">
      <div class="connection-card__icon">${icon(workspace.userId ? "bank" : "settings")}</div>
      <div><strong>${workspace.userId ? "Finance profile connected" : "Connect your finance profile"}</strong><p>${workspace.userId ? `Showing entries for ${workspace.userId}` : "Enter the UUID of the user record whose finance entries you want to view."}</p></div>
      <form id="workspace-form" class="workspace-form"><label class="sr-only" for="workspace-user-id">User UUID</label><input id="workspace-user-id" name="userId" value="${escapeAttribute(workspace.userId)}" placeholder="User UUID" autocomplete="off" required /><button class="button button--secondary" type="submit">${workspace.userId ? "Change" : "Connect"}</button></form>
    </section>
    <section class="metric-grid" aria-label="Financial summary">
      <article class="metric-card"><span class="metric-card__label">Your entries</span><strong id="entry-count" class="metric-card__value skeleton-text">—</strong><span class="metric-card__note">In the active profile</span></article>
      <article class="metric-card metric-card--accent"><span class="metric-card__label">Your recorded value</span><strong id="profile-total" class="metric-card__value skeleton-text">—</strong><span class="metric-card__note">Calculated from your entries</span></article>
      <article class="metric-card"><span class="metric-card__label">System total</span><strong id="system-total" class="metric-card__value skeleton-text">—</strong><span class="metric-card__note">Reported by FlowTrack</span></article>
    </section>
    <section class="content-card entries-card">
      <div class="content-card__header"><div><h2>Recent entries</h2><p>A complete view of the active finance profile.</p></div><button class="icon-button" id="refresh-entries" aria-label="Refresh entries">${icon("refresh")}</button></div>
      <div id="entries-content" class="entries-content"><div class="table-loading"><span class="spinner"></span> Loading your entries…</div></div>
    </section>
    <div class="modal-backdrop" id="entry-modal" hidden>
      <section class="modal" role="dialog" aria-modal="true" aria-labelledby="entry-modal-title">
        <button class="icon-button modal__close" type="button" data-close-entry aria-label="Close">${icon("close")}</button>
        <span class="eyebrow">New record</span><h2 id="entry-modal-title">Add finance entry</h2><p>Log a value against the connected profile.</p>
        <form id="entry-form" class="form-stack form-stack--compact" novalidate>
          <label class="field"><span>Label</span><input name="key" maxlength="255" required placeholder="e.g. Project payment" /></label>
          <div class="form-row"><label class="field"><span>Value</span><input name="value" type="number" step="0.01" required placeholder="0.00" /></label><label class="field"><span>Category</span><input name="category" maxlength="100" placeholder="e.g. Income" /></label></div>
          <p class="form-error" id="entry-error" hidden></p>
          <button class="button button--primary button--full" type="submit">Save entry</button>
        </form>
      </section>
    </div>`;

  const form = container.querySelector("#workspace-form");
  form.addEventListener("submit", (event) => {
    event.preventDefault();
    if (!form.reportValidity()) return;
    const userId = new FormData(form).get("userId").trim();
    sessionStore.setWorkspace({ userId });
    renderDashboard();
  });

  container.querySelector("#refresh-entries").addEventListener("click", () => loadDashboardData());
  container.querySelector("#entries-content").addEventListener("click", (event) => {
    if (event.target.closest("#retry-entries")) loadDashboardData();
  });
  container.querySelector("#open-entry-modal").addEventListener("click", () => openEntryModal());
  container.querySelector("[data-close-entry]").addEventListener("click", closeEntryModal);
  container.querySelector("#entry-modal").addEventListener("click", (event) => { if (event.target.id === "entry-modal") closeEntryModal(); });
  container.querySelector("#entry-form").addEventListener("submit", submitEntry);

  if (workspace.userId) loadDashboardData();
  else renderNotConnected();
}

async function loadDashboardData() {
  const userId = sessionStore.getWorkspace().userId;
  if (!userId) return renderNotConnected();
  setLoading();
  try {
    const [entries, total] = await Promise.all([financeApi.list(userId), financeApi.total().catch(() => null)]);
    renderMetrics(entries, total);
    renderEntries(entries);
  } catch (cause) {
    renderMetrics([], null);
    document.getElementById("entries-content").innerHTML = errorState(cause.message);
  }
}

function setLoading() {
  document.getElementById("entries-content").innerHTML = '<div class="table-loading"><span class="spinner"></span> Refreshing entries…</div>';
}

function renderNotConnected() {
  renderMetrics([], null);
  document.getElementById("entries-content").innerHTML = `
    <div class="empty-state"><span class="empty-state__icon">${icon("bank")}</span><h3>Your workspace is ready</h3><p>Connect an existing user UUID to load their live finance records.</p><button class="button button--secondary" id="focus-connect">Connect profile</button></div>`;
  document.getElementById("focus-connect")?.addEventListener("click", () => document.getElementById("workspace-user-id").focus());
}

function renderMetrics(entries, systemTotal) {
  const personalTotal = entries.reduce((sum, entry) => sum + Number(entry.value || 0), 0);
  document.getElementById("entry-count").textContent = entries.length.toLocaleString();
  document.getElementById("profile-total").textContent = formatNumber(personalTotal);
  document.getElementById("system-total").textContent = systemTotal === null ? "Unavailable" : formatNumber(systemTotal);
}

function renderEntries(entries) {
  const content = document.getElementById("entries-content");
  if (!entries.length) {
    content.innerHTML = `<div class="empty-state"><span class="empty-state__icon">${icon("plus")}</span><h3>No entries yet</h3><p>Your first record is only one small step away.</p><button class="button button--secondary" id="empty-add-entry">Add your first entry</button></div>`;
    content.querySelector("#empty-add-entry").addEventListener("click", openEntryModal);
    return;
  }

  content.innerHTML = `<div class="table-wrap"><table><thead><tr><th>Entry</th><th>Category</th><th>Created</th><th class="table-number">Value</th><th><span class="sr-only">Actions</span></th></tr></thead><tbody>${entries.map((entry) => `
    <tr><td><strong>${escapeHtml(entry.key || "Untitled entry")}</strong></td><td><span class="tag">${escapeHtml(entry.category?.name || "Uncategorized")}</span></td><td>${formatDate(entry.created_at)}</td><td class="table-number value-cell">${formatNumber(entry.value)}</td><td>${entry.id ? `<button class="icon-button icon-button--quiet delete-entry" data-entry-id="${escapeAttribute(entry.id)}" aria-label="Delete ${escapeAttribute(entry.key || "entry")}">${icon("trash")}</button>` : `<span class="identifier-unavailable" title="The list endpoint does not return an entry ID, which the delete endpoint requires.">ID unavailable</span>`}</td></tr>`).join("")}</tbody></table></div>`;
  content.querySelectorAll(".delete-entry").forEach((button) => button.addEventListener("click", () => deleteEntry(button.dataset.entryId)));
}

async function submitEntry(event) {
  event.preventDefault();
  const form = event.currentTarget;
  const error = form.querySelector("#entry-error");
  const userId = sessionStore.getWorkspace().userId;
  if (!userId) {
    error.textContent = "Connect a finance profile before creating an entry.";
    error.hidden = false;
    return;
  }
  if (!form.reportValidity()) return;
  const formData = Object.fromEntries(new FormData(form));
  const payload = { user_id: userId, key: formData.key.trim(), value: Number(formData.value) };
  if (formData.category.trim()) payload.category = { name: formData.category.trim() };
  const button = form.querySelector("button[type='submit']");
  button.disabled = true;
  error.hidden = true;
  try {
    await financeApi.create(payload);
    toast("Entry saved to your workspace.");
    form.reset();
    closeEntryModal();
    loadDashboardData();
  } catch (cause) {
    error.textContent = cause.message;
    error.hidden = false;
  } finally {
    button.disabled = false;
  }
}

async function deleteEntry(entryId) {
  if (!entryId) return;
  const confirmed = await confirmDialog({ title: "Delete this entry?", description: "This action permanently removes the selected finance record.", confirmLabel: "Delete entry", danger: true });
  if (!confirmed) return;
  try {
    await financeApi.remove(entryId);
    toast("Entry deleted.");
    loadDashboardData();
  } catch (cause) {
    toast(cause.message, "error");
  }
}

function openEntryModal() {
  const modal = document.getElementById("entry-modal");
  modal.hidden = false;
  modal.querySelector("input[name='key']").focus();
}

function closeEntryModal() {
  document.getElementById("entry-modal").hidden = true;
}

function formatNumber(value) {
  const number = Number(value || 0);
  return new Intl.NumberFormat(undefined, { maximumFractionDigits: 2, minimumFractionDigits: Number.isInteger(number) ? 0 : 2 }).format(number);
}

function formatDate(value) {
  if (!value) return "—";
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? "—" : new Intl.DateTimeFormat(undefined, { month: "short", day: "numeric", year: "numeric" }).format(date);
}

function errorState(message) {
  return `<div class="empty-state empty-state--error"><span class="empty-state__icon">${icon("warning")}</span><h3>We couldn’t load these entries</h3><p>${escapeHtml(message || "Please try again in a moment.")}</p><button class="button button--secondary" id="retry-entries">Try again</button></div>`;
}

function escapeHtml(value) {
  return String(value).replace(/[&<>'"]/g, (character) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", "'": "&#039;", '"': "&quot;" }[character]));
}

function escapeAttribute(value) {
  return escapeHtml(value || "");
}
