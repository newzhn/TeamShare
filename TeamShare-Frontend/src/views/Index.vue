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
                    <PageLayout></PageLayout>
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
import Nav from '@/components/common/Nav.vue'
import Footer from '@/components/common/Footer.vue'
import BackTop from '@/components/common/BackTop.vue';
import PhoneNav from '@/components/common/PhoneNav.vue';
import PageLayout from '@/components/layout/PageLayout.vue';
</script>

<style scoped>
/* 首页底部版权样式 */
.front_footer {
    border-top: 1px solid #ebebeb;
    background: #fff;
}
</style>