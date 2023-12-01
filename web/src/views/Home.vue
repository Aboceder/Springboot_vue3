<template>
    <h1>hello {{ username }}</h1>
    <el-button type="warning" @click="logout()">logout</el-button>
</template>

<script>
import router from "@/router";
import {ElMessage} from "element-plus";
import Cookies from "js-cookie";

export default {
    name: "Home",
    created() {
        this.getUseInfo();
    },
    data() {
        return {
            duration: 1500,
            username: ''
        }
    },
    methods: {
        logout() {
            this.axios.post('/api/member/logout', null, {
                    headers: {
                        "Authorization": "Bearer " + Cookies.get("access_token"),
                    }
                }
            )
                .then(response => {
                    // 清除token
                    this.Cookies.remove("access_token");

                    // 弹窗提示
                    ElMessage({message: '退出成功!', type: 'success', duration: this.duration})

                    // 延时跳转到登录页面
                    setTimeout(() => {
                        router.push('/login');
                    }, this.duration);
                })
        },
        getUseInfo() {
            this.axios.get('/api/member/getUserInfo', {
                headers: {
                    "Authorization": "Bearer " + Cookies.get("access_token"),
                },
            })
                .then(response => {
                    this.username = response.data.data.username;
                })
        }
    },
}


</script>

<style scoped>

</style>