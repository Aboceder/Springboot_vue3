<template>

    <div class="search">
        <el-form :model="form" label-width="120px" class="flex-form">
            <!-- 城市选择框 -->
            <el-form-item label="城市" class="flex-form-item">
                <el-select
                    v-model="form.location"
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
            <!-- 提交按钮 -->
            <el-form-item class="flex-form-item">
                <el-button type="primary" @click="onSubmit()">Submit</el-button>
            </el-form-item>
        </el-form>
    </div>
    <div class="search">
        <!-- 使用el-row创建一个行容器 -->
        <el-row :gutter="20">
            <!-- 使用v-for遍历weatherList数组 -->
            <el-col :span="24 / sevenDayWeather.length" v-for="(weather, index) in sevenDayWeather" :key="index">
                <!-- 使用el-card创建方框 -->
                <el-card>
                    <!-- 此处插入天气信息 -->
                    <div class="weather-info">
                        <p>{{ weather.fxDate }}</p> <!-- 日期 -->
                        <p>{{ weather.textDay }} ~ {{ weather.textNight }}</p> <!-- 天气状态 -->
                        <p>{{ weather.tempMax }}°C ~ {{ weather.tempMin }}°C</p> <!-- 温度 -->
                    </div>
                </el-card>
            </el-col>
        </el-row>
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
            cities: [],
            sevenDayWeather: []
        }
    },
    methods: {
        onSubmit() {
            this.axios.get(`/api/weather/sevenDayWeather?location=${this.form.location}`)
                .then(res => {
                    if (res.data.success) {
                        this.sevenDayWeather = res.data.result.daily;
                    }
                })
        },
        lookupCity(query) {
            if (query) {
                this.axios.get(`/api/weather/lookupCity?location=${query}`)
                    .then((res) => {
                        if (res.data.success) {
                            this.cities = res.data.result;
                        }
                    })
            }

        },
    },
}

</script>

<style scoped>
.search {
    display: flex;
    justify-content: center;
    padding-top: 100px;
}

.flex-form {
    display: flex;
    justify-content: flex-start;
    align-items: flex-end; /* 根据需要对齐表单元素 */
}

.weather-info p {
    text-align: center;
}
</style>