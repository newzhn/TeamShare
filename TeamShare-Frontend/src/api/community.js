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