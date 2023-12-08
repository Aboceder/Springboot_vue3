<template>
    <el-main>
        <h1>hello {{ username }}</h1>
    </el-main>
</template>

<script>
import {User} from "@element-plus/icons-vue";

export default {
    name: "Home",
    components: {User},
    created() {
        this.getUseInfo();
    },
    data() {
        return {
            username: ''
        }
    },
    methods: {
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