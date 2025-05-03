<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-dark-bg to-sub-bg p-4">
    <div class="w-full max-w-md">
      <!-- 登录卡片 -->
      <div class="bg-dark-bg border border-gray-800 rounded-xl shadow-2xl p-6 md:p-8">
        <div class="text-center mb-8">
          <div class="flex items-center justify-center mb-4">
            <i class="fa-solid fa-shield-halved text-primary text-3xl mr-2"></i>
            <span class="text-2xl font-bold text-white">406实验平台</span>
          </div>
          <h2 class="text-xl font-semibold text-white">{{ $t('pages.login.title') }}</h2>
          <p class="text-gray-400 mt-2">{{ $t('pages.login.subtitle') }}</p>
        </div>

        <!-- 登录表单 -->
        <form @submit.prevent="login">
          <!-- 用户名或邮箱 -->
          <div class="mb-4">
            <label for="email" class="block text-sm font-medium text-gray-400 mb-1">
              {{ $t('pages.login.usernameOrEmail') }}
            </label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <i class="fa-solid fa-user text-gray-500"></i>
              </div>
              <input
                type="text"
                id="email"
                v-model="form.username"
                class="bg-sub-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                placeholder="Username or Email"
                required
              />
            </div>
            <p v-if="errors.username" class="text-red-500 text-xs mt-1">
              {{ errors.username }}
            </p>
          </div>

          <!-- 密码 -->
          <div class="mb-6">
            <div class="flex items-center justify-between mb-1">
              <label for="password" class="block text-sm font-medium text-gray-400">
                {{ $t('pages.login.password') }}
              </label>
              <a href="#" class="text-sm text-primary hover:text-primary/80 transition-colors">
                {{ $t('pages.login.forgotPassword') }}
              </a>
            </div>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <i class="fa-solid fa-lock text-gray-500"></i>
              </div>
              <input
                :type="showPassword ? 'text' : 'password'"
                id="password"
                v-model="form.password"
                class="bg-sub-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-10 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                placeholder="Password"
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

          <!-- 登录按钮 -->
          <div class="mb-4">
            <button
              type="submit"
              class="w-full bg-primary hover:bg-primary/90 text-white font-medium rounded-lg py-2.5 transition-colors flex items-center justify-center space-x-2"
              :disabled="loading"
            >
              <span>{{ $t('pages.login.loginButton') }}</span>
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

          <!-- 注册链接 -->
          <p class="text-sm text-gray-400 text-center">
            {{ $t('pages.login.noAccount') }}
            <router-link to="/register" class="text-primary hover:text-primary/80 font-medium transition-colors">
              {{ $t('pages.login.register') }}
            </router-link>
          </p>
        </form>
      </div>

      <!-- 底部链接 -->
      <div class="mt-6 text-center">
        <a href="#" class="text-gray-400 hover:text-white transition-colors text-sm">
          {{ $t('pages.login.privacyPolicy') }}
        </a>
        <span class="mx-2 text-gray-600">|</span>
        <a href="#" class="text-gray-400 hover:text-white transition-colors text-sm">
          {{ $t('pages.login.termsOfService') }}
        </a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/modules/user';
import { useI18n } from 'vue-i18n';

const router = useRouter();
const userStore = useUserStore();
const { t } = useI18n();

// 表单数据
const form = reactive({
  username: '',
  password: ''
});

// 错误信息
const errors = reactive({
  username: '',
  password: ''
});

// 状态
const loading = ref(false);
const errorMessage = ref('');
const showPassword = ref(false);

// 登录方法
const login = async () => {
  // 重置错误
  errorMessage.value = '';
  errors.username = '';
  errors.password = '';

  // 简单验证
  if (!form.username) {
    errors.username = t('pages.login.usernameRequired');
  }

  if (!form.password) {
    errors.password = t('pages.login.passwordRequired');
  }

  if (errors.username || errors.password) {
    return;
  }

  loading.value = true;

  try {
    console.log('发送登录请求:', form);
    await userStore.login(form);
    
    console.log('登录成功，用户信息:', userStore.user);
    router.push('/');

    // 显示成功通知
    window.$notify.success(
      t('pages.login.successTitle'),
      t('pages.login.successMessage')
    );
  } catch (error) {
    console.error('登录失败:', error);
    
    // 处理错误响应
    if (error.response) {
      console.error('响应数据:', error.response.data);
      console.error('响应状态:', error.response.status);
      console.error('响应头:', error.response.headers);
    }
    
    errorMessage.value = error.response?.data?.message || t('pages.login.errorMessage');
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
