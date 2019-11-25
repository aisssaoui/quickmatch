import Vue from 'vue'
import Router from 'vue-router'
/* components */
import App from '../App'
import Login from '../components/Login'
import Profile from '../components/Profile'
import Home from '../components/Home'
import Invitation from '../components/Invitation'

Vue.use(Router)

export default new Router({
    routes: [
        { path: '/', component: Home },
        { path: '/login', component: Login },
        { path: '/profile/:id', component: Profile },
        { path: '/invitation', component: Invitation}
        ]
})