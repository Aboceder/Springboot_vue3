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
h1 {
    /* 本例12个文字(加标点符号)；有多少个文字，width就是多少个em */
    width: 14em;
    /* 加上两个动画，一个是打字动画，使用steps让字一个一个的出现，
     注意step和字数保持一致，光标动画也是同理，*/
    animation: typingWords 5s steps(14) infinite, cursor 0.5s steps(1) infinite;
    /* 要设置不允许换行，且溢出隐藏 */
    white-space: nowrap;
    overflow: hidden;
    /* 使用右边框作为打印的指针光标 */
    border-right: 1px solid #000;
}

@keyframes typingWords {
    0% {
        width: 0;
    }
}

@keyframes cursor {
    50% {
        border-color: transparent;
    }
}
</style>