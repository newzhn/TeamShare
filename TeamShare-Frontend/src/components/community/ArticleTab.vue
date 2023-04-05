<!-- 文章列表tab -->
<template>
    <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="个人Blog" name="first"></el-tab-pane>
        <el-tab-pane label="热点分享" name="second"></el-tab-pane>
        <!-- 文章列表面板 -->
        <ArticleListPane></ArticleListPane>
    </el-tabs>
</template>

<script setup>
import ArticleListPane from '@/components/community/ArticleListPane.vue'
import { ref } from 'vue'

const activeName = ref('first')

const handleClick = (tab, event) => {
    console.log(tab, event)
}
</script>

<style scoped>

</style>