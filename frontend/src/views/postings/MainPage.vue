<template>
  <v-container fluid>
    <div class="d-flex justify-center menu-tab">
      <div v-if="isLogon" @click="clickFeed">피드</div>
      <div @click="clickPopular">인기</div>
      <div @click="clickNew">최신</div>
    </div>
    <v-row v-if="tab===1">
      <FeedImage
        v-for="(image, idx) in feedImages" 
        :key="1-idx"
        :image="image"
      />
    </v-row>
    <v-row v-if="tab===2">
      <MainImage
        v-for="(image, idx) in popularImages" 
        :key="2-idx"
        :image="image"
      />
    </v-row>
    <v-row v-if="tab===3">
      <MainImage
        v-for="(image, idx) in newImages" 
        :key="3-idx"
        :image="image"
      />
    </v-row>
  </v-container>
</template>

<script>
import "@/css/postings/MainPage.scss"
import FeedImage from "@/components/postings/FeedImage"
import MainImage from "@/components/postings/MainImage"
import axios from 'axios'
import { mapState } from 'vuex'



export default {
  name: "MainPage",
  components: {
    FeedImage,
    MainImage
  },
  data:  function () {
    return {
      tab: 1,
      feedImages: [],
      popularImages: [],
      newImages: [],
      feedPage: 0,
      popularPage: 0,
      newPage: 0,
      size: 9,
    }
  },
  methods: {
    getFeedImages: function () {
      axios ({
        method: 'get',
        url: `${this.host}/postings/${this.userInfo.userId}/feed`,
        params: { page: this.feedPage, size: this.size },
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {
          this.feedImages = this.feedImages.concat(res.data)
        })
        .catch(err => {
          console.log(err)
        })
    },
    getPopularImages: function () {
      // popularImages에 이미지 집어넣기
      axios.get(`${this.host}/postings/popular/`, { params: { page: this.popularPage, size: this.size } })
      .then(res => {
        this.popularImages = this.popularImages.concat(res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },

    getNewImages: function () {
      // newImages에 이미지 집어넣기
      axios.get(`${this.host}/postings/new/`, { params: { page: this.newPage, size: this.size } })
      .then(res => {
        this.newImages = this.newImages.concat(res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },

    clickFeed: function (e) {
      this.tab= 1
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
      e.target.nextSibling.nextSibling.style.color="black"
    },
    clickPopular: function (e) {
      if (this.isLogon) {
        e.target.previousSibling.style.color="black"
        e.target.nextSibling.style.color="black"
      } else {
        e.target.nextSibling.style.color="black"
      }
      this.tab= 2
      e.target.style.color="skyblue"
    },
    clickNew: function (e) {
      if (this.isLogon) {
        e.target.previousSibling.style.color="black"
        e.target.previousSibling.previousSibling.style.color="black"
      } else {
        e.target.previousSibling.style.color="black"
      }
      this.tab= 3
      e.target.style.color="skyblue"
    },

    // 스크롤이 끝에 닿을 때 쯤 추가 페이지 받아오기
    scrollLoadMore() {
        var clientHeight = document.documentElement.clientHeight
        var scrollTop = document.documentElement.scrollTop
        var scrollHeight = document.documentElement.scrollHeight
        if(clientHeight+scrollTop+20>=scrollHeight){
            if(this.tab === 1) {
              this.feedPage = this.feedPage + 1;
              this.getFeedImages()
            } 
            else if (this.tab === 2) {
              this.popularPage = this.popularPage + 1;
              this.getPopularImages()
            } else {
              this.newPage = this.newPage + 1;
              this.getNewImages()
            }
        }
		},

  },
  computed: {
    ...mapState([
      'host',
      'isLogon',
      'userInfo'
    ]),
  },
  created() {
    // 게시물 받아오기
    if (this.isLogon === true) {
      this.getFeedImages()
      this.getPopularImages()
      this.getNewImages()
      this.tab = 1
    } else {
      this.getPopularImages()
      this.getNewImages()
      this.tab = 2
    }
    
    // this.getPopularImages() => 인기 게시물 받아오기
    window.addEventListener('scroll', this.scrollLoadMore)
  },

  // 페이지 이동할 때 스크롤 이벤트 리스너 삭제
  beforeDestroy () { 
    window.removeEventListener('scroll', this.scrollLoadMore) 
  },


}
</script>

<style>

</style>
