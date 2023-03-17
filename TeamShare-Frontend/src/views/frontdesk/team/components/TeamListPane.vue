<!-- 队伍列表面板 -->
<template>
    <!-- 搜索框和新建按钮 -->
    <el-card class="form-wrapper hidden-xs-only">
        <div class="card-content">
            <el-input v-model="state.searchInput" placeholder="请输入你想搜索的队伍吧（回车进行搜索）" 
            :prefix-icon="Search" clearable class="left-element"/>
            <el-button type="primary" class="right-element" @click="createOnclick()">新建队伍</el-button>
        </div>
    </el-card>
    <!-- 队伍列表 -->
    <el-card class="form-wrapper">
        <template #header>
            <div class="container">
                <h3>内测学习小组</h3>
                <el-button class="btn" type="primary" plain>查看队伍</el-button>
            </div>
        </template>
        <div class="card-content" style="margin-top: -5px;">
            <el-image style="width: 100px; height: 90px;margin-right: 10px;" src="https://q1.qlogo.cn/g?b=qq&nk=919968602&s=100" fit="fill" />
            <el-descriptions direction="vertical" :column="4" border style="width: 100%;">
                <el-descriptions-item label="队长">
                    <el-text tag="b" truncated>黑沫渃DeSplendor.Ghast</el-text>
                </el-descriptions-item>
                <el-descriptions-item label="创建时间">2023/3/15 17:00</el-descriptions-item>
                <el-descriptions-item label="截止时间">2023/3/15 17:00</el-descriptions-item>
                <el-descriptions-item label="人数">4/10</el-descriptions-item>
            </el-descriptions>
        </div>
    </el-card>
    <el-card class="form-wrapper">
        <template #header>
            <div class="container">
                <h3>内测学习小组</h3>
                <el-button class="btn" type="primary" plain>查看队伍</el-button>
            </div>
        </template>
        <div class="card-content" style="margin-top: -5px;">
            <el-image style="width: 100px; height: 90px;margin-right: 10px;" src="https://q1.qlogo.cn/g?b=qq&nk=969025821&s=100" fit="fill" />
            <el-descriptions direction="vertical" :column="4" border style="width: 100%;">
                <el-descriptions-item label="队长">亦不可止</el-descriptions-item>
                <el-descriptions-item label="创建时间">2023/3/15 17:00</el-descriptions-item>
                <el-descriptions-item label="截止时间">2023/3/15 17:00</el-descriptions-item>
                <el-descriptions-item label="人数">4/10</el-descriptions-item>
            </el-descriptions>
        </div>
    </el-card>
    <el-card class="form-wrapper">
        <template #header>
            <div class="container">
                <h3>内测学习小组</h3>
                <el-button class="btn" type="primary" plain>查看队伍</el-button>
            </div>
        </template>
        <div class="card-content" style="margin-top: -5px;">
            <el-image style="width: 100px; height: 90px;margin-right: 10px;" src="https://q1.qlogo.cn/g?b=qq&nk=2571469810&s=100" fit="fill" />
            <el-descriptions direction="vertical" :column="4" border style="width: 100%;">
                <el-descriptions-item label="队长">张三</el-descriptions-item>
                <el-descriptions-item label="创建时间">2023/3/15 17:00</el-descriptions-item>
                <el-descriptions-item label="截止时间">2023/3/15 17:00</el-descriptions-item>
                <el-descriptions-item label="人数">4/10</el-descriptions-item>
            </el-descriptions>
        </div>
    </el-card>

    <!-- 新建队伍抽屉表单 -->
    <el-drawer ref="drawerRef" v-model="state.dialog" title="快来创建你自己的队伍吧!" :before-close="handleClose" direction="rtl">       
        <el-form :model="form" label-width="120px" label-position="right">
            <!-- 队伍名 -->
            <el-form-item label="队伍名：">
                <el-input v-model="form.teamName" maxlength="20" show-word-limit clearable />
            </el-form-item>
            <!-- 队伍人数 -->
            <el-form-item label="队伍人数：">
                <el-input-number v-model="form.teamSize" :min="2" :max="10"/>
            </el-form-item>
            <!-- 队伍截止时间 -->
            <el-form-item label="队伍截止时间：">
                <el-date-picker v-model="form.deadline" type="datetime" placeholder="请选择一个时间节点" />
            </el-form-item>
            <!-- 队伍状态 -->
            <el-form-item label="队伍状态：">
                <el-radio-group v-model="form.teamStatus">
                    <el-radio :label="1">公开</el-radio>
                    <el-radio :label="2">加密</el-radio>
                </el-radio-group>
            </el-form-item>
            <!-- 队伍密码，在队伍状态为加密时出现 -->
            <el-form-item v-if="form.teamStatus === '2'" label="队伍密码：">
                <el-input v-model="form.teamPassword" type="password" placeholder="在加入队伍时需要输入该密码" maxlength="10" show-password/>
            </el-form-item>
            <!-- 队伍描述 -->
            <el-form-item label="队伍描述：">
                <el-input v-model="form.teamDescribe" type="textarea" maxlength="40" :rows="4" show-word-limit />
            </el-form-item>
        </el-form>
        <div class="btn-container">
            <el-button @click="cancelForm">取消</el-button>
            <el-button type="primary" :loading="loading" @click="onClick">{{loading ? '创建中 ...' : '创建'}}</el-button>
        </div>
    </el-drawer>
