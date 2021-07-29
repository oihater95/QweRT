<template>
  <div id="DetailImg-section" class="container d-flex">
    <v-card
    id="DetailImg-card"
    class="row"
    elevation="5"
    max-width="1050px"
    max-height="1050px"
    >
      <img 
      id="DetailImage"
      class="col-12"
      :src="$route.params.imageSrc">
    </v-card>
  </div>
</template>

<script>
// import axios from'axios'
import "@/css/postings/DetailImage.scss"

export default {
  name: 'DetailImage',
  
}
</script>

<style>

</style>