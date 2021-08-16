<template>
  <div>
    <div class="search-keyword__result">{{keyword}} 검색 결과</div>
    <div class="d-flex justify-center search-keyword__select">
      <span @click="clickImage">작품</span>
      <span @click="clickArtist">아티스트</span>
    </div>
    <div class="d-flex flex-row-reverse search-keyword__tab">
      <span @click="clickNew">최신순</span>
      <span @click="clickPopular">인기순</span>
    </div>
    <!-- 작품 인기순 -->
    <v-row v-if="first_tab===1&&second_tab===1">
      <MainImage
        v-for="(image, idx) in results" 
        :key="1-idx"
        :image="image"
      />
    </v-row>
    <!-- 작품 최신순 -->
    <v-row v-if="first_tab===1&&second_tab===2">
      <MainImage
        v-for="(image, idx) in results" 
        :key="2-idx"
        :image="image"
      />
    </v-row>
    <!-- 아티스트 인기순 -->
    <v-row v-if="first_tab===2&&second_tab===1">
      <MainImage
        v-for="(image, idx) in results" 
        :key="2-idx"
        :image="image"
      />
    </v-row>
    <!-- 아티스트 최신순 -->
    <v-row v-if="first_tab===2&&second_tab===2">
      <MainImage
        v-for="(image, idx) in results" 
        :key="2-idx"
        :image="image"
      />
    </v-row>
  </div>
</template>

<script>
import "@/css/search/SearchKeyword.scss"
import MainImage from "@/components/postings/MainImage"

export default {
  name: "SearchKeyword",
  components: {
    MainImage
  },
  data:  function () {
    return {
      first_tab: 1,
      second_tab: 2,
      results: [],
      keyword: this.$route.params.keyword
    }
  },
  methods: {
    getResults: function () {
      // results 이미지 집어넣기
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
  },
    // 검색 결과 받아오기
  created() {
    this.getResults()
  }, 
}
</script>

<style>

</style>