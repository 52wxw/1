import { defineStore } from 'pinia';
import axios from 'axios';

export const useRankingStore = defineStore('ranking', {
  state: () => ({
    users: [],
    teams: [],
    loading: false,
    error: null,
    pagination: {
      page: 1,
      limit: 10,
      total: 0,
      pages: 0
    }
  }),
  
  getters: {
    // 获取用户排行榜
    userRanking: (state) => state.users,
    
    // 获取团队排行榜
    teamRanking: (state) => state.teams,
    
    // 获取当前页用户
    currentPageUsers: (state) => {
      const start = (state.pagination.page - 1) * state.pagination.limit;
      const end = start + state.pagination.limit;
      return state.users.slice(start, end);
    },
    
    // 获取当前页团队
    currentPageTeams: (state) => {
      const start = (state.pagination.page - 1) * state.pagination.limit;
      const end = start + state.pagination.limit;
      return state.teams.slice(start, end);
    }
  },
  
  actions: {
    // 获取用户排行榜
    async fetchUserRanking(params = {}) {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.get('/api/ranking/users', {
          params: {
            page: this.pagination.page,
            limit: this.pagination.limit,
            ...params
          }
        });
        
        this.users = response.data.items;
        this.pagination = {
          ...this.pagination,
          total: response.data.total,
          pages: Math.ceil(response.data.total / this.pagination.limit)
        };
        
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 获取团队排行榜
    async fetchTeamRanking(params = {}) {
      try {
        this.loading = true;
        this.error = null;
        
        const response = await axios.get('/api/ranking/teams', {
          params: {
            page: this.pagination.page,
            limit: this.pagination.limit,
            ...params
          }
        });
        
        this.teams = response.data.items;
        this.pagination = {
          ...this.pagination,
          total: response.data.total,
          pages: Math.ceil(response.data.total / this.pagination.limit)
        };
        
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || error.message;
        throw error;
      } finally {
        this.loading = false;
      }
    },
    
    // 切换页码
    setPage(page) {
      this.pagination.page = page;
    },
    
    // 设置每页显示数量
    setLimit(limit) {
      this.pagination.limit = limit;
      this.pagination.page = 1; // 重置页码
    },
    
    // 清除错误
    clearError() {
      this.error = null;
    }
  }
});
