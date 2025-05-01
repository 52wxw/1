<template>
  <div class="min-h-screen flex flex-col bg-dark-bg">
    <!-- 顶部导航栏 -->
    <header class="bg-sub-bg border-b border-gray-800 sticky top-0 z-50 transition-all duration-300" :class="{ 'shadow-lg': scrolled }">
      <div class="container mx-auto px-4 py-3 flex items-center justify-between">
        <!-- Logo -->
        <div class="flex items-center">
          <a href="/" class="flex items-center">
            <i class="fa-solid fa-shield-halved text-primary text-2xl mr-2"></i>
            <span class="text-xl font-bold text-white">406 实验平台</span>
          </a>
        </div>

        <!-- 导航菜单 - 桌面版 -->
        <nav class="hidden md:flex items-center space-x-6">
          <router-link to="/tasks" class="text-gray-400 hover:text-white transition-colors">
            <i class="fa-solid fa-list-ul mr-1"></i> {{ $t('navigation.tasks') }}
          </router-link>
          <router-link to="/ranking" class="text-gray-400 hover:text-white transition-colors">
            <i class="fa-solid fa-trophy mr-1"></i> {{ $t('navigation.ranking') }}
          </router-link>
          <router-link to="/teams" class="text-gray-400 hover:text-white transition-colors">
            <i class="fa-solid fa-users mr-1"></i> {{ $t('navigation.teams') }}
          </router-link>
          <router-link to="/challenges" class="text-gray-400 hover:text-white transition-colors">
            <i class="fa-solid fa-puzzle-piece mr-1"></i> {{ $t('navigation.challenges') }}
          </router-link>
        </nav>

        <!-- 用户操作区 -->
        <div class="flex items-center space-x-4">
          <!-- 搜索按钮 -->
          <button class="text-gray-400 hover:text-white transition-colors">
            <i class="fa-solid fa-search"></i>
          </button>
          <!-- 语言选择器 -->
          <div class="relative">
            <button class="text-gray-400 hover:text-white transition-colors flex items-center" @click="languageMenuOpen = !languageMenuOpen">
              <i class="fa-solid fa-globe mr-1"></i>
              {{ locale === 'en' ? 'EN' : '中文' }}
              <i class="fa-solid fa-chevron-down ml-1 text-xs"></i>
            </button>
            <div
              v-show="languageMenuOpen"
              class="absolute right-0 mt-2 w-32 bg-dark-bg border border-gray-700 rounded-lg shadow-lg py-1 z-50"
              @click.away="languageMenuOpen = false"
            >
              <button
                class="w-full text-left px-4 py-2 hover:bg-gray-800 transition-colors"
                @click="setLocale('en')"
              >
                English
              </button>
              <button
                class="w-full text-left px-4 py-2 hover:bg-gray-800 transition-colors"
                @click="setLocale('zh-CN')"
              >
                中文
              </button>
            </div>
          </div>

          <!-- 用户菜单 -->
          <div class="relative">
            <button 
              class="flex items-center space-x-2 text-gray-400 hover:text-white transition-colors" 
              @click="userMenuOpen = !userMenuOpen"
            >
              <img
                src="https://picsum.photos/32/32?random=1"
                alt="User avatar"
                class="w-8 h-8 rounded-full object-cover border-2 border-gray-700"
              />
              <span class="hidden md:inline-block font-medium">{{ user.username || 'Guest' }}</span>
              <i class="fa-solid fa-chevron-down text-xs"></i>
            </button>

            <div
              v-show="userMenuOpen"
              class="absolute right-0 mt-2 w-48 bg-dark-bg border border-gray-700 rounded-lg shadow-lg py-1 z-50"
              @click.away="userMenuOpen = false"
            >
              <router-link
                to="/profile"
                class="block px-4 py-2 hover:bg-gray-800 transition-colors"
                v-if="isAuthenticated"
              >
                <i class="fa-solid fa-user mr-2"></i> {{ $t('navigation.profile') }}
              </router-link>
              <router-link
                to="/settings"
                class="block px-4 py-2 hover:bg-gray-800 transition-colors"
                v-if="isAuthenticated"
              >
                <i class="fa-solid fa-cog mr-2"></i> {{ $t('navigation.settings') }}
              </router-link>
              <div class="border-t border-gray-700 my-1"></div>
              <button
                v-if="isAuthenticated"
                class="w-full text-left px-4 py-2 hover:bg-gray-800 transition-colors text-red-400"
                @click="logout"
              >
                <i class="fa-solid fa-sign-out-alt mr-2"></i> {{ $t('navigation.logout') }}
              </button>
              <router-link
                v-else
                to="/login"
                class="block px-4 py-2 hover:bg-gray-800 transition-colors"
              >
                <i class="fa-solid fa-sign-in-alt mr-2"></i> {{ $t('navigation.login') }}
              </router-link>
            </div>
          </div>

          <!-- 移动端菜单按钮 -->
          <button class="md:hidden text-gray-400 hover:text-white transition-colors" @click="mobileMenuOpen = !mobileMenuOpen">
            <i class="fa-solid fa-bars"></i>
          </button>
        </div>
      </div>

      <!-- 移动端导航菜单 -->
      <div
        v-show="mobileMenuOpen"
        class="md:hidden bg-sub-bg border-t border-gray-800 transition-all duration-300"
      >
        <div class="container mx-auto px-4 py-3 flex flex-col space-y-3">
          <router-link to="/tasks" class="py-2 text-gray-400 hover:text-white transition-colors">
            <i class="fa-solid fa-list-ul mr-1"></i> {{ $t('navigation.tasks') }}
          </router-link>
          <router-link to="/ranking" class="py-2 text-gray-400 hover:text-white transition-colors">
            <i class="fa-solid fa-trophy mr-1"></i> {{ $t('navigation.ranking') }}
          </router-link>
          <router-link to="/teams" class="py-2 text-gray-400 hover:text-white transition-colors">
            <i class="fa-solid fa-users mr-1"></i> {{ $t('navigation.teams') }}
          </router-link>
          <router-link to="/challenges" class="py-2 text-gray-400 hover:text-white transition-colors">
            <i class="fa-solid fa-puzzle-piece mr-1"></i> {{ $t('navigation.challenges') }}
          </router-link>
        </div>
      </div>
    </header>

    <!-- 主要内容区 -->
    <main class="flex-grow container mx-auto px-4 py-6">
      <NotificationContainer />
      <router-view />
    </main>

    <!-- 页脚 -->
    <footer class="bg-sub-bg border-t border-gray-800 py-6">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div>
            <div class="flex items-center mb-4">
              <i class="fa-solid fa-shield-halved text-primary text-2xl mr-2"></i>
              <span class="text-xl font-bold text-white">406 实验平台</span>
            </div>
            <p class="text-gray-400 text-sm">
              {{ $t('footer.description') }}
            </p>
          </div>

          <div>
            <h3 class="text-white font-medium mb-4">{{ $t('footer.quickLinks') }}</h3>
            <ul class="space-y-2">
              <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.about') }}</a></li>
              <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.rules') }}</a></li>
              <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.faq') }}</a></li>
              <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.contact') }}</a></li>
            </ul>
          </div>

          <div>
            <h3 class="text-white font-medium mb-4">{{ $t('footer.resources') }}</h3>
            <ul class="space-y-2">
              <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.tutorials') }}</a></li>
              <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.tools') }}</a></li>
              <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.documentation') }}</a></li>
              <li><a href="#" class="text-gray-400 hover:text-primary transition-colors">{{ $t('footer.blog') }}</a></li>
            </ul>
          </div>

          <div>
            <h3 class="text-white font-medium mb-4">{{ $t('footer.followUs') }}</h3>
            <div class="flex space-x-4 mb-4">
              <a href="#" class="w-8 h-8 rounded-full bg-dark-bg flex items-center justify-center text-gray-400 hover:bg-primary hover:text-white transition-all">
                <i class="fa-brands fa-twitter"></i>
              </a>
              <a href="#" class="w-8 h-8 rounded-full bg-dark-bg flex items-center justify-center text-gray-400 hover:bg-primary hover:text-white transition-all">
                <i class="fa-brands fa-github"></i>
              </a>
              <a href="#" class="w-8 h-8 rounded-full bg-dark-bg flex items-center justify-center text-gray-400 hover:bg-primary hover:text-white transition-all">
                <i class="fa-brands fa-discord"></i>
              </a>
              <a href="#" class="w-8 h-8 rounded-full bg-dark-bg flex items-center justify-center text-gray-400 hover:bg-primary hover:text-white transition-all">
                <i class="fa-brands fa-telegram"></i>
              </a>
            </div>
            <p class="text-gray-400 text-sm">
              {{ $t('footer.subscribe') }}
            </p>
            <div class="flex flex-col space-y-2">
              <button
                class="bg-transparent text-gray-700 rounded-lg px-4 py-2 flex items-center justify-center space-x-2"  >
                <i class="fa-solid fa-paper-plane text-gray-600"></i>
                <span>3034853320@qq.com</span>
               </button>
            </div>
          </div>
        </div>

        <div class="border-t border-gray-800 mt-8 pt-6 flex flex-col md:flex-row md:items-center md:justify-between">
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
  </div>
