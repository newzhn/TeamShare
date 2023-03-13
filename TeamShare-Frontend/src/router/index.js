import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    name:'login',
    path:'/login',
    component:() => import('@/views/login/Main.vue'),
    meta: { title: '登录 - 每天都是不一样' },
  },
  {
    name:'register',
    path:'/register',
    component:() => import('@/views/register/Main.vue'),
    meta: { title: '注册 - 每天都是不一样' },
  },
  // 前台路由
  {
    path:'/',
    component:() => import('../views/frontdesk/Main.vue'),
    redirect:'home',
    children:[
      {
        name:'home',
        path:'/home',
        component:() => import('../views/frontdesk/home/Home.vue'),
        meta: { title: '首页 - 每天都是不一样' },
      },
      {
        name:'team',
        path:'/team',
        component:() => import('../views/frontdesk/team/Team.vue'),
        meta: { title: '组队 - 每天都是不一样' },
      },
      {
        name:'community',
        path:'/community',
        component:() => import('../views/frontdesk/community/Community.vue'),
        meta: { title: '社区 - 每天都是不一样' },
      },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
