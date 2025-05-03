import axios from 'axios';
import { ElMessage } from 'element-plus';
import { useUserStore } from '@/store/modules/user';

// 创建axios实例
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    const userStore = useUserStore();
    const token = userStore.token;
    
    // 如果有token，则添加到请求头
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    
    return config;
  },
  (error) => {
    console.error('Request error:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  (response) => {
    const res = response.data;
    
    // 如果状态码不是200，显示错误信息
    if (response.status !== 200) {
      ElMessage({
        message: res.message || 'Error',
        type: 'error',
        duration: 5000
      });
      
      // 401: 未登录; 403: 权限不足
      if (response.status === 401 || response.status === 403) {
        const userStore = useUserStore();
        userStore.logout();
        location.reload();
      }
      
      return Promise.reject(new Error(res.message || 'Error'));
    } else {
      return res;
    }
  },
  (error) => {
    console.error('Response error:', error);
    
    let message = error.message;
    if (error.response) {
      const { data, status } = error.response;
      
      // 处理特定状态码
      if (status === 401) {
        const userStore = useUserStore();
        userStore.logout();
        location.reload();
        message = 'Authentication failed, please log in again';
      } else if (status === 403) {
        message = 'Access denied';
      } else if (status === 404) {
        message = 'Resource not found';
      } else if (status === 500) {
        message = 'Server error';
      } else {
        message = data.message || `Request failed with status code ${status}`;
      }
    } else if (error.code === 'ECONNABORTED') {
      message = 'Request timeout';
    }
    
    ElMessage({
      message: message,
      type: 'error',
      duration: 5000
    });
    
    return Promise.reject(error);
  }
);

/**
 * 通用API请求方法
 * @param {string} method - 请求方法
 * @param {string} url - 请求URL
 * @param {object} data - 请求数据
 * @param {object} config - 请求配置
 * @returns {Promise} - 返回Promise对象
 */
const request = async (method, url, data = {}, config = {}) => {
  try {
    const response = await service({
      method,
      url,
      [method.toLowerCase() === 'get' ? 'params' : 'data']: data,
      ...config
    });
    
    return response;
  } catch (error) {
    throw error;
  }
};

// 导出API请求方法
export default {
  get: (url, params = {}, config = {}) => request('GET', url, params, config),
  post: (url, data = {}, config = {}) => request('POST', url, data, config),
  put: (url, data = {}, config = {}) => request('PUT', url, data, config),
  delete: (url, data = {}, config = {}) => request('DELETE', url, data, config),
  patch: (url, data = {}, config = {}) => request('PATCH', url, data, config)
};

// 导出API端点
export const API_ENDPOINTS = {
  // 认证相关
  AUTH: {
    LOGIN: '/auth/login',
    REGISTER: '/auth/register',
    LOGOUT: '/auth/logout',
    REFRESH_TOKEN: '/auth/refresh-token',
    VERIFY_EMAIL: '/auth/verify-email',
    FORGOT_PASSWORD: '/auth/forgot-password',
    RESET_PASSWORD: '/auth/reset-password'
  },
  
  // 用户相关
  USER: {
    PROFILE: '/users/profile',
    UPDATE_PROFILE: '/users/profile',
    CHANGE_PASSWORD: '/users/change-password',
    UPLOAD_AVATAR: '/users/upload-avatar',
    LIST: '/users',
    DETAIL: (id) => `/users/${id}`,
    UPDATE: (id) => `/users/${id}`,
    DELETE: (id) => `/users/${id}`
  },
  
  // 任务相关
  TASK: {
    LIST: '/tasks',
    DETAIL: (id) => `/tasks/${id}`,
    CREATE: '/tasks',
    UPDATE: (id) => `/tasks/${id}`,
    DELETE: (id) => `/tasks/${id}`,
    SUBMIT_FLAG: (id) => `/tasks/${id}/submit`,
    CATEGORIES: '/tasks/categories',
    STATISTICS: '/tasks/statistics'
  },
  
  // 团队相关
  TEAM: {
    LIST: '/teams',
    DETAIL: (id) => `/teams/${id}`,
    CREATE: '/teams',
    UPDATE: (id) => `/teams/${id}`,
    DELETE: (id) => `/teams/${id}`,
    JOIN: (id) => `/teams/${id}/join`,
    LEAVE: (id) => `/teams/${id}/leave`,
    INVITE: (id) => `/teams/${id}/invite`,
    ACCEPT_INVITE: (id) => `/teams/${id}/accept-invite`,
    REJECT_INVITE: (id) => `/teams/${id}/reject-invite`,
    KICK_MEMBER: (teamId, memberId) => `/teams/${teamId}/kick/${memberId}`
  },
  
  // 竞赛相关
  COMPETITION: {
    LIST: '/competitions',
    DETAIL: (id) => `/competitions/${id}`,
    CREATE: '/competitions',
    UPDATE: (id) => `/competitions/${id}`,
    DELETE: (id) => `/competitions/${id}`,
    JOIN: (id) => `/competitions/${id}/join`,
    LEAVE: (id) => `/competitions/${id}/leave`,
    START: (id) => `/competitions/${id}/start`,
    STOP: (id) => `/competitions/${id}/stop`,
    SUBMIT_FLAG: (competitionId, taskId) => `/competitions/${competitionId}/tasks/${taskId}/submit`
  },
  
  // 公告相关
  ANNOUNCEMENT: {
    LIST: '/announcements',
    DETAIL: (id) => `/announcements/${id}`,
    CREATE: '/announcements',
    UPDATE: (id) => `/announcements/${id}`,
    DELETE: (id) => `/announcements/${id}`
  },
  
  // 管理员相关
  ADMIN: {
    DASHBOARD: '/admin/dashboard',
    USERS: '/admin/users',
    TASKS: '/admin/tasks',
    TEAMS: '/admin/teams',
    COMPETITIONS: '/admin/competitions',
    ANNOUNCEMENTS: '/admin/announcements',
    SYSTEM_SETTINGS: '/admin/settings',
    LOGS: '/admin/logs'
  }
};
