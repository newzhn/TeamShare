<!-- 热点文章卡片 -->
<template> 
    <el-card class="resource" v-loading="state.loading" element-loading-text="玩命加载中..." shadow="hover">
        <div class="resource_title">
            <h3 class="title"><b>文章推荐</b></h3>
        </div>
        <div class="resource_layout">
            <div v-for="(item,index) in state.recommendArticleList">
                <div class="contentBox" v-if="index===0">
                    <a :href="'/community/article/'+item.articleId" :title="item.title">
                        <h4 class="title">{{item.title}}</h4>
                        <p>{{item.category.categoryName}} · {{dateFilter(item.createTime)}}</p>
                    </a>
                </div>
                <div class="contentBox item">
                    <spana :href="'/community/article/'+item.articleId" :title="item.title">
                        <div class="articleContent">
                            <h4 class="title">{{item.title}}</h4>
                            <p>{{item.category.categoryName}} · {{dateFilter(item.createTime)}}</p>
                        </div>
                    </spana>
                </div>
            </div>
        </div>
    </el-card>
</template>

<script setup>
import { onMounted, reactive } from 'vue';
import { getRecommandArticleList } from '@/api/home.js'
import dateFilter from '@/utils/time.js'

const state = reactive({
    loading:true,
    recommendArticleList:[]
})

onMounted(() => {
    getRecommandArticleList().then(res => {
        state.recommendArticleList = res.data.data
        state.loading = false
    }).catch(() => {
        console.log('请求发送失败');
    })
    
})
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
        padding-top: 15px;
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
    /* .technology_layout .contentBox.item .title {
        font-size: 15px;
        overflow: hidden;
        -webkit-box-orient: vertical;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        text-align: justify;
        cursor: pointer;
        line-height: 20px;
        color: #727272;
    } */

    .technology_layout .contentBox.item .title:hover{
        color: #2fa7b9;
    }

    .articleContent {
        width: 100%;
        float: left;
        word-break: break-all;
        text-align: justify;
        
    }
</style>