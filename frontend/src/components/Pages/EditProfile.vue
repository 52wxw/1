<template>
  <div class="bg-dark-bg min-h-screen pb-12">
    <!-- 页面标题 -->
    <div class="bg-sub-bg border-b border-gray-800 py-8 px-4">
      <div class="container mx-auto">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-[clamp(1.5rem,3vw,2.5rem)] font-bold text-white">
              {{ $t('editProfile.title') }}
            </h1>
            <p class="text-gray-400 mt-2">
              {{ $t('editProfile.subtitle') }}
            </p>
          </div>
          <button 
            @click="cancelEdit" 
            class="text-gray-400 hover:text-white transition-colors"
          >
            <i class="fa-solid fa-times text-xl"></i>
          </button>
        </div>
      </div>
    </div>
    
    <!-- 编辑表单 -->
    <div class="container mx-auto px-4 mt-8">
      <div class="bg-sub-bg border border-gray-800 rounded-xl p-6 max-w-3xl mx-auto">
        <form @submit.prevent="saveProfile">
          <!-- 头像上传 -->
          <div class="mb-8 text-center">
            <div class="relative inline-block">
              <img 
                :src="formData.avatar || 'https://picsum.photos/200/200?random=1'" 
                alt="User avatar" 
                class="w-32 h-32 rounded-full object-cover border-4 border-gray-700"
              />
              <div class="absolute inset-0 bg-black/50 rounded-full flex items-center justify-center opacity-0 hover:opacity-100 transition-opacity cursor-pointer">
                <label class="cursor-pointer">
                  <input type="file" class="hidden" @change="handleAvatarUpload" />
                  <i class="fa-solid fa-camera text-white text-2xl"></i>
                </label>
              </div>
            </div>
            <p class="mt-2 text-sm text-gray-400">
              {{ $t('editProfile.clickToChangeAvatar') }}
            </p>
          </div>
          
          <!-- 个人信息 -->
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
            <div>
              <label for="username" class="block text-sm font-medium text-gray-400 mb-1">
                {{ $t('editProfile.username') }}
              </label>
              <input
                type="text"
                id="username"
                v-model="formData.username"
                class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                required
              />
            </div>
            
            <div>
              <label for="email" class="block text-sm font-medium text-gray-400 mb-1">
                {{ $t('editProfile.email') }}
              </label>
              <input
                type="email"
                id="email"
                v-model="formData.email"
                class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                required
              />
            </div>
            
            <div>
              <label for="location" class="block text-sm font-medium text-gray-400 mb-1">
                {{ $t('editProfile.location') }}
              </label>
              <input
                type="text"
                id="location"
                v-model="formData.location"
                class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
              />
            </div>
            
            <div>
              <label for="website" class="block text-sm font-medium text-gray-400 mb-1">
                {{ $t('editProfile.website') }}
              </label>
              <input
                type="url"
                id="website"
                v-model="formData.website"
                class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
              />
            </div>
            
            <div>
              <label for="organization" class="block text-sm font-medium text-gray-400 mb-1">
                {{ $t('editProfile.organization') }}
              </label>
              <input
                type="text"
                id="organization"
                v-model="formData.organization"
                class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
              />
            </div>
            
            <div>
              <label for="position" class="block text-sm font-medium text-gray-400 mb-1">
                {{ $t('editProfile.position') }}
              </label>
              <input
                type="text"
                id="position"
                v-model="formData.position"
                class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
              />
            </div>
          </div>
          
          <!-- 关于我 -->
          <div class="mb-8">
            <label for="about" class="block text-sm font-medium text-gray-400 mb-1">
              {{ $t('editProfile.about') }}
            </label>
            <textarea
              id="about"
              v-model="formData.about"
              rows="4"
              class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
            ></textarea>
          </div>
          
          <!-- 社交链接 -->
          <div class="mb-8">
            <h3 class="text-lg font-medium text-white mb-4">
              {{ $t('editProfile.socialLinks') }}
            </h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label for="github" class="block text-sm font-medium text-gray-400 mb-1">
                  GitHub
                </label>
                <div class="relative">
                  <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                    <i class="fa-brands fa-github text-gray-400"></i>
                  </div>
                  <input
                    type="text"
                    id="github"
                    v-model="formData.github"
                    class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                    placeholder="github.com/"
                  />
                </div>
              </div>
              
              <div>
                <label for="twitter" class="block text-sm font-medium text-gray-400 mb-1">
                  Twitter
                </label>
                <div class="relative">
                  <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                    <i class="fa-brands fa-twitter text-gray-400"></i>
                  </div>
                  <input
                    type="text"
                    id="twitter"
                    v-model="formData.twitter"
                    class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                    placeholder="@username"
                  />
                </div>
              </div>
              
              <div>
                <label for="linkedin" class="block text-sm font-medium text-gray-400 mb-1">
                  LinkedIn
                </label>
                <div class="relative">
                  <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                    <i class="fa-brands fa-linkedin text-gray-400"></i>
                  </div>
                  <input
                    type="text"
                    id="linkedin"
                    v-model="formData.linkedin"
                    class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                  />
                </div>
              </div>
              
              <div>
                <label for="discord" class="block text-sm font-medium text-gray-400 mb-1">
                  Discord
                </label>
                <div class="relative">
                  <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                    <i class="fa-brands fa-discord text-gray-400"></i>
                  </div>
                  <input
                    type="text"
                    id="discord"
                    v-model="formData.discord"
                    class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full pl-10 pr-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                    placeholder="username#1234"
                  />
                </div>
              </div>
            </div>
          </div>
          
          <!-- 隐私设置 -->
          <div class="mb-8">
            <h3 class="text-lg font-medium text-white mb-4">
              {{ $t('editProfile.privacySettings') }}
            </h3>
            <div class="space-y-4">
              <div class="flex items-center justify-between">
                <div>
                  <h4 class="font-medium text-white">
                    {{ $t('editProfile.showEmail') }}
                  </h4>
                  <p class="text-sm text-gray-400 mt-1">
                    {{ $t('editProfile.showEmailDescription') }}
                  </p>
                </div>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input type="checkbox" v-model="formData.show_email" class="sr-only peer">
                  <div class="w-11 h-6 bg-gray-700 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary"></div>
                </label>
              </div>
              
              <div class="flex items-center justify-between">
                <div>
                  <h4 class="font-medium text-white">
                    {{ $t('editProfile.showSolves') }}
                  </h4>
                  <p class="text-sm text-gray-400 mt-1">
                    {{ $t('editProfile.showSolvesDescription') }}
                  </p>
                </div>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input type="checkbox" v-model="formData.show_solves" class="sr-only peer">
                  <div class="w-11 h-6 bg-gray-700 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary"></div>
                </label>
              </div>
              
              <div class="flex items-center justify-between">
                <div>
                  <h4 class="font-medium text-white">
                    {{ $t('editProfile.showRank') }}
                  </h4>
                  <p class="text-sm text-gray-400 mt-1">
                    {{ $t('editProfile.showRankDescription') }}
                  </p>
                </div>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input type="checkbox" v-model="formData.show_rank" class="sr-only peer">
                  <div class="w-11 h-6 bg-gray-700 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary"></div>
                </label>
              </div>
            </div>
          </div>
          
          <!-- 提交按钮 -->
          <div class="flex items-center justify-end space-x-4">
            <button 
              @click="cancelEdit" 
              class="bg-sub-bg hover:bg-gray-700 border border-gray-700 text-white font-medium rounded-lg px-6 py-2.5 transition-colors"
            >
              {{ $t('editProfile.cancel') }}
            </button>
            <button
              type="submit"
              class="bg-primary hover:bg-primary/90 text-white font-medium rounded-lg px-6 py-2.5 transition-colors flex items-center space-x-2"
              :disabled="loading"
            >
              <span>{{ $t('editProfile.saveChanges') }}</span>
              <div v-if="loading" class="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/modules/user';

