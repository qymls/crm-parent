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
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  /*微信登录的绑定账号路由*/
  {
    path: '/bind',
    component: () => import('@/views/bind'),
    hidden: true
  },

  /**
   * 微信登录成功的页面
   */
  {
    path: '/success',
    component: () => import('@/views/success/index'),
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
      meta: { title: 'dashboard', icon: 'ios-alarm-outline' },
      Affix: true,
      noCache: true
    }]
  },

  /*{
    path: '/system',
    component: Layout,
    name: '系统管理',
    meta: { title: '系统管理', icon: 'ios-apps-outline' },
    children: [{
      path: '/department',
      name: '部门管理',
      component: () => import('@/views/department/index'),
      meta: { title: '部门管理', icon: 'ios-apps-outline' }
    },
      {
        path: 'system/employee',
        name: '员工管理',
        component: () => import('@/views/employee/index'),
        meta: { title: '员工管理', icon: 'dashboard' }
      },
      {
        path: 'system/menu',
        name: '菜单管理',
        component: () => import('@/views/menu/menu'),
        meta: { title: '菜单管理', icon: 'dashboard' }
      }
    ]
  },
  {
    path: '',
    component: Layout,
    name: '订单合同管理',
    meta: { title: '订单合同管理', icon: 'ios-apps-outline' },
    children: [{
      path: '/order',
      name: '订金订单管理',
      component: () => import('@/views/department/index'),
      meta: { title: '订金订单管理', icon: 'ios-apps-outline' }
    },
      {
        path: '/contract',
        name: '合同管理',
        component: () => import('@/views/contract/index'),
        meta: { title: '合同管理', icon: 'dashboard' }
      },
      {
        path: '/contractitem',
        name: '菜单管理',
        component: () => import('@/views/contractitem/index'),
        meta: { title: '合同明细管理', icon: 'dashboard' }
      }
    ]
  },
  },*/


  // 404 page must be placed at the end !!!
  /*{ path: '*', redirect: '/404', hidden: true }*/
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
