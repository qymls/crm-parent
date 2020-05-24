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
        path: '/redirect/:path(.*)',
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
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'dashboard', icon: 'dashboard' },
      Affix: true,
      noCache: true
    }]
  },

  {
    path: '',
    component: Layout,
    name: '系统管理',
    meta: { title: '系统管理', icon: 'example' },
    children: [{
      path: '/department',
      name: '部门管理',
      component: () => import('@/views/department/index'),
      meta: { title: '部门管理', icon: 'dashboard' }
    },
      {
        path: '/employee',
        name: '员工管理',
        component: () => import('@/views/employee/index'),
        meta: { title: '员工管理', icon: 'dashboard' }
      },
      {
        path: '/menu',
        name: '菜单管理',
        component: () => import('@/views/menu/index'),
        meta: { title: '菜单管理', icon: 'dashboard' }
      }
    ]
  },


  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/menu/menu'),
        meta: { title: 'Form', icon: 'form' }
      }
    ]
  },
  // 404 page must be placed at the end !!!
 /* { path: '*', redirect: '/404', hidden: true }*/
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
