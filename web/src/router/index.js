import {createRouter, createWebHistory} from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/loginPage/Login.vue'
import ForgotPassword from "@/views/loginPage/ForgotPassword.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        {
            path: '/forgotPassword',
            name: 'forgotPassword',
            component: ForgotPassword
        }
    ]
})

export default router
