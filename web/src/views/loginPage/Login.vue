<!-- Login.vue -->
<template>
    <div class="login-container">
        <div class="left-column">
            <!-- 放置左侧图片 -->
            <el-image src="./src/assets/img/bg.jpg" style="width: 100%; height: 100%"/>
        </div>
        <div class="right-column">
            <!--右侧登录表单-->
            <div style="padding: 50px">
                <el-image src="./src/assets/app_svg/google_logo.svg" style="padding-bottom: 40px"/>
                <div style="font-size: 25px; font-weight: bold">
                    登 录
                </div>
                <el-input type="text" :prefix-icon="User" placeholder="用户名/手机号" class="pt20"
                          v-model="loginForm.username"/>
                <el-input type="password" :prefix-icon="Lock" show-password placeholder="密码" class="pt20"
                          v-model="loginForm.password"/>
                <el-row type="flex" justify="space-between" align="middle" style="margin-top: 10px">
                    <!-- 左侧自动登录 -->
                    <el-col :span="12" style="text-align: left">
                        <el-checkbox v-model="rememberMe">自动登录</el-checkbox>
                    </el-col>
                    <!-- 右侧忘记密码 -->
                    <el-col :span="12">
                        <div style="text-align: right;">
                            <router-link to="/forgotPassword" style="font-size: 14px">忘记密码</router-link>
                        </div>
                    </el-col>
                </el-row>
                <el-row style="padding: 30px 0;">
                    <el-col :span="12">
                        <el-button type="success" plain style="width: 100px;" @click="login()">登录</el-button>
                    </el-col>
                    <el-col :span="12">
                        <el-button type="warning" plain style="width: 100px;">注册</el-button>
                    </el-col>
                </el-row>
                <el-divider>其他登录方式</el-divider>
                <el-row style="padding-top: 30px;">
                    <el-col :span="6">
                        <el-tooltip content="WeChat" placement="bottom" effect="light">
                            <el-image class="third-party-login" src="./src/assets/app_svg/WeChat.svg"/>
                        </el-tooltip>
                    </el-col>
                    <el-col :span="6">
                        <el-tooltip content="Github账号" placement="bottom" effect="light">
                            <el-image class="third-party-login" src="./src/assets/app_svg/github_green.svg"
                                      @click="thirdPartLogin()"/>
                        </el-tooltip>
                    </el-col>
                    <el-col :span="6">
                        <el-tooltip content="Google账号" placement="bottom" effect="light">
                            <el-image class="third-party-login" src="./src/assets/app_svg/google.svg"/>
                        </el-tooltip>
                    </el-col>
                    <el-col :span="6">
                        <el-tooltip content="AppleID" placement="bottom" effect="light">
                            <el-image class="third-party-login" src="./src/assets/app_svg/apple.svg"/>
                        </el-tooltip>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>


import {Lock, User, View} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import router from "@/router";

export default {
    components: {View},
    created() {
        this.getClientId();
    },
    computed: {
        User() {
            return User
        },
        Lock() {
            return Lock
        }
    },
    data() {
        return {
            loginForm: {
                username: '',
                password: '',
            },
            rememberMe: false,
            duration: 1500, // 设置弹窗显示时间
            clientId: '',
            randomStr: ''
        };
    },
    methods: {
        login() {
            this.axios.post('/api/member/login', {
                    username: this.loginForm.username,
                    password: this.loginForm.password,
                    "remember-me": this.rememberMe
                },
                {headers: {"ignoreToken": true}}
            )
                .then(response => {
                    console.log(response)
                    if (response.data.success) {
                        // 弹窗提示
                        ElMessage({message: '登录成功!', type: 'success', duration: this.duration})

                        //todo 存储token
                        const token = JSON.parse(response.data.result).accessToken;

                        this.Cookies.set('access_token', token);
                        // 延时跳转到首页
                        setTimeout(() => {
                            router.push('/');
                        }, this.duration);
                    } else {
                        ElMessage.error(response.data.message)
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        },
        thirdPartLogin() {
            location.href = `https://github.com/login/oauth/authorize?client_id=${this.clientId}&state=${this.randomStr}`
        },
        getClientId() {
            this.axios.get('/github/getGithubClientId')
                .then(res => {
                    this.clientId = res.data[0];
                    this.randomStr = res.data[1];
                })
        }
    },
};
</script>

<style scoped>
.login-container {
    display: flex;
    height: 100vh;
    width: 100vw;
    overflow: hidden;
}

.left-column {
    flex: 1;
    display: flex;
}

.right-column {
    width: 380px;
    text-align: center;
    /*background-color: rgba(242,240,233);*/
}

.pt20 {
    padding-top: 20px;
}

.third-party-login {
    width: 38px;
}

.third-party-login:hover {
    cursor: pointer;
}
</style>
