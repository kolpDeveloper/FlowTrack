import { icon } from "./icons.js";

const root = document.getElementById("dialog-root");

export function confirmDialog({ title, description, confirmLabel = "Confirm", danger = false }) {
  return new Promise((resolve) => {
    const dialog = document.createElement("div");
    dialog.className = "dialog-backdrop";
    dialog.innerHTML = `
      <section class="dialog" role="dialog" aria-modal="true" aria-labelledby="dialog-title">
        <button class="icon-button dialog__close" aria-label="Close dialog">${icon("close")}</button>
        <div class="dialog__eyebrow">Please confirm</div>
        <h2 id="dialog-title">${title}</h2>
        <p>${description}</p>
        <div class="dialog__actions">
          <button class="button button--secondary" data-action="cancel">Cancel</button>
          <button class="button ${danger ? "button--danger" : "button--primary"}" data-action="confirm">${confirmLabel}</button>
        </div>
      </section>`;

    const close = (result) => {
      dialog.classList.add("is-leaving");
      window.setTimeout(() => dialog.remove(), 160);
      resolve(result);
    };
    dialog.addEventListener("click", (event) => {
      if (event.target === dialog) close(false);
      if (event.target.closest("[data-action='cancel'], .dialog__close")) close(false);
      if (event.target.closest("[data-action='confirm']")) close(true);
    });
    root.append(dialog);
    dialog.querySelector("[data-action='confirm']").focus();
  });
}
