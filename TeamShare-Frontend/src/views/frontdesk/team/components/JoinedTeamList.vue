<!-- 已加入队伍卡片 -->
<template>
    <el-card class="resource" v-loading="state.loading" element-loading-text="玩命加载中..." shadow="hover">
        <div class="resource_title">
            <h3 class="title"><b>已加入队伍</b></h3>
        </div>
        <div class="resource_layout">
            <div v-for="(item,index) in articleList">
                <div class="contentBox" v-if="index===0">
                    <h4 class="title">{{item.articleTitle}}</h4>
                    <p>队长：{{item.articleClassifyName}} · </p>
                    <el-row style="margin-top: 10px;">
                        <el-col :span="16">
                            <el-avatar :size="20" src="https://q1.qlogo.cn/g?b=qq&nk=1485359480&s=100" />
                            <el-avatar :size="20" src="https://q1.qlogo.cn/g?b=qq&nk=2571469810&s=100" />
                            <el-avatar :size="20" src="https://q1.qlogo.cn/g?b=qq&nk=919968602&s=100" />
                            <el-avatar :size="20" src="https://q1.qlogo.cn/g?b=qq&nk=969025821&s=100" />
                        </el-col>
                        <el-col :span="8">
                            <el-link style="float: right;" type="primary" :underline="false">前往学习<el-icon><DArrowRight /></el-icon></el-link>
                        </el-col>
                    </el-row>
                </div>
                <div class="contentBox item" v-if="index > 0">
                    <div class="articleContent">
                        <h4 class="title">{{item.articleTitle}}</h4>
                        <p>队长：{{item.articleClassifyName}} · </p>
                    </div>
                    <el-row style="margin-top: 10px;">
                        <el-col :span="16">
                            <el-avatar :size="20" src="https://q1.qlogo.cn/g?b=qq&nk=1485359480&s=100" />
                            <el-avatar :size="20" src="https://q1.qlogo.cn/g?b=qq&nk=2571469810&s=100" />
                            <el-avatar :size="20" src="https://q1.qlogo.cn/g?b=qq&nk=919968602&s=100" />
                            <el-avatar :size="20" src="https://q1.qlogo.cn/g?b=qq&nk=969025821&s=100" />
                        </el-col>
                        <el-col :span="8">
                            <el-link style="float: right;" type="primary" :underline="false">前往学习<el-icon><DArrowRight /></el-icon></el-link>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </div>
    </el-card>
</template>

<script setup>
import { reactive } from 'vue';

const state = reactive({
    loading:false
})
const articleList = reactive([
    {
        articleId:1,
        articleTitle:'24届考研小组',
        articleImgLitimg:'',
        articleClassifyName:'亦不可止',
        publishTime:'2023/2/23',

    },
    {
        articleId:1,
        articleTitle:'备战春招Java实习',
        articleImgLitimg:'',
        articleClassifyName:'黑沫渃DeSplendor.Ghast',
        publishTime:'2023/2/23',

    },
    {
        articleId:1,
        articleTitle:'物理实验小组',
        articleImgLitimg:'',
        articleClassifyName:'橘子皮',
        publishTime:'2023/2/23',

    },
])
</script>

<style scoped>
    .resource {
        border-radius: 6px;
        margin-bottom: 20px;
    }

    .resource_title {
        margin-bottom: 15px;
        margin-top: -10px;
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid rgb(238, 238, 238);
        overflow: hidden;
    }

    .resource_title .title {
        line-height: 40px;
        color: #474749;
    }

    .resource_title .title b {
        display: inline-block;
        border-bottom: 4px solid #2fa7b9;
    }

    .resource_layout .contentBox {
        margin-bottom: 15px;
        position: relative;
        overflow: hidden;
        font-size: 12px;
        color: #999;
        line-height: 18px;
    }

    .resource_layout .contentBox h4:hover {
        color: #2fa7b9;
    }

    .resource_layout .contentBox span {
        cursor: pointer;
    }

    .resource_layout .bannerImg {
        display: block;
        width: 100%;
        object-fit: cover;
        margin-bottom: 10px;
        border-radius: 6px;
    }

    .resource_layout .title {
        font-size: 15px;
        line-height: 24px;
        margin-bottom: 10px;
        color: #727272;
        overflow: hidden;
        -webkit-box-orient: vertical;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        text-align: justify;
        cursor: pointer;
    }

    .resource_layout p {
        font-size: 12px;
        color: #999;
    }

    .resource_layout .contentBox.item {
        padding-top: 10px;
        border-top: 1px solid #eee;
    }

    .resource_layout .contentBox.item .banner {
        width: 30%;
        height: 60px;
        overflow: hidden;
        float: left;
        border-radius: 4px;
        margin-right: 15px;
        cursor: pointer;
    }

    .resource_layout .contentBox.item .banner img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

    .my-link {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
</style>