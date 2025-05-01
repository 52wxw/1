<template>
  <div class="fixed top-4 right-4 z-50 flex flex-col items-end space-y-3 w-80 max-w-sm">
    <Notification
      v-for="(notification, index) in notifications"
      :key="notification.id"
      :id="notification.id"
      :type="notification.type"
      :title="notification.title"
      :message="notification.message"
      :duration="notification.duration"
      :offset="calculateOffset(index)"
      @close="handleClose(notification.id)"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, defineEmits } from 'vue';
import Notification from './Notification.vue';

const notifications = ref([]);
const emits = defineEmits(['notification-closed']);

// 计算通知的垂直偏移量
const calculateOffset = (index) => {
  return 12 + (index * 96); // 4px margin + 88px notification height
};

// 添加通知
const addNotification = (notification) => {
  notifications.value = [...notifications.value, notification];
};

// 关闭通知
const handleClose = (id) => {
  notifications.value = notifications.value.filter(notification => notification.id !== id);
  emits('notification-closed', id);
};

// 全局注册通知方法
onMounted(() => {
  // 确保在浏览器环境中执行
  if (typeof window !== 'undefined') {
    window.$notify = (type, title, message, duration = 5000) => {
      const id = `notification-${Date.now()}`;
      addNotification({ id, type, title, message, duration });
      return id;
    };
    
    // 提供便捷方法
    window.$notify.success = (title, message, duration = 5000) => {
      return window.$notify('success', title, message, duration);
    };
    
    window.$notify.error = (title, message, duration = 5000) => {
      return window.$notify('error', title, message, duration);
    };
    
    window.$notify.warning = (title, message, duration = 5000) => {
      return window.$notify('warning', title, message, duration);
    };
    
    window.$notify.info = (title, message, duration = 5000) => {
      return window.$notify('info', title, message, duration);
    };
    
    window.$notify.close = (id) => {
      handleClose(id);
    };
  }
});
</script>
