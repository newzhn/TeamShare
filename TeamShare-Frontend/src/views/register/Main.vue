<!-- 注册页 -->
<template>
    <div class="loginBox">
        <div class="midgroup">
            <div class="loginwrap loginwarrp">
                <div class="logingroup login_form">
                    <!-- title -->
                    <h2 style="text-align: center; color: #2fa7b9;">注 册</h2>
                    <!-- 注册表单 -->
                    <el-form ref="registerFormRef" :model="registerForm" status-icon :rules="rules" label-width="120px"
                        style="padding-top:20px;" label-position="top" size="large" class="demo-ruleForm">
                        <!-- 用户名 -->
                        <el-form-item prop="registerName">
                            <el-input :prefix-icon="User" v-model="registerForm.registerName" maxlength="10" placeholder="请输入用户名"
                            :autofocus="true" clearable />
                        </el-form-item>
                        <!-- 邮箱 -->
                        <el-form-item prop="registerEmail">
                            <el-input :prefix-icon="Message" v-model="registerForm.registerEmail" placeholder="请输入电子邮箱" clearable />
                        </el-form-item>
                        <!-- QQ -->
                        <el-form-item prop="registerQQ">
                            <el-input :prefix-icon="Edit" v-model="registerForm.registerQQ" maxlength="11" placeholder="请输入QQ获取昵称和头像" clearable />
                        </el-form-item>
                        <!-- 密码 -->
                        <el-form-item prop="registerPass">
                            <el-input :prefix-icon="Lock" v-model="registerForm.registerPass" show-password maxlength="20"
                                autocomplete="off" placeholder="请输入密码" clearable />
                        </el-form-item>
                        <!-- 邮箱验证码 -->
                        <el-form-item prop="verificationCode">
                            <el-input :prefix-icon="Right" v-model="registerForm.verificationCode" maxlength="6" placeholder="邮箱验证码">
                                <template #append>
                                    <input type="button" :plain="true" @click="getCode()" :disabled="state.show"
                                        style="width: 100%;height: 100%;border: 1px;background: none;width: 70px;color: #ababab;"
                                        :value="state.codeText" />
                                </template>
                            </el-input>
                        </el-form-item>
                        <!-- 注册按钮 -->
                        <el-form-item prop="loginBtn" class="loginBtn">
                            <el-button color="#2fa7b9" plain style="width:100%;" @click="submitAddRegisterInfo">立即注册
                            </el-button>
                        </el-form-item>
                        <!-- 其它 -->
                        <p style="font-size: 12px; text-align: center;color:#999;">
                            已有账号，<router-link to="/login" style="color:#2fa7b9;">立即登录</router-link>
                        </p>
                    </el-form>
                    <!-- 页脚 -->
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
import { User,Lock,Right,Message,Edit } from '@element-plus/icons-vue'
import { checkUserName,checkEmail,sendCode,userRegister } from "@/api/register/register.js"
import { ElMessage,ElLoading,ElNotification } from 'element-plus'

