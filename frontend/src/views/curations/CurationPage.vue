<template>
  <v-container fluid>
    <div class="d-flex justify-center curation-tab">
      <div @click="clickMy">My</div>
      <div @click="clickNew">최신</div>
    </div>
     <v-row v-if="tab===1">
      <CurationDisplay/>
    </v-row>
    <v-row v-if="tab===2">
      
    </v-row>
  </v-container>
</template>

<script>
import "@/css/curations/CurationPage.scss"
import CurationDisplay from "@/components/curations/CurationDisplay"

export default {
  name: "CurationPage",
  components: {
    CurationDisplay
  },
  data:  function () {
    return {
      tab: 1,
      myCurations: [],
    }
  },
  methods: {
    getMyCurations: function () {
      // myCurations에 이미지 집어넣기
    },
    clickMy: function (e) {
      this.tab= 1
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
    },
    clickNew: function (e) {
      this.tab= 2
      e.target.style.color="skyblue"
      e.target.previousSibling.style.color="black"
    },
  },
  // 처음엔 My 큐레이션
  created() {
    this.getMyCurations()
  }, 

}
</script>

<style>

</style>