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
        v-for="(image, idx) in popularResults" 
        :key="1-idx"
        :image="image"
      />
    </v-row>
    <v-row v-if="tab===2">
      <MainImage
        v-for="(image, idx) in newResults" 
        :key="2-idx"
        :image="image"
      />
    </v-row>
  </div>
</template>

<script>
import "@/css/search/SearchCategory.scss"
import MainImage from "@/components/postings/MainImage"
import axios from'axios'
import { mapState } from 'vuex'

export default {
  name: "SearchCategory",
  components: {
    MainImage
  },
  data:  function () {
    return {
      tab: 1,
      popularResults: [],
      newResults: [],
      item: this.$route.params.category,
      id : 0,
      popularPage: 0,
      newPage: 0,
      size: 9
    }
  },
  methods: {
    getPopularResults: function () {
      axios.get(`${this.host}/category`)
        .then(res => {
          for (let item of res.data) {
            if ( this.item === item.categoryName) {
              this.id = item.categoryId
              axios.get(`${this.host}/search/popular/category/${this.id}`, { params: { page: this.popularPage, size: this.size } })
              .then(res => {
                this.popularResults = res.data
                axios.get(`${this.host}/search/new/category/${this.id}`, { params: { page: this.newPage, size: this.size } })
                  .then(res => {
                    this.newResults = res.data
                  })
                  .catch(err => {
                    console.log(err)
                  })
              })
              .catch(err => {
                console.log(err)
              })
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
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
  },
  computed: {
    ...mapState(['host'])
  },
  mounted() {
    this.getPopularResults()
  },
}
</script>

<style>

</style>