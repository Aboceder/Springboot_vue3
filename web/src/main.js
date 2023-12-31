import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import myAxios from "./config/axiosConfig";
import Cookies from "js-cookie";

const app = createApp(App)

// 全局注册路由
app.use(router)
// 全局注册element
app.use(ElementPlus)

// 全局注册el-icon
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.config.globalProperties.axios = myAxios;
app.config.globalProperties.Cookies = Cookies;

app.mount('#app')
