<template>
  <div
    v-show="visible"
    class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm transition-opacity duration-300"
    :class="{ 'opacity-0 pointer-events-none': !visible }"
  >
    <div class="bg-dark-bg p-6 rounded-xl shadow-2xl flex flex-col items-center max-w-xs w-full">
      <div class="w-16 h-16 border-4 border-primary border-t-transparent rounded-full animate-spin mb-4"></div>
      <h3 class="text-xl font-bold text-white mb-2">{{ title }}</h3>
      <p class="text-gray-400 text-center">{{ message }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref, watch } from 'vue';

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: '加载中'
  },
  message: {
    type: String,
    default: '请稍候...'
  }
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
