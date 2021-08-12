<template>
  <v-container
    class="container"
    fluid>
    <div class="d-flex justify-center curation-menu__tab">
      <div @click="clickMy">My</div>
      <div @click="clickNew">최신</div>
    </div>
    <v-row v-if="tab===1">
      <!-- tab을 통해서 my curation을 식별하고 수정버튼을 만들겠다. -->
      <CurationImage
        v-for="(curation, idx) in myCurationImages" 
        :key="1-idx"
        :curation="curation"
        :tab="tab"
      />
    </v-row>
    <v-row v-if="tab===2">
      <CurationImage
        v-for="(curation, idx) in newCurationImages" 
        :key="2-idx"
        :curation="curation"
        :tab="tab"
      />
    </v-row>
  </v-container>
</template>

<script>
import "@/css/curations/CurationPage.scss"
import CurationImage from "@/components/curations/CurationImage"
import { mapState } from 'vuex'

export default {
  name: "CurationPage",
  components: {
    CurationImage,
  },
  data:  function () {
    return {
      tab: 1,
      newPage: 0,
      myPage,
      size: 6,
      myCurationImages: [],
      newCurationImages: [],
      
    }
  },
  methods: {
    getMyCuration: function () {
      // myCurationImages에 이미지 집어넣기
    },
    getNewCuration: function () {
      axios.get(`${this.host}/curations/new`, { params: { page: this.newPage, size: this.size }} )
      .then(res => {
        // newCurationImages에 이미지 집어넣기
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
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
  computed: {
    ...mapState(['host']),
  },
  // 처음엔 내 큐레이션 이미지
  created() {
    this.getMyCuration()
    this.getNewCuration()
  }, 
}
</script>

<style>

</style>