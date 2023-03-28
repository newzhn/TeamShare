<!-- 最近的文章 -->
<template>
    <div class="articleBox" v-for="(item,index) in articleInfo" v-loading="loading2" element-loading-text="玩命加载中...">
        <!-- 文章分类 -->
        <div class="tagBox">
            <p>
                <span>
                    <el-icon class="tagIcon"><FolderOpened /></el-icon>{{item.articleClassifyName}}
                </span>
            </p>
        </div>
        <!-- 文章信息 -->
        <a :href="'/articleDetails/'+item.articleId">
            <!-- 标题 -->
            <h4 class="title"><span>{{item.articleTitle}}</span></h4>
            <div class="article">
                <div class="textBox textBox2">
                    <!-- 简介 -->
                    <p style="margin-bottom: 10px;">{{item.articleDase}}</p>
                    <!-- 其他信息 -->
                    <p class="articleMessage">
                        <!-- 作者 -->
                        <span>
                            <el-icon><User /></el-icon>{{item.userName}}
                        </span>
                        <!-- 发布日期 -->
                        <span>
                            <el-icon><Timer /></el-icon>{{item.publishTime}}
                        </span>
                        <!-- 阅读量 -->
                        <span>
                            <el-icon><View /></el-icon>{{item.click}}
                        </span>
                        <!-- 标签 -->
                        <span style="float: right;" class="hidden-sm-and-down">
                            <el-row>
                                <el-tag type="primary">面试题</el-tag>
                                <el-tag type="primary">打卡</el-tag>
                            </el-row>
                        </span>
                    </p>
                </div>
            </div>
        </a>
        <el-divider v-if="index != articleInfo.length - 1" style="margin-top: -10px;margin-bottom: 1px;"/>
    </div>
</template>

<script setup>
import { reactive } from 'vue';


const articleInfo = reactive([
    {
        articleId:1,
        articleClassifyName:'技术',
        articleTitle:'什么是OSI七层模型',
        articleImgLitimg:'',
        articleDase:'最开始的OSI七层模型是由物理层、数据链路层、网络层、传输层、会话层、表示层、应用层组成，之后不断优化后形成了现在流行的TCP/IP模型',
        userName:'zhn',
        publishTime:'2023/2/24',
        click:20
    },
    {
        articleId:2,
        articleClassifyName:'技术',
        articleTitle:'什么是OSI七层模型',
        articleImgLitimg:'',
        articleDase:'最开始的OSI七层模型是由物理层、数据链路层、网络层、传输层、会话层、表示层、应用层组成，之后不断优化后形成了现在流行的TCP/IP模型',
        userName:'zhn',
        publishTime:'2023/2/24',
        click:20
    },
    {
        articleId:3,
        articleClassifyName:'技术',
        articleTitle:'什么是OSI七层模型',
        articleImgLitimg:'',
        articleDase:'最开始的OSI七层模型是由物理层、数据链路层、网络层、传输层、会话层、表示层、应用层组成，之后不断优化后形成了现在流行的TCP/IP模型',
        userName:'zhn',
        publishTime:'2023/2/24',
        click:20
    },
    {
        articleId:4,
        articleClassifyName:'技术',
        articleTitle:'什么是OSI七层模型',
        articleImgLitimg:'',
        articleDase:'最开始的OSI七层模型是由物理层、数据链路层、网络层、传输层、会话层、表示层、应用层组成，之后不断优化后形成了现在流行的TCP/IP模型',
        userName:'zhn',
        publishTime:'2023/2/24',
        click:20
    },
    {
        articleId:5,
        articleClassifyName:'技术',
        articleTitle:'什么是OSI七层模型',
        articleImgLitimg:'',
        articleDase:'最开始的OSI七层模型是由物理层、数据链路层、网络层、传输层、会话层、表示层、应用层组成，之后不断优化后形成了现在流行的TCP/IP模型',
        userName:'zhn',
        publishTime:'2023/2/24',
        click:20
    },
])
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