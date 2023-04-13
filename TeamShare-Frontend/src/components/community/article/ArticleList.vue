<!-- 文章列表 -->
<template>
    <el-card v-for="(item, index) in props.articleList" :key="item.articleId" shadow="hover" style="margin-bottom: 0.7em;">
        <!-- 作者头像、文章分类|标题 -->
        <el-row style="margin-bottom: 1em;">
            <el-avatar :size="25" :src="item.author.avatarUrl" style="margin-right: 1em;;"/>
            <el-link @click="toArticle(item.articleId)" :underline="false" class="title-link">
                {{ item.category.categoryName }} | {{ item.title }}
            </el-link>
        </el-row>
        <!-- 文章简介 -->
        <el-row>
            <p class="content" v-html="item.content"></p>
        </el-row>
        <!-- 文章相关信息 -->
        <el-row class="articleMessage">
            <!-- 作者 -->
            <span>
                <el-icon><User /></el-icon>{{ item.author.nickname }}
            </span>
            <!-- 阅读量 -->
            <span>
                <el-icon><View /></el-icon>{{item.readingVolume}}
            </span>
            <!-- 评论 -->
            <span>
                <el-icon><ChatDotSquare /></el-icon>120
            </span>
            <!-- 发布日期 -->
            <span>
                <el-icon><Timer /></el-icon>{{ dateFilter(item.createTime) }}
            </span>
            <!-- 标签 -->
            <span class="hidden-sm-and-down">
                <el-row>
                    <el-tag v-for="tag in item.tags">{{ tag.tagName }}</el-tag>
                </el-row>
            </span>
        </el-row>
    </el-card>
</template>

<script setup>
import { defineProps } from 'vue'
import { useRouter } from 'vue-router'
import dateFilter from '@/utils/time.js'

const router = useRouter()
const props = defineProps(['articleList'])

const toArticle = (id) => {
    const url='/community/article/' + id
    router.push(url)
}
</script>

<style scoped>
.title-link {
    font-size: 17px;
    line-height: 22px;
    font-weight: 540;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.content {
    font-size: 15px;
    color: #999;
    margin-bottom: 15px;
    -webkit-box-orient: vertical;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    text-align: justify;
    overflow: hidden;
}

.articleMessage span {
    font-size: 13px;
    color: #999;
    line-height: 20px;
    margin-right: 15px;
    display: inline-flex;
    align-items: center;
}

.articleMessage .el-icon {
    font-size: 15px;
    margin-right: 5px;
}
</style>