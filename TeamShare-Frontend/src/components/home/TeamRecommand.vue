<!-- 队伍推荐卡片 -->
<template>
    <el-row :gutter="10">
        <el-col v-for="team in state.teamList" :span="12">
            <el-card shadow="hover" class="m-card">
                <el-descriptions :title="'队伍 | ' + team.teamName">
                    <template #extra>
                        <el-tag type="primary" round 
                        v-if="team.teamSize === team.members.length">学习中</el-tag>
                        <el-tag type="warning" round
                        v-if="team.teamSize > team.members.length">招人中</el-tag>
                    </template>
                    <el-descriptions-item>{{ team.teamDescribe }}</el-descriptions-item>
                </el-descriptions>
                <div style="position: absolute;bottom: 30px;display:flex; align-items:center;">
                    <el-avatar v-for="member in team.members" :size="20" :src="member.avatarUrl" />
                    <p style="font-size: small;margin-left: 10px;">{{ team.members.length }} 人学习中</p>
                </div>
                <el-link style="float: right;margin-top: 23px;" 
                type="primary" :underline="false" @click="toCommunity()">前往组队<el-icon><DArrowRight /></el-icon>
                </el-link>
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { onMounted, reactive } from 'vue';
import { getRecommandTeamList } from '@/api/home.js'

const router = useRouter()
const state = reactive({
    teamList:[]
})

const toCommunity = () => {
    router.push('/community')
}

onMounted(() => {
    getRecommandTeamList().then(res => {
        state.teamList = res.data.data
    }).catch(() => {
        console.log('请求发送失败');
    })
})
</script>

<style scoped>
    .m-card {
        border-radius: 6px;
        height: 170px;
        position: relative;
    }

    .m-botton {
        position: absolute;
        bottom: 30px;
    }
</style>