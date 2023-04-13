// 社区页API请求
import axios from "@/plugins/axiosConfig"

// 获取文章所有分类列表
export function getCategoryAllList() {
    return axios({
        url:'category/list',
        method:'Get'
    })
}

// 获取文章所有标签列表
export function getTagAllList() {
    return axios({
        url:'tag/finalList',
        method:'Get'
    })
}

// 发送请求发布文章
export function addArticle(form) {
    return axios({
        url:'article/',
        method:'Post',
        data:{
            title: form.title,
            content: form.content,
            categoryId: form.category,
            tagIds: form.tags
        }
    })
}

// 获取最新文章列表
export function getArticleList() {
    return axios({
        url:'article/list',
        method:'Get'
    })
}

// 模糊查询标题获取文章列表
export function searchArticleList(searchText) {
    return axios({
        url:'article/search/' + searchText,
        method:'Get'
    })
}

// 获取必读榜文章数据
export function getMustReadArticleList() {
    return axios({
        url:'article/mustRead',
        method:'Get'
    })
}

// 根据Id获取文章详细信息
export function getArticleDetail(id) {
    return axios({
        url:'article/' + id,
        method:'Get'
    })
}

// 根据文章id获取其评论数据
export function getCommentList(articleId) {
    return axios({
        url:'comment/publicList/' + articleId,
        method:'Get',
    })
}

// 添加评论
export function addComment(userInfo,content,articleId,parentCommentId) {
    return axios({
        url:'comment/',
        method:'Post',
        data:{
            avatarUrl:userInfo.avatarUrl,
            nickname:userInfo.nickname,
            content,
            parentCommentId,
            articleId,
            authorId:userInfo.userId
        }
    })
}