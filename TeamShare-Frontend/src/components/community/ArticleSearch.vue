<!-- 文章搜索 -->
<template>
    <el-card class="form-wrapper hidden-xs-only">
        <div class="card-content">
            <el-input v-model="state.searchInput" placeholder="请输入你想搜索的文章吧（回车进行搜索）" 
            :prefix-icon="Search" clearable class="left-element" @keyup.enter="search"/>
            <el-button type="primary" class="right-element" @click="putArticleOnclick()">发布文章</el-button>
        </div>
    </el-card>
</template>

<script setup>
import { reactive } from 'vue';

const state = reactive({
    searchInput:''
})

const putArticleOnclick = () => {

}
</script>

<style scoped>
.card-content {
    display: flex;
    align-items: center;
}
.left-element {
    flex: 1; /* 设置左侧元素占据剩余宽度 */
}

.right-element {
    width: 150px;
    margin-left: 10px; /* 可根据需要进行调整 */
}
.form-wrapper {
    margin-bottom: 0.7em; /* 设置上下间隔固定 */
}
</style>