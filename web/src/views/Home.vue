<template>
    <div style="height: 100%;">
        <h1>hello {{ username }}</h1>
    </div>
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
                    console.log(response)
                    if (response.data.success) {
                        console.log(response.data.result.username)
                        this.username = response.data.result.username;
                    }
                })
        }
    },
}


</script>

<style scoped>

</style>