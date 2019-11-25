import Vue from 'vue'
import router from './router'
import store from './store'
import App from './App'

import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)

new Vue({
    render:h =>h(App),
    router,
    store
}).$mount('#app')
