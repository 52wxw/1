<template>
  <div class="bg-dark-bg min-h-screen pb-12">
    <!-- 其他代码... -->

    <!-- 设置 -->
    <div v-if="selectedTab === 'settings'">
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- 个人信息设置 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6 lg:col-span-2">
          <!-- 表单内容... -->
        </div>

        <!-- 安全设置 -->
        <div class="bg-sub-bg border border-gray-800 rounded-xl p-6">
          <h2 class="text-xl font-semibold text-white mb-6">
            {{ $t('profile.securitySettings') }}
          </h2>

          <!-- 更改密码 -->
          <div class="mb-8">
            <!-- 密码表单内容... -->
          </div>

          <!-- 双因素认证 -->
          <div>
            <h3 class="text-lg font-medium text-white mb-4">
              {{ $t('profile.twoFactorAuth') }}
            </h3>

            <div class="flex items-center justify-between p-4 bg-dark-bg border border-gray-700 rounded-lg">
              <div>
                <h4 class="font-medium text-white">
                  {{ $t('profile.enable2FA') }}
                </h4>
                <p class="text-sm text-gray-400 mt-1">
                  {{ $t('profile.2FADescription') }}
                </p>
              </div>
              <!-- 修正：补充完整的 label 标签闭合 -->
              <label class="relative inline-flex items-center cursor-pointer">
                <input type="checkbox" v-model="twoFactorEnabled" class="sr-only peer">
                <div class="w-11 h-6 bg-gray-700 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary"></div>
              </label> <!-- 补充结束标签 -->
            </div>

            <p class="text-xs text-gray-400 mt-3">
              {{ $t('profile.2FAWarning') }}
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- 其他代码... -->
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/store/modules/user';

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

// 状态
const loading = ref(true);
const selectedTab = ref('overview');
const user = ref({});
const isSelf = computed(() => {
  return userStore.user.id === route.params.id;
});

// 获取用户资料
const fetchUser = async () => {
  try {
    loading.value = true;
    await userStore.fetchUser(route.params.id);
    user.value = userStore.user;
  } catch (error) {
    router.push('/404');
  } finally {
    loading.value = false;
  }
};

// 监听路由变化
watch(
  () => route.params.id,
  () => {
    fetchUser();
  },
  { immediate: true }
);

// 表单数据
const profileForm = reactive({
  username: '',
  email: '',
  location: '',
  website: '',
  organization: '',
  position: '',
  about: '',
  github: '',
  twitter: '',
  linkedin: '',
  discord: '',
});

const passwordForm = reactive({
  current_password: '',
  new_password: '',
  confirm_password: '',
});

const twoFactorEnabled = ref(false);

// 编辑资料
const editProfile = () => {
  router.push(`/profile/${userStore.user.id}/edit`);
};

// 保存资料
const saveProfile = async () => {
  // 保存逻辑...
};

// 更改密码
const changePassword = async () => {
  // 密码更改逻辑...
};

// 前往任务列表
const goToTasks = () => {
  router.push('/tasks');
};

// 计算属性：过滤解决的任务
const solveCategories = [
  { id: 'web', name: 'web' },
  { id: 'crypto', name: 'crypto' },
  // 其他分类...
];

const solveFilterCategory = ref('all');
const solveFilterDifficulty = ref('all');

const filteredSolves = computed(() => {
  // 过滤逻辑...
  return user.value.solves || [];
});

// 工具函数：获取分类颜色
const categoryColor = (category) => {
  // 颜色映射逻辑...
  return '#7C3AED';
};

const solveCategoryClass = (category) => {
  // 分类样式逻辑...
  return `bg-${category}-bg/20 text-${category}-text`;
};

const solveDifficultyClass = (difficulty) => {
  // 难度样式逻辑...
  return `bg-${difficulty}-bg/20 text-${difficulty}-text`;
};
</script>

<style scoped>
/* 样式代码... */
</style>
