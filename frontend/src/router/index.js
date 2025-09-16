import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/HomeView.vue')
  },
  {
    path: '/example',
    name: 'example',
    component: () => import('../views/ExampleView.vue')
  }
  // Altre rotte verranno aggiunte dopo
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router