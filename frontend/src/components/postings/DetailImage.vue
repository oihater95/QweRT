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
    :src="$route.params.imageSrc">
    </v-card>
  </div>
</template>

<script>
// import axios from'axios'
import "@/css/postings/DetailImage.scss"

import { mapState } from 'vuex'

export default {
  name: 'DetailImage',
  data: function() {
    return {
      img: ''
    }
  },
  
  computed: {
    ...mapState(['host', 'userInfo'])
  },
}
</script>

<style>

</style>