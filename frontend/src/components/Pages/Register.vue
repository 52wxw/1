<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-dark-bg to-sub-bg p-4">
    <div class="w-full max-w-md">
      <!-- 注册卡片 -->
      <div class="bg-dark-bg border border-gray-800 rounded-xl shadow-2xl p-6 md:p-8">
        <div class="text-center mb-8">
          <div class="flex items-center justify-center mb-4">
            <i class="fa-solid fa-shield-halved text-primary text-3xl mr-2"></i>
            <span class="text-2xl font-bold text-white">CTF Platform</span>
          </div>
          <h2 class="text-xl font-semibold text-white">{{ $t('register.title') }}</h2>
          <p class="text-gray-400 mt-2">{{ $t('register.subtitle') }}</p>
        </div>
        
        <!-- 注册表单 -->
        <form @submit.prevent="register">
          <!-- 用户名 -->
          <div class="mb-4">
            <label for="username" class="block text-sm font-medium text-gray-400 mb-1">
              {{ $t('register.username') }}
            </label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <i class="fa-solid fa-user text-gray-500"></i>
              </div>
              <input
                type="text"
                id="username"
                v-model="form.username"
                class="bg-sub-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                placeholder="{{ $t('register.usernamePlaceholder') }}"
                required
              />
            </div>
            <p v-if="errors.username" class="text-red-500 text-xs mt-1">
              {{ errors.username }}
            </p>
          </div>
          
          <!-- 邮箱 -->
          <div class="mb-4">
            <label for="email" class="block text-sm font-medium text-gray-400 mb-1">
              {{ $t('register.email') }}
            </label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <i class="fa-solid fa-envelope text-gray-500"></i>
              </div>
              <input
                type="email"
                id="email"
                v-model="form.email"
                class="bg-sub-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                placeholder="{{ $t('register.emailPlaceholder') }}"
                required
              />
            </div>
            <p v-if="errors.email" class="text-red-500 text-xs mt-1">
              {{ errors.email }}
            </p>
          </div>
          
          <!-- 密码 -->
          <div class="mb-4">
            <label for="password" class="block text-sm font-medium text-gray-400 mb-1">
              {{ $t('register.password') }}
            </label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <i class="fa-solid fa-lock text-gray-500"></i>
              </div>
              <input
                :type="showPassword ? 'text' : 'password'"
                id="password"
                v-model="form.password"
                class="bg-sub-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-10 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                placeholder="{{ $t('register.passwordPlaceholder') }}"
                required
              />
              <button
                type="button"
                class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-500 hover:text-white transition-colors"
                @click="showPassword = !showPassword"
              >
                <i :class="showPassword ? 'fa-eye' : 'fa-eye-slash'"></i>
              </button>
            </div>
            <p v-if="errors.password" class="text-red-500 text-xs mt-1">
              {{ errors.password }}
            </p>
          </div>
          
          <!-- 确认密码 -->
          <div class="mb-6">
            <label for="password_confirmation" class="block text-sm font-medium text-gray-400 mb-1">
              {{ $t('register.confirmPassword') }}
            </label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <i class="fa-solid fa-lock text-gray-500"></i>
              </div>
              <input
                :type="showPassword ? 'text' : 'password'"
                id="password_confirmation"
                v-model="form.password_confirmation"
                class="bg-sub-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-10 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                placeholder="{{ $t('register.confirmPasswordPlaceholder') }}"
                required
              />
              <button
                type="button"
                class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-500 hover:text-white transition-colors"
                @click="showPassword = !showPassword"
              >
                <i :class="showPassword ? 'fa-eye' : 'fa-eye-slash'"></i>
              </button>
            </div>
            <p v-if="errors.password_confirmation" class="text-red-500 text-xs mt-1">
              {{ errors.password_confirmation }}
            </p>
          </div>
          
          <!-- 注册按钮 -->
          <div class="mb-4">
            <button
              type="submit"
              class="w-full bg-primary hover:bg-primary/90 text-white font-medium rounded-lg py-2.5 transition-colors flex items-center justify-center space-x-2"
              :disabled="loading"
            >
              <span>{{ $t('register.registerButton') }}</span>
              <div v-if="loading" class="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
            </button>
          </div>
          
          <!-- 错误消息 -->
          <div v-if="errorMessage" class="bg-red-500/10 border border-red-500/30 text-red-400 rounded-lg p-3 mb-4 text-sm">
            <div class="flex items-center">
              <i class="fa-solid fa-exclamation-circle mr-2"></i>
              <span>{{ errorMessage }}</span>
            </div>
          </div>
          
          <!-- 登录链接 -->
          <p class="text-sm text-gray-400 text-center">
            {{ $t('register.haveAccount') }}
            <router-link to="/login" class="text-primary hover:text-primary/80 font-medium transition-colors">
              {{ $t('register.login') }}
            </router-link>
          </p>
        </form>
      </div>
      
      <!-- 底部链接 -->
      <div class="mt-6 text-center">
        <a href="#" class="text-gray-400 hover:text-white transition-colors text-sm">
          {{ $t('register.privacyPolicy') }}
        </a>
        <span class="mx-2 text-gray-600">|</span>
        <a href="#" class="text-gray-400 hover:text-white transition-colors text-sm">
          {{ $t('register.termsOfService') }}
        </a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/modules/user';

const router = useRouter();
const userStore = useUserStore();

// 表单数据
const form = reactive({
  username: '',
  email: '',
  password: '',
  password_confirmation: ''
});

// 错误信息
const errors = reactive({
  username: '',
  email: '',
  password: '',
  password_confirmation: ''
});

// 状态
const loading = ref(false);
const errorMessage = ref('');
const showPassword = ref(false);

// 注册方法
const register = async () => {
  // 重置错误
  errorMessage.value = '';
  errors.username = '';
  errors.email = '';
  errors.password = '';
  errors.password_confirmation = '';
  
  // 简单验证
  if (!form.username) {
    errors.username = $t('register.usernameRequired');
  }
  
  if (!form.email) {
    errors.email = $t('register.emailRequired');
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = $t('register.emailInvalid');
  }
  
  if (!form.password) {
    errors.password = $t('register.passwordRequired');
  } else if (form.password.length < 8) {
    errors.password = $t('register.passwordMinLength');
  }
  
  if (!form.password_confirmation) {
    errors.password_confirmation = $t('register.confirmPasswordRequired');
  } else if (form.password !== form.password_confirmation) {
    errors.password_confirmation = $t('register.passwordsDoNotMatch');
  }
  
  if (errors.username || errors.email || errors.password || errors.password_confirmation) {
    return;
  }
  
  loading.value = true;
  
  try {
    // 调用注册API
    await userStore.register(form);
    
    // 注册成功，重定向到登录页
    router.push('/login');
    
    // 显示成功通知
    window.$notify.success(
      $t('register.successTitle'),
      $t('register.successMessage')
    );
  } catch (error) {
    // 显示错误信息
    errorMessage.value = error.response?.data?.message || $t('register.errorMessage');
  } finally {
    loading.value = false;
  }
};
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
