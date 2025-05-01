<template>
  <aside 
    class="fixed top-0 left-0 h-full w-64 bg-sub-bg border-r border-gray-800 z-40 transform -translate-x-full md:translate-x-0 transition-transform duration-300 ease-in-out"
    :class="{ '-translate-x-full': !sidebarOpen }"
  >
    <!-- 侧边栏内容 -->
    <div class="h-full flex flex-col">
      <!-- 侧边栏头部 -->
      <div class="p-4 border-b border-gray-800">
        <div class="flex items-center">
          <i class="fa-solid fa-shield-halved text-primary text-2xl mr-2"></i>
          <span class="text-xl font-bold text-white">CTF Platform</span>
        </div>
      </div>
      
      <!-- 侧边栏导航 -->
      <nav class="flex-1 overflow-y-auto py-4">
        <div class="px-4 mb-4">
          <h3 class="text-xs font-semibold text-gray-400 uppercase tracking-wider mb-2">
            {{ $t('sidebar.main') }}
          </h3>
          <ul class="space-y-1">
            <router-link 
              to="/" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'home' }"
            >
              <i class="fa-solid fa-home w-5 h-5 mr-3"></i>
              {{ $t('sidebar.dashboard') }}
            </router-link>
            <router-link 
              to="/tasks" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'tasks' }"
            >
              <i class="fa-solid fa-list-ul w-5 h-5 mr-3"></i>
              {{ $t('sidebar.tasks') }}
            </router-link>
            <router-link 
              to="/ranking" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'ranking' }"
            >
              <i class="fa-solid fa-trophy w-5 h-5 mr-3"></i>
              {{ $t('sidebar.ranking') }}
            </router-link>
            <router-link 
              to="/teams" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'teams' }"
            >
              <i class="fa-solid fa-users w-5 h-5 mr-3"></i>
              {{ $t('sidebar.teams') }}
            </router-link>
          </ul>
        </div>
        
        <div class="px-4 mb-4">
          <h3 class="text-xs font-semibold text-gray-400 uppercase tracking-wider mb-2">
            {{ $t('sidebar.categories') }}
          </h3>
          <ul class="space-y-1">
            <router-link 
              to="/tasks?category=web" 
              class="flex items-center justify-between px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
            >
              <span class="flex items-center">
                <i class="fa-solid fa-code w-5 h-5 mr-3 text-web-text"></i>
                {{ $t('sidebar.web') }}
              </span>
              <span class="bg-web-bg/20 text-web-text text-xs px-2 py-0.5 rounded-full">24</span>
            </router-link>
            <router-link 
              to="/tasks?category=crypto" 
              class="flex items-center justify-between px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
            >
              <span class="flex items-center">
                <i class="fa-solid fa-lock w-5 h-5 mr-3 text-crypto-text"></i>
                {{ $t('sidebar.crypto') }}
              </span>
              <span class="bg-crypto-bg/20 text-crypto-text text-xs px-2 py-0.5 rounded-full">18</span>
            </router-link>
            <router-link 
              to="/tasks?category=pwn" 
              class="flex items-center justify-between px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
            >
              <span class="flex items-center">
                <i class="fa-solid fa-bomb w-5 h-5 mr-3 text-pwn-text"></i>
                {{ $t('sidebar.pwn') }}
              </span>
              <span class="bg-pwn-bg/20 text-pwn-text text-xs px-2 py-0.5 rounded-full">12</span>
            </router-link>
            <router-link 
              to="/tasks?category=reversing" 
              class="flex items-center justify-between px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
            >
              <span class="flex items-center">
                <i class="fa-solid fa-undo w-5 h-5 mr-3 text-reversing-text"></i>
                {{ $t('sidebar.reversing') }}
              </span>
              <span class="bg-reversing-bg/20 text-reversing-text text-xs px-2 py-0.5 rounded-full">9</span>
            </router-link>
            <router-link 
              to="/tasks?category=forensics" 
              class="flex items-center justify-between px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
            >
              <span class="flex items-center">
                <i class="fa-solid fa-magnifying-glass w-5 h-5 mr-3 text-forensics-text"></i>
                {{ $t('sidebar.forensics') }}
              </span>
              <span class="bg-forensics-bg/20 text-forensics-text text-xs px-2 py-0.5 rounded-full">7</span>
            </router-link>
            <router-link 
              to="/tasks?category=misc" 
              class="flex items-center justify-between px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
            >
              <span class="flex items-center">
                <i class="fa-solid fa-ellipsis-h w-5 h-5 mr-3 text-misc-text"></i>
                {{ $t('sidebar.misc') }}
              </span>
              <span class="bg-misc-bg/20 text-misc-text text-xs px-2 py-0.5 rounded-full">15</span>
            </router-link>
          </ul>
        </div>
        
        <div class="px-4 mb-4">
          <h3 class="text-xs font-semibold text-gray-400 uppercase tracking-wider mb-2">
            {{ $t('sidebar.account') }}
          </h3>
          <ul class="space-y-1">
            <router-link 
              to="/profile" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'profile' }"
            >
              <i class="fa-solid fa-user w-5 h-5 mr-3"></i>
              {{ $t('sidebar.profile') }}
            </router-link>
            <router-link 
              to="/settings" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'settings' }"
            >
              <i class="fa-solid fa-cog w-5 h-5 mr-3"></i>
              {{ $t('sidebar.settings') }}
            </router-link>
            <router-link 
              to="/notifications" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'notifications' }"
            >
              <i class="fa-solid fa-bell w-5 h-5 mr-3"></i>
              {{ $t('sidebar.notifications') }}
              <span class="ml-auto bg-red-500 text-white text-xs px-2 py-0.5 rounded-full">3</span>
            </router-link>
          </ul>
        </div>
        
        <!-- 管理员菜单 -->
        <div class="px-4" v-if="isAdmin">
          <h3 class="text-xs font-semibold text-gray-400 uppercase tracking-wider mb-2">
            {{ $t('sidebar.admin') }}
          </h3>
          <ul class="space-y-1">
            <router-link 
              to="/admin" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'admin-dashboard' }"
            >
              <i class="fa-solid fa-tachometer-alt w-5 h-5 mr-3"></i>
              {{ $t('sidebar.dashboard') }}
            </router-link>
            <router-link 
              to="/admin/tasks" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'admin-tasks' }"
            >
              <i class="fa-solid fa-list w-5 h-5 mr-3"></i>
              {{ $t('sidebar.manageTasks') }}
            </router-link>
            <router-link 
              to="/admin/users" 
              class="flex items-center px-3 py-2 text-sm font-medium rounded-lg text-gray-400 hover:bg-gray-700 hover:text-white transition-colors"
              :class="{ 'bg-primary/10 text-primary': $route.name === 'admin-users' }"
            >
              <i class="fa-solid fa-users-cog w-5 h-5 mr-3"></i>
              {{ $t('sidebar.manageUsers') }}
            </router-link>
          </ul>
        </div>
      </nav>
      
      <!-- 侧边栏底部 -->
      <div class="p-4 border-t border-gray-800">
        <div class="flex items-center">
          <img 
            src="https://picsum.photos/40/40?random=1" 
            alt="User avatar" 
            class="w-10 h-10 rounded-full object-cover border-2 border-gray-700"
          />
          <div class="ml-3">
            <p class="text-sm font-medium text-white truncate">{{ user.username || 'Guest' }}</p>
            <p class="text-xs text-gray-400 truncate">
              {{ user.score || 0 }} {{ $t('sidebar.points') }}
            </p>
          </div>
          <button class="ml-auto text-gray-400 hover:text-white transition-colors" @click="logout">
            <i class="fa-solid fa-sign-out-alt"></i>
          </button>
        </div>
      </div>
    </div>
  </aside>
  
  <!-- 侧边栏遮罩层 -->
  <div 
    v-if="sidebarOpen && !isDesktop" 
    class="fixed inset-0 bg-black/50 z-30 transition-opacity duration-300"
    @click="sidebarOpen = false"
  ></div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '@/store/modules/user';

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

