<!-- 个人编辑页 -->
<template>
    <el-card>
        <el-form :model="form" label-width="120px">
            <!-- 昵称 -->
            <el-form-item label="昵称：">
                <el-input v-model="form.nickname" :value="userInfo.nickname" maxlength="20" show-word-limit clearable style="width: 400px;"/>
            </el-form-item>
            <!-- 个性签名 -->
            <el-form-item label="个性签名：">
                <el-input v-model="form.signature" :value="userInfo.signature" maxlength="40" show-word-limit clearable style="width: 400px;"/>
            </el-form-item>
            <!-- 性别 -->
            <el-form-item label="性别：">
                <el-radio-group v-model="form.gender" >
                    <el-radio label="男" />
                    <el-radio label="女" />
                </el-radio-group>
            </el-form-item>
            <!-- 邮箱 -->
            <el-form-item label="邮箱：">
                <el-input v-model="form.email" :value="userInfo.email" clearable style="width: 400px;"/>
            </el-form-item>
            <!-- QQ -->
            <el-form-item label="QQ：">
                <el-input v-model="form.qq" :value="userInfo.qq" maxlength="10" show-word-limit clearable style="width: 400px;"/>
            </el-form-item>
            <!-- 个人标签 -->
            <el-form-item label="标签：">
                <el-select v-model="form.tagNames" filterable allow-create multiple placeholder="请选择你的标签" style="width: 400px;">
                    <el-option-group v-for="group in options" :key="group.label" :label="group.label">
                        <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value"/>
                    </el-option-group>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button @click="router.push('/info')">取消</el-button>
                <el-button type="primary" :loading="state.loading" @click="onSubmit">{{state.loading ? '更改中 ...' : '提交'}}</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script setup>
import { reactive,onMounted,watchEffect  } from 'vue'
import { useRouter } from "vue-router"
import { useUserStore } from "@/stores/user";
import { storeToRefs } from 'pinia'
import { updateLoginUserInfo } from '@/api/info.js'
import { ElMessage } from 'element-plus';

const router = useRouter()
// 获取pinia中公共存储的用户信息，
const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)

const state = reactive({
    loading:false
})
const form = reactive({
    userId:'',
    nickname:'',
    signature:'',
    gender:'',
    email:'',
    qq:'',
    tagNames:[],
})


const options = [
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

const onSubmit = () => {
    state.loading = true;
    updateLoginUserInfo(form).then(res => {
        if(res.data.code === 200) {
            ElMessage.success("修改个人信息成功")
            formConvert()
            router.push('/info')
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
    state.loading = false;
}

const formConvert = () => {
    userInfo.value.nickname = form.nickname
    userInfo.value.signature = form.signature
    userInfo.value.gender = form.gender
    userInfo.value.email = form.email
    userInfo.value.qq = form.qq
    userInfo.value.tagNames = form.tagNames
}

onMounted(() => {
    form.userId = userInfo.value.userId
    form.nickname = userInfo.value.nickname
    form.signature = userInfo.value.signature
    form.gender = userInfo.value.gender
    form.email = userInfo.value.email
    form.qq = userInfo.value.qq
    form.tagNames = userInfo.value.tagNames
})

</script>

<style scoped>

</style>