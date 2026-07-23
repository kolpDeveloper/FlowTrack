import { icon } from "./icons.js";

const region = document.getElementById("toast-region");

export function toast(message, type = "success") {
  const element = document.createElement("div");
  element.className = `toast toast--${type}`;
  element.setAttribute("role", "status");
  element.innerHTML = `<span class="toast__icon">${type === "error" ? icon("warning") : "✓"}</span><span>${message}</span><button class="icon-button toast__close" aria-label="Dismiss notification">${icon("close")}</button>`;
  element.querySelector("button").addEventListener("click", () => element.remove());
  region.append(element);
  window.setTimeout(() => element.remove(), 5000);
}
