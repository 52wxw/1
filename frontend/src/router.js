import { createRouter, createWebHistory } from 'vue-router';
// 导入用户模块的 defineStore（用于获取登录状态）
import { useUserStore } from './store/modules/user';

// 导入所有页面组件（明确指定 `Pages/` 子目录）
import Login from './components/Pages/Login.vue';
import Register from './components/Pages/Register.vue';
import TaskList from './components/Pages/TaskList.vue';
import TaskDetail from './components/Pages/TaskDetail.vue';
import Ranking from './components/Pages/Ranking.vue';
import Profile from './components/Pages/Profile.vue';
import EditProfile from './components/Pages/EditProfile.vue';
import AdminDashboard from './components/Pages/AdminDashboard.vue';
import NotFound from './components/Pages/NotFound.vue';

const routes = [
  { path: '/', redirect: '/tasks' }, // 首页重定向到任务列表
  { path: '/login', component: Login, meta: { auth: false } }, // 无需认证
  { path: '/register', component: Register, meta: { auth: false } }, // 无需认证
  { path: '/tasks', component: TaskList, meta: { auth: true } }, // 需要登录
  { path: '/tasks/:id', component: TaskDetail, name: 'TaskDetail', meta: { auth: true } }, // 需要登录
  { path: '/ranking', component: Ranking, meta: { auth: true } }, // 需要登录
  { path: '/profile', component: Profile, meta: { auth: true } }, // 需要登录
  { path: '/profile/edit', component: EditProfile, name: 'EditProfile', meta: { auth: true } }, // 需要登录
  { path: '/admin', component: AdminDashboard, meta: { auth: true, admin: true } }, // 需要登录且是管理员
  { path: '/:pathMatch(.*)*', component: NotFound, name: 'NotFound' }, // 404页面
];

const router = createRouter({
  history: createWebHistory(), // 使用 HTML5 History 模式（无哈希值）
  routes, // 路由配置
  scrollBehavior(to, from, savedPosition) {
    // 页面滚动行为：返回顶部或保留滚动位置
    if (savedPosition) return savedPosition; // 保留滚动位置（如浏览器前进/后退）
    else return { top: 0 }; // 其他情况返回顶部
  },
});

// 路由守卫：全局前置守卫（权限验证核心逻辑）
router.beforeEach((to, from, next) => {
  // 获取用户模块实例（Pinia 自动管理单例，可在任何地方调用）
  const userStore = useUserStore();
  // 从路由元信息中获取权限需求
  const requiresAuth = to.meta.auth || false; // 是否需要登录认证
  const requiresAdmin = to.meta.admin || false; // 是否需要管理员权限
  // 从用户模块中获取登录状态和用户角色（关键修正点）
  const hasToken = userStore.token; // 用户令牌（登录后由后端返回）
  const userRole = userStore.user?.role || 'USER'; // 用户角色（默认普通用户）

  // 逻辑判断：
  // 1. 需要认证但未登录，重定向到登录页
  if (requiresAuth && !hasToken) {
    next('/login');
  }
  // 2. 需要管理员权限但不是管理员，重定向到任务列表
  else if (requiresAdmin && userRole !== 'ADMIN') {
    next('/tasks');
  }
  // 3. 其他情况允许访问
  else {
    next();
  }
});

export default router;
