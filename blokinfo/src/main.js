import Vue from 'vue'
import App from './App.vue'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import 'font-awesome/css/font-awesome.min.css'
Vue.config.productionTip = false
Vue.use(iView)
new Vue({
    render: h => h(App),
}).$mount('#app')