</template>

<script setup>
import { Search } from '@element-plus/icons-vue';
import { reactive, ref } from 'vue'
import { useUserStore } from '@/stores/user';
import { ElDrawer, ElMessageBox, ElMessage } from 'element-plus'
import { createTeam } from '@/api/frontdesk/team.js'

// 页面状态
const state = reactive({
    // 搜索框搜索内容
    searchInput:'',
    // 控制创建队伍抽屉展开
    dialog:false,
    // 表单提交加载状态
    loading:false,
    // 计时器
    timer:'',
})
// 全局用户状态
const userStore = useUserStore()

// 创建队伍表单内容
const form = reactive({
    teamName:'',
    teamDescribe:'',
    teamSize:2,
    deadline:'',
    teamStatus:1,
    teamPassword:''
})

const drawerRef = ref('')

// 创建队伍按钮响应方法，打开抽屉表单
const createOnclick = () => {
    // 判断用户是否登录
    if(!userStore.isLogin()) {
        ElMessage.error("必须要登录后才能进行该操作")
        return
    }
    state.dialog = true
}
// 取消队伍创建，清除表单内容
const cancelForm = () => {
    state.loading = false
    state.dialog = false
    clearTimeout(state.timer)
    clearForm()
}
// 发送表单信息创建队伍
const onClick = () => {
    createTeam(form).then(res => {
        if(res.data.code === 200 && res.data.data) {
            ElMessage.success("成功创建你自己的队伍")
            drawerRef.value.close()
        }else{
            ElMessage.error(res.data.message)
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
}
// 点击抽屉外内容触发响应
const handleClose = (done) => {
    if (state.loading) {
        clearForm
        return
    }
    ElMessageBox.confirm('你确定要暂时退出吗，表单内容将会被保存').then(() => {
        state.loading = true
        state.timer = setTimeout(() => {
            done()
            // 动画关闭需要一定的时间
            setTimeout(() => {
                state.loading = false
            }, 400)
        }, 1000)
    }).catch(() => {
        // catch error
    })
}
// 清除表单内容
const clearForm = () => {
    Object.keys(form).forEach(key => {
        form[key] = ''
    })
    form.teamSize = 2
    form.teamStatus = '1'
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
    margin-bottom: 20px; /* 设置上下间隔固定 */
}

.container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.btn {
    margin-left: auto;
}

.btn-container {
    display: flex;
    justify-content: left;
    margin-left: 40px;
}
</style>