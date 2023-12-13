<script>

import {defineComponent} from "vue";
import {Message, User} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

export default defineComponent({
    components: {Message, User},
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

    <div v-if="!hasHeader">
        <div class="div-header">
            <div class="logo">
                <el-image src="./src/assets/gif/duofu.gif" style="height: 50px"/>
            </div>
            <div style="display: flex; justify-content: flex-end; flex: 1">
                <el-button type="warning" @click="logout()">logout</el-button>
            </div>
        </div>
    </div>

    <div style="display: flex;width: 100vw;height: 100vh;overflow: hidden">
        <div class="aside">
            <el-scrollbar>
                <el-menu :default-openeds="['1', '3']">
                    <el-sub-menu index="1">
                        <template #title>
                            <el-icon>
                                <message/>
                            </el-icon>
                            Navigator One
                        </template>
                        <el-menu-item-group>
                            <template #title>Group 1</template>
                            <el-menu-item index="1-1">Option 1</el-menu-item>
                            <el-menu-item index="1-2">Option 2</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group title="Group 2">
                            <el-menu-item index="1-3">Option 3</el-menu-item>
                        </el-menu-item-group>
                        <el-sub-menu index="1-4">
                            <template #title>Option4</template>
                            <el-menu-item index="1-4-1">Option 4-1</el-menu-item>
                        </el-sub-menu>
                    </el-sub-menu>
                </el-menu>
            </el-scrollbar>
        </div>
        <div>
            <el-main>
                <router-view/>
            </el-main>
        </div>
    </div>


</template>

<style scoped>
.div-header {
    display: flex;
    align-items: center;
    height: 100%;
    padding: 0 20px;
    background-color: #f7f3ff;
    border-bottom: 1px solid #DDDFE5;
}

.logo {
    width: 200px;
}

.aside {
    width: 200px;
}
</style>
