<!-- 文章列表面板 -->
<template>
    <!-- 搜索框 -->
    <ArticleSearch></ArticleSearch>
    <!-- 文章列表 -->
    <el-card v-for="(item, index) in articleList" :key="item.articleId" shadow="hover" style="margin-bottom: 0.7em;">
        <!-- 作者头像、文章分类|标题 -->
        <el-row style="margin-bottom: 1em;">
            <el-avatar :size="25" src="https://q1.qlogo.cn/g?b=qq&nk=969025821&s=100" style="margin-right: 1em;;"/>
            <el-link :underline="false" class="title-link">面试题 | {{ item.title }}</el-link>
        </el-row>
        <!-- 文章简介 -->
        <el-row>
            <p class="content">{{ item.content }}</p>
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
                    <el-tag type="primary">面试题</el-tag>
                    <el-tag type="primary">打卡</el-tag>
                </el-row>
            </span>
        </el-row>
    </el-card>
</template>

<script setup>
import ArticleSearch from './ArticleSearch.vue';
import { reactive, ref } from 'vue'
import dateFilter from '@/utils/time.js'

const activeName = ref('first')
const user1 = {
    userId: 1,
    username: 'user1',
    nickname: 'User One',
    avatar: 'https://example.com/avatar/user1.jpg'
    }

const user2 = {
    userId: 2,
    username: 'user2',
    nickname: 'User Two',
    avatar: 'https://example.com/avatar/user2.jpg'
}

const articleList = reactive([
    {
        articleId: 1,
        title: 'Vue 3.x Composition API',
        content: 'Vue 3.x Composition API is a new way to organize logic in Vue components. With Composition API, you can create reusable and composable logic that can be shared across components. This makes it easier to write and maintain complex applications.',
        outline: 'Learn about the new way to organize logic in Vue components',
        articlePictures: 'https://example.com/pictures/article1.jpg',
        articleStatus: 1,
        readingVolume: 100,
        likes: [user1, user2],
        author: user1,
        createTime: '2022-03-20 10:00:00'
    },
    {
        articleId: 2,
        title: 'Creating a Vue 3 Component Library',
        content: 'Creating a Vue 3 Component Library is a great way to share your UI components across multiple applications. With Vue 3, it is easier than ever to create and publish your own component library. You can use the new Composition API to create composable and reusable components that can be easily customized by the consumer.',
        outline: 'Learn how to create a Vue 3 component library',
        articlePictures: '',
        articleStatus: 1,
        readingVolume: 50,
        likes: [user1, user2],
        author: user2,
        createTime: '2022-03-21 12:00:00'
    }
])

const handleClick = (tab, event) => {
    console.log(tab, event)
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