// axios-config.js
import axios from 'axios';
import Cookies from 'js-cookie'
import {ElMessage} from "element-plus";

const myAxios = axios.create();

// post请求默认携带请求表单提交请求头
myAxios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
// 通过环境变量获取 baseURL
myAxios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;

// 全局请求拦截器
myAxios.interceptors.request.use(config => {
    // 在请求头中添加 token
    const token = Cookies.get("access_token")
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

myAxios.interceptors.response.use(
    (res) => {
        console.log(res)
        console.log(123)
        if (res.data.status === 401) {
            const duration = 1500;
            // 清除token
            Cookies.remove("access_token");

            // 弹窗提示
            ElMessage({message: res.data.message, type: 'error', duration: duration})

            // 延时跳转到登录页面
            setTimeout(() => {
                location.href = '/login';
            }, duration);
        }


        return res;
    }
)

export default myAxios;
