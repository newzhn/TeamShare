<!-- 文章搜索 -->
<template>
    <el-card class="form-wrapper">
        <div class="card-content">
            <el-input v-model="state.searchInput" placeholder="请输入你想搜索的文章吧（回车进行搜索）" 
            :prefix-icon="Search" clearable class="left-element" @keyup.enter="search"/>
            <el-button type="primary" class="right-element" @click="putArticleOnclick()">发布文章</el-button>
        </div>
    </el-card>
</template>

<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user.js'
import { ElMessage } from 'element-plus';

const router = useRouter()
const store = useUserStore()
const state = reactive({
    searchInput:''
})

const putArticleOnclick = () => {
    if(!store.isLogin()) {
        ElMessage.error('用户登录后才可以发布文章')
        return
    }
    router.push('/community/add')
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