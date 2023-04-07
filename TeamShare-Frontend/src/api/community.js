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