<!-- 社区页 -->
<template>
    <el-row :gutter="20">
        <!-- 左侧具体内容，占17列 -->
        <el-col :xs="24" :sm="24" :md="17" :lg="17" :xl="17">
            <ArticleTab></ArticleTab>
        </el-col>
        <!-- 右侧边栏，占7列 -->
        <el-col class="hidden-sm-and-down" :md="7" :lg="7" :xl="7">
            <!-- 必读榜 -->
            <MustReadList></MustReadList>
        </el-col>
    </el-row>
</template>

<script setup>
import MustReadList from '../../components/community/article/MustReadList.vue';
import ArticleTab from '@/components/community/article/ArticleTab.vue'

</script>

<style scoped>

</style>