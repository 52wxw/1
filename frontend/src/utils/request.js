import axios from 'axios';
import { useUserStore } from '../store/modules/user'; // 引入 Pinia 的 userStore

const service = axios.create({
  baseURL: '/api', // 与前端代理配置一致（如 vite.config.js 中的 /api 代理到后端）
  timeout: 10000
});

// 请求拦截器：添加 JWT 令牌到请求头
service.interceptors.request.use(config => {
  const userStore = useUserStore();
  const token = userStore.token;
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  console.log('请求拦截器:', config.url, config.headers); // 调试用
  return config;
});

// 响应拦截器：处理认证和权限错误
service.interceptors.response.use(
  (response) => {
    console.log('响应拦截器:', response.config.url, response.data); // 调试用
    // 成功响应：直接返回数据
    return response.data;
  },
  (error) => {
    const { status } = error.response || {};
    const userStore = useUserStore();

    console.error('响应错误:', error); // 调试用

    // 1. 处理未认证错误（401：令牌无效或未登录）
    if (status === 401) {
      userStore.logout(); // 清除用户状态
      window.location.href = '/login'; // 跳转登录页
    }

    // 2. 处理权限不足错误（403：无角色或权限）
    if (status === 403) {
      alert('无权限执行此操作！');
    }

    // 3. 处理其他错误
    return Promise.reject(error);
  }
);

export default service;
