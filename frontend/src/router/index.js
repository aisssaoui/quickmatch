import Vue from 'vue'
import Router from 'vue-router'
/* components */
import Login from '../components/Login'
import Profile from '../components/Profile'
import Home from '../components/Home'
import Invitation from '../components/Invitation'
import Signin from '../components/Signin'
import Calendar from '../components/Calendar'
import Stat from '../components/Stat'
import Match from '../components/Match'

Vue.use(Router)

export default new Router({
    routes: [
        { path: '/', component: Home },
        { path: '/login', component: Login },
        { path: '/signin', component: Signin },
        { path: '/profile/:id', component: Profile },
        { path: '/invitation', component: Invitation },
        { path: '/calendar', component: Calendar },
        { path: '/stat', component: Stat },
        { path: '/match', component: Match } 
    ],
    mode: 'history'
})