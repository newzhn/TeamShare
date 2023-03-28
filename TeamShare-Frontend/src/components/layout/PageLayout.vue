<!-- 整体页面布局 -->
<template>
    <el-row :gutter="20">
        <el-col :xs="0" :sm="1" :md="1" :lg="4" :xl="4">
            <!-- 左侧间隙 -->
        </el-col>
        <el-col :xs="24" :sm="22" :md="22" :lg="16" :xl="16">
            <!-- 中间具体内容 -->
            <RouterView></RouterView>
        </el-col>
        <el-col :xs="0" :sm="1" :md="1" :lg="4" :xl="4">
            <!-- 右侧间隙 -->
        </el-col>
    </el-row>
</template>

<script setup>

</script>

<style scoped>

</style>