import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import VueCookie from 'vue-cookie'
import VueCookies from 'vue-cookies-reactive'
import GAuth from 'vue3-google-oauth2'
// import VueLoadmore from 'vuejs-loadmore';
// import ScrollLoader from 'vue-scroll-loader'
import VueScroller from 'vue-scroller'
import DKToast from 'vue-dk-toast';
import Autocomplete from 'v-autocomplete'
import 'v-autocomplete/dist/v-autocomplete.css'
import Datepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"




var cors = require('cors');
const gAuthOptions = { clientId: '786231171977-gp77172eu0ptde2lbjdhdfriqftred7t.apps.googleusercontent.com', scope: 'email', prompt: 'consent', fetch_basic_profile: false }
createApp(App).use(VueScroller).use(store).use(GAuth, gAuthOptions).use(router).use(VueAxios,VueCookie,VueCookies,axios,cors({origin: ['http://localhost:8080/**'],
})).use(DKToast).use(Autocomplete).component('Datepicker', Datepicker).mount('#app')
