/**
 * 格式化日期为人类可读的字符串
 * @param {string|Date} date - 日期字符串或Date对象
 * @param {boolean} [showTime=false] - 是否显示时间
 * @returns {string} - 格式化后的日期字符串
 */
export const formatDate = (date, showTime = false) => {
  if (!date) return '';
  
  const options = {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  };
  
  if (showTime) {
    Object.assign(options, {
      hour: '2-digit',
      minute: '2-digit'
    });
  }
  
  return new Date(date).toLocaleDateString('zh-CN', options);
};

/**
 * 格式化时间差为人类可读的字符串
 * @param {string|Date} startTime - 开始时间
 * @param {string|Date} endTime - 结束时间
 * @returns {string} - 时间差字符串
 */
export const formatTimeDiff = (startTime, endTime) => {
  const start = new Date(startTime);
  const end = new Date(endTime);
  const diffMs = end - start;
  
  if (diffMs < 0) return '0秒';
  
  const days = Math.floor(diffMs / 86400000);
  const hours = Math.floor((diffMs % 86400000) / 3600000);
  const minutes = Math.floor((diffMs % 3600000) / 60000);
  const seconds = Math.floor((diffMs % 60000) / 1000);
  
  let result = '';
  if (days > 0) result += `${days}天`;
  if (hours > 0) result += `${hours}小时`;
  if (minutes > 0) result += `${minutes}分钟`;
  if (seconds > 0 || result === '') result += `${seconds}秒`;
  
  return result;
};

/**
 * 格式化数字为带单位的字符串（如：1k, 2.5k, 1M）
 * @param {number} num - 数字
 * @returns {string} - 格式化后的字符串
 */
export const formatNumber = (num) => {
  if (num >= 1000000) {
    return (num / 1000000).toFixed(1) + 'M';
  } else if (num >= 1000) {
    return (num / 1000).toFixed(1) + 'k';
  } else {
    return num.toString();
  }
};

/**
 * 生成随机字符串
 * @param {number} [length=10] - 字符串长度
 * @returns {string} - 随机字符串
 */
export const generateRandomString = (length = 10) => {
  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let result = '';
  
  for (let i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(randomNumber(0, characters.length - 1)));
  }
  
  return result;
};

/**
 * 生成随机数
 * @param {number} min - 最小值
 * @param {number} max - 最大值
 * @returns {number} - 随机数
 */
export const randomNumber = (min, max) => {
  return Math.random() * (max - min) + min;
};

/**
 * 检查字符串是否是有效的URL
 * @param {string} url - 待检查的URL
 * @returns {boolean} - 是否是有效URL
 */
export const isValidUrl = (url) => {
  try {
    new URL(url);
    return true;
  } catch (error) {
    return false;
  }
};

/**
 * 从URL获取参数
 * @param {string} url - URL
 * @param {string} paramName - 参数名
 * @returns {string|null} - 参数值
 */
export const getUrlParam = (url, paramName) => {
  const urlObj = new URL(url);
  return urlObj.searchParams.get(paramName);
};

/**
 * 检查对象是否为空
 * @param {object} obj - 待检查的对象
 * @returns {boolean} - 对象是否为空
 */
export const isEmptyObject = (obj) => {
  return Object.keys(obj).length === 0;
};

/**
 * 深度克隆对象
 * @param {any} obj - 待克隆的对象
 * @returns {any} - 克隆后的对象
 */
export const deepClone = (obj) => {
  if (obj === null || typeof obj !== 'object') {
    return obj;
  }
  
  if (obj instanceof Date) {
    return new Date(obj.getTime());
  }
  
  if (obj instanceof Array) {
    return obj.map(item => deepClone(item));
  }
  
  const clone = {};
  for (const key in obj) {
    if (obj.hasOwnProperty(key)) {
      clone[key] = deepClone(obj[key]);
    }
  }
  
  return clone;
};

/**
 * 防抖函数
 * @param {Function} func - 要防抖的函数
 * @param {number} delay - 延迟时间（毫秒）
 * @returns {Function} - 防抖后的函数
 */
export const debounce = (func, delay) => {
  let timer;
  return function(...args) {
    clearTimeout(timer);
    timer = setTimeout(() => {
      func.apply(this, args);
    }, delay);
  };
};

/**
 * 节流函数
 * @param {Function} func - 要节流的函数
 * @param {number} limit - 限制时间（毫秒）
 * @returns {Function} - 节流后的函数
 */
export const throttle = (func, limit) => {
  let inThrottle;
  return function(...args) {
    if (!inThrottle) {
      func.apply(this, args);
      inThrottle = true;
      setTimeout(() => inThrottle = false, limit);
    }
  };
};

/**
 * 获取任务分类的图标
 * @param {string} category - 任务分类
 * @returns {string} - Font Awesome图标类名
 */
export const getCategoryIcon = (category) => {
  const icons = {
    web: 'fa-solid fa-globe',
    crypto: 'fa-solid fa-lock',
    pwn: 'fa-solid fa-bomb',
    reversing: 'fa-solid fa-undo',
    misc: 'fa-solid fa-question',
    forensics: 'fa-solid fa-magnifying-glass',
    osint: 'fa-solid fa-database',
    hardware: 'fa-solid fa-microchip'
  };
  
  return icons[category] || 'fa-solid fa-puzzle-piece';
};

/**
 * 获取任务分类的颜色
 * @param {string} category - 任务分类
 * @returns {string} - 颜色类名
 */
export const getCategoryColor = (category) => {
  const colors = {
    web: 'bg-blue-500/20 text-blue-500',
    crypto: 'bg-purple-500/20 text-purple-500',
    pwn: 'bg-red-500/20 text-red-500',
    reversing: 'bg-yellow-500/20 text-yellow-500',
    misc: 'bg-gray-500/20 text-gray-500',
    forensics: 'bg-green-500/20 text-green-500',
    osint: 'bg-indigo-500/20 text-indigo-500',
    hardware: 'bg-cyan-500/20 text-cyan-500'
  };
  
  return colors[category] || 'bg-gray-500/20 text-gray-500';
};
