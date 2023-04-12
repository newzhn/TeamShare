<!-- 文章展示 -->
<template>
    <!-- 文章 -->
    <el-card>
        <!-- 页头 -->
        <el-page-header @back="onBack">
            <!-- 面包屑 -->
            <template #breadcrumb>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{ path: '/community' }">
                        community
                    </el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/community' }">
                        {{ state.article.articleStatus === 3 ? '个人Blog' : '热点分享' }}
                    </el-breadcrumb-item>
                    <el-breadcrumb-item>
                        {{ state.category.categoryName + ' | ' + state.article.title }}
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </template>
            <!-- 标题 -->
            <template #content>
                <el-row>
                    <el-avatar :size="35" :src="state.author.avatarUrl" />
                    <span style="margin-left: 10px;margin-top: 5px;">
                        {{ state.category.categoryName + ' | ' + state.article.title }} 
                    </span>
                    
                </el-row>
            </template>
            <!-- 内容 -->
            <p v-html="state.article.content" style="margin-top: 1.3em;"></p>
        </el-page-header>
        <!-- 页尾 -->
        <el-divider style="margin-bottom: 5px;"/>
        <p class="content" >
            {{ state.author.nickname }} · 发布于
            {{ dateFilter(state.article.createTime) }} · 再次编辑于
            {{ dateFilter(state.article.updateTime) }}
        </p>
        <el-tag v-for="tag in state.tags" style="margin-right: 1em;">{{ tag.tagName }}</el-tag>
    </el-card>
    <!-- 评论列表 -->
    <CommentList style="margin-top: 1em;"></CommentList>
</template>

<script setup>
import { getArticleDetail } from '@/api/community.js'
import { onMounted, reactive } from 'vue';
import { useRouter,useRoute } from 'vue-router'
import CommentList from '@/components//community/CommentList.vue'
import dateFilter from '@/utils/time.js'

const route = useRoute()
const router = useRouter()
const state = reactive({
    article:'',
    author:'',
    category:'',
    tags:'',
})

const onBack = () => {
    router.go(-1)
}

onMounted(() => {
    const id = route.params.id
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
.content {
    font-size: 10px;
    color: #999;
    margin-bottom: 15px;
    text-align: justify;
    overflow: hidden;
}
</style>