<!-- 自定义手机端菜单列表 -->
<template>
    <el-menu :default-active="route.path" class="el-menu-vertical-demo" text-color="#646464"
        active-text-color="#2FA7B9" :router="true">
        <el-menu-item index="/home" @click="closeDrawer">
            <span>首页</span>
        </el-menu-item>
        <el-menu-item index="/team" @click="closeDrawer">
            <span>组队</span>
        </el-menu-item>
        <el-menu-item index="/community" @click="closeDrawer">
            <span>社区</span>
        </el-menu-item>
        <el-menu-item index="/info" v-show="userStore.isLogin()" @click="closeDrawer">
            <span>个人</span>
        </el-menu-item>
        <!-- 根据登录态进行不同样式展示 -->
        <el-menu-item index="/login" v-show="!userStore.isLogin()" @click="closeDrawer">
            <span>登录/注册</span>
        </el-menu-item>
        <el-menu-item index="/backstageHome" v-show="userStore.isLogin()" @click="closeDrawer">
            <span>进入后台</span>
        </el-menu-item>
        <el-menu-item v-show="userStore.isLogin()" >
            <el-link type="danger" :underline="false" @click="logout()">退出登录</el-link>
        </el-menu-item>
    </el-menu>
</template>

<script>
    import { onMounted, } from "vue"
    import { useRoute } from "vue-router"
    import { getCurrentUser } from "@/api/common.js"
    import { userLogout } from "@/api/login.js"
    import { useUserStore } from "@/stores/user";
    import { storeToRefs } from 'pinia'

    export default {
        emits: ["onCloseDrawer"],
        setup(props, {emit}) {
            const route = useRoute();
            // 获取pinia中公共存储的用户信息，
            const userStore = useUserStore()
            const { userInfo } = storeToRefs(userStore)

            onMounted(() => {
                // 获取用户信息
                getCurrentUser().then(res => {
                    if(res.data.code === 200) {
                        userStore.updateUserInfo(res.data.data)
                    }
                }).catch(() => {
                    console.log('请求发送失败');
                })
            });
            // 关闭抽屉
            const closeDrawer = ()=>{
                emit("onCloseDrawer", false);
            }
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
            return {
                route,
                closeDrawer,
                logout,
                userInfo,
                userStore
            }
        }
    }
</script>

<style scoped>
    :global(.el-drawer__body) {
        padding: 0px !important;
    }

    .el-menu-item:hover {
        background-color: rgba(47, 167, 185, 0.1);
    }

    .el-menu-item.is-active {
        border-left: 5px solid #409EFF;
    }

    :deep(.el-dropdown-menu__item:not(.is-disabled):focus) {
        background-color: rgba(47, 167, 185, 0.1);
        color: #2fa7b9;
    }

    .el-menu-item * {
        vertical-align: baseline;
    }
</style>