import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
    token: null,
    loading: false,
    error: null
  }),
  
  getters: {
    isAuthenticated: (state) => !!state.token,
    isAdmin: (state) => state.user?.role === 'admin',
    username: (state) => state.user?.username || '',
    avatar: (state) => state.user?.avatar || 'https://picsum.photos/200/200?random=user'
  },
  
  actions: {
    // 初始化用户状态
    async init() {
      try {
        this.loading = true;
        this.error = null;
        
        // 从本地存储获取token
        const token = localStorage.getItem('token');
        if (!token) return;
        
        this.token = token;
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        
        // 获取用户信息
        await this.fetchUser();
      } catch (error) {
        this.error = error.message;
        this.logout(); // 清除无效的token
      } finally {
        this.loading = false;
      }
    },
    
    // 登录
    async login(credentials) {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.post('/api/auth/login', credentials);
        this.token = response.data.token;
        this.user = response.data.user;
        
        // 保存token到本地存储
        localStorage.setItem('token', this.token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${this.token}`;
        
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 注册
    async register(userData) {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.post('/api/auth/register', userData);
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 获取用户信息
    async fetchUser() {
      try {
        const response = await axios.get('/api/users/me');
        this.user = response.data;
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      }
    },
    
    // 更新用户信息
    async updateUser(userData) {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.patch('/api/users/me', userData);
        this.user = response.data;
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 登出
    logout() {
      this.user = null;
      this.token = null;
      localStorage.removeItem('token');
      delete axios.defaults.headers.common['Authorization'];
    },
    
    // 清除错误
    clearError() {
      this.error = null;
    }
  },
  
  // 持久化配置
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'user-store',
        storage: localStorage,
        paths: ['token'] // 只持久化token，用户信息会在初始化时重新获取
      }
    ]
  }
});
