<template>
    <h1>hello</h1>
    <el-button type="warning" @click="logout()">logout</el-button>
</template>

<script>
import router from "@/router";
import {ElMessage} from "element-plus";

export default {
    name: "Home",
    data() {
        return {
            duration: 1500
        }
    },
    methods: {
        logout() {
            this.axios.post('/api/member/logout')

            //todo 清除token

            // 弹窗提示
            ElMessage({message: '退出成功!', type: 'success', duration: this.duration})

            // 延时跳转到登录页面
            setTimeout(() => {
                router.push('/login');
            }, this.duration);
        }
    },
}
</script>

<style scoped>

</style>