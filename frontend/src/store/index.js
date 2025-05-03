import { createPinia } from 'pinia';
import { createPersistedState } from 'pinia-plugin-persistedstate';

// 创建Pinia实例
const pinia = createPinia();

// 使用持久化插件
pinia.use(createPersistedState({
  storage: localStorage, // 也可以使用sessionStorage
  // 可以在这里配置特定store的持久化选项
  // key: (id) => `__persisted__${id}`,
  // paths: ['user', 'settings'],
}));

export default pinia;
