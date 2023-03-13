// 首页API请求
import axios from "@/plugins/axiosConfig"

// 获取当前登录用户信息
export function getCurrentUser() {
    return axios({
        url:'user/currentUser',
        method:'Get'
    })
}

// 获取首页推荐好友
export function getRecommandUsers() {
    return axios({
        url:'user/recommend',
        method:'Get',
    })
}
