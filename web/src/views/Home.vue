<template>
    <h1>hello {{ username }}</h1>
    <el-button type="warning" @click="logout()">logout</el-button>
</template>

<script>
import router from "@/router";
import {ElMessage} from "element-plus";

export default {
    name: "Home",
    created() {
        this.getUseInfo();
    },
    data() {
        return {
            duration: 1500,
            username: 'bopomofo'
        }
    },
    methods: {
        logout() {
            this.axios.post('/api/member/logout')
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
            this.axios.get('/api/member/gerUserInfo')
                .then(response => {
                    console.log(response.data)
                })
        }
    },
}


</script>

<style scoped>

</style>