<template>
  <v-container
    class="container"
    fluid>
    <div class="d-flex justify-center curation-menu__tab">
      <div v-if="isLogon" @click="clickMy">My</div>
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
import axios from "axios"
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
      myPage: 0,
      newEnd: false,
      myEnd: false,
      size: 6,
      myCurationImages: [],
      newCurationImages: [],
      
    }
  },
  methods: {
    clickMy: function (e) {
      this.tab= 1
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
    },
    clickNew: function (e) {
      if (this.isLogon) {
        this.tab= 2
        e.target.style.color="skyblue"
        e.target.previousSibling.style.color="black"
      }
    },
    getMyCuration: async function () {
      if (!this.myEnd) {
        axios.get(`${this.host}/curations/${this.userInfo.userId}`, { params: { page: this.myPage, size: this.size }} )
          .then(res => {
            const arr = res.data
             // 마지막이라는 뜻
            if (arr.length < this.size) {
              this.myEnd = true
            }
            this.myCurationImages = this.myCurationImages.concat(arr)
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    getNewCuration: async function () {
      if (!this.newEnd) {
      axios.get(`${this.host}/curations/new`, { params: { page: this.newPage, size: this.size }} )
        .then(res => {
          const arr = res.data
          // 마지막이라는 뜻
          if (arr.length < this.size) {
            this.newEnd = true
          }
          this.newCurationImages = this.newCurationImages.concat(arr)
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    checkScroll: function () {
      const {scrollTop, clientHeight, scrollHeight} = document.documentElement
      if (scrollHeight-scrollTop < clientHeight+150) {
        if (this.tab === 1) {
          this.myPage += 1
          this.getMyCuration()
          } else {
          this.newPage += 1
          this.getNewCuration()
        }
      }
    }
  },
  computed: {
    ...mapState(['host', 'isLogon', 'userInfo']),
  },
  created() {
    if (!this.isLogon) {
      this.tab= 2
      this.getNewCuration()
    } else {
        this.getMyCuration()
      this.getNewCuration()
    }
     window.addEventListener('scroll', this.checkScroll);
  }
}
</script>

<style>

</style>