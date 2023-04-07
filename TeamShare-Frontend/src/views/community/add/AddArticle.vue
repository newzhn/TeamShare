<!-- 文章发布页 -->
<template>
    <el-card>
        <el-form ref="articleFormRef" :rules="rules" :model="form" label-width="70px">
            <!-- 标题 -->
            <el-form-item label="标题：" prop="title">
                <el-input v-model="form.title" type="text" maxlength="20" placeholder="文章标题限定在20字符以内" clearable
                    show-word-limit />
            </el-form-item>
            <!-- 内容 -->
            <el-form-item label="内容：" prop="content">
                <div style="border: 1px solid #ccc">
                    <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :defaultConfig="toolbarConfig"
                        :mode="mode" />
                    <Editor style="height: 500px; overflow-y: hidden;" v-model="form.content" :defaultConfig="editorConfig"
                        :mode="mode" @onCreated="handleCreated" />
                </div>
            </el-form-item>
            <!-- 分类 -->
            <el-form-item label="分类：" prop="category">
                <el-select v-model="form.category" placeholder="文章所属分类" style="width: 100%;">
                    <el-option v-for="item in state.categoryOptions" :key="item.categoryId" :label="item.categoryName"
                        :value="item.categoryId" />
                </el-select>
            </el-form-item>
            <!-- 标签 -->
            <el-form-item label="标签：" prop="tags">
                <el-select v-model="form.tags" filterable allow-create multiple placeholder="请选择文章标签" style="width: 100%;">
                    <el-option-group v-for="group in state.tagOptions" :key="group.tagId" :label="group.tagName">
                        <el-option v-for="item in group.childrenTags" :key="item.tagId" :label="item.tagName"
                            :value="item.tagId" />
                    </el-option-group>
                </el-select>
            </el-form-item>
            <!-- 发布/取消按钮 -->
            <el-form-item>
                <el-button type="primary" @click="onSubmit(articleFormRef)">发布</el-button>
                <el-button @click="onCancel(articleFormRef)">取消</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script setup>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { reactive } from 'vue'
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { getCategoryAllList,getTagAllList,addArticle } from '@/api/community.js'

const router = useRouter()
const articleFormRef = ref('')
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()
// 编辑器配置
const mode = 'default'
const toolbarConfig = {}
const editorConfig = { placeholder: '请输入文章内容...' }
// do not use same name with ref
const form = reactive({
    title: '',
    content: '',
    category: '',
    tags: []
})
// 表单校验规则
const rules = reactive({
    title: [
        { required: true, message: '文章标题不能为空哦', trigger: 'blur' },
        { min: 3, max: 20, message: '标题长度必须要在3-20字符之间', trigger: 'blur' },
    ],
    category: [
        {
            required: true,
            message: '文章标签不能为空哦',
            trigger: 'change',
        },
    ],
    tags: [
        {
            required: true,
            message: '文章标签不能为空哦',
            trigger: 'change',
        },
    ],
})
// 页面状态
const state = reactive({
    categoryOptions: [],
    tagOptions: []
})

// 发布按钮响应
const onSubmit = async (formEl) => {
    if (!formEl) return
    if (form.content === "<p><br></p>") {
        ElMessage.error('文章内容不可以为空哦')
        return
    }
    await formEl.validate((valid, fields) => {
        // 校验通过则发送add请求
        if (valid) {
            addArticle(form).then(res => {
                if(res.data.code === 200) {
                    ElMessage.success('发布文章成功')
                    setTimeout(() => {router.push('/community')},2000)
                }
            }).catch(() => {
                console.log('请求发送失败');
            })
        }
    })
}
// 取消按钮响应
const onCancel = (formEl) => {
    if (!formEl) return
    formEl.resetFields()
    router.go(-1)
}
// 记录 editor编辑器 实例，重要！
const handleCreated = (editor) => {
    editorRef.value = editor
}

// 发送请求获取页面所需分类和标签内容
onMounted(() => {
    // 获取分类列表
    getCategoryAllList().then(res => {
        if(res.data.code === 200) {
            state.categoryOptions = res.data.data
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
    // 获取标签列表
    getTagAllList().then(res => {
        if(res.data.code === 200) {
            state.tagOptions = res.data.data
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
})

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
})
</script>

<style scoped></style>