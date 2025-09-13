import { createRouter, createWebHistory } from 'vue-router'
import SignupView from '@/views/SignupView.vue'
import HomeView from '@/views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignupView
  },
  // Altre rotte verranno aggiunte dopo
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router