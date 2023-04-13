<!-- 评论列表 -->
<template>
    <!-- 简介条 -->
    <el-card style="margin-top: 0.5em;">
        共{{ state.commentList.length }}条评论
        <el-button type="primary" :icon="Edit" class="reply-button"
        @click="replyOnClick(0)">发布评论</el-button>
    </el-card>
    <!-- 评论 -->
    <el-card v-for="item in state.commentList" style="margin-top: 0.5em;">
        <!-- 头像、昵称、时间 -->
        <el-row style="display: flex; align-items: center;">
            <el-avatar :size="25" :src="item.avatarUrl" />
            <el-link :underline="false" style="margin-left: 5px;">{{ item.nickname }}</el-link>
            <p class="content" style="margin-left: auto;">评论于{{ dateFilter(item.createTime) }}</p>
            <el-link :underline="false" :icon="Edit" style="margin-left: 5px;"
            @click="replyOnClick(item.commentId)">回复</el-link>
        </el-row>
        <!-- 内容 -->
        <p v-html="item.content" style="margin-top: 0.4em;"></p>
        <!-- 回复评论 -->
        <el-card v-for="reply in item.replyComments" style="margin-top: 0.5em;">
            <!-- 头像、昵称、时间 -->
            <el-row style="display: flex; align-items: center;">
                <el-avatar :size="25" :src="reply.avatarUrl" />
                <el-link :underline="false" style="margin-left: 5px;">
                    {{ reply.nickname }} 回复了 {{ item.nickname }}
                </el-link>
                <p class="content" style="margin-left: auto;">评论于{{ dateFilter(reply.createTime) }}</p>
                <el-link :underline="false" :icon="Edit" style="margin-left: 5px;"
                @click="replyOnClick(reply.commentId)">回复</el-link>
            </el-row>
            <!-- 内容 -->
            <p v-html="reply.content" style="margin-top: 0.4em;"></p>
        </el-card>
    </el-card>
    <!-- 评论编辑抽屉 -->
    <el-drawer v-model="state.drawer" direction="btt" size="50%">
        <template #header>
            <h4>评论千万条，友善第一条</h4>
        </template>
        <template #default>
            <div style="border: 1px solid #ccc">
                <Toolbar
                    style="border-bottom: 1px solid #ccc"
                    :editor="editorRef"
                    :defaultConfig="toolbarConfig"
                    :mode="mode"
                />
                <Editor
                    style="height: 500px; overflow-y: hidden;"
                    v-model="state.content"
                    :defaultConfig="editorConfig"
                    :mode="mode"
                    @onCreated="handleCreated"
                />
            </div>
        </template>
        <template #footer>
            <div style="flex: auto">
                <el-button @click="cancelClick">取消</el-button>
                <el-button type="primary" @click="confirmClick">发布</el-button>
            </div>
        </template>
    </el-drawer>
</template>

<script setup>
import { defineProps, onMounted, reactive, onBeforeUnmount, shallowRef } from 'vue';
import { useUserStore } from '@/stores/user.js'
import { ElMessage } from 'element-plus';
import { getCommentList,addComment } from '@/api/community.js'
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { Edit } from '@element-plus/icons-vue'
import dateFilter from '@/utils/time.js'

const props = defineProps(['articleId'])
const userStore = useUserStore()
const state = reactive({
    // 评论列表数据
    commentList:[],
    // 控制抽屉的展开
    drawer:false,
    // 评论内容
    content:'',
    // 记录评论的父评论Id
    parentCommentId:0,
})
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()
const mode =  'default'
const toolbarConfig = {}
const editorConfig = { placeholder: '请输入内容...' }

// 抽屉取消按钮响应
function cancelClick() {
    state.drawer = false
}
// 抽屉发布按钮响应
function confirmClick() {
    addComment(userStore.userInfo,state.content,props.articleId,state.parentCommentId).then(res => {
        if(res.data.code === 200) {
            ElMessage.success('评论发布成功')
            state.drawer = false
            state.content = ''
            getData()
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
}
const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}
// 发布评论响应
const replyOnClick = (parentCommentId) => {
    if(!userStore.isLogin()) {
        ElMessage.error("必须要登录才能发布评论")
        return
    }
    state.parentCommentId = parentCommentId,
    state.drawer = true
}

// 页面挂载时加载列表数据
onMounted(() => {
    getData()
})
// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
})

const getData = () => {
    getCommentList(props.articleId).then(res => {
        state.commentList = res.data.data
    }).catch(() => {
        console.log('请求发送失败');
    })
}
</script>

<style scoped>
.reply-button {
    float: right;
    margin-top: -5px;
    width:120px
}
.content {
    font-size: 10px;
    color: #999;
    text-align: justify;
}
</style>