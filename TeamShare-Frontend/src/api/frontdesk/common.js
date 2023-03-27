// 公共API请求
import axios from "@/plugins/axiosConfig"

// 获取当前登录用户信息
export function getCurrentUser() {
    return axios({
        url:'user/getLoginUser',
        method:'Get'
    })
}