<template>
  <div
    :class="[
      'fixed right-4 top-4 z-50 p-4 rounded-lg shadow-lg flex items-center transition-all duration-500',
      typeClass,
      {
        'translate-x-full opacity-0': !visible,
        'translate-x-0 opacity-100': visible
      }
    ]"
    :style="{ top: topOffset }"
  >
    <div class="mr-3">
      <i :class="iconClass" class="text-xl"></i>
    </div>
    <div class="flex-1">
      <h3 class="font-bold text-white mb-1">{{ title }}</h3>
      <p class="text-white/80 text-sm">{{ message }}</p>
    </div>
    <button
      class="ml-2 text-white/60 hover:text-white transition-colors"
      @click="close"
    >
      <i class="fa-solid fa-times"></i>
    </button>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, onMounted, onUnmounted, nextTick } from 'vue';
import { watch } from 'vue'
const props = defineProps({
  id: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'info',
    validator: (value) => ['success', 'error', 'warning', 'info'].includes(value)
  },
  title: {
    type: String,
    default: ''
  },
  message: {
    type: String,
    default: ''
  },
  duration: {
    type: Number,
    default: 5000
  },
  offset: {
    type: Number,
    default: 0
  }
});

const emits = defineEmits(['close']);

// 状态
const visible = ref(true);
const timeoutId = ref(null);
const topOffset = ref(`${props.offset}px`);

// 关闭通知
const close = () => {
  visible.value = false;
  // 动画结束后触发关闭事件
  setTimeout(() => {
    emits('close', props.id);
  }, 500);
};

// 设置自动关闭
onMounted(() => {
  if (props.duration > 0) {
    timeoutId.value = setTimeout(close, props.duration);
  }
});

// 清理
onUnmounted(() => {
  if (timeoutId.value) {
    clearTimeout(timeoutId.value);
  }
});

// 监听offset变化
const updateOffset = () => {
  topOffset.value = `${props.offset}px`;
};

watch(() => props.offset, updateOffset);
</script>

<style scoped>
/* 通知类型样式 */
.success {
  background-color: rgba(16, 185, 129, 0.9);
}

.error {
  background-color: rgba(239, 68, 68, 0.9);
}

.warning {
  background-color: rgba(245, 158, 11, 0.9);
}

.info {
  background-color: rgba(59, 130, 246, 0.9);
}

/* 图标样式 */
.success i {
  color: white;
}

.error i {
  color: white;
}

.warning i {
  color: white;
}

.info i {
  color: white;
}
</style>
