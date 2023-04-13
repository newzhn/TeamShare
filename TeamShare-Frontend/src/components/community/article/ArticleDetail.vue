<!-- 文章展示 -->
<template>
    <!-- 文章 -->
    <ArticleItem :article="state.article" :author="state.author" 
    :category="state.category" :tags="state.tags"></ArticleItem>
    <!-- 评论列表 -->
    <CommentList style="margin-top: 1em;" :articleId="id"></CommentList>
</template>

<script setup>
import { getArticleDetail } from '@/api/community.js'
import { onMounted, reactive } from 'vue';
import { useRoute } from 'vue-router'
import ArticleItem from './ArticleItem.vue';
import CommentList from '@/components/community/comment/CommentList.vue'

const route = useRoute()
const id = route.params.id

const state = reactive({
    article:'',
    author:'',
    category:'',
    tags:'',
})

onMounted(() => {
    getArticleDetail(id).then(res => {
        const article = res.data.data
        state.article = article
        state.author = article.author
        state.category = article.category
        state.tags = article.tags
    }).catch(() => {
        console.log('请求发送失败');
    })
})
</script>

<style scoped>

</style>