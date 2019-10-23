import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/publish/index'),
        name: '发布平台',
        meta: { title: '发布平台', icon: 'guide' }
      }
    ]
  }, {
    path: '/tb/create',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/tb/create/index'),
        name: '创建合表',
        meta: { title: '创建合表', icon: 'guide' }
      }
    ]
  }, {
    path: '/tb/list',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/tb/list/index'),
        name: '所有合表',
        meta: { title: '所有合表', icon: 'guide' }
      }
    ]
  }

]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
