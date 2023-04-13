<!-- 文章 -->
<template>
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
                        {{ props.article.articleStatus === 3 ? '个人Blog' : '热点分享' }}
                    </el-breadcrumb-item>
                    <el-breadcrumb-item>
                        {{ props.category.categoryName + ' | ' + props.article.title }}
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </template>
            <!-- 标题 -->
            <template #content>
                <el-row>
                    <el-avatar :size="32" :src="props.author.avatarUrl" />
                    <span style="margin-left: 10px;margin-top: 5px;">
                        {{ props.category.categoryName + ' | ' + props.article.title }} 
                    </span>
                    
                </el-row>
            </template>
            <!-- 内容 -->
            <p v-html="props.article.content" style="margin-top: 1.3em;"></p>
        </el-page-header>
        <!-- 页尾 -->
        <el-divider style="margin-bottom: 5px;"/>
        <p class="content" >
            {{ props.author.nickname }} · 发布于
            {{ dateFilter(props.article.createTime) }} · 再次编辑于
            {{ dateFilter(props.article.updateTime) }}
        </p>
        <el-tag v-for="tag in props.tags" style="margin-right: 1em;">{{ tag.tagName }}</el-tag>
    </el-card>
</template>

<script setup>
import { defineProps } from 'vue'
import { useRouter } from 'vue-router'
import dateFilter from '@/utils/time.js'

const router = useRouter()
const props = defineProps(['article','author','category','tags'])

const onBack = () => {
    router.go(-1)
}
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