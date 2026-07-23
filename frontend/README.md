# FlowTrack frontend

A dependency-free, responsive single-page frontend for the FlowTrack services. It is deliberately built from native ES modules so it adds no framework or runtime dependency to the repository.

## Run with Docker Compose

The root `docker-compose.yml` includes a `frontend` service. After the backend stack has started, open `http://localhost:3000`.

The Nginx container serves the app and proxies these existing backend paths on the same origin:

| UI area | Backend endpoint(s) |
| --- | --- |
| Authentication | `POST /auth/registration`, `/auth/login`, `/auth/refresh`, `/auth/revoke` |
| Finance workspace | `GET /api/value/user/{userId}`, `POST /api/value`, `DELETE /api/value/{id}`, `GET /api/value/sum` |
| User directory | `GET/POST /api/user`, `PATCH/DELETE /api/user/{userId}` |
| User finance/internal email API | `GET /api/user/{userId}/finances`, `GET /api/internal/user/emails` |
| Broadcast email | `POST /api/admin/send/bulk` |

## Current backend integration constraints

The frontend uses every exposed controller endpoint and sends bearer credentials for protected requests. Two backend contracts currently prevent a fully authenticated user-management or messaging flow:

1. `POST /auth/login` returns a token whose subject is the username. The gateway filter requires a UUID subject plus `username` and `role` claims before it can populate the `X-USER-*` headers required by the protected services.
2. The current gateway routes do not expose `/auth/**`, `/api/value/**`, or `/api/admin/**`, and `JwtAuthenticFilter` is commented out in `api-gateway/application.yml`. The Nginx proxy therefore routes requests directly to services, but the user and notification services still require gateway-injected identity headers.
3. `GET /api/value/user/{userId}` does not return an entry ID, while `DELETE /api/value/{id}` requires one. Entries can be created and listed, but cannot be safely selected for deletion from the current API response.
4. `GET /api/user` returns `UserRegistrationDTO` without a user UUID, while its patch and delete routes require the UUID. The directory can list and create users but cannot safely offer rename or deletion controls from that response.

To enable those protected screens without fabricating identity in the browser, the backend needs an authenticated `GET /api/users/me` endpoint (returning at least the UUID, username, email, and role) and a gateway route/filter configuration that validates the existing bearer token and forwards verified identity headers. Finance list responses also need to include the entry UUID. The finance service endpoints are currently public, but the user UUID cannot be derived from the present login response; the workspace transparently asks for the existing user UUID until a `me` endpoint is available.
