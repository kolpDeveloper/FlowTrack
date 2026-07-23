import { APP_NAME } from "../config.js";
import { authApi } from "../services/api.js";
import { sessionStore } from "../services/session.js";
import { icon } from "../components/icons.js";
import { toast } from "../components/toast.js";

export function renderLoginPage({ onAuthenticated }) {
  const app = document.getElementById("app");
  app.innerHTML = `
    <main class="auth-layout">
      <section class="auth-hero">
        <a class="brand brand--light" href="#/login"><span class="brand__mark">${icon("bank")}</span>${APP_NAME}</a>
        <div class="auth-hero__copy">
          <span class="eyebrow eyebrow--light">A clearer financial rhythm</span>
          <h1>Make every number feel intentional.</h1>
          <p>Bring your financial entries into one calm, focused workspace built for momentum.</p>
        </div>
        <div class="auth-orbit auth-orbit--one"></div><div class="auth-orbit auth-orbit--two"></div>
        <div class="auth-quote"><span class="auth-quote__mark">“</span><p>Clarity is not a feature. It’s the feeling you get when everything has a place.</p></div>
      </section>
      <section class="auth-panel">
        <div class="auth-card">
          <div class="auth-card__header"><span class="eyebrow">Welcome to FlowTrack</span><h2 id="auth-title">Welcome back</h2><p id="auth-subtitle">Sign in to continue to your workspace.</p></div>
          <div class="segmented-control" role="tablist" aria-label="Authentication">
            <button class="is-active" type="button" data-auth-tab="login" role="tab" aria-selected="true">Sign in</button>
            <button type="button" data-auth-tab="register" role="tab" aria-selected="false">Create account</button>
          </div>
          <form id="login-form" class="form-stack" novalidate>
            <label class="field"><span>Username</span><input name="username" autocomplete="username" minlength="2" required placeholder="Your username" /></label>
            <label class="field"><span>Password</span><input name="password" type="password" autocomplete="current-password" required placeholder="Your password" /></label>
            <p class="form-error" id="login-error" hidden></p>
            <button class="button button--primary button--full" type="submit"><span>Continue</span>${icon("arrowRight")}</button>
          </form>
          <form id="register-form" class="form-stack" novalidate hidden>
            <label class="field"><span>Username</span><input name="username" autocomplete="username" minlength="2" maxlength="30" required placeholder="Choose a username" /></label>
            <label class="field"><span>Email address</span><input name="email" type="email" autocomplete="email" required placeholder="you@example.com" /></label>
            <label class="field"><span>Password</span><input name="password" type="password" autocomplete="new-password" minlength="8" required placeholder="At least 8 characters" /></label>
            <p class="form-error" id="register-error" hidden></p>
            <button class="button button--primary button--full" type="submit"><span>Create account</span>${icon("arrowRight")}</button>
          </form>
        </div>
      </section>
    </main>`;

  const loginForm = app.querySelector("#login-form");
  const registerForm = app.querySelector("#register-form");
  app.querySelectorAll("[data-auth-tab]").forEach((tab) => tab.addEventListener("click", () => {
    const registering = tab.dataset.authTab === "register";
    app.querySelectorAll("[data-auth-tab]").forEach((item) => {
      const active = item === tab;
      item.classList.toggle("is-active", active);
      item.setAttribute("aria-selected", String(active));
    });
    loginForm.hidden = registering;
    registerForm.hidden = !registering;
    app.querySelector("#auth-title").textContent = registering ? "Create your account" : "Welcome back";
    app.querySelector("#auth-subtitle").textContent = registering ? "Start organizing your finances in minutes." : "Sign in to continue to your workspace.";
  }));

  loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(loginForm));
    if (!loginForm.reportValidity()) return;
    await submitForm(loginForm, "#login-error", async () => {
      const response = await authApi.login(data);
      sessionStore.setSession(response);
      toast(`Welcome back, ${response.username}.`);
      onAuthenticated();
    });
  });

  registerForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(registerForm));
    if (!registerForm.reportValidity()) return;
    await submitForm(registerForm, "#register-error", async () => {
      await authApi.register(data);
      toast("Account created. Sign in with your new details.");
      app.querySelector("[data-auth-tab='login']").click();
      loginForm.elements.username.value = data.username;
      loginForm.elements.password.focus();
    });
  });
}

async function submitForm(form, errorSelector, action) {
  const button = form.querySelector("button[type='submit']");
  const error = form.querySelector(errorSelector);
  error.hidden = true;
  button.disabled = true;
  button.classList.add("is-loading");
  try {
    await action();
  } catch (cause) {
    error.textContent = cause.message || "Something went wrong. Please try again.";
    error.hidden = false;
  } finally {
    button.disabled = false;
    button.classList.remove("is-loading");
  }
}
