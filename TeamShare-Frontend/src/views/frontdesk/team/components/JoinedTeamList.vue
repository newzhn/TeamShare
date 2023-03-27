<!-- 已加入队伍卡片 -->
<template>
    <el-card class="resource" v-loading="state.loading" element-loading-text="玩命加载中..." shadow="hover">
        <div class="resource_title">
            <h3 class="title"><b>已加入队伍</b></h3>
        </div>
        <div class="resource_layout">
            <div v-for="(team,index) in state.joinedTeamList" :key="team.teamId">
                <div class="contentBox" v-if="index===0">
                    <!-- 队伍名和队长名 -->
                    <h4 class="title">{{ team.teamName }}</h4>
                    <p>队长：{{ team.captain.nickname }} · </p>
                    <el-row style="margin-top: 10px;">
                        <el-col :span="16">
                            <!-- 队员头像列表 -->
                            <el-avatar v-for="member in team.members" :key="member.userId" :size="20" :src="member.avatarUrl" />
                        </el-col>
                        <el-col :span="8">
                            <el-link style="float: right;" type="primary" :underline="false">前往学习<el-icon><DArrowRight /></el-icon></el-link>
                        </el-col>
                    </el-row>
                </div>
                <div class="contentBox item" v-if="index > 0">
                    <div class="articleContent">
                        <h4 class="title">{{ team.teamName }}</h4>
                        <p>队长：{{ team.captain.nickname }} · </p>
                    </div>
                    <el-row style="margin-top: 10px;">
                        <el-col :span="16">
                            <!-- 队员头像列表 -->
                            <el-avatar v-for="member in team.members" :key="member.userId" :size="20" :src="member.avatarUrl" />
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
import { reactive, onMounted, watch } from 'vue';
import { getJoinedTeamList } from '@/api/frontdesk/team.js'
import { useTeamStore } from '../../../../stores/team';

const store = useTeamStore()

const state = reactive({
    // 当前组件加载状态
    loading:false,
    // 已加入队伍列表
    joinedTeamList:[]
})

const getData = () => {
    getJoinedTeamList().then(res => {
        if(res.data.code === 200) {
            state.joinedTeamList = res.data.data
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
}

// 组件加载时获取一次加入队伍信息
onMounted(() => {
    getData()
})

// 监视全局状态中的joinFlag，发生变化时重新获取数据
watch(() => store.joinFlag, (newVal, oldVal) => {
    getData()
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