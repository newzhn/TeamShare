<!-- 文章列表面板 -->
<template>
    <!-- 搜索框、文章发布按钮 -->
    <el-card class="form-wrapper">
        <div class="card-content">
            <el-input v-model="state.searchInput" placeholder="请输入你想搜索的文章吧（回车进行搜索）" 
            :prefix-icon="Search" clearable class="left-element" @keyup.enter="search"/>
            <el-button type="primary" class="right-element" @click="putArticleOnclick()">发布文章</el-button>
        </div>
    </el-card>
    <!-- 文章列表 -->
    <ArticleList :articleList="state.articleList"></ArticleList>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user.js'
import { ElMessage } from 'element-plus';
import { reactive, onMounted, watch, defineProps } from 'vue'
import { getArticleList,searchArticleList } from '@/api/community.js'
import ArticleList from './ArticleList.vue';

const props = defineProps(['articleTab'])
const router = useRouter()
const store = useUserStore()
const state = reactive({
    articleList:[],
    searchInput:''
})

// 搜索响应
const search = () => {
    searchArticleList(state.searchInput).then(res => {
        state.articleList = res.data.data
    }).catch(() => {
        console.log('请求发送失败');
    })
    
}
// 发布文章按钮响应事件
const putArticleOnclick = () => {
    if(!store.isLogin()) {
        ElMessage.error('用户登录后才可以发布文章')
        return
    }
    router.push('/community/add')
}

// 获取数据函数
const getData = () => {
    getArticleList().then(res => {
        state.articleList = res.data.data
    }).catch(() => {
        console.log('请求发送失败');
    })
}

// 页面挂载时获取一次文章列表
onMounted(() => {
    getData()
})

// 监视父组件传入的tab页面值，每次发生变化时都再次获取一遍数据
watch(props.articleTab,(newValue,oldValue) => {
    getData()
})
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