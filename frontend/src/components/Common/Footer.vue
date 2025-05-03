<template>
  <footer class="bg-sub-bg border-t border-gray-800 py-12">
    <div class="container mx-auto px-4">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
        <!-- 平台信息 -->
        <div class="md:col-span-1">
          <div class="flex items-center mb-4">
            <i class="fa-solid fa-shield-halved text-primary text-2xl mr-2"></i>
            <span class="text-xl font-bold text-white">CTF Platform</span>
          </div>
          <p class="text-gray-400 text-sm mb-4">
            {{ $t('footer.description') }}
          </p>
          <div class="flex space-x-4">
            <a href="#" class="text-gray-400 hover:text-primary transition-colors">
              <i class="fa-brands fa-twitter"></i>
            </a>
            <a href="#" class="text-gray-400 hover:text-primary transition-colors">
              <i class="fa-brands fa-github"></i>
            </a>
            <a href="#" class="text-gray-400 hover:text-primary transition-colors">
              <i class="fa-brands fa-discord"></i>
            </a>
            <a href="#" class="text-gray-400 hover:text-primary transition-colors">
              <i class="fa-brands fa-telegram"></i>
            </a>
          </div>
        </div>
        
        <!-- 快速链接 -->
        <div>
          <h3 class="text-white font-medium mb-4 text-lg">{{ $t('footer.quickLinks') }}</h3>
          <ul class="space-y-2">
            <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.about') }}</a></li>
            <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.rules') }}</a></li>
            <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.faq') }}</a></li>
            <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.contact') }}</a></li>
          </ul>
        </div>
        
        <!-- 资源链接 -->
        <div>
          <h3 class="text-white font-medium mb-4 text-lg">{{ $t('footer.resources') }}</h3>
          <ul class="space-y-2">
            <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.tutorials') }}</a></li>
            <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.tools') }}</a></li>
            <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.documentation') }}</a></li>
            <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.blog') }}</a></li>
          </ul>
        </div>
        
        <!-- 订阅 -->
        <div>
          <h3 class="text-white font-medium mb-4 text-lg">{{ $t('footer.followUs') }}</h3>
          <p class="text-gray-400 text-sm mb-4">
            {{ $t('footer.subscribe') }}
          </p>
          <div class="flex">
            <input 
              type="email" 
              placeholder="{{ $t('footer.email') }}" 
              class="bg-dark-bg border border-gray-700 rounded-l-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent text-sm w-full"
            />
            <button class="bg-primary hover:bg-primary/80 text-white rounded-r-lg px-4 transition-colors">
              <i class="fa-solid fa-paper-plane"></i>
            </button>
          </div>
        </div>
      </div>
      
      <div class="border-t border-gray-800 mt-8 pt-8 flex flex-col md:flex-row md:items-center md:justify-between">
        <p class="text-gray-400 text-sm">
          &copy; {{ new Date().getFullYear() }} {{ $t('footer.copyright') }}
        </p>
        <div class="flex space-x-4 mt-4 md:mt-0">
          <a href="#" class="text-gray-400 hover:text-primary text-sm transition-colors">{{ $t('footer.privacyPolicy') }}</a>
          <a href="#" class="text-gray-400 hover:text-primary text-sm transition-colors">{{ $t('footer.termsOfService') }}</a>
          <a href="#" class="text-gray-400 hover:text-primary text-sm transition-colors">{{ $t('footer.cookiePolicy') }}</a>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup>
// 页脚组件逻辑
</script>

<style scoped>
/* 页脚样式 */
footer {
  background-color: rgba(15, 23, 42, 0.8);
}

/* 响应式调整 */
@media (max-width: 768px) {
  .grid-cols-4 {
    grid-template-columns: 1fr 1fr;
  }
}

@media (max-width: 640px) {
  .grid-cols-4 {
    grid-template-columns: 1fr;
  }
}
</style>
