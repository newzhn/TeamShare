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
            <el-form-item label="分类：" prop="categary">
                <el-select v-model="form.categary" placeholder="文章所属分类" style="width: 100%;">
                    <el-option v-for="item in state.categaryOptions" :key="item.value" :label="item.label"
                        :value="item.value" />
                </el-select>
            </el-form-item>
            <!-- 标签 -->
            <el-form-item label="标签：" prop="tags">
                <el-select v-model="form.tags" filterable allow-create multiple placeholder="请选择文章标签" style="width: 100%;">
                    <el-option-group v-for="group in state.tagOptions" :key="group.label" :label="group.label">
                        <el-option v-for="item in group.options" :key="item.value" :label="item.label"
                            :value="item.value" />
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
    categary: '',
    tags: []
})
// 表单校验规则
const rules = reactive({
    title: [
        { required: true, message: '文章标题不能为空哦', trigger: 'blur' },
        { min: 5, max: 20, message: '标题长度必须要在5-20字符之间', trigger: 'blur' },
    ],
    categary: [
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
    categaryOptions: [
        {
            value: 'Option1',
            label: 'Option1',
        },
        {
            value: 'Option2',
            label: 'Option2',
        },
        {
            value: 'Option3',
            label: 'Option3',
        },
        {
            value: 'Option4',
            label: 'Option4',
        },
        {
            value: 'Option5',
            label: 'Option5',
        },
    ],
    tagOptions: [
        {
            label: '专业技能',
            options: [
                {
                    value: 'Java',
                    label: 'Java',
                },
                {
                    value: 'C++',
                    label: 'C++',
                },
                {
                    value: 'Python',
                    label: 'Python',
                },
                {
                    value: 'C',
                    label: 'C',
                },
                {
                    value: 'Go',
                    label: 'Go',
                },
                {
                    value: 'C#',
                    label: 'C#',
                },
                {
                    value: 'PHP',
                    label: 'PHP',
                },
            ],
        },
        {
            label: '发展方向',
            options: [
                {
                    value: '前端',
                    label: '前端',
                },
                {
                    value: '后端',
                    label: '后端',
                },
                {
                    value: '算法',
                    label: '算法',
                },
                {
                    value: '网络',
                    label: '网络',
                },
                {
                    value: '测试',
                    label: '测试',
                },
                {
                    value: '嵌入式',
                    label: '嵌入式',
                },
            ],
        },
        {
            label: '当前状态',
            options: [
                {
                    value: '大学生',
                    label: '大学生',
                },
                {
                    value: '已就业',
                    label: '已就业',
                },
                {
                    value: '单身',
                    label: '单身',
                },
                {
                    value: '学习中',
                    label: '学习中',
                },
            ],
        },
    ]
})

// 发布按钮响应
const onSubmit = async (formEl) => {
    if (!formEl) return
    if (form.content === "<p><br></p>") {
        ElMessage.error('文章内容不可以为空哦')
        return
    }
    await formEl.validate((valid, fields) => {
        if (valid) {
        console.log('submit!')
        } else {
        console.log('error submit!', fields)
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
    
})

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
})
</script>

<style scoped></style>