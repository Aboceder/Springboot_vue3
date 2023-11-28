# Springboot_vue3

## SpringSecurity 登录鉴权

### 安装element plus

```sh
yarn add element-plus
```

### 安装element plus图标库

```sh
yarn add @element-plus/icons-vue
```

### 安装axios发送网络请求

```sh
yarn add axios 
```

#### 修改axios的默认配置

```js
// 通过环境变量获取 baseURL
axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;

// 配置post请求的Content-Type
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
```

#### 在main.js里面引入axios

```js
// 引入 Axios  
import axios from "axios";

// 将自定义的 Axios 实例挂载到全局，方便在组件中使用
app.config.globalProperties.axios = axios;

// 在组件里面不需要导入，直接通过`this.axios`来使用axios
this.axios.post(url, data)
```

