import { defineStore } from 'pinia';
import axios from 'axios';

export const useTasksStore = defineStore('tasks', {
  state: () => ({
    tasks: [],
    solvedTasks: [],
    loading: false,
    error: null
  }),
  
  getters: {
    // 获取所有任务
    allTasks: (state) => state.tasks,
    
    // 获取已解决的任务ID列表
    solvedTaskIds: (state) => state.solvedTasks.map(task => task.id),
    
    // 根据ID获取任务
    getTaskById: (state) => (id) => state.tasks.find(task => task.id === id),
    
    // 按分类筛选任务
    tasksByCategory: (state) => (category) => {
      if (!category || category === 'all') return state.tasks;
      return state.tasks.filter(task => task.category === category);
    },
    
    // 按难度筛选任务
    tasksByDifficulty: (state) => (difficulty) => {
      if (!difficulty || difficulty === 'all') return state.tasks;
      return state.tasks.filter(task => task.difficulty === difficulty);
    },
    
    // 获取任务统计信息
    taskStats: (state) => {
      const total = state.tasks.length;
      const solved = state.solvedTasks.length;
      const unsolved = total - solved;
      
      return {
        total,
        solved,
        unsolved,
        solvedPercentage: total > 0 ? Math.round((solved / total) * 100) : 0
      };
    }
  },
  
  actions: {
    // 获取所有任务
    async fetchTasks() {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.get('/api/tasks');
        this.tasks = response.data;
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 获取已解决的任务
    async fetchSolvedTasks() {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.get('/api/tasks/solved');
        this.solvedTasks = response.data;
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 提交任务答案
    async submitTask(taskId, flag) {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.post(`/api/tasks/${taskId}/submit`, { flag });
        
        // 更新已解决任务列表
        if (response.data.correct) {
          this.solvedTasks.push(this.getTaskById(taskId));
        }
        
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 刷新任务状态
    async refreshTaskStatus(taskId) {
      try {
        const response = await axios.get(`/api/tasks/${taskId}/status`);
        const taskIndex = this.tasks.findIndex(task => task.id === taskId);
        
        if (taskIndex !== -1) {
          this.tasks[taskIndex] = { ...this.tasks[taskIndex], ...response.data };
        }
        
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      }
    },
    
    // 清除错误
    clearError() {
      this.error = null;
    }
  }
});
