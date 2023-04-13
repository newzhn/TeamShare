// 首页API请求
import axios from "@/plugins/axiosConfig"

// 获取首页推荐好友
export function getRecommandUsers() {
    return axios({
        url:'user/recommendList',
        method:'Get',
    })
}

// 获取首页推荐文章列表
export function getRecommandArticleList() {
    return axios({
        url:'article/recommendList',
        method:'Get'
    })
}

// 获取首页推荐队伍列表
export function getRecommandTeamList() {
    return axios({
        url:'team/recommendList',
        method:'Get'
    })
}

// 获取首页最新文章列表
export function getRecentPosts() {
    return axios({
        url:'article/recentPosts',
        method:'Get'
    })
}
