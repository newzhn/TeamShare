<!-- 前台导航 -->
<template>
    <el-menu :default-active="route.path" class="el-menu-demo" mode="horizontal" :ellipsis="false"
        :router="true" @select="handleSelect">
        <!-- LOGO -->
        <img src="@/assets/image/logo.png" style="cursor: pointer;width:50px;" alt="TeamShare" @click="router.push({path:'/'})">
        <div class="flex-grow" />
        <el-menu-item index="/home">首页</el-menu-item>
        <el-menu-item index="/team">组队</el-menu-item>
        <el-menu-item index="/community">社区</el-menu-item>
        <el-menu-item index="/info">个人</el-menu-item>
        <router-link v-if="Object.keys(user).length === 0" to="/login" class="loginA">登录/注册</router-link>
        <el-dropdown v-if="Object.keys(user).length !== 0">
            <el-avatar :size="40" style="margin-top: 8px;margin-left: 20px;" :src="user.avatarUrl"/>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item>
                        <el-link href="/info" :underline="false">个人主页</el-link>
                    </el-dropdown-item>
                    <el-dropdown-item @click="logout()">
                        <el-link type="danger" :underline="false">退出登录</el-link>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
    </el-menu>
</template>

<script setup>
import { reactive,onMounted } from "vue";
import { useRoute,useRouter } from "vue-router"
import { getCurrentUser } from "@/api/frontdesk/home.js"
import { userLogout } from "@/api/login/login.js"
import { ElNotification } from 'element-plus'

const route = useRoute()
const router = useRouter()
const handleSelect = (key, keyPath) => {
    console.log(key, keyPath)
}
const user = reactive({})

const logout = () => {
    userLogout().then(res => {
        if(res.data.code === 200 && res.data.data) {
            location.reload();
        } else {
            ElNotification({
                title: '退出失败',
                type: 'error'
            })
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
    
}

onMounted(() => {
    getCurrentUser().then(res => {
        if(res.data.code === 200) {
            Object.assign(user, reactive(res.data.data))
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
})
</script>

<style scoped>
.flex-grow {
    flex-grow: 1;
}
.el-menu--horizontal {
    border-bottom: none;
}

.el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
    color: #2FA7B9;
    background-color: rgba(47, 167, 185, 0.1);
}

.header-img {
    display: flex;
    align-items: center;
    text-align: center;
    line-height: 90px;
}

.el-menu--horizontal>.el-menu-item a {
    color: #303133;
}

.el-menu--horizontal>.el-menu-item a:hover {
    color: #2FA7B9;
}

.loginA {
    line-height: 56px;
    font-size: 14px;
    margin-left: 10px;
}

.example-showcase .el-dropdown-link {
    cursor: pointer;
    color: var(--el-color-primary);
    display: flex;
    align-items: center;
}

:deep(.el-dropdown-menu__item:not(.is-disabled):focus) {
    background-color: rgba(47, 167, 185, 0.1);
    color: #2fa7b9;
}
</style>