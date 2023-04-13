<!-- 最近的文章 -->
<template>
    <div class="articleBox" v-for="(item,index) in state.articleList" v-loading="state.loading" element-loading-text="玩命加载中...">
        <!-- 文章分类 -->
        <div class="tagBox">
            <p>
                <span>
                    <el-icon class="tagIcon"><FolderOpened /></el-icon>{{item.category.categoryName}}
                </span>
            </p>
        </div>
        <!-- 文章信息 -->
        <a :href="'/community/article/' + item.articleId">
            <!-- 标题 -->
            <h4 class="title"><span>{{item.title}}</span></h4>
            <div class="article">
                <div class="textBox textBox2">
                    <!-- 简介 -->
                    <p style="margin-bottom: 10px;" v-html="item.content"></p>
                    <!-- 其他信息 -->
                    <p class="articleMessage">
                        <!-- 作者 -->
                        <span>
                            <el-icon><User /></el-icon>{{item.author.nickname}}
                        </span>
                        <!-- 发布日期 -->
                        <span>
                            <el-icon><Timer /></el-icon>{{dateFilter(item.createTime)}}
                        </span>
                        <!-- 阅读量 -->
                        <span>
                            <el-icon><View /></el-icon>{{item.readingVolume}}
                        </span>
                        <!-- 标签 -->
                        <span style="float: right;" class="hidden-sm-and-down">
                            <el-row>
                                <el-tag v-for="tag in item.tags" type="primary">{{ tag.tagName }}</el-tag>
                            </el-row>
                        </span>
                    </p>
                </div>
            </div>
        </a>
        <el-divider v-if="index != state.articleList.length - 1" style="margin-top: -10px;margin-bottom: 1px;"/>
    </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue';
import { getRecentPosts } from '@/api/home.js'
import dateFilter from '@/utils/time.js'

const state = reactive({
    loading:true,
    articleList:[]
})

onMounted(() => {
    getRecentPosts().then(res => {
        state.articleList = res.data.data
        state.loading = false
    }).catch(() => {
        console.log('请求发送失败');
    })
    
})
</script>

<style scoped>
    /* 展示文章样式 */
    .articleBox {
        padding: 20px;
        padding-top: 15px;
        padding-bottom: 0px;
        background-color: white;
        border: 1px solid white;
    }

    .articleBox:hover {
        border: 1px solid #2fa7b9;
    }

    .tagBox span {
        background: #f6f6f6;
        padding: 2px 2px;
        border-radius: 20px;
        color: #2fa6b8;
        display: inline-flex;
        align-items: center;
        font-size: 13px;
        cursor: pointer;
    }

    .tagIcon {
        margin-right: 5px;
        vertical-align: middle;
        background: #2fa7b9;
        color: white;
        border-radius: 15px;
        padding: 5px;
        font-size: 13px;
    }

    .articleBox .title {
        font-size: 17px;
        line-height: 45px;
        color: #454545;
        overflow: hidden;
        -webkit-box-orient: vertical;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        text-align: justify;
    }

    .title span {
        cursor: pointer;
    }

    .title span:hover {
        color: #2fa7b9;
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
        margin-right: 8px;
    }

    .article {
        display: flex;
        justify-content: space-between;
        font-size: 12px;
        color: #999;
    }

    .article img {
        width: 140px;
        height: 90px;
        border-radius: 4px;
        object-fit: cover;
        overflow: hidden;
    }

    .textBox {
        width: 420px;
        height: 100px;
        line-height: 22px;
        font-size: 13px;
        color: #999;
        margin-left: 10px;
    }

    .textBox2 {
        width: auto;
        margin-left: 0px;
    }

    .textBox p {
        font-size: 14px;
        color: #999;
        margin-bottom: 15px;
        -webkit-box-orient: vertical;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        text-align: justify;
        margin-bottom: 25px;
        cursor: pointer;
        overflow: hidden;
    }

    @media screen and (max-width: 768px) {
        .textBox {
            width: 500px;
        }
    }
</style>