<!-- 前台导航 -->
<template>
    <el-menu :default-active="route.path" class="el-menu-demo" mode="horizontal" :ellipsis="false" :router="true">
        <!-- LOGO -->
        <h2 class="m-title">TeamShare</h2>
        <div class="flex-grow" />
        <el-menu-item class="hidden-xs-only" index="/home">首页</el-menu-item>
        <el-menu-item class="hidden-xs-only" index="/team">组队</el-menu-item>
        <el-menu-item class="hidden-xs-only" index="/community">社区</el-menu-item>
        <el-menu-item class="hidden-xs-only" index="/info" v-if="userStore.isLogin()">个人</el-menu-item>
        <!-- 根据用户登录态决定展示样式 -->
        <router-link v-if="!userStore.isLogin()" to="/login" class="loginA hidden-xs-only">登录/注册</router-link>
        <el-dropdown class="hidden-xs-only" v-if="userStore.isLogin()">
            <el-avatar :size="40" style="margin-top: 8px;margin-left: 20px;" :src="userInfo.avatarUrl"/>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item>
                        <el-link href="/info" :underline="false">个人主页</el-link>
                    </el-dropdown-item>
                    <!-- 只有管理员角色展示此选项 -->
                    <el-dropdown-item v-if="userInfo.userRole === 2">
                        <el-link href="/admin" :underline="false">进入后台</el-link>
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
import { onMounted } from "vue";
import { useRoute } from "vue-router"
import { useUserStore } from "@/stores/user";
import { storeToRefs } from 'pinia'
import { getCurrentUser } from "@/api/common.js"
import { userLogout } from "@/api/login.js"

const route = useRoute()
// 获取pinia中公共存储的用户信息，
const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)

// 退出登录
const logout = () => {
    userLogout().then(res => {
        if(res.data.code === 200 && res.data.data) {
            // 清除store中存储的登录信息
            userStore.updateUserInfo()
            location.reload();
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
    
}

// 加载当前登录用户信息
onMounted(() => {
    getCurrentUser().then(res => {
        if(res.data.code === 200) {
            // 将信息存储store
            userStore.updateUserInfo(res.data.data)
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

/* 默认样式，标题不居中 */
.m-title {
    color: #409EFF;
    margin-top: 10px;
}

/* 在移动设备上，标题居中 */
@media screen and (max-width: 768px) {
    .m-title {
        margin-left: 25%;
    }
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