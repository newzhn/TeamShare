// 注册相关API请求
import axios from "@/plugins/axiosConfig"

// 检验用户名是否被注册
export function checkUserName(userName) {
    return axios({
        url:'register/checkUserName',
        method:'Get',
        params:{
            userName
        }
    })
}

// 检验邮箱是否被注册
export function checkEmail(email) {
    return axios({
        url:'register/checkEmail',
        method:'Get',
        params:{
            email
        }
    })
}

// 发送请求通知后端发送验证码
export function sendCode(email) {
    return axios({
        url:'register/sendCode',
        method:'Get',
        params:{
            email
        }
    })
}

// 发送注册请求
export function userRegister(registerForm) {
    return axios({
        url:'register',
        method:'Post',
        data:{
            registerName:registerForm.registerName,
            registerEmail:registerForm.registerEmail,
            registerQQ:registerForm.registerQQ,
            registerPass:registerForm.registerPass,
            verificationCode:registerForm.verificationCode
        }
    })
}