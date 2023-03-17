import { reactive } from 'vue'
import { defineStore } from 'pinia'

// 管理用户相关状态
export const useUserStore = defineStore('user', () => {
  // 当前登录用户信息
  const userInfo = reactive({})

  // 判断当前登录用户是否登录
  function isLogin() {
    return Object.keys(userInfo).length !== 0
  }

  // 修改用户信息
  function updateUserInfo(user) {
    Object.assign(userInfo, reactive(user))
  }

  return { userInfo,isLogin,updateUserInfo}
})
