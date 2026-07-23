/** @typedef {{ access_token: string, expires_in: number, token_type: string, username: string, role: string, refreshToken: string }} AuthResponse */
/** @typedef {{ username: string, email: string, password?: string }} User */
/** @typedef {{ name: string }} Category */
/** @typedef {{ id?: string, user_id: string, key: string, value: number|string, created_at?: string, category?: Category }} FinanceEntry */
/** @typedef {{ content: User[], totalPages: number, totalElements: number, number: number, size: number, first: boolean, last: boolean }} Page */

export const emptyWorkspace = Object.freeze({ userId: "" });
