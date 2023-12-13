# Springboot_vue3

## SpringSecurity 登录鉴权
_**注意注意！！！！：springboot security自定义过滤器 使用 permitAll() 之后仍然会走过滤器**_
### 安装element plus
```sh
# 官网：https://element-plus.org/zh-CN/
yarn add element-plus
```

### 安装element plus图标库
```sh
yarn add @element-plus/icons-vue
```

### 安装axios发送网络请求
```sh
# 官网：https://axios-http.com/zh/
yarn add axios 
```

#### 修改axios的默认配置
```js
// 通过环境变量获取 baseURL
axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;

// 配置post请求的Content-Type
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

// 判断有没有token，如果没有跳转登录页面，如果有，axios设置请求头携带token
const token = Cookies.get("access_token")
if (!token) {
    ElMessage.error({message: '登录凭证失效，请重新登录!', type: 'error', duration: 1500})
    setTimeout(() => {
        router.push("/login");
    }, 1500)
}
axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
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

### 安装js-cookie

```shell
yarn add js-cookie
```

#### js-cookie基础方法

```js
// 创建一个 cookie，在整个网站上有效：
Cookies.set('name', 'value')

// 创建一个 7 天后过期的 cookie，在整个网站上有效：
Cookies.set('name', 'value', {expires: 7})

// 创建一个过期的cookie，对当前页面的路径有效： 
Cookies.set('name', 'value', {expires: 7, path: ''})

// 读取cookie：
Cookies.get('name') // => 'value'
Cookies.get('nothing') // => undefined

// 读取所有可见的cookie：
Cookies.get() // => { name: 'value' }
```