// axios-config.js
import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL, // 通过环境变量获取 baseURL
    // 其他自定义配置...
});

const get = (url, params) => {
    return axiosInstance.get(url, {params});
};

const post = (url, data) => {
    return axiosInstance.post(url, data, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            // 其他自定义头部...
        },
    });
}
export default axiosInstance;
