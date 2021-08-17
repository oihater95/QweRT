import Vue from 'vue'
import VueRouter from 'vue-router'

import Signup from '@/views/accounts/Signup'
import Login from '@/views/accounts/Login'

import Profile from '@/views/profiles/Profile'
import ModifyProfile from '@/views/profiles/ModifyProfile'

import CurationPage from '@/views/curations/CurationPage.vue'
import NewCuration from '@/views/curations/NewCuration.vue'
import ChangeCuration from '@/views/curations/ChangeCuration.vue'
import CurationDetail from '@/views/curations/CurationDetail.vue'

import MainPage from '@/views/postings/MainPage.vue'
import PostingDetail from '@/views/postings/PostingDetail'
import UploadPage from '@/views/postings/UploadPage'
import Drawing from '@/components/postings/Drawing'

import SearchPage from '@/views/search/SearchPage'
import SearchCategory from '@/views/search/SearchCategory'
import SearchKeyword from '@/views/search/SearchKeyword'

import NotFoundComponent from '@/views/NotFoundComponent'

import store from "../vuex/store"

Vue.use(VueRouter)

// 로그인되지 않아도 갈 수 있는 곳: 회원가입, 로그인, 검색, 메인페이지, 그림 상세페이지, 프로필, 큐레이션 메인페이지, 큐레이션 상세페이지
// next('/login') : 로그인 페이지로 이동시키기
// next() : 원하는대로 이동시키기
const authenticated = (_to, _from, next) => {
  if (!store.state.isLogon) {
    next('/login');
    return;
  } else {
    next();
  }
};

const routes = [
  { path: '/', name: 'MainPage', component: MainPage, },

  { path: '/signup', name: 'Signup', component: Signup },
  { path: '/login', name: 'Login', component: Login },

  { path: '/profile/:userId', name: 'Profile', component: Profile },
  { path: '/profile/:userId/edit', name: 'ModifyProfile', component: ModifyProfile, beforeEnter: authenticated },

  { path: '/curation', name: 'CurationPage', component: CurationPage },
  { path: '/curation/new', name: 'NewCuration', component: NewCuration, beforeEnter: authenticated },
  { path: '/curation/detail/:id', name: 'CurationDetail', component: CurationDetail },
  { path: '/curation/:id/edit', name: 'ChangeCuration', component: ChangeCuration, beforeEnter: authenticated },

  { path: '/postings/detail/:postingId', name: 'PostingDetail', component: PostingDetail },
  { path: '/postings/drawing', name: 'Drawing', component: Drawing , beforeEnter: authenticated },
  { path: '/postings',  name: 'UploadPage', component: UploadPage, props: true , beforeEnter: authenticated },

  { path: '/search', name: 'SearchPage', component: SearchPage },
  { path: '/search/category/:category', name: 'SearchCategory', component: SearchCategory },
  { path: '/search/keyword/:keyword', name: 'SearchKeyword', component: SearchKeyword },
  
  // 위에 해당하지 않을 경우
  { path: '*', component: NotFoundComponent }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

export default router
