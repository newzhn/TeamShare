import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // 登录注册
  {
    name:'login',
    path:'/login',
    component:() => import('@/views/login/Login.vue'),
    meta: { title: '登录 - 每天都是不一样' },
  },
  {
    name:'register',
    path:'/register',
    component:() => import('@/views/register/Register.vue'),
    meta: { title: '注册 - 每天都是不一样' },
  },
  // 业务路由
  {
    path:'/',
    component:() => import('@/views/Index.vue'),
    redirect:'/home',
    children:[
      {
        name:'home',
        path:'/home',
        component:() => import('@/views/home/Home.vue'),
        meta: { title: '首页 - 每天都是不一样' },
      },
      {
        name:'team',
        path:'/team',
        component:() => import('@/views/team/Team.vue'),
        meta: { title: '组队 - 每天都是不一样' },
      },
      {
        name:'community',
        path:'/community',
        component:() => import('@/views/community/Community.vue'),
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
