<!-- 必读榜卡片 -->
<template>
    <el-affix :offset="12">
        <el-card class="resource" v-loading="state.loading" element-loading-text="玩命加载中..." shadow="hover">
            <div class="resource_title">
                <h3 class="title"><b>必读榜</b></h3>
            </div>
            <div v-for="(item, index) in state.mustReadList" :key="item.articleId">
                <el-row>
                    <span :style="{color: index < 3 ? 'orange' : 'gray', marginRight: '0.7em'}">{{ index + 1 }}</span>
                    <el-avatar :size="20" :src="item.author.avatarUrl" style="margin-right: 0.5em;"/>
                    <el-link @click="toArticle(item.articleId)" :underline="false" class="title-link">面试题 | {{ item.title }}</el-link>
                </el-row>
                <p class="content" v-html="item.content"></p>
            </div>
        </el-card>
    </el-affix>
</template>

<script setup>
import { reactive,onMounted } from 'vue';
import { getMustReadArticleList } from '@/api/community.js'
import { useRouter } from 'vue-router'

const router = useRouter()
const state = reactive({
    loading:false,
    mustReadList:[]
})

const toArticle = (id) => {
    const url='/community/article/' + id
    router.push(url)
}

onMounted(() => {
    state.loading = true
    getMustReadArticleList().then(res => {
        state.mustReadList = res.data.data
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

    .title-link {
        font-size: 16px;
        line-height: 22px;
        font-weight: 540;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .content {
        font-size: 13px;
        color: #999;
        margin-left: 20px;
        margin-top: 5px;
        margin-bottom: 15px;
        -webkit-box-orient: vertical;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        text-align: justify;
        overflow: hidden;
    }
</style>