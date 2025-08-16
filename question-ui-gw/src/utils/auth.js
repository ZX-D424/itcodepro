// question-ui-gw/src/utils/auth.js
// export const TOKEN_KEY = 'Admin-Token';
// export const setToken = (token) => localStorage.setItem(TOKEN_KEY, token);

// export const getToken = () => localStorage.getItem(TOKEN_KEY);

// export const removeToken = () => localStorage.removeItem(TOKEN_KEY);

// export const isAuthenticated = () => !!getToken();


// 存储token的键名
const TokenKey = 'Question-2025-08-16-Token';

// 获取token
export function getToken() {
  return localStorage.getItem(TokenKey);
}

// 设置token
export function setToken(token) {
  return localStorage.setItem(TokenKey, token);
}

// 移除token
export function removeToken() {
  return localStorage.removeItem(TokenKey);
}

// 判断是否已登录
export function isAuthenticated() {
  return !!getToken();
}
