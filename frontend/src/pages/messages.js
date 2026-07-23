import { messagesApi } from "../services/api.js";
import { icon } from "../components/icons.js";
import { confirmDialog } from "../components/dialog.js";
import { toast } from "../components/toast.js";

export function renderMessagesPage() {
  const container = document.getElementById("page-content");
  container.innerHTML = `
    <section class="page-heading"><span class="eyebrow">Outreach</span><h1>Messages</h1><p>Send a polished email broadcast to your FlowTrack audience.</p></section>
    <section class="message-layout">
      <article class="content-card compose-card"><div class="content-card__header"><div><h2>New broadcast</h2><p>The message is sent through the notification service.</p></div><span class="status-dot"><i></i> Ready to compose</span></div>
        <form id="message-form" class="form-stack" novalidate>
          <label class="field"><span>Recipients</span><textarea name="recipients" rows="3" required placeholder="person@example.com, another@example.com"></textarea><small>Separate email addresses with commas or new lines.</small></label>
          <label class="field"><span>Subject</span><input name="subject" maxlength="200" required placeholder="A concise, helpful subject" /></label>
          <label class="field"><span>Message</span><textarea name="body" rows="9" required placeholder="Write your email…"></textarea></label>
          <p id="message-error" class="form-error" hidden></p>
          <div class="form-actions"><span id="recipient-count" class="muted-text">0 recipients</span><button class="button button--primary" type="submit">${icon("mail")}<span>Review & send</span></button></div>
        </form>
      </article>
      <aside class="message-side"><article class="insight-card"><span class="insight-card__icon">${icon("mail")}</span><h3>Thoughtful messages land better</h3><p>Use a direct subject line and a clear next step in the first paragraph.</p></article><article class="notice-card notice-card--vertical"><span>${icon("warning")}</span><p>Sending requires the backend notification endpoint and gateway identity headers. If either is unavailable, FlowTrack will show the server response and will not report a successful send.</p></article></aside>
    </section>`;

  const form = container.querySelector("#message-form");
  const recipients = form.elements.recipients;
  recipients.addEventListener("input", () => updateRecipientCount(recipients.value));
  form.addEventListener("submit", submitMessage);
}

function updateRecipientCount(value) {
  const emails = parseRecipients(value);
  const label = document.getElementById("recipient-count");
  if (label) label.textContent = `${emails.length} ${emails.length === 1 ? "recipient" : "recipients"}`;
}

async function submitMessage(event) {
  event.preventDefault();
  const form = event.currentTarget;
  if (!form.reportValidity()) return;
  const data = Object.fromEntries(new FormData(form));
  const recipients = parseRecipients(data.recipients);
  const error = form.querySelector("#message-error");
  if (!recipients.length) {
    error.textContent = "Add at least one valid email address.";
    error.hidden = false;
    return;
  }
  const confirmed = await confirmDialog({ title: `Send to ${recipients.length} ${recipients.length === 1 ? "recipient" : "recipients"}?`, description: "The broadcast will be handed to the notification service immediately.", confirmLabel: "Send message" });
  if (!confirmed) return;

  const button = form.querySelector("button[type='submit']");
  button.disabled = true;
  error.hidden = true;
  try {
    await messagesApi.sendBulk(recipients, data.subject.trim(), data.body.trim());
    toast("Your broadcast has been queued for delivery.");
    form.reset();
    updateRecipientCount("");
  } catch (cause) {
    error.textContent = cause.message;
    error.hidden = false;
  } finally {
    button.disabled = false;
  }
}

function parseRecipients(value) {
  return value.split(/[\n,;]/).map((email) => email.trim()).filter((email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email));
}
