<!-- 手机端菜单 -->
<template>
    <!-- 打开菜单栏固钉 -->
    <div class="menu_box hidden-sm-and-up" @click="state.visible = true" title="菜单">
        <el-icon :size="20">
            <Menu />
        </el-icon>
    </div>
    <!-- 菜单列表 -->
    <div class="hidden-sm-and-up">
        <el-drawer v-model="state.visible" :show-close="true" size="55%">
            <!-- 标题 -->
            <template #header="{ close, titleId, titleClass }">
                <h2 style="color: #409EFF;">TeamShare</h2>
            </template>

            <!-- 内容 -->
            <MenuList @onCloseDrawer="closeDrawer"></MenuList>
        </el-drawer>
    </div>

</template>

<script setup>
// 引入菜单列表自定义组件
import MenuList from './MenuList.vue';
import { Menu } from '@element-plus/icons-vue'
import { ref,reactive,onMounted } from 'vue'
import { ElDrawer} from 'element-plus'

// 默认隐藏弹窗表单
const state = reactive({
    visible: false,
});
// 生命周期
onMounted(() => {
    getWindowResize()
    window.addEventListener('resize', getWindowResize)
})
// 获取屏幕尺寸
const getWindowResize = function () {
    if (window.innerWidth > 768) {
        state.visible = false;
    }
}
// 点击路由关闭抽屉
const closeDrawer = (val)=>{
    state.visible = val;
}
</script>

<style scoped>
    /* 打开菜单栏固钉 */
    .menu_box {
        height: 40px;
        width: 40px;
        background-color: white;
        border-radius: 5px;
        border: 1px solid #E9E9E9;
        color: #646464;
        position: fixed;
        display: flex;
        align-items: center;
        justify-content: center;
        box-shadow: var(--el-box-shadow-lighter);
        cursor: pointer;
        right: 40px;
        bottom: 150px;
        text-align: center;
        line-height: 50px;
        z-index: 999;
    }

    /* css注释：设置了浏览器宽度不大于768px时 */
    @media screen and (max-width: 768px) {
        .hidden-sm-and-up {
            display: block !important;
        }
    }

    @media screen and (min-width: 768px) {
        .hidden-sm-and-up {
            display: none !important;
        }
    }

    .menu_box:hover {
        /* background-color: rgba(47, 167, 185); */
        /* 1、linear-gradient() 线性渐变 2、radial-gradient() 径向渐变 3、repeating-linear-gradient() 重复线性渐变 4、repeating-radial-gradient() 重复径向渐变  */
        background: repeating-linear-gradient(#2fa7b9, #535b9a);
        color: white;
    }

    /* 抽屉菜单样式 */
    :global(.el-drawer.rtl) {
        overflow: initial;
    }

    :global(.el-drawer__close-btn) {
        margin-left: 30% !important;
    }
</style>