<template>
  <div>
    <div class="d-flex justify-center">
        <div class="search-input__div d-flex">
          <v-icon 
            class="search-page__icon"
          >
            fas fa-search
          </v-icon>
          <v-text-field
            placeholder="검색"
            v-model="textInput"
            @keyup.enter="inputEnter"
          >
          </v-text-field>
        </div>
    </div>
    <div class="category-div">카테고리로 검색하기</div>
    <div class="category-grid">
      <v-row>
        <RoundedBtn
          v-for="(item, idx) in category" 
          :key="idx"
          :item="item"
          :idx="idx"
          @clickCategory="clickCategory"
        />
    </v-row>
    </div>
  </div>
</template>

<script>
import "@/css/search/SearchPage.scss"
import RoundedBtn from "@/components/common/RoundedBtn"
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: "SearchPage",
  components: {
    RoundedBtn,
  },
  data:  function () {
    return {
      category: [],
      textInput: "",
    }
  },
  methods: {
    inputEnter: function () {
      if (this.textInput) {
        this.$router.push({ name: 'SearchKeyword', params: { keyword: this.textInput }, query: { t: new Date().getTime() }})
      }
    },
    clickCategory: function (item) {
      this.$router.push({ name: 'SearchCategory', params: { category: item } })
    },
  },
  computed: {
    ...mapState([
      'host',
    ])
  },
  mounted () {
    axios.get(`${this.host}/category`)
    .then(res => {
      for (const item of res.data) {
        this.category.push(item.categoryName)
      }
    })
    .catch(err => {
      console.log(err)
    })
  }
}
</script>

<style>

</style>