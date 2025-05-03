<template>
  <div class="bg-dark-bg min-h-screen pb-12">
    <!-- 页面标题 -->
    <div class="bg-sub-bg border-b border-gray-800 py-8 px-4">
      <div class="container mx-auto">
        <h1 class="text-[clamp(1.5rem,3vw,2.5rem)] font-bold text-white">
          {{ $t('adminDashboard.title') }}
        </h1>
        <p class="text-gray-400 mt-2 max-w-2xl">
          {{ $t('adminDashboard.subtitle') }}
        </p>
      </div>
    </div>
    
    <!-- 统计卡片 -->
    <div class="container mx-auto px-4 mt-8">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <!-- 用户统计 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6 transform hover:-translate-y-1 transition-all duration-300">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-gray-400 text-sm">
                {{ $t('adminDashboard.totalUsers') }}
              </p>
              <h3 class="text-3xl font-bold text-white mt-1">
                {{ statistics.totalUsers || 0 }}
              </h3>
              <p class="text-green-500 text-sm mt-2 flex items-center">
                <i class="fa-solid fa-arrow-up mr-1"></i>
                {{ statistics.userGrowth || 0 }}% {{ $t('adminDashboard.fromLastWeek') }}
              </p>
            </div>
            <div class="w-12 h-12 bg-blue-500/20 rounded-full flex items-center justify-center">
              <i class="fa-solid fa-users text-blue-500 text-xl"></i>
            </div>
          </div>
        </div>
        
        <!-- 团队统计 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6 transform hover:-translate-y-1 transition-all duration-300">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-gray-400 text-sm">
                {{ $t('adminDashboard.totalTeams') }}
              </p>
              <h3 class="text-3xl font-bold text-white mt-1">
                {{ statistics.totalTeams || 0 }}
              </h3>
              <p class="text-green-500 text-sm mt-2 flex items-center">
                <i class="fa-solid fa-arrow-up mr-1"></i>
                {{ statistics.teamGrowth || 0 }}% {{ $t('adminDashboard.fromLastWeek') }}
              </p>
            </div>
            <div class="w-12 h-12 bg-purple-500/20 rounded-full flex items-center justify-center">
              <i class="fa-solid fa-users-rectangle text-purple-500 text-xl"></i>
            </div>
          </div>
        </div>
        
        <!-- 任务统计 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6 transform hover:-translate-y-1 transition-all duration-300">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-gray-400 text-sm">
                {{ $t('adminDashboard.totalTasks') }}
              </p>
              <h3 class="text-3xl font-bold text-white mt-1">
                {{ statistics.totalTasks || 0 }}
              </h3>
              <p class="text-green-500 text-sm mt-2 flex items-center">
                <i class="fa-solid fa-arrow-up mr-1"></i>
                {{ statistics.taskGrowth || 0 }}% {{ $t('adminDashboard.fromLastWeek') }}
              </p>
            </div>
            <div class="w-12 h-12 bg-yellow-500/20 rounded-full flex items-center justify-center">
              <i class="fa-solid fa-puzzle-piece text-yellow-500 text-xl"></i>
            </div>
          </div>
        </div>
        
        <!-- 提交统计 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6 transform hover:-translate-y-1 transition-all duration-300">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-gray-400 text-sm">
                {{ $t('adminDashboard.totalSubmissions') }}
              </p>
              <h3 class="text-3xl font-bold text-white mt-1">
                {{ statistics.totalSubmissions || 0 }}
              </h3>
              <p class="text-red-500 text-sm mt-2 flex items-center">
                <i class="fa-solid fa-arrow-down mr-1"></i>
                {{ statistics.failureRate || 0 }}% {{ $t('adminDashboard.failureRate') }}
              </p>
            </div>
            <div class="w-12 h-12 bg-red-500/20 rounded-full flex items-center justify-center">
              <i class="fa-solid fa-paper-plane text-red-500 text-xl"></i>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 图表和数据 -->
    <div class="container mx-auto px-4 mt-8">
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- 活动趋势 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6 lg:col-span-2">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-xl font-semibold text-white">
              {{ $t('adminDashboard.activityTrend') }}
            </h2>
            <div class="flex items-center space-x-3">
              <button 
                @click="selectedTimeRange = 'week'"
                :class="{ 'bg-primary text-white': selectedTimeRange === 'week', 'bg-dark-bg text-gray-400 hover:bg-gray-700': selectedTimeRange !== 'week' }"
                class="px-3 py-1.5 rounded-lg text-sm transition-colors"
              >
                {{ $t('adminDashboard.week') }}
              </button>
              <button 
                @click="selectedTimeRange = 'month'"
                :class="{ 'bg-primary text-white': selectedTimeRange === 'month', 'bg-dark-bg text-gray-400 hover:bg-gray-700': selectedTimeRange !== 'month' }"
                class="px-3 py-1.5 rounded-lg text-sm transition-colors"
              >
                {{ $t('adminDashboard.month') }}
              </button>
              <button 
                @click="selectedTimeRange = 'year'"
                :class="{ 'bg-primary text-white': selectedTimeRange === 'year', 'bg-dark-bg text-gray-400 hover:bg-gray-700': selectedTimeRange !== 'year' }"
                class="px-3 py-1.5 rounded-lg text-sm transition-colors"
              >
                {{ $t('adminDashboard.year') }}
              </button>
            </div>
          </div>
          
          <!-- 图表容器 -->
          <div class="h-80">
            <canvas id="activityChart"></canvas>
          </div>
        </div>
        
        <!-- 任务分类统计 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6">
          <h2 class="text-xl font-semibold text-white mb-6">
            {{ $t('adminDashboard.taskCategories') }}
          </h2>
          
          <!-- 图表容器 -->
          <div class="h-64">
            <canvas id="categoryChart"></canvas>
          </div>
          
          <!-- 分类列表 -->
          <div class="mt-6 space-y-3">
            <div v-for="category in taskCategories" :key="category.id" class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full mr-2" :style="{ backgroundColor: categoryColor(category.id) }"></div>
                <span class="text-gray-300">
                  {{ $t(`taskList.categories.${category.id}`) }}
                </span>
              </div>
              <span class="text-white font-medium">
                {{ category.count || 0 }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 最近活动和待处理项 -->
    <div class="container mx-auto px-4 mt-8">
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- 最近活动 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-xl font-semibold text-white">
              {{ $t('adminDashboard.recentActivity') }}
            </h2>
            <a href="#" class="text-primary hover:text-primary/80 text-sm transition-colors">
              {{ $t('adminDashboard.viewAll') }}
            </a>
          </div>
          
          <div class="space-y-4">
            <div v-for="activity in recentActivity" :key="activity.id" class="flex items-start">
              <div class="flex-shrink-0 h-8 w-8 rounded-full flex items-center justify-center mr-3" :class="activityIconClass(activity.type)">
                <i :class="activityIcon(activity.type)" class="text-white text-sm"></i>
              </div>
              <div>
                <p class="text-gray-300">
                  <span class="font-medium text-white">{{ activity.user }}</span>
                  {{ activity.message }}
                </p>
                <p class="text-xs text-gray-400 mt-1">
                  {{ activity.time }}
                </p>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 待处理任务 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-xl font-semibold text-white">
              {{ $t('adminDashboard.pendingTasks') }}
            </h2>
            <span class="bg-red-500/20 text-red-400 text-xs font-medium px-2.5 py-0.5 rounded-full">
              {{ pendingTasks.length || 0 }} {{ $t('adminDashboard.pending') }}
            </span>
          </div>
          
          <div class="space-y-4">
            <div v-for="task in pendingTasks" :key="task.id" class="bg-dark-bg border border-gray-700 rounded-lg p-4 hover:bg-gray-800/50 transition-colors">
              <div class="flex items-start">
                <div class="flex-shrink-0 h-8 w-8 rounded-full flex items-center justify-center mr-3 bg-blue-500/20">
                  <i class="fa-solid fa-question text-blue-500"></i>
                </div>
                <div>
                  <h3 class="font-medium text-white">
                    {{ task.title }}
                  </h3>
                  <p class="text-sm text-gray-400 mt-1">
                    {{ task.description }}
                  </p>
                </div>
              </div>
              <div class="mt-3 flex justify-between items-center">
                <span class="text-xs text-gray-400">
                  {{ task.assignee || $t('adminDashboard.unassigned') }}
                </span>
                <button class="text-primary hover:text-primary/80 text-sm transition-colors">
                  {{ $t('adminDashboard.takeAction') }}
                </button>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 系统状态 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-xl font-semibold text-white">
              {{ $t('adminDashboard.systemStatus') }}
            </h2>
            <span class="bg-green-500/20 text-green-400 text-xs font-medium px-2.5 py-0.5 rounded-full">
              {{ $t('adminDashboard.online') }}
            </span>
          </div>
          
          <div class="space-y-4">
            <div class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full bg-green-500 mr-2"></div>
                <span class="text-gray-300">
                  {{ $t('adminDashboard.apiServer') }}
                </span>
              </div>
              <span class="text-green-400 text-sm font-medium">
                {{ $t('adminDashboard.running') }}
              </span>
            </div>
            <div class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full bg-green-500 mr-2"></div>
                <span class="text-gray-300">
                  {{ $t('adminDashboard.database') }}
                </span>
              </div>
              <span class="text-green-400 text-sm font-medium">
                {{ $t('adminDashboard.connected') }}
              </span>
            </div>
            <div class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full bg-green-500 mr-2"></div>
                <span class="text-gray-300">
                  {{ $t('adminDashboard.challengeServers') }}
                </span>
              </div>
              <span class="text-green-400 text-sm font-medium">
                {{ $t('adminDashboard.up') }} ({{ statistics.activeChallenges || 0 }})
              </span>
            </div>
            <div class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full bg-yellow-500 mr-2"></div>
                <span class="text-gray-300">
                  {{ $t('adminDashboard.memoryUsage') }}
                </span>
              </div>
              <span class="text-yellow-400 text-sm font-medium">
                {{ statistics.memoryUsage || 0 }}%
              </span>
            </div>
            <div class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full bg-green-500 mr-2"></div>
                <span class="text-gray-300">
                  {{ $t('adminDashboard.cpuUsage') }}
                </span>
              </div>
              <span class="text-green-400 text-sm font-medium">
                {{ statistics.cpuUsage || 0 }}%
              </span>
            </div>
          </div>
          
          <!-- 服务器负载 -->
          <div class="mt-8">
            <h3 class="text-lg font-medium text-white mb-3">
              {{ $t('adminDashboard.serverLoad') }}
            </h3>
            <div class="w-full bg-gray-700 rounded-full h-2 mb-2">
              <div 
                class="h-2 rounded-full bg-gradient-to-r from-green-500 via-yellow-500 to-red-500"
                :style="{ width: statistics.serverLoad + '%' }"
              ></div>
            </div>
            <div class="flex justify-between text-xs text-gray-400">
              <span>{{ $t('adminDashboard.low') }}</span>
              <span>{{ $t('adminDashboard.medium') }}</span>
              <span>{{ $t('adminDashboard.high') }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted, watch } from 'vue';
import Chart from 'chart.js/auto';
import { useAdminStore } from '@/store/modules/admin';

const adminStore = useAdminStore();

// 状态
const loading = ref(true);
const selectedTimeRange = ref('week');
const statistics = reactive({});
const taskCategories = reactive([]);
const recentActivity = reactive([]);
const pendingTasks = reactive([]);

// 图表实例
let activityChart = null;
let categoryChart = null;

// 获取仪表盘数据
const fetchDashboardData = async () => {
  try {
    loading.value = true;
    
    // 获取统计数据
    const dashboardData = await adminStore.fetchDashboardData();
    Object.assign(statistics, dashboardData.statistics);
    taskCategories.splice(0, taskCategories.length, ...dashboardData.taskCategories);
    recentActivity.splice(0, recentActivity.length, ...dashboardData.recentActivity);
    pendingTasks.splice(0, pendingTasks.length, ...dashboardData.pendingTasks);
    
    // 初始化图表
    initCharts();
  } catch (error) {
    window.$notify.error(
      $t('adminDashboard.fetchErrorTitle'),
      $t('adminDashboard.fetchErrorMessage')
    );
  } finally {
    loading.value = false;
  }
};

// 初始化图表
const initCharts = () => {
  // 销毁现有图表
  if (activityChart) activityChart.destroy();
  if (categoryChart) categoryChart.destroy();
  
  // 活动趋势图表
  const activityCtx = document.getElementById('activityChart').getContext('2d');
  activityChart = new Chart(activityCtx, {
    type: 'line',
    data: {
      labels: statistics.activityLabels || [],
      datasets: [
        {
          label: $t('adminDashboard.users'),
          data: statistics.userActivity || [],
          borderColor: '#3B82F6',
          backgroundColor: 'rgba(59, 130, 246, 0.1)',
          tension: 0.3,
          fill: true
        },
        {
          label: $t('adminDashboard.submissions'),
          data: statistics.submissionActivity || [],
          borderColor: '#EC4899',
          backgroundColor: 'rgba(236, 72, 153, 0.1)',
          tension: 0.3,
          fill: true
        },
        {
          label: $t('adminDashboard.newTasks'),
          data: statistics.taskActivity || [],
          borderColor: '#8B5CF6',
          backgroundColor: 'rgba(139, 92, 246, 0.1)',
          tension: 0.3,
          fill: true
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: 'top',
          labels: {
            color: '#9CA3AF'
          }
        },
        tooltip: {
          mode: 'index',
          intersect: false
        }
      },
      scales: {
        x: {
          grid: {
            color: 'rgba(156, 163, 175, 0.1)'
          },
          ticks: {
            color: '#9CA3AF'
          }
        },
        y: {
          grid: {
            color: 'rgba(156, 163, 175, 0.1)'
          },
          ticks: {
            color: '#9CA3AF',
            stepSize: 1
          }
        }
      },
      interaction: {
        mode: 'nearest',
        axis: 'x',
        intersect: false
      },
      elements: {
        point: {
          radius: 2,
          hoverRadius: 5
        }
      }
    }
  });
  
  // 任务分类图表
  const categoryCtx = document.getElementById('categoryChart').getContext('2d');
  categoryChart = new Chart(categoryCtx, {
    type: 'doughnut',
    data: {
      labels: taskCategories.map(cat => $t(`taskList.categories.${cat.id}`)),
      datasets: [{
        data: taskCategories.map(cat => cat.count || 0),
        backgroundColor: taskCategories.map(cat => categoryColor(cat.id)),
        borderWidth: 0
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      cutout: '70%',
      plugins: {
        legend: {
          display: false
        },
        tooltip: {
          callbacks: {
            label: function(context) {
              const label = context.label || '';
              const value = context.raw || 0;
              const total = context.dataset.data.reduce((a, b) => a + b, 0);
              const percentage = Math.round((value / total) * 100);
              return `${label}: ${value} (${percentage}%)`;
            }
          }
        }
      }
    }
  });
};

// 获取分类颜色
const categoryColor = (category) => {
  const colors = {
    web: '#3B82F6', // 蓝色
    crypto: '#EC4899', // 粉色
    pwn: '#8B5CF6', // 紫色
    reversing: '#F59E0B', // 橙色
    misc: '#10B981', // 绿色
    forensics: '#EF4444', // 红色
    osint: '#6366F1', // 靛蓝色
    hardware: '#06B6D4' // 青色
  };
  return colors[category] || '#9CA3AF';
};

// 获取活动图标
const activityIcon = (type) => {
  const icons = {
    user: 'fa-solid fa-user-plus',
    task: 'fa-solid fa-puzzle-piece',
    submission: 'fa-solid fa-paper-plane',
    team: 'fa-solid fa-users'
  };
  return icons[type] || 'fa-solid fa-circle';
};

// 获取活动图标类
const activityIconClass = (type) => {
  const classes = {
    user: 'bg-blue-500/20 text-blue-500',
    task: 'bg-yellow-500/20 text-yellow-500',
    submission: 'bg-green-500/20 text-green-500',
    team: 'bg-purple-500/20 text-purple-500'
  };
  return classes[type] || 'bg-gray-700 text-gray-400';
};

// 监听时间范围变化
watch(selectedTimeRange, () => {
  fetchDashboardData();
});

// 初始化
onMounted(() => {
  fetchDashboardData();
});

// 清理
onUnmounted(() => {
  if (activityChart) activityChart.destroy();
  if (categoryChart) categoryChart.destroy();
});
</script>

<style scoped>
/* 动画效果 */
.animate-spin {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
</style>
