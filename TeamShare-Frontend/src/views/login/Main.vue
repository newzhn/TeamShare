<!-- 登录页 -->
<template>
    <div class="loginBox">
        <div class="midgroup">
            <div class="loginwrap loginwarrp">
                <div class="logingroup login_form">
                    <!-- 标题 -->
                    <h2 style="text-align: center; color: #2fa7b9;">登 录</h2>
                    <!-- 登录表单 -->
                    <el-form ref="loginFormRef" :model="loginForm" status-icon :rules="rules" label-width="120px"
                        style="padding-top:20px;" label-position="top" size="large" class="demo-ruleForm">
                        <!-- 账户 -->
                        <el-form-item prop="loginName">
                            <el-input :prefix-icon="User" v-model="loginForm.loginName" placeholder="请输入用户名或者电子邮箱" />
                        </el-form-item>
                        <!-- 密码 -->
                        <el-form-item prop="loginPass">
                            <el-input :prefix-icon="Lock" v-model="loginForm.loginPass" show-password autocomplete="off"
                                placeholder="请输入密码" @keyup.enter="submitLoginInfo"/>
                        </el-form-item>
                        <!-- 登录按钮 -->
                        <el-form-item prop="loginBtn" class="loginBtn">
                            <el-button color="#2fa7b9" plain style="width:100%;" @click="submitLoginInfo">立即登录</el-button>
                        </el-form-item>
                        <!-- 页面页脚 -->
                        <p class="toPath">
                            还没有账号，<span style="color:#2fa7b9;" @click="toPath('/register')">立即注册</span>
                            <span style="float:right;color:#999;" @click="toPath('/resetPassword')">忘记密码？</span>
                        </p>
                    </el-form>

                    <footer style="text-align:center;">
                        <p>相信有一天, 理想主义终将所向披靡.</p>
                    </footer>
                </div>
            </div>
        </div>
        <div class="footer_copyright">
            <p>· Designed by TeamShare. All Rights Reserved. Copyright © 2022 TeamShare ·</p>
        </div>
    </div>
</template>

<script setup>
import { ref,reactive } from 'vue';
import { useRouter } from 'vue-router';
import { userLogin } from "@/api/login/login.js"
import { ElLoading,ElNotification } from 'element-plus'

const router = useRouter();
const loginForm = reactive({
    loginName: '',
    loginPass: '',
})
// 获取页面元素
const loginFormRef = ref('');

// 前往某个界面
const toPath = (path) => {
    router.push(path)
}
// 用户名的非空验证
const validateName = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('请填写此字段~'))
    } else {
        callback()
    }
};
// 密码的非空验证
const validatePass = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('请填写此字段~'))
    } else {
        callback()
    }
};
// 约束规则 正则
const rules = reactive({
    loginName: [{
        validator: validateName,
        trigger: 'blur'
    }],
    loginPass: [{
        validator: validatePass,
        trigger: 'blur'
    }],
})
// 登录
const submitLoginInfo = () => {
    // 校验登录信息
    loginFormRef.value.validate((valid) => {
        if(!valid) {
            return
        }
        // 校验成功后向后端发送登录请求
        userLogin(loginForm).then(res => {
            // 加载动画，锁定页面，等待后端请求返回，一秒后解除锁定
            const loading = ElLoading.service({
                lock: true,
                text: 'Loading',
                background: 'rgba(0, 0, 0, 0.7)',
            })
            // 请求成功则关闭加载动画，清除页面数据，跳转到首页
            if(res.data.code === 200 && res.data.data) {
                loading.close()
                // 把token存储到浏览器
                localStorage.setItem('authorization', res.data.data);
                setTimeout(() => {
                    ElNotification({
                        title: '登录成功',
                        message: '即将跳转至首页',
                        duration: 2000,
                        type: 'success',
                    })
                    // 重置表单项，将其值重置为初始值，并移除校验结果
                    loginFormRef.value.resetFields()
                }, 1000)
                setTimeout(() => {
                    router.push('/home')
                }, 3000)
            }else{
                //失败，或者超时都弹出错误提示框
                loading.close()
                ElNotification({
                    title: '登录失败',
                    message: res.data.message,
                    type: 'error'
                })
            }
        }).catch(() => {
            console.log('请求发送失败');
        })
        
    })
}
</script>

<style scoped>
    .loginBox {
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        width: 100%;
        height: 100%;
        background-size: cover;
    }

    .loginBox .midgroup {
        width: 420px;
        margin: 20vh auto;
        position: relative;
    }

    .loginBox .midgroup .logingroup {
        padding: 20px 40px;
        background: #CDD0D6;
        overflow: hidden;
        box-shadow: 0 1px 10px 0 rgb(7 17 27 / 10%);
        padding-top: 20px;
        border-radius: 10px;
    }

    .loginBox .midgroup .logingroup footer {
        margin-top: 10px;
        padding-top: 10px;
        border-top: 1px dotted #ddd;
        color: #999;
        font-size: 12px;
        overflow: hidden;
    }

    .footer_copyright {
        width: 100%;
        text-align: center;
        line-height: 24px;
        color: #999;
        font-size: 12px;
        position: fixed;
        left: 0;
        bottom: 0;
        overflow: hidden;
        padding: 10px;
        border-top: 1px solid #eee;
        background: white;
    }

    :global(.el-input-group__append) {
        padding: 0px;
        background: #f6f6f6;
    }

    .loginBtn :deep(.el-form-item__error) {
        text-align: center;
        width: 100%;
    }

    .toPath {
        font-size: 12px;
        color: #999;
    }

    .toPath span {
        cursor: pointer;
    }

    .toPath span:hover {
        color: #2fa7b9;
    }
</style>