const router = useRouter();
const userStore = useUserStore();

// 状态
const loading = ref(false);
const formData = reactive({
  username: '',
  email: '',
  avatar: '',
  location: '',
  website: '',
  organization: '',
  position: '',
  about: '',
  github: '',
  twitter: '',
  linkedin: '',
  discord: '',
  show_email: true,
  show_solves: true,
  show_rank: true
});

// 初始化表单数据
const initFormData = () => {
  const user = userStore.user;
  if (user) {
    formData.username = user.username;
    formData.email = user.email;
    formData.avatar = user.avatar;
    formData.location = user.location || '';
    formData.website = user.website || '';
    formData.organization = user.organization || '';
    formData.position = user.position || '';
    formData.about = user.about || '';
    formData.github = user.github || '';
    formData.twitter = user.twitter || '';
    formData.linkedin = user.linkedin || '';
    formData.discord = user.discord || '';
    formData.show_email = user.show_email !== undefined ? user.show_email : true;
    formData.show_solves = user.show_solves !== undefined ? user.show_solves : true;
    formData.show_rank = user.show_rank !== undefined ? user.show_rank : true;
  }
};

// 处理头像上传
const handleAvatarUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      formData.avatar = e.target.result;
    };
    reader.readAsDataURL(file);
  }
};

// 保存个人资料
const saveProfile = async () => {
  loading.value = true;
  
  try {
    // 准备表单数据
    const profileData = {
      username: formData.username,
      email: formData.email,
      location: formData.location,
      website: formData.website,
      organization: formData.organization,
      position: formData.position,
      about: formData.about,
      github: formData.github,
      twitter: formData.twitter,
      linkedin: formData.linkedin,
      discord: formData.discord,
      show_email: formData.show_email,
      show_solves: formData.show_solves,
      show_rank: formData.show_rank
    };
    
    // 保存个人资料
    await userStore.updateProfile(profileData);
    
    // 显示成功通知
    window.$notify.success(
      $t('editProfile.successTitle'),
      $t('editProfile.successMessage')
    );
    
    // 返回个人资料页面
    router.push({ name: 'Profile', params: { id: userStore.user.id } });
  } catch (error) {
    // 显示错误通知
    window.$notify.error(
      $t('editProfile.errorTitle'),
      $t('editProfile.errorMessage')
    );
  } finally {
    loading.value = false;
  }
};

// 取消编辑
const cancelEdit = () => {
  router.go(-1);
};

// 初始化
onMounted(() => {
  initFormData();
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
