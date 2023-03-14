<!-- 前台模板 -->
<template>
    <div id="frontdesk">
        <div class="common-layout">
            <el-container>
                <!-- 导航菜单 -->
                <el-header>
                    <Nav></Nav>
                </el-header>
                <!-- 页面内容 -->
                <el-main>
                    <router-view></router-view>
                </el-main>
                <!-- 底部页脚 -->
                <el-footer class="front_footer">
                    <Footer></Footer>
                </el-footer>
            </el-container>
        </div>

        <!-- Backtop 回到顶部 -->
        <BackTop></BackTop>
        <!-- 手机端菜单栏 -->
        <PhoneNav></PhoneNav>
    </div>
</template>

<script setup>
import Nav from '@/components/frontdesk/layout/Nav.vue'
import Footer from '@/components/frontdesk/layout/Footer.vue'
import BackTop from '../../components/frontdesk/layout/BackTop.vue';
import PhoneNav from '../../components/frontdesk/layout/PhoneNav.vue';

</script>

<style scoped>
/* 首页底部版权样式 */
.front_footer {
    border-top: 1px solid #ebebeb;
    background: #fff;
}
.el-container {
    min-height: 100vh;
}
</style>