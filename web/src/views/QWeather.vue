<template>
    <div class="weatherBox">
        <el-form :model="form" label-width="120px">
            <el-form-item label="城市">
                <!--                <el-input v-model="form.location" @focusout="lookupCity()"/>-->
                <el-select
                        v-model="form"
                        filterable
                        remote
                        reserve-keyword
                        placeholder="请输入城市"
                        remote-show-suffix
                        :remote-method="lookupCity"
                >
                    <el-option
                            v-for="item in cities"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                    />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit()">Submit</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    name: "QWeather",
    data() {
        return {
            form: {
                location: ''
            },
            cities: []
        }
    },
    methods: {
        onSubmit() {
            this.axios.get(`/api/weather/sevenDayWeather?location=${this.form.location}`)
                .then(res => {
                    console.log(res)
                })
        },
        lookupCity(query) {
            console.log(query)
            this.axios.get(`/api/weather/lookupCity?location=${query}`)
                .then(res => {
                    console.log(res.data.data)
                    this.cities = res.data.data;
                })
        }
    },

}

</script>

<style scoped>
.weatherBox {
    width: 300px;
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>
<style scoped>

</style>