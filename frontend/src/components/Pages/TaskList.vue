<template>
  <div class="bg-dark-bg min-h-screen pb-12">
    <!-- 页面标题 -->
    <div class="bg-sub-bg border-b border-gray-800 py-8 px-4">
      <div class="container mx-auto">
        <h1 class="text-[clamp(1.5rem,3vw,2.5rem)] font-bold text-white">
          {{$t('pages.taskList.title') }}
        </h1>
        <p class="text-gray-400 mt-2 max-w-2xl">
          {{ $t('pages.taskList.subtitle') }}
        </p>
      </div>
    </div>
    
    <!-- 筛选和搜索 -->
    <div class="bg-dark-bg border-b border-gray-800 py-4 px-4">
      <div class="container mx-auto">
        <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
          <!-- 分类筛选 -->
          <div class="relative">
            <select
              v-model="selectedCategory"
              class="bg-sub-bg border border-gray-700 text-white rounded-lg py-2 pl-4 pr-10 appearance-none focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
            >
              <option value="all">{{ $t('pages.taskList.allCategories') }}</option>
              <option 
                v-for="category in categories" 
                :key="category.id" 
                :value="category.id"
              >
                {{ $t(`pages.taskList.categories.${category.id}`) }}
              </option>
            </select>
            <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
              <i class="fa-solid fa-chevron-down text-gray-400"></i>
            </div>
          </div>
          
          <!-- 难度筛选 -->
          <div class="relative">
            <select
              v-model="selectedDifficulty"
              class="bg-sub-bg border border-gray-700 text-white rounded-lg py-2 pl-4 pr-10 appearance-none focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
            >
              <option value="all">{{ $t('pages.taskList.allDifficulties') }}</option>
              <option value="easy">{{ $t('pages.taskList.difficulties.easy') }}</option>
              <option value="medium">{{ $t('pages.taskList.difficulties.medium') }}</option>
              <option value="hard">{{ $t('pages.taskList.difficulties.hard') }}</option>
              <option value="expert">{{ $t('pages.taskList.difficulties.expert') }}</option>
            </select>
            <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
              <i class="fa-solid fa-chevron-down text-gray-400"></i>
            </div>
          </div>
          
          <!-- 搜索框 -->
          <div class="relative w-full md:w-64">
            <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
              <i class="fa-solid fa-search text-gray-400"></i>
            </div>
            <input
              type="text"
              v-model="searchQuery"
              class="bg-sub-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-4 py-2 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
              placeholder="{{ $t('pages.taskList.searchPlaceholder') || '未找到翻译' }}"
            />
          </div>
        </div>
      </div>
    </div>
    
    <!-- 任务列表 -->
    <div class="container mx-auto px-4 mt-8">
      <!-- 加载状态 -->
      <div v-if="loading" class="flex justify-center items-center py-12">
        <div class="w-12 h-12 border-4 border-primary border-t-transparent rounded-full animate-spin"></div>
      </div>
      
      <!-- 空状态 -->
      <div v-else-if="filteredTasks.length === 0" class="text-center py-12">
        <div class="inline-flex items-center justify-center w-16 h-16 bg-gray-800 rounded-full mb-4">
          <i class="fa-solid fa-puzzle-piece text-primary text-2xl"></i>
        </div>
        <h3 class="text-xl font-semibold text-white mb-2">
          {{ $t('pages.taskList.noTasksTitle') }}
        </h3>
        <p class="text-gray-400 max-w-md mx-auto">
          {{ $t('pages.taskList.noTasksDescription') }}
        </p>
      </div>
      
      <!-- 任务卡片网格 -->
      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <TaskCard
          v-for="task in filteredTasks"
          :key="task.id"
          :task="task"
          :solved="solvedTaskIds.includes(task.id)"
          @solve="handleSolveTask"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import TaskCard from '@/components/Common/TaskCard.vue';
import { useUserStore } from '@/store/modules/user.js';
import { useTasksStore } from '@/store/modules/tasks.js';
import { useI18n } from 'vue-i18n'; 
const { t } = useI18n();

const userStore = useUserStore();
const taskStore = useTasksStore();

// 状态
const loading = ref(true);
const selectedCategory = ref('all');
const selectedDifficulty = ref('all');
const searchQuery = ref('');

// 获取任务列表
const fetchTasks = async () => {
  try {
    loading.value = true;
    await taskStore.fetchTasks();
  } catch (error) {
    window.$notify.error(
      t('pages.taskList.fetchErrorTitle'),
      t('pages.taskList.fetchErrorMessage')
    );
  } finally {
    loading.value = false;
  }
};

// 处理任务解决
const handleSolveTask = (taskId) => {
  // 显示成功通知
  window.$notify.success(
    t('pages.taskList.solveSuccessTitle'),
    t('pages.taskList.solveSuccessMessage')
  );
  
  // 更新已解决任务ID列表
  userStore.addSolvedTask(taskId);
};

// 计算属性：过滤后的任务列表
const filteredTasks = computed(() => {
  let tasks = taskStore.tasks;
  
  // 按分类筛选
  if (selectedCategory.value !== 'all') {
    tasks = tasks.filter(task => task.category === selectedCategory.value);
  }
  
  // 按难度筛选
  if (selectedDifficulty.value !== 'all') {
    tasks = tasks.filter(task => task.difficulty === selectedDifficulty.value);
  }
  
  // 按搜索词筛选
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase();
    tasks = tasks.filter(task => 
      task.title.toLowerCase().includes(query) || 
      task.description.toLowerCase().includes(query)
    );
  }
  
  // 按解决状态排序（已解决的排在前面）
  tasks.sort((a, b) => {
    const aSolved = solvedTaskIds.includes(a.id);
    const bSolved = solvedTaskIds.includes(b.id);
    
    if (aSolved && !bSolved) return -1;
    if (!aSolved && bSolved) return 1;
    return 0;
  });
  
  return tasks;
});

// 计算属性：已解决任务ID列表
const solvedTaskIds = computed(() => userStore.solvedTasks);

// 任务分类
const categories = [
  { id: 'web', icon: 'fa-code' },
  { id: 'crypto', icon: 'fa-lock' },
  { id: 'pwn', icon: 'fa-bomb' },
  { id: 'reversing', icon: 'fa-undo' },
  { id: 'forensics', icon: 'fa-magnifying-glass' },
  { id: 'misc', icon: 'fa-ellipsis-h' }
];

// 初始化
onMounted(() => {
  fetchTasks();
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
