<template>
  <button
    :type="type"
    :disabled="disabled || loading"
    :class="[
      'inline-flex items-center justify-center px-4 py-2 rounded-lg transition-all duration-200 font-medium',
      baseClass,
      disabled ? 'opacity-50 cursor-not-allowed' : '',
      loading ? 'animate-pulse' : ''
    ]"
    @click="handleClick"
    :aria-disabled="disabled || loading"
  >
    <span v-if="loading" class="mr-2">
      <div class="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
    </span>
    <slot>{{ label }}</slot>
    <slot name="icon" />
  </button>
</template>

<script setup>
import { defineProps, defineEmits, ref, computed, watch } from 'vue';

const props = defineProps({
  type: {
    type: String,
    default: 'button',
    validator: (value) => ['button', 'submit', 'reset'].includes(value)
  },
  variant: {
    type: String,
    default: 'primary',
    validator: (value) => [
      'primary', 'secondary', 'success', 'error', 'warning', 'info', 'outline', 'ghost', 'link'
    ].includes(value)
  },
  size: {
    type: String,
    default: 'medium',
    validator: (value) => ['small', 'medium', 'large'].includes(value)
  },
  disabled: {
    type: Boolean,
    default: false
  },
  loading: {
    type: Boolean,
    default: false
  },
  label: {
    type: String,
    default: ''
  },
  fullWidth: {
    type: Boolean,
    default: false
  }
});

const emits = defineEmits(['click']);

// 处理点击事件
const handleClick = (event) => {
  if (!props.disabled && !props.loading) {
    emits('click', event);
  }
};

// 计算按钮的基础类
const baseClass = computed(() => {
  const sizeClasses = {
    small: 'text-sm px-3 py-1.5',
    medium: 'text-base',
    large: 'text-lg px-5 py-2.5'
  };

  const variantClasses = {
    primary: 'bg-primary hover:bg-primary/90 text-white',
    secondary: 'bg-sub-bg hover:bg-sub-bg/90 text-white',
    success: 'bg-green-500 hover:bg-green-600 text-white',
    error: 'bg-red-500 hover:bg-red-600 text-white',
    warning: 'bg-yellow-500 hover:bg-yellow-600 text-white',
    info: 'bg-blue-500 hover:bg-blue-600 text-white',
    outline: 'border border-primary text-primary hover:bg-primary/10',
    ghost: 'text-primary hover:bg-primary/10',
    link: 'text-primary hover:underline'
  };

  return [
    sizeClasses[props.size],
    variantClasses[props.variant],
    props.fullWidth ? 'w-full' : ''
  ].join(' ');
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
