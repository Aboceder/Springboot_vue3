import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus, {ElMessage} from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from "axios";
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

// 通过环境变量获取 baseURL
axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;
// 配置post请求的Content-Type
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
// 请求头携带token
const token = Cookies.get("access_token")
if (!token) {
    ElMessage.error({message: '登录凭证失效，请重新登录!', type: 'error', duration: 1500})
    setTimeout(() => {
        router.push("/login");
    }, 1500)
}
axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
app.config.globalProperties.axios = axios;
app.config.globalProperties.Cookies = Cookies;

app.mount('#app')
