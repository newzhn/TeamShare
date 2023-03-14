// 首页API请求
import axios from "@/plugins/axiosConfig"

// 获取首页推荐好友
export function getRecommandUsers() {
    return axios({
        url:'user/recommend',
        method:'Get',
    })
}
