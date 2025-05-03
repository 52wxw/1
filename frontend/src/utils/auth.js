import { useUserStore } from '@/store/modules/user';
import { ElMessage } from 'element-plus';
import router from '@/router';

/**
 * 检查用户是否已登录
 * @returns {boolean} - 用户是否已登录
 */
export const isLoggedIn = () => {
  const userStore = useUserStore();
  return !!userStore.token;
};

/**
 * 检查用户是否具有管理员权限
 * @returns {boolean} - 用户是否是管理员
 */
export const isAdmin = () => {
  const userStore = useUserStore();
  return userStore.user?.role === 'admin';
};

/**
 * 验证用户是否已登录，如果未登录则重定向到登录页
 * @returns {boolean} - 用户是否已登录
 */
export const authenticate = () => {
  if (!isLoggedIn()) {
    router.push({ name: 'Login' });
    ElMessage.warning('请先登录');
    return false;
  }
  return true;
};

/**
 * 验证用户是否是管理员，如果不是则重定向到首页
 * @returns {boolean} - 用户是否是管理员
 */
export const authorizeAdmin = () => {
  if (!isAdmin()) {
    router.push({ name: 'Home' });
    ElMessage.error('权限不足');
    return false;
  }
  return true;
};

/**
 * 保存认证信息到本地存储
 * @param {string} token - JWT令牌
 * @param {object} user - 用户信息
 */
export const saveAuthInfo = (token, user) => {
  const userStore = useUserStore();
  userStore.setToken(token);
  userStore.setUser(user);
};

/**
 * 清除认证信息
 */
export const clearAuthInfo = () => {
  const userStore = useUserStore();
  userStore.clearToken();
  userStore.clearUser();
};

/**
 * 获取当前用户ID
 * @returns {number|null} - 当前用户ID
 */
export const getCurrentUserId = () => {
  const userStore = useUserStore();
  return userStore.user?.id || null;
};

/**
 * 获取当前用户名
 * @returns {string|null} - 当前用户名
 */
export const getCurrentUsername = () => {
  const userStore = useUserStore();
  return userStore.user?.username || null;
};

/**
 * 获取当前用户角色
 * @returns {string|null} - 当前用户角色
 */
export const getCurrentUserRole = () => {
  const userStore = useUserStore();
  return userStore.user?.role || null;
};

/**
 * 获取当前用户头像
 * @returns {string|null} - 当前用户头像URL
 */
export const getCurrentUserAvatar = () => {
  const userStore = useUserStore();
  return userStore.user?.avatar || null;
};
