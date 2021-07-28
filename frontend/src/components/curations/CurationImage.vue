<template>
    <!-- 한 행에 2개씩 -->
  <v-col 
    cols=6
    class="col">
    <!-- elevation: 테두리 -->
    <v-card
      elevation=1
      class="v-card mb-5"
    >
      <div class="curation-image__div">

        <h1># {{curation.title}}</h1>
        <p>{{curation.content}}</p>
        <img v-if="curation.thumbnail"
          :src="curation.thumbnail"
          alt="curation_thumbnail"
          class="curation-thumbnail">
        <v-row
          v-else
          no-gutters
          class="curation-three__images d-flex justify-space-around">
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
    }
  },
}
</script>

<style>

</style>