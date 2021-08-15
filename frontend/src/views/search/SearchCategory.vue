<template>
  <div>
    <div class="search-category__result">카테고리 {{item}} 검색 결과</div>
    <div class="d-flex flex-row-reverse search-category__tab">
      <span @click="clickNew">최신순</span>
      <span @click="clickPopular">인기순</span>
    </div>
    <!-- 인기순 -->
    <v-row v-if="tab===1">
      <MainImage
        v-for="(image, idx) in results" 
        :key="1-idx"
        :image="image"
      />
    </v-row>
    <v-row v-if="tab===2">
      <MainImage
        v-for="(image, idx) in results" 
        :key="2-idx"
        :image="image"
      />
    </v-row>
  </div>
</template>

<script>
import "@/css/search/SearchCategory.scss"
import MainImage from "@/components/postings/MainImage"

export default {
  name: "SearchCategory",
  components: {
    MainImage
  },
  data:  function () {
    return {
      tab: 1,
      results: [],
      item: this.$route.params.category
    }
  },
  methods: {
    getResults: function () {
      // results 이미지 집어넣기
    },
    clickPopular: function (e) {
      this.tab= 1
      e.target.style.color="skyblue"
      e.target.previousSibling.style.color="black"
    },
    clickNew: function (e) {
      this.tab= 2
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
    },
    // 검색 결과 받아오기
    created() {
      this.getResults()
    }, 
  }
}
</script>

<style>

</style>