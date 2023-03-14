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
        <el-menu-item index="/info" @click="closeDrawer">
            <span>个人</span>
        </el-menu-item>
        <!-- 根据登录态进行不同样式展示 -->
        <el-menu-item index="/login" v-show="!userInfo" @click="closeDrawer">
            <span>登录/注册</span>
        </el-menu-item>
        <el-menu-item index="/backstageHome" v-show="userInfo" @click="closeDrawer">
            <span>已登录，进入后台</span>
        </el-menu-item>
        
    </el-menu>
</template>

<script>
    import { reactive,toRefs,onMounted, } from "vue"
    import { useRoute } from "vue-router"
    import { getCurrentUser } from "@/api/frontdesk/common.js"
    export default {
        emits: ["onCloseDrawer"],
        setup(props, {emit}) {
            const route = useRoute();
            const state = reactive({
                // 当前登录的用户信息
                userInfo: '',
            });
            onMounted(() => {
                // 获取用户信息
                getCurrentUser().then(res => {
                    if(res.data.code === 200) {
                        state.userInfo = res.data.data
                    }
                }).catch(() => {
                    console.log('请求发送失败');
                })
            });
            // 关闭抽屉
            const closeDrawer = ()=>{
                emit("onCloseDrawer", false);
            }
            return {
                ...toRefs(state),
                route,
                closeDrawer
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