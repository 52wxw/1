<template>
  <header class="bg-sub-bg border-b border-gray-800 sticky top-0 z-50 transition-all duration-300" :class="{ 'shadow-lg': scrolled }">
    <div class="container mx-auto px-4 py-3 flex items-center justify-between">
      <!-- Logo -->
      <div class="flex items-center">
        <a href="/" class="flex items-center">
          <i class="fa-solid fa-shield-halved text-primary text-2xl mr-2"></i>
          <span class="text-xl font-bold text-white">CTF Platform</span>
        </a>
      </div>
      
      <!-- 桌面导航 -->
      <nav class="hidden md:flex items-center space-x-6">
        <router-link 
          to="/tasks" 
          class="text-gray-400 hover:text-white transition-colors py-2 border-b-2 border-transparent hover:border-primary/50"
          :class="{ 'border-primary text-white': $route.name === 'tasks' }"
        >
          <i class="fa-solid fa-list-ul mr-1"></i> {{ $t('navigation.tasks') }}
        </router-link>
        <router-link 
          to="/ranking" 
          class="text-gray-400 hover:text-white transition-colors py-2 border-b-2 border-transparent hover:border-primary/50"
          :class="{ 'border-primary text-white': $route.name === 'ranking' }"
        >
          <i class="fa-solid fa-trophy mr-1"></i> {{ $t('navigation.ranking') }}
        </router-link>
        <router-link 
          to="/teams" 
          class="text-gray-400 hover:text-white transition-colors py-2 border-b-2 border-transparent hover:border-primary/50"
          :class="{ 'border-primary text-white': $route.name === 'teams' }"
        >
          <i class="fa-solid fa-users mr-1"></i> {{ $t('navigation.teams') }}
        </router-link>
        <router-link 
          to="/challenges" 
          class="text-gray-400 hover:text-white transition-colors py-2 border-b-2 border-transparent hover:border-primary/50"
          :class="{ 'border-primary text-white': $route.name === 'challenges' }"
        >
          <i class="fa-solid fa-puzzle-piece mr-1"></i> {{ $t('navigation.challenges') }}
        </router-link>
      </nav>
      
      <!-- 用户操作区 -->
      <div class="flex items-center space-x-4">
        <!-- 搜索按钮 -->
        <button class="text-gray-400 hover:text-white transition-colors p-2 rounded-full hover:bg-gray-800">
          <i class="fa-solid fa-search"></i>
        </button>
        
        <!-- 通知按钮 -->
        <button class="text-gray-400 hover:text-white transition-colors p-2 rounded-full hover:bg-gray-800 relative">
          <i class="fa-solid fa-bell"></i>
          <span class="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-4 h-4 flex items-center justify-center">3</span>
        </button>
        
        <!-- 语言选择器 -->
        <div class="relative">
          <button class="text-gray-400 hover:text-white transition-colors flex items-center px-2 py-1 rounded-full hover:bg-gray-800" @click="toggleLanguageMenu">
            <i class="fa-solid fa-globe mr-1"></i>
            {{ currentLocale === 'en' ? 'EN' : '中文' }}
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
          <button class="flex items-center space-x-2 text-gray-400 hover:text-white transition-colors px-2 py-1 rounded-full hover:bg-gray-800" @click="toggleUserMenu">
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
            >
              <i class="fa-solid fa-user mr-2"></i> {{ $t('navigation.profile') }}
            </router-link>
            <router-link 
              to="/settings" 
              class="block px-4 py-2 hover:bg-gray-800 transition-colors"
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
              to="/login" 
              class="block px-4 py-2 hover:bg-gray-800 transition-colors"
              v-else
            >
              <i class="fa-solid fa-sign-in-alt mr-2"></i> {{ $t('navigation.login') }}
            </router-link>
          </div>
        </div>
        
        <!-- 移动端菜单按钮 -->
        <button class="md:hidden text-gray-400 hover:text-white transition-colors p-2 rounded-full hover:bg-gray-800" @click="toggleMobileMenu">
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
        <router-link 
          to="/tasks" 
          class="py-2 text-gray-400 hover:text-white transition-colors border-l-4 border-transparent hover:border-primary/50 pl-3"
          :class="{ 'border-primary text-white': $route.name === 'tasks' }"
        >
          <i class="fa-solid fa-list-ul mr-1"></i> {{ $t('navigation.tasks') }}
        </router-link>
        <router-link 
          to="/ranking" 
          class="py-2 text-gray-400 hover:text-white transition-colors border-l-4 border-transparent hover:border-primary/50 pl-3"
          :class="{ 'border-primary text-white': $route.name === 'ranking' }"
        >
          <i class="fa-solid fa-trophy mr-1"></i> {{ $t('navigation.ranking') }}
        </router-link>
        <router-link 
          to="/teams" 
          class="py-2 text-gray-400 hover:text-white transition-colors border-l-4 border-transparent hover:border-primary/50 pl-3"
          :class="{ 'border-primary text-white': $route.name === 'teams' }"
        >
          <i class="fa-solid fa-users mr-1"></i> {{ $t('navigation.teams') }}
        </router-link>
        <router-link 
          to="/challenges" 
          class="py-2 text-gray-400 hover:text-white transition-colors border-l-4 border-transparent hover:border-primary/50 pl-3"
          :class="{ 'border-primary text-white': $route.name === 'challenges' }"
        >
          <i class="fa-solid fa-puzzle-piece mr-1"></i> {{ $t('navigation.challenges') }}
        </router-link>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '@/store/modules/user';

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

// 菜单状态
const mobileMenuOpen = ref(false);
const userMenuOpen = ref(false);
const languageMenuOpen = ref(false);
const scrolled = ref(false);

// 当前用户和认证状态
const user = computed(() => userStore.user);
const isAuthenticated = computed(() => userStore.isAuthenticated);

// 当前语言
const currentLocale = ref(i18n.global.locale.value);

// 切换移动端菜单
const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value;
  userMenuOpen.value = false;
  languageMenuOpen.value = false;
};

// 切换用户菜单
const toggleUserMenu = () => {
  userMenuOpen.value = !userMenuOpen.value;
  mobileMenuOpen.value = false;
  languageMenuOpen.value = false;
};

// 切换语言菜单
const toggleLanguageMenu = () => {
  languageMenuOpen.value = !languageMenuOpen.value;
  mobileMenuOpen.value = false;
  userMenuOpen.value = false;
};

// 设置语言
const setLocale = (locale) => {
  i18n.global.locale.value = locale;
  localStorage.setItem('locale', locale);
  currentLocale.value = locale;
  languageMenuOpen.value = false;
};

// 登出
const logout = () => {
  userStore.logout();
  router.push('/login');
};

// 监听滚动事件
onMounted(() => {
  window.addEventListener('scroll', handleScroll);
});

// 清理滚动事件
onMounted(() => {
  return () => {
    window.removeEventListener('scroll', handleScroll);
  };
});

// 处理滚动
const handleScroll = () => {
  if (window.scrollY > 10) {
    scrolled.value = true;
  } else {
    scrolled.value = false;
  }
};

// 监听路由变化，关闭所有菜单
watch(route, () => {
  mobileMenuOpen.value = false;
  userMenuOpen.value = false;
  languageMenuOpen.value = false;
});
</script>

<style scoped>
/* 导航栏样式 */
header {
  background-color: rgba(15, 23, 42, 0.95);
}

/* 滚动效果 */
.shadow-lg {
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.3);
}
</style>
