<template>
  <div class="bg-sub-bg border border-gray-800 rounded-lg p-6 transition-all hover:shadow-lg">
    <!-- 任务卡片内容 -->
    <div class="flex justify-between items-start">
      <!-- 左侧内容 -->
      <div class="flex items-center space-x-4">
        <!-- 分类标签 -->
        <div
          :class="['px-2 py-1 rounded-full text-xs font-medium', categoryClass(task.category)]"
        >
          {{ $t(`taskList.categories.${task.category}`) }}
        </div>

        <!-- 任务标题 -->
        <div>
          <h3 class="text-lg font-medium text-white">
            <router-link :to="`/tasks/${task.id}`">{{ task.title }}</router-link>
          </h3>
          <p class="text-gray-400 text-sm mt-1">
            {{ task.description | truncate(50) }}
          </p>
        </div>
      </div>

      <!-- 右侧信息 -->
      <div class="flex items-center space-x-6">
        <!-- 难度标签 -->
        <div :class="['px-2 py-1 rounded-full text-xs font-medium', difficultyClass(task.difficulty)]">
          {{ $t(`taskList.difficulties.${task.difficulty}`) }}
        </div>

        <!-- 分数 -->
        <div class="text-xl font-bold text-primary">
          {{ task.points }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

// 接收任务数据
const props = defineProps(['task']);

// 工具函数：获取分类标签颜色
const categoryClass = (category) => {
  return `bg-${category}-bg/20 text-${category}-text`;
};

// 工具函数：获取难度标签颜色
const difficultyClass = (difficulty) => {
  return `bg-${difficulty}-bg/20 text-${difficulty}-text`;
};

// 自定义过滤器：截断文本
const truncate = (value, length = 50) => {
  return value.length > length ? `${value.slice(0, length)}...` : value;
};
</script>

<style scoped>
/* 任务卡片样式 */
:deep(.task-card) {
  transition: all 0.3s ease;
  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
}
</style>
