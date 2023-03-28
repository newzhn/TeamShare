// 登录相关API请求
import axios from "@/plugins/axiosConfig"

// 发送登录请求
export function userLogin(loginForm) {
    return axios({
        url:'login',
        method:'Post',
        data:{
            loginUsername:loginForm.loginName,
            loginPassword:loginForm.loginPass
        }
    })
}

// 发送登场请求
export function userLogout() {
    return axios({
        url:'logout',
        method:'Get'
    })
}