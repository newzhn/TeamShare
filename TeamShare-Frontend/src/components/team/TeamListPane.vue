<!-- 队伍列表面板 -->
<template>
    <!-- 搜索框和新建按钮 -->
    <el-card class="form-wrapper hidden-xs-only">
        <div class="card-content">
            <el-input v-model="state.searchInput" placeholder="请输入你想搜索的队伍吧（回车进行搜索）" 
            :prefix-icon="Search" clearable class="left-element" @keyup.enter="search"/>
            <el-button type="primary" class="right-element" @click="createOnclick()">新建队伍</el-button>
        </div>
    </el-card>
    <!-- 队伍列表 -->
    <el-card v-for="(team, index) in state.teamList" :key="team.teamId" class="form-wrapper">
        <!-- 队伍名 -->
        <template #header>
            <div class="container">
                <h3>{{ team.teamName }}</h3>
                <el-button class="btn" type="primary" plain @click="changeDialogVisible(team.teamStatus,team.teamId)">加入队伍</el-button>
            </div>
            <!-- 公开队伍对话框 -->
            <el-dialog v-model="state.publicDialogVisible" title="Tips" width="30%" destroy-on-close>
                <span>你确定要加入该队伍吗？</span>
                <template #footer>
                    <span>
                        <el-button @click="state.publicDialogVisible = false">Cancel</el-button>
                        <el-button type="primary" @click="joinInOnclick">ok</el-button>
                    </span>
                </template>
            </el-dialog>
            <!-- 加密队伍对话框 -->
            <el-dialog v-model="state.privateDialogVisible" title="请输入队伍密码：" width="30%" destroy-on-close>
                <el-input v-model="state.teamPassword" type="password" placeholder="Please input password" show-password/>
                <template #footer>
                    <span>
                        <el-button @click="state.privateDialogVisible = false">Cancel</el-button>
                        <el-button type="primary" @click="joinInOnclick">ok</el-button>
                    </span>
                </template>
            </el-dialog>
        </template>
        <!-- 队伍信息 -->
        <div class="card-content" style="margin-top: -5px;">
            <el-image class="hidden-xs-only" style="width: 100px; height: 90px;margin-right: 10px;" :src="team.captain.avatarUrl" fit="fill" />
            <el-descriptions direction="vertical" :column="4" border style="width: 100%;">
                <el-descriptions-item label="队长">{{ team.captain.nickname }}</el-descriptions-item>
                <el-descriptions-item label="创建时间">{{ dateFliter(team.createTime) }}</el-descriptions-item>
                <el-descriptions-item label="截止时间">{{ dateFliter(team.deadline) }}</el-descriptions-item>
                <el-descriptions-item label="人数">{{ team.members.length + "/" + team.teamSize }} </el-descriptions-item>
            </el-descriptions>
        </div>
    </el-card>

    <!-- 新建队伍抽屉表单 -->
    <el-drawer ref="drawerRef" v-model="state.drawerVisible" title="快来创建你自己的队伍吧!" :before-close="handleClose" direction="rtl">       
        <el-form ref="teamFormRef" :model="form" :rules="rules" label-width="120px" label-position="right">
            <!-- 队伍名 -->
            <el-form-item prop="teamName" label="队伍名：">
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
            <el-form-item v-if="form.teamStatus === 2" label="队伍密码：">
                <el-input v-model="form.teamPassword" type="password" placeholder="在加入队伍时需要输入该密码" maxlength="10" show-password/>
            </el-form-item>
            <!-- 队伍描述 -->
            <el-form-item prop="teamDescribe" label="队伍描述：">
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
import { reactive, ref, onMounted, watch } from 'vue'
import { useUserStore } from '@/stores/user.js';
import { useTeamStore } from '@/stores/team.js';
import { ElDrawer, ElMessageBox, ElMessage } from 'element-plus'
import { createTeam,checkTeamName,getTeamListForStatus,joinInTeam,searchTeamList } from '@/api/team.js'
import dateFliter from '@/utils/time.js'

// 全局用户状态
const userStore = useUserStore()
// 全队队伍状态
const teamStore = useTeamStore()

