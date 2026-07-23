import { authApi } from "./services/api.js";
import { sessionStore } from "./services/session.js";
import { renderShell } from "./components/layout.js";
import { toast } from "./components/toast.js";
import { renderLoginPage } from "./pages/login.js";
import { renderDashboard } from "./pages/dashboard.js";
import { renderUsersPage } from "./pages/users.js";
import { renderMessagesPage } from "./pages/messages.js";

const validRoutes = new Set(["dashboard", "users", "messages", "login"]);

function currentRoute() {
  const candidate = location.hash.replace(/^#\/?/, "") || "dashboard";
  return validRoutes.has(candidate) ? candidate : "dashboard";
}

function navigate(route) {
  location.hash = `/${route}`;
}

function renderApp() {
  const route = currentRoute();
  const session = sessionStore.getSession();
  if (!session) {
    if (route !== "login") history.replaceState(null, "", "#/login");
    renderLoginPage({ onAuthenticated: () => navigate("dashboard") });
    return;
  }

  if (route === "login") {
    navigate("dashboard");
    return;
  }
  renderShell({ route, session });
  document.getElementById("sign-out").addEventListener("click", signOut);

  if (route === "users") renderUsersPage();
  else if (route === "messages") renderMessagesPage();
  else renderDashboard();
}

async function signOut() {
  const session = sessionStore.getSession();
  try {
    if (session?.refreshToken) await authApi.revoke(session.refreshToken);
  } catch {
    // The local session must still be cleared when the server is unavailable.
  } finally {
    sessionStore.clearSession();
    navigate("login");
    toast("You’ve been signed out.");
  }
}

window.addEventListener("hashchange", renderApp);
sessionStore.subscribe(({ session }) => {
  if (!session && currentRoute() !== "login") navigate("login");
});

renderApp();