</template>

<script setup>
import { ref, computed, onUnmounted } from 'vue';
import { useUserStore } from './store/modules/user';
import { useI18n } from 'vue-i18n';
import NotificationContainer from './components/Common/NotificationContainer.vue';

// 响应式状态定义
const scrolled = ref(false); // 导航栏滚动状态
const languageMenuOpen = ref(false); // 语言菜单展开状态
const userMenuOpen = ref(false); // 用户菜单展开状态
const mobileMenuOpen = ref(false); // 移动端菜单展开状态

// 国际化配置
const { locale, t } = useI18n();

// 用户存储状态（处理 null 初始值）
const userStore = useUserStore();
const user = computed(() => userStore.user || {}); // 初始化为空对象，避免 null
const isAuthenticated = computed(() => userStore.isAuthenticated);

// 滚动事件监听（监听窗口滚动，非 header）
const handleScroll = () => {
  scrolled.value = window.scrollY > 0;
};
window.addEventListener('scroll', handleScroll);

// 清理滚动事件监听（防止内存泄漏）
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});

// 语言切换方法（使用国际化 API）
const setLocale = (lang) => {
  locale.value = lang;
  languageMenuOpen.value = false;
};

// 退出登录方法（示例，需根据实际逻辑实现）
const logout = () => {
  userStore.logout(); // 假设 userStore 有 logout action
  userMenuOpen.value = false;
};
</script>

<style scoped>
.tooltip {
  position: relative;
}

.tooltip::after {
  content: attr(title);
  position: absolute;
  bottom: 100%;
  left: 50%;
  transform: translateX(-50%);
  padding: 4px 8px;
  background-color: rgba(0, 0, 0, 0.8);
  color: white;
  border-radius: 4px;
  font-size: 12px;
  opacity: 0;
  pointer-events: none;
  transition: opacity 0.2s;
  white-space: nowrap;
  z-index: 50;
}

.tooltip:hover::after {
  opacity: 1;
}
</style>