// 侧边栏状态
const sidebarOpen = ref(false);
const isDesktop = computed(() => window.innerWidth >= 768);

// 当前用户和认证状态
const user = computed(() => userStore.user);
const isAuthenticated = computed(() => userStore.isAuthenticated);
const isAdmin = computed(() => userStore.isAdmin);

// 切换侧边栏
const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
};

// 登出
const logout = () => {
  userStore.logout();
  router.push('/login');
  sidebarOpen.value = false;
};

// 监听窗口大小变化
onMounted(() => {
  window.addEventListener('resize', handleResize);
});

// 清理窗口大小变化监听
onMounted(() => {
  return () => {
    window.removeEventListener('resize', handleResize);
  };
});

// 处理窗口大小变化
const handleResize = () => {
  if (isDesktop.value) {
    sidebarOpen.value = true;
  } else {
    sidebarOpen.value = false;
  }
};

// 监听路由变化，关闭移动端侧边栏
watch(route, () => {
  if (!isDesktop.value) {
    sidebarOpen.value = false;
  }
});

// 初始化
onMounted(() => {
  sidebarOpen.value = isDesktop.value;
});
</script>

<style scoped>
/* 侧边栏样式 */
aside {
  background-color: rgba(15, 23, 42, 0.95);
}

/* 滚动条样式 */
aside::-webkit-scrollbar {
  width: 4px;
}

aside::-webkit-scrollbar-track {
  background: transparent;
}

aside::-webkit-scrollbar-thumb {
  background-color: rgba(100, 116, 139, 0.5);
  border-radius: 20px;
}
</style>
