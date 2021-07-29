<template>
  <div>
    <div class="d-flex justify-center">
        <div class="search-input__div d-flex">
          <v-icon 
            class="search-page__icon"
            @click.native="clickSearch"
          >
            fas fa-search
          </v-icon>
          <v-text-field
            :rules="rules"
            placeholder="검색"
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
        />
    </v-row>
    </div>
  </div>
</template>

<script>
import "@/css/search/SearchPage.scss"
import RoundedBtn from "@/components/common/RoundedBtn"

export default {
  name: "SearchPage",
  components: {
    RoundedBtn,
  },
  data:  function () {
    return {
      category: ["인물", "동물", "식물", "풍경", "음식","건물", "패션", "캐릭터","낙서", "3D", "흑백","기타"]
    }
  },
  methods: {
    inputEnter: function () {
      // 검색 결과로 이동하기
    },
  },
}
</script>

<style>

</style>