<!-- 队伍推荐卡片 -->
<template>
    <el-row :gutter="10">
        <el-col :span="12">
            <el-card shadow="hover" class="m-card">
                <el-descriptions title="队伍 | Java学习小组">
                    <template #extra>
                        <el-tag type="primary" round>学习中</el-tag>
                    </template>
                    <el-descriptions-item>一个人学习太无聊了，想找些人一起，不限Java</el-descriptions-item>
                </el-descriptions>
                <el-tag style="position: absolute;bottom: 30px;" type="primary">失败总是贯串人生始终</el-tag>
                <el-link style="float: right;margin-top: 23px;" type="primary" :underline="false">前往组队<el-icon><DArrowRight /></el-icon></el-link>
            </el-card>
        </el-col>
        <el-col :span="12">
            <el-card shadow="hover" class="m-card">
                <el-descriptions title="队伍 | 考研打卡">
                    <template #extra>
                        <el-tag type="warning" round>招人中</el-tag>
                    </template>
                    <el-descriptions-item>备战24届考研，共勉</el-descriptions-item>
                </el-descriptions>
                <el-tag style="position: absolute;bottom: 30px;" type="primary">4人</el-tag>
                <el-link style="float: right;margin-top: 23px;" type="primary" :underline="false">前往组队<el-icon><DArrowRight /></el-icon></el-link>
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup>
import { reactive } from 'vue';


const teamInfo = reactive([
    {
        teamId:1,
        teamName:'Java学习小组',
        outLine:'一个人学习太无聊了，想找些人一起，不限Java，欢迎大家加入',

    }
])
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