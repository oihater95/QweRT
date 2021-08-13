<template>
    <!-- 한 행에 2개씩 -->
  <v-col 
    cols=6
    class="curation-image__col">
    <!-- elevation: 테두리 -->
    <v-card
      elevation=1
      class="v-card mb-5"
    >
      <v-btn
        v-if="tab===1"
        class="curation-edit__btn"
        elevation="1"
        @click="clickChangeCuration"
        small
      >
        <v-icon left>
          mdi-pencil
        </v-icon>
        수정하기
      </v-btn>
      <div class="curation-image__div" @click="clickCurationDiv">

        <span class="curation-title__span"># {{curation.title}}</span>
        <span v-if="tab===2" class="curation-user__span">by {{curation.user_name}}</span>
        <p>{{curation.content}}</p>
        <img v-if="curation.thumbnail"
          :src="curation.thumbnail"
          alt="curation_thumbnail"
          class="curation-thumbnail">
        <v-row
          v-else
          no-gutters
          class="curation-three__images d-flex">
          <v-col 
            cols=4
            v-for="(image, idx) in curation.images"  
            :key="idx"
          >
            <img
              :src="image"
              alt="curatioon_images">
          </v-col>
        </v-row>
      </div>
    </v-card>
  </v-col>
</template>



<script>
import "@/css/curations/CurationImage.scss"

export default {
  name: "CurationImage",
  props: {
    curation: {
      type: Object
    },
    tab: {
      type: Number
    },
  
  },
  methods: {
    clickCurationDiv: function () {
      this.$router.push({ name: 'CurationDetail' , params: { id: this.curation.curationId}})
    },
    clickChangeCuration: function () {
      this.$router.push({ name: 'ChangeCuration', params: { id: this.curation.curationId} })
    },
  },
}
</script>

<style>

</style>