// 组件状态信息
const state = reactive({
    // 队伍列表
    teamList:[],
    // 搜索框搜索内容
    searchInput:'',
    // 控制创建队伍抽屉展开
    drawerVisible:false,
    // 控制对话框展开
    publicDialogVisible:false,
    privateDialogVisible:false,
    // 表单提交加载状态
    loading:false,
    // 计时器
    timer:'',
    // 队伍密码
    teamPassword:'',
    teamId:''
})

// 创建队伍的表单内容
const form = reactive({
    teamName:'',
    teamDescribe:'',
    teamSize:2,
    deadline:'',
    teamStatus:1,
    teamPassword:''
})
const drawerRef = ref('')
const teamFormRef = ref('')

// 创建队伍按钮响应方法，打开抽屉表单
const createOnclick = () => {
    // 判断用户是否登录
    if(!userStore.isLogin()) {
        ElMessage.error("必须要登录后才能进行该操作")
        return
    }
    state.drawerVisible = true
}
// 取消队伍创建，清除表单内容
const cancelForm = () => {
    state.loading = false
    state.drawerVisible = false
    clearTimeout(state.timer)
    clearForm()
}
// 发送表单信息创建队伍
const onClick = () => {
    createTeam(form).then(res => {
        if(res.data.code === 200 && res.data.data) {
            ElMessage.success("成功创建你自己的队伍")
            drawerRef.value.close()
            // 刷新数据
            if(props.teamTab === form.teamStatus){
                getData(form.teamStatus)
            }
            // 清除表单数据
            clearForm()
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

// 队伍名校验
const validateTeamName = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('队伍名不能为空哦'))
    } else {
        // 去重
        checkTeamName(form.teamName).then(res => {
            if(res.data.code === 200 && res.data.data) {
                callback(new Error('队伍名已被使用'))
            }else{
                callback()
            }
        }).catch(() => {
            console.log('请求发送失败');
        })
    }
}
// 队伍描述校验
const validateTeamDescribe = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('队伍描述不能为空哦'))
    }
    callback()
}
// 表单校验规则
const rules = reactive({
    teamName: [{
        validator: validateTeamName,
        trigger: 'blur'
    }],
    teamDescribe: [{
        validator: validateTeamDescribe,
        trigger: 'blur'
    }],
})

// 获取队伍列表
const getData = (teamTab) => {
    getTeamListForStatus(teamTab).then(res => {
        if(res.data.code === 200) {
            state.teamList = res.data.data
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
}
const changeDialogVisible = (teamStatus,teamId) => {
    state.teamId = teamId
    if(teamStatus === 1) {
        state.publicDialogVisible = true
    }else{
        state.privateDialogVisible = true
    }
}
// 加入队伍按钮响应
const joinInOnclick = () => {
    // 判断用户是否登录
    if(!userStore.isLogin()) {
        ElMessage.error("必须要登录后才能进行该操作")
        return
    }
    joinInTeam(state.teamId,state.teamPassword).then(res => {
        state.teamPassword = ''
        if(res.data.code === 200) {
            state.publicDialogVisible = false
            state.privateDialogVisible = false
            ElMessage.success("加入队伍成功")
            // 刷新页面数据
            teamStore.changeJoinFlag()
            getData(teamStore.teamTab)
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
    
}
// 搜索队伍
const search = () => {
    searchTeamList(state.searchInput,teamStore.teamTab).then(res => {
        if(res.data.code === 200) {
            state.teamList = res.data.data
            const length = state.teamList.length;
            ElMessage.success(length === 0 ? "没有搜索到队伍" : "共搜索到" + length + "只队伍")
        }
    }).catch(() => {
        console.log('请求发送失败');
    })
    
}

// 监视全局状态中的teamTab，变化时重新获取最新队伍数据
watch(() => teamStore.teamTab, (newVal, oldVal) => {
    if (newVal !== undefined) {
        getData(teamStore.teamTab)
    }
})

// 组件创建时获取一次最新队伍列表数据
onMounted(() => {
    getData(teamStore.teamTab)
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