// 个人页API请求
import axios from "@/plugins/axiosConfig"

// 修改用户信息
export function updateLoginUserInfo(from) {
    return axios({
        url:'user/',
        method:'Put',
        data:{
            userId:from.userId,
            nickname:from.nickname,
            signature:from.signature,
            gender:from.gender,
            email:from.email,
            qq:from.qq,
            tagNames:from.tagNames
        }
    })
}