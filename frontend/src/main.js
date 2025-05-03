import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import App from './App.vue';
import router from './router'; // 直接导入路由实例
import { createI18n } from 'vue-i18n';
import zhCN from './locales/zh-CN.json';
import enUS from './locales/en.json';
import './assets/styles/global.css';
import './assets/styles/tailwind.css';

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const i18n = createI18n({
  legacy: false,
  locale: 'zh-CN',
  fallbackLocale: 'en',
  messages: {
    'zh-CN': zhCN,
    en: enUS,
  },
});

const app = createApp(App);
app.use(pinia);
app.use(router);
app.use(i18n);

import NotificationContainer from './components/Common/NotificationContainer.vue';
app.component('NotificationContainer', NotificationContainer);

app.mount('#app');
