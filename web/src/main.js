import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from "axios";

const app = createApp(App)

// 全局注册路由
app.use(router)
// 全局注册element
app.use(ElementPlus)

// 全局注册el-icon
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// 通过环境变量获取 baseURL
axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;
// 配置post请求的Content-Type
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
app.config.globalProperties.axios = axios;

app.mount('#app')
