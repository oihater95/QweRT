<template>
  <div id="DetailImg-section" class="container d-flex">
    <v-card
    id="DetailImg-card"
    class="row"
    elevation="5"
    max-width="850px"
    max-height="850px"
    >
    <img 
    id="DetailImage"
    class="col-12"
    style="object-fit: contain;"
    :src="postingImg">
    </v-card>
  </div>
</template>

<script>
// import axios from'axios'
import "@/css/postings/DetailImage.scss"

export default {
  name: 'DetailImage',
  props: {
    postingImg: {
      type: String
    }
  },
}
</script>

<style>

</style>