// 获取验证码60秒倒计时
const TIME_COUNT = 60;
const registerFormRef = ref('')
// 注册表单内容
const registerForm = reactive({
    registerName:'',
    registerEmail:'',
    registerQQ:'',
    registerPass: '',
    // 输入的验证码
    verificationCode: ''
})
// 页面状态
const state = reactive({
    // 获取验证码 点击后为禁止状态
    show: false,
    // 获取验证码的文字 点击后改变
    codeText: '获取验证码',
    // 当前秒数
    count: '',
    // 时间  计时器
    timer: null,
})
// 用户名正则表达式判断
var nameGrep = /^[\u4e00-\u9fa5a-zA-Z0-9_]{2,10}$/;
// 邮箱正则表达式判断
var emailGrep = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
// QQ号正则表达式判断
var qqGrep = /^[1-9][0-9]{4,10}$/;
// 密码正则表达式判断
var passGrep = /^[a-zA-Z0-9_.-=*&^%$#@!+]{6,20}$/;

// 用户名的非空、正则验证
const validateName = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('请填写此字段~'))
    } else {
        // 正则校验
        if (!nameGrep.test(value)) {
            callback(new Error('用户名由2~10位中英文、数字、下划线组成'));
        // 重复校验
        } else {
            checkUserName(registerForm.registerName).then(res => {
                if(res.data.code === 200 && res.data.data) {
                    callback(new Error('此用户名已被注册，请更换一个'));
                }else {
                    callback()
                }
            }).catch(() => {
                console.log('请求发送失败');
            })
        }
    }
}
// 邮箱的非空验证
const validateEmail = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('请填写此字段~'))
    } else {
        if (!emailGrep.test(value)) {
            callback(new Error('请按照正确的邮箱格式输入'));
        } else {
            checkEmail(registerForm.registerEmail).then(res => {
                if(res.data.code === 200 && res.data.data) {
                    callback(new Error('此邮箱已被注册，请更换一个'));
                }else {
                    callback()
                }
            }).catch(() => {
                console.log('请求发送失败');
            })
        }
    }
}
// QQ的校验
const validateQQ = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('请填写此字段~'))
    } else {
        if (!qqGrep.test(value)) {
            callback(new Error('QQ号为从1000开始的11位数字'));
        } else {
            callback()
        }
    }
}
// 密码的非空验证
const validatePass = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('请填写此字段~'))
    } else {
        if (!passGrep.test(value)) {
            callback(new Error('密码6~18位字母、数字、特殊字符组成'));
        } else {
            callback()
        }
    }
}
// 验证码的非空、输入正确验证
const validateVerificationCode = (rule, value, callback) => {
    // 判断邮箱的正则是否通过，没有通过则先不验证此字段
    registerFormRef.value.validateField('registerEmail', err => {
        if (err) {
            if(!value) {
                return callback(new Error('请填写此字段~'))
            }else{
                return callback()
            }
        }else {
            return callback(new Error('请先填写邮箱获取验证码~'))
        }
    })
}
// 表单校验规则
const rules = reactive({
    registerName: [{
        validator: validateName,
        trigger: 'blur'
    }],
    registerEmail: [{
        validator: validateEmail,
        trigger: 'blur'
    }],
    registerQQ: [{
        validator: validateQQ,
        trigger: 'blur'
    }],
    registerPass: [{
        validator: validatePass,
        trigger: 'blur'
    }],
    verificationCode: [{
        validator: validateVerificationCode,
        trigger: 'blur'
    }],
})
// 通知后端发送验证码
const getCode = () => {
    // 判断邮箱的正则是否通过
    registerFormRef.value.validateField('registerEmail', err => {
        if (err) {
            // 通过则通知后端发送验证码
            state.show = true
            sendCode(registerForm.registerEmail).then(res => {
                if(res.data.code === 200 && res.data.data) {
                    ElMessage.success("验证码已发送至邮箱")
                    // 后端发送成功后开启定时器记时
                    state.count = TIME_COUNT
                    state.timer = setInterval(() => {
                        if (state.count > 0 && state.count <= TIME_COUNT) {
                            state.count--;
                            state.codeText = state.count + 's';
                        }else{
                            state.show = false;
                            window.clearInterval(state.timer);
                            state.timer = null;
                            state.codeText = "重新获取";
                        }
                    },1000)
                } else {
                    ElMessage.error(res.data.message)
                }
            }).catch(() => {
                console.log('请求发送失败');
            })
        } else {
            ElMessage.error('按要求填写电子邮箱后再获取验证码.')
            return false;
        }
    });
}
// 发送注册请求
const submitAddRegisterInfo = () => {
    // 1：校验注册信息  validate
    registerFormRef.value.validate((valid) => {
        if(!valid) {
            return
        }
        // 表单校验成功 提交数据到后台
        userRegister(registerForm).then(res => {
            // 加载动画，锁定页面，等待后端请求返回，一秒后解除锁定
            const loading = ElLoading.service({
                lock: true,
                text: 'Loading',
                background: 'rgba(0, 0, 0, 0.7)',
            })
            // 请求成功则关闭加载动画，清除页面数据，跳转到登录页
            if(res.data.code === 200 && res.data.data) {
                loading.close()
                setTimeout(() => {
                    ElNotification({
                        title: '注册成功',
                        message: '即将跳转至登录页面',
                        type: 'success',
                    })
                    // 重置表单项，将其值重置为初始值，并移除校验结果
                    registerFormRef.value.resetFields()
                    state.show = true;
                    window.clearInterval(state.timer);
                    state.timer = null;
                    state.codeText = "获取验证码";
                }, 1000)
                setTimeout(() => {
                    window.location.href = "/login"
                }, 3000)
            }else{
                //失败，或者超时都弹出错误提示框
                loading.close()
                ElNotification({
                    title: '注册失败',
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
        padding: 0px 10px;
        background: #f6f6f6;
    }

    .loginBtn :deep(.el-form-item__error) {
        text-align: center;
        width: 100%;
    }
</style>