<!-- 个人页 -->
<template>
    <el-card>
        <el-row>
            <el-avatar :size="90" :src="userInfo.avatarUrl" />
            <el-descriptions :title="userInfo.nickname" :column="1" style="margin-left: 1.5em;">
                <template #extra>
                    <el-button type="primary" @click="router.push('/info/edit')">编辑个人资料</el-button>
                </template>
                <el-descriptions-item label="ID:">{{ userInfo.userId }}</el-descriptions-item>
                <el-descriptions-item label="账户名:">{{ userInfo.userName }}</el-descriptions-item>
                <el-descriptions-item label="个性签名:">{{ userInfo.signature }}</el-descriptions-item>
                <el-descriptions-item label="性别:">{{ userInfo.gender }}</el-descriptions-item>
                <el-descriptions-item label="邮箱:">{{ userInfo.email }}</el-descriptions-item>
                <el-descriptions-item label="QQ:">{{ userInfo.qq }}</el-descriptions-item>
                <el-descriptions-item label="标签:">
                    <el-tag v-for="tagName in userInfo.tagNames" style="margin-right: 1em;">{{ tagName }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="注册时间:">{{ dateFilter(userInfo.createTime) }}</el-descriptions-item>
            </el-descriptions>
        </el-row>
    </el-card>
</template>

<script setup>
import { useRouter } from "vue-router"
import { useUserStore } from "@/stores/user";
import { storeToRefs } from 'pinia'
import dateFilter from '@/utils/time.js'

const router = useRouter()
// 获取pinia中公共存储的用户信息，
const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)


</script>

<style scoped>

</style>