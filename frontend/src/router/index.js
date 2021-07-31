import Vue from 'vue'
import VueRouter from 'vue-router'
import Signup from '@/views/accounts/Signup'
import Login from '@/views/accounts/Login'
import MainPage from '@/views/postings/MainPage.vue'
import CurationPage from '@/views/curations/CurationPage.vue'
import PostingDetail from '@/views/postings/PostingDetail'
import dragNdrop from '@/components/common/dragNdrop'  // 확인용 (업로드 페이지 대체)
import SearchPage from '@/views/search/SearchPage'
import SearchCategory from '@/views/search/SearchCategory'
import SearchKeyword from '@/views/search/SearchKeyword'
import NotifyPage from '@/views/notify/NotifyPage'
import Drawing from '@/components/postings/Drawing'

Vue.use(VueRouter)

const routes = [
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
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
  {
    // path: '/postings/:posting_id',
    path: '/postings/detail',  // 임시 path
    name: 'PostingDetail',
    component: PostingDetail,
    props: true,
  },
  {
    
    path: '/postings/drawing',
    name: 'Drawing',
    component: Drawing,
  },
  {
    path: '/postings',  // 확인용 임시 path
    name: 'dragNdrop',
    component: dragNdrop,
  },
  {
    path: '/search',
    name: 'SearchPage',
    component: SearchPage
  },
  {
    path: '/search/category/:category',
    name: 'SearchCategory',
    component: SearchCategory
  },
  {
    path: '/search/keyword/:keyword',
    name: 'SearchKeyword',
    component: SearchKeyword
  },
  {
    path: '/notify',
    name: 'NotifyPage',
    component: NotifyPage
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
