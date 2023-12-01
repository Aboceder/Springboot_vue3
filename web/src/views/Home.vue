<template>
    <h1>hello {{ username }}</h1>
    <el-button type="warning" @click="logout()">logout</el-button>
</template>

<script>
import {ElMessage} from "element-plus";

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
            this.axios.post('/api/member/logout')
                .then(response => {
                    // 清除token
                    this.Cookies.remove("access_token");

                    // 弹窗提示
                    ElMessage({message: response.data.data, type: 'success', duration: this.duration})

                    // 延时跳转到登录页面
                    setTimeout(() => {
                        location.href = '/login';
                    }, this.duration);
                })
        },
        getUseInfo() {
            this.axios.get('/api/member/getUserInfo')
                .then(response => {
                    if (response.data.success) {
                        this.username = response.data.data.username;
                    }
                })
        }
    },
}


</script>

<style scoped>

</style>