import { APP_NAME } from "../config.js";
import { icon } from "./icons.js";

const navItems = [
  ["dashboard", "Overview", "dashboard"],
  ["users", "People", "users"],
  ["messages", "Messages", "mail"],
];

export function renderShell({ route, session }) {
  const app = document.getElementById("app");
  app.innerHTML = `
    <div class="app-shell">
      <aside class="sidebar" id="sidebar">
        <a class="brand" href="#/dashboard" aria-label="${APP_NAME} home">
          <span class="brand__mark">${icon("bank")}</span>
          <span>${APP_NAME}</span>
        </a>
        <nav class="nav" aria-label="Main navigation">
          <span class="nav__label">Workspace</span>
          ${navItems.map(([path, label, glyph]) => `<a href="#/${path}" class="nav__link ${route === path ? "is-active" : ""}">${icon(glyph)}<span>${label}</span></a>`).join("")}
        </nav>
        <div class="sidebar__footer">
          <div class="user-chip"><span class="avatar">${escapeHtml(session.username?.slice(0, 1) || "U")}</span><span><strong>${escapeHtml(session.username || "Account")}</strong><small>${escapeHtml(session.role || "Member")}</small></span></div>
          <button class="nav__link nav__link--button" id="sign-out">${icon("logout")}<span>Sign out</span></button>
        </div>
      </aside>
      <main class="main-content">
        <header class="mobile-header"><button class="icon-button" id="menu-toggle" aria-label="Open navigation">${icon("menu")}</button><a class="brand" href="#/dashboard"><span class="brand__mark">${icon("bank")}</span>${APP_NAME}</a></header>
        <div class="page-container" id="page-content"></div>
      </main>
    </div>`;

  app.querySelector("#menu-toggle")?.addEventListener("click", () => app.querySelector("#sidebar").classList.toggle("is-open"));
}

function escapeHtml(value) {
  return String(value).replace(/[&<>'"]/g, (character) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", "'": "&#039;", '"': "&quot;" }[character]));
}
