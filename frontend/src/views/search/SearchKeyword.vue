<template>
  <div>
    <div class="search-keyword__result">{{keyword}} 검색 결과</div>
    <div class="d-flex justify-center search-keyword__select">
      <span @click="clickImage">작품</span>
      <span @click="clickArtist">아티스트</span>
    </div>
    <div v-if="first_tab===1" class="d-flex flex-row-reverse search-keyword__tab">
      <span @click="clickNew">최신순</span>
      <span @click="clickPopular">인기순</span>
    </div>
    <div v-else class="d-flex flex-row-reverse search-keyword__tab">
      <span @click="clickNew">가입일순</span>
      <span @click="clickPopular">팔로워순</span>
    </div>
    <!-- 작품 인기순 -->
    <v-row v-if="first_tab===1&&second_tab===1">
      <MainImage
        v-for="(image, idx) in popularImages" 
        :key="1-idx"
        :image="image"
      />
    </v-row>
    <!-- 작품 최신순 -->
    <v-row v-if="first_tab===1&&second_tab===2">
      <MainImage
        v-for="(image, idx) in newImages" 
        :key="2-idx"
        :image="image"
      />
    </v-row>
    <!-- 아티스트 인기순 -->
    <v-row v-if="first_tab===2&&second_tab===1">
      <ArtistImage
        v-for="(artist, idx) in popularArtists" 
        :key="3-idx"
        :artist="artist"
        :tab=1
      />
    </v-row>
    <!-- 아티스트 최신순 -->
    <v-row v-if="first_tab===2&&second_tab===2">
      <ArtistImage
        v-for="(artist, idx) in newArtists" 
        :key="4-idx"
        :artist="artist"
        :tab=2
      />
    </v-row>
  </div>
</template>

<script>
import "@/css/search/SearchKeyword.scss"
import MainImage from "@/components/postings/MainImage"
import ArtistImage from "@/components/search/ArtistImage"
import axios from "axios"
import { mapState } from 'vuex'

export default {
  name: "SearchKeyword",
  components: {
    MainImage,
    ArtistImage
  },
  data:  function () {
    return {
      first_tab: 1,
      second_tab: 1,
      keyword: this.$route.params.keyword,
      
      popularImages: [],
      newImages: [],
      popularArtists: [],
      newArtists: [],
      
      popularImageEnd: false,
      newImageEnd: false,
      popularArtistEnd: false,
      newArtistEnd: false,

      popularImagePage: 0,
      newImagePage: 0,
      popularArtistPage: 0,
      newArtistPage: 0,
      
      imageSize: 9,
      artistSize: 16
    }
  },
  methods: {
    getResults: function () {
      this.getPopularImages()
      this.getNewImages()
      this.getPopularArtists()
      this.getNewArtists()
    },
    getPopularImages: function () {
      if (!this.popularImageEnd) {
        axios.get(`${this.host}/search/popular/posting/${this.keyword}`, { params: { page: this.popularImagePage, size: this.imageSize } })
        .then(res => {
          const arr = res.data
          if (arr.length < this.size) {
            this.popularImageEnd = true
          }
          this.popularImages = this.popularImages.concat(arr)
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    getNewImages: function () {
      if (!this.newImageEnd) {
       axios.get(`${this.host}/search/new/posting/${this.keyword}`, { params: { page: this.newImagePage, size: this.imageSize } })
        .then(res => {
          const arr = res.data
          if (arr.length < this.size) {
            this.newImageEnd = true
          }
          this.newImages = this.newImages.concat(arr)
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    getPopularArtists: function () {
      if (!this.popularArtistEnd) {
        axios.get(`${this.host}/search/popular/artist/${this.keyword}`, { params: { page: this.popularArtistPage, size: this.artistSize } })
        .then(res => {
          const arr = res.data
          if (arr.length < this.size) {
            this.popularArtistEnd = true
          }
          this.popularArtists = this.popularArtists.concat(arr)
          console.log(this.popularArtists)
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    getNewArtists: function () {
      if (!this.newArtistEnd) {
       axios.get(`${this.host}/search/new/artist/${this.keyword}`, { params: { page: this.newArtistPage, size: this.artistSize } })
        .then(res => {
          const arr = res.data
          if (arr.length < this.size) {
            this.newArtistEnd = true
          }
          this.newArtists = this.newArtists.concat(arr)
        })
        .catch(err => {
          console.log(err)
        }) 
      }
    },
    clickImage: function (e) {
      this.first_tab=1
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
    },
    clickArtist: function (e) {
      this.first_tab=2
      e.target.style.color="skyblue"
      e.target.previousSibling.style.color="black"
    },
    clickPopular: function (e) {
      this.second_tab=1
      e.target.style.color="skyblue"
      e.target.previousSibling.style.color="black"
    },
    clickNew: function (e) {
      this.second_tab=2
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
    },


    checkScroll: function () {
      const {scrollTop, clientHeight, scrollHeight} = document.documentElement
      if (scrollHeight-scrollTop < clientHeight+150) {
        if (this.first_tab === 1 && this.second_tab === 1) {
          this.popularImagePage += 1
          this.getPopularImages()
          } else if (this.first_tab === 1 && this.second_tab === 2) {
          this.newImagePage += 1
          this.getNewImages()
        }
      }
    }
  },
  computed: {
    ...mapState(['host'])
  },
    // 검색 결과 받아오기
  mounted() {
    this.getResults()
    window.addEventListener('scroll', this.checkScroll);
  }, 
}
</script>

<style>

</style>