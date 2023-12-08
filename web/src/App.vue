<script>

import {defineComponent} from "vue";
import {User} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

export default defineComponent({
    components: {User},
    computed: {
        hasHeader() {
            let arr = ['/login', '/forgotPassword']
            return arr.includes(this.$route.path)
        }
    },
    data() {
        return {
            duration: 1500,
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
    },
})
</script>

<template>
    <el-header v-if="!hasHeader">
        <div class="div-header">
            <div class="logo">
                <el-image src="./src/assets/gif/duofu.gif" style="height: 50px"/>
            </div>
            <div style="display: flex; justify-content: flex-end; flex: 1">
                <el-button type="warning" @click="logout()">logout</el-button>
            </div>
        </div>
    </el-header>
    <router-view/>
</template>

<style scoped>
.div-header {
    display: flex;
    align-items: center;
    height: 100%;
    padding: 0 20px;
    background-color: #fff;
    border-bottom: 1px solid #DDDFE5;
}
.logo {
    width: 200px;
}
</style>
