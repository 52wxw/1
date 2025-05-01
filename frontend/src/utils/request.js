import axios from 'axios';
import store from '../store.js';

const service = axios.create({
  baseURL: '/api',
  timeout: 10000
});

// 请求拦截器
service.interceptors.request.use(config => {
  const token = store.state.token;
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// 响应拦截器
service.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response.status === 401) {
      store.commit('setToken', null);
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default service;
