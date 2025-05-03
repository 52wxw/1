import { defineStore } from 'pinia';
import request from '@/utils/request'; // 引入配置好的请求实例

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null, // 存储后端返回的用户信息（包含 role 字段：1=管理员，2=普通用户）
    token: null,
    loading: false,
    error: null
  }),

  getters: {
    // 是否认证通过（存在有效 token）
    isAuthenticated: (state) => !!state.token,
    // 是否为管理员（角色值与后端统一：1=管理员）
    isAdmin: (state) => state.user?.role === 1,
    // 用户名校验
    username: (state) => state.user?.username || '',
  },

  actions: {
    // 初始化用户状态（用于页面加载时获取用户信息）
    async init() {
      try {
        this.loading = true;
        this.error = null;
        // 从本地存储获取 token
        const token = localStorage.getItem('token');
        if (!token) return; // 无 token 则跳过
        // 设置 token 到状态
        this.token = token;
        // 获取用户信息（包含 role 字段）
        await this.fetchUser();
      } catch (error) {
        this.error = error.message;
        this.logout(); // 清除无效 token
      } finally {
        this.loading = false;
      }
    },

    // 登录方法（接收用户名/密码，调用后端接口）
    async login(credentials) {
      try {
        this.loading = true;
        this.error = null;
        // 调用后端登录接口（使用配置好的 request 实例）
        const response = await request.post('/auth/login', credentials);
        // 存储 token 和用户信息（包含 role）
        this.token = response.token;
        this.user = response.user; // 关键：后端返回的 user 需包含 role 字段（1/2）
        // 持久化 token 到本地存储
        localStorage.setItem('token', response.token);
        // 登录成功后获取完整用户信息
        await this.fetchUser();
        return response;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 注册方法（可选，根据项目需求）
    async register(userData) {
      try {
        this.loading = true;
        this.error = null;
        const response = await request.post('/auth/register', userData);
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 获取用户详情（用于初始化或更新用户信息）
    async fetchUser() {
      try {
        const response = await request.get('/users/me');
        this.user = response; // 包含 role: 1（管理员）或 2（普通用户）
        return response;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      }
    },

    // 更新用户信息（可选，根据项目需求）
    async updateUser(userData) {
      try {
        this.loading = true;
        this.error = null;
        const response = await request.patch('/users/me', userData);
        this.user = response;
        return response;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },

    // 登出方法（清除用户状态和 token）
    logout() {
      this.user = null;
      this.token = null;
      localStorage.removeItem('token');
    },

    // 清除错误信息
    clearError() {
      this.error = null;
    }
  },

  // 持久化配置（仅持久化 token，用户信息每次初始化时重新获取）
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'user-store',
        storage: localStorage,
        paths: ['token'] // 只持久化 token，保证安全性
      }
    ]
  }
});
