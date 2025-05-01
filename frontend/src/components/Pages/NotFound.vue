<template>
  <div class="bg-dark-bg min-h-screen flex items-center justify-center pb-12 px-4">
    <div class="max-w-4xl w-full text-center">
      <!-- 错误标题 -->
      <div class="relative">
        <h1 class="text-[clamp(8rem,20vw,15rem)] font-bold text-gray-800 leading-none">
          404
        </h1>
        <div class="absolute inset-0 flex items-center justify-center">
          <i class="fa-solid fa-exclamation-triangle text-8xl text-primary animate-pulse"></i>
        </div>
      </div>
      
      <!-- 错误信息 -->
      <h2 class="text-[clamp(1.5rem,3vw,2.5rem)] font-bold text-white mt-6">
        {{ $t('notFound.title') }}
      </h2>
      <p class="text-xl text-gray-400 mt-4 max-w-2xl mx-auto">
        {{ $t('notFound.message') }}
      </p>
      
      <!-- 行动按钮 -->
      <div class="mt-10 flex flex-col sm:flex-row items-center justify-center gap-4">
        <button 
          @click="goHome" 
          class="bg-primary hover:bg-primary/90 text-white font-medium rounded-lg px-8 py-3 transition-colors flex items-center justify-center w-full sm:w-auto"
        >
          <i class="fa-solid fa-home mr-2"></i>
          {{ $t('notFound.goHome') }}
        </button>
        <button 
          @click="goBack" 
          class="bg-sub-bg hover:bg-gray-700 border border-gray-700 text-white font-medium rounded-lg px-8 py-3 transition-colors flex items-center justify-center w-full sm:w-auto"
        >
          <i class="fa-solid fa-arrow-left mr-2"></i>
          {{ $t('notFound.goBack') }}
        </button>
      </div>
      
      <!-- 装饰元素 -->
      <div class="mt-16 flex justify-center space-x-4">
        <div class="w-3 h-3 rounded-full bg-primary"></div>
        <div class="w-3 h-3 rounded-full bg-primary/70"></div>
        <div class="w-3 h-3 rounded-full bg-primary/50"></div>
        <div class="w-3 h-3 rounded-full bg-primary/30"></div>
        <div class="w-3 h-3 rounded-full bg-primary/20"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

// 返回主页
const goHome = () => {
  router.push({ name: 'Home' });
};

// 返回上一页
const goBack = () => {
  router.back();
};
</script>

<style scoped>
/* 动画效果 */
.animate-pulse {
  animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

@keyframes pulse {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0.7;
  }
}
</style>
