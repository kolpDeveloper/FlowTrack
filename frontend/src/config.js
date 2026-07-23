/**
 * Leave this empty in Docker: Nginx proxies API requests to the services.
 * A deployed static build may set window.FLOWTRACK_API_BASE before main.js loads.
 */
export const API_BASE_URL = window.FLOWTRACK_API_BASE ?? "";

export const APP_NAME = "FlowTrack";
export const SESSION_STORAGE_KEY = "flowtrack.session";
export const WORKSPACE_STORAGE_KEY = "flowtrack.workspace";
