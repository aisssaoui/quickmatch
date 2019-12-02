import Vue from 'vue'
import Router from 'vue-router'

import Home from '../components/Home.vue'
import Player from '../components/Player.vue'
import Club from '../components/Club.vue'

Vue.use(Router)

export default new Router({
    routes: [
        {path: '/', name: 'Home', component: Home },
        {path: '/Player', name: 'Player', component: Player },
        {path: '/Club', name: 'Club', component: Club },
    ]
})
