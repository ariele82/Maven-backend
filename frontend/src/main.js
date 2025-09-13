import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // Verifica questo import

// Bootstrap
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'
import BootstrapVue3 from 'bootstrap-vue-3'

// Stili personalizzati
import './assets/css/custom.css'

const app = createApp(App)
app.use(router) // Verifica questa riga
app.use(BootstrapVue3)
app.mount('#app')