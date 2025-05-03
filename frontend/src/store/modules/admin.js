import { defineStore } from 'pinia';
import axios from 'axios';

export const useAdminStore = defineStore('admin', {
  state: () => ({
    users: [],
    tasks: [],
    submissions: [],
    loading: false,
    error: null
  }),
  
  getters: {
    // 获取所有用户
    allUsers: (state) => state.users,
    
    // 获取所有任务
    allTasks: (state) => state.tasks,
    
    // 获取所有提交记录
    allSubmissions: (state) => state.submissions
  },
  
  actions: {
    // 获取用户列表
    async fetchUsers() {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.get('/api/admin/users');
        this.users = response.data;
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 创建新任务
    async createTask(taskData) {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.post('/api/admin/tasks', taskData);
        this.tasks.push(response.data);
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 更新任务
    async updateTask(taskId, taskData) {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.put(`/api/admin/tasks/${taskId}`, taskData);
        const taskIndex = this.tasks.findIndex(task => task.id === taskId);
        
        if (taskIndex !== -1) {
          this.tasks[taskIndex] = response.data;
        }
        
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 删除任务
    async deleteTask(taskId) {
      try {
        this.loading = true;
        this.error = null;
        
        await axios.delete(`/api/admin/tasks/${taskId}`);
        this.tasks = this.tasks.filter(task => task.id !== taskId);
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 获取所有任务
    async fetchTasks() {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.get('/api/admin/tasks');
        this.tasks = response.data;
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 获取所有提交记录
    async fetchSubmissions() {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.get('/api/admin/submissions');
        this.submissions = response.data;
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 清除错误
    clearError() {
      this.error = null;
    }
  }
});
