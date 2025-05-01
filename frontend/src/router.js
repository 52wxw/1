import { createRouter, createWebHistory } from 'vue-router';
import store from './store'; // 导入Pinia Store（用于路由守卫）

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
  { path: '/', redirect: '/tasks' }, // 首页重定向到任务列表（可根据需求调整）
  { path: '/login', component: Login, meta: { auth: false } },
  { path: '/register', component: Register, meta: { auth: false } },
  { path: '/tasks', component: TaskList, meta: { auth: true } },
  { path: '/tasks/:id', component: TaskDetail, name: 'TaskDetail', meta: { auth: true } },
  { path: '/ranking', component: Ranking, meta: { auth: true } },
  { path: '/profile', component: Profile, meta: { auth: true } },
  { path: '/profile/edit', component: EditProfile, name: 'EditProfile', meta: { auth: true } },
  { path: '/admin', component: AdminDashboard, meta: { auth: true, admin: true } },
  { path: '/:pathMatch(.*)*', component: NotFound, name: 'NotFound' }, // 404页面
];

const router = createRouter({
  history: createWebHistory(), // 使用HTML5 History模式
  routes,
  scrollBehavior(to, from, savedPosition) {
    // 页面滚动行为：返回顶部或保留滚动位置
    if (savedPosition) return savedPosition;
    else return { top: 0 };
  },
});

// 路由守卫：权限验证
router.beforeEach((to, from, next) => {
  const requiresAuth = to.meta.auth || false; // 是否需要认证
  const requiresAdmin = to.meta.admin || false; // 是否需要管理员权限
  const hasToken = store.state.token; // 从Store获取登录状态
  const userRole = store.state.user?.role || 'USER'; // 用户角色（默认普通用户）

  // 未登录且需要认证的页面，重定向到登录页
  if (requiresAuth && !hasToken) {
    next('/login');
  }
  // 非管理员访问管理员页面，重定向到任务列表
  else if (requiresAdmin && userRole !== 'ADMIN') {
    next('/tasks');
  }
  // 其他情况正常访问
  else {
    next();
  }
});

export default router;
