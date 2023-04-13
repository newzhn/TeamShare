<!-- 好友推荐卡片 -->
<template>
    <el-card class="resource" v-loading="state.loading" element-loading-text="玩命加载中..." shadow="hover">
        <div class="resource_title">
            <h3 class="title"><b>推荐好友</b></h3>
        </div>
        <div class="resource_layout">
            <!-- 推荐好友展示列表 -->
            <div v-for="(user,index) in state.recommendUserList" :key="user.userId">
                <el-row :gutter="10">
                    <el-space :size="0">
                        <!-- 头像 -->
                        <el-col :span="5">
                            <el-avatar :size="40" :src="user.avatarUrl" />
                        </el-col>
                        <!-- 个人信息 -->
                        <el-col :span="19">
                            <el-space wrap direction="vertical" alignment="flex-start" :size="4">
                                <!-- 昵称 -->
                                <el-row>
                                    <el-link class="my-link" to="https://element-plus.org" target="_blank" :underline="false" >
                                        {{ user.nickname }}
                                    </el-link>
                                </el-row>
                                <!-- 标签,只展示两个标签 -->
                                <el-row>
                                    <el-space>
                                        <el-tag v-if="user.tagNames.length != 0" v-for="tagIndex in 2" type="info" effect="light" size="small">{{ user.tagNames[tagIndex - 1] }}</el-tag>
                                        <el-tag type="info" effect="light" size="small">....</el-tag>
                                    </el-space>
                                </el-row>
                            </el-space>
                        </el-col>
                    </el-space>
                </el-row>
                <el-divider v-if="index != state.recommendUserList.length - 1" style="margin-top: 8px;margin-bottom: 8px;"/>
            </div>
        </div>
    </el-card>
</template>

<script setup>
import { ref,onMounted } from 'vue';
import { getRecommandUsers } from '@/api/home.js';
import { reactive } from 'vue';

const state = reactive({
    loading:true,
    recommendUserList:[]
})

onMounted(async () => {
    await getRecommandUsers().then(res => {
        state.recommendUserList = res.data.data
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

    .my-link {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
</style>