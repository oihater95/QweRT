import Vue from 'vue'
import VueRouter from 'vue-router'
import MainPage from '@/views/postings/MainPage.vue'
import CurationPage from '@/views/curations/CurationPage.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'MainPage',
    component: MainPage
  },
  {
    path: '/curation',
    name: 'CurationPage',
    component: CurationPage
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
