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
                        <el-image class="third-party-login" src="./src/assets/app_svg/WeChat.svg"/>
                    </el-col>
                    <el-col :span="6">
                        <el-image class="third-party-login" src="./src/assets/app_svg/github_green.svg"/>
                    </el-col>
                    <el-col :span="6">
                        <el-image class="third-party-login" src="./src/assets/app_svg/google.svg"/>
                    </el-col>
                    <el-col :span="6">
                        <el-image class="third-party-login" src="./src/assets/app_svg/apple.svg"/>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>


import {Lock, User, View} from "@element-plus/icons-vue";

export default {
    components: {View},
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
        };
    },
    methods: {
        login() {
            this.axios.post('/api/member/login', {
                    username: this.loginForm.username,
                    password: this.loginForm.password,
                    rememberMe: this.rememberMe
                }
            )
                .then(response => {
                    console.log(response)
                })
        },
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
