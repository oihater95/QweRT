<template>
  <div>
    <v-card
    elevation="2"
    >ImageSection!
    <img :src="$route.params.imageSrc" style="height: 500px; width: 500px">
    <br>
    </v-card>
  </div>
</template>

<script>
// import axios from'axios'
import "@/components/css/postings/ImageSection.scss"

export default {
  name: 'ImageSection',
  
}
</script>

<style>

</style>