<template>
<div>
  <div class="container">
    <DetailImage filename="filename" imageSrc="imageSrc"/>
    <div class="posting-icon__buttons text-right me-5 pe-5 my-5">
      <v-btn
        class="mx-2"
        fab
        small
        outlined
        color="green darken-3">
        <v-icon dark>
          mdi-expand-all-outline
        </v-icon>
      </v-btn>
      <v-btn
        class="mx-2"
        fab
        small
        outlined
        color="blue darken-3">
        <v-icon dark>
          mdi-image-edit-outline
        </v-icon>
      </v-btn>
      <v-btn
        class="mx-2"
        fab
        small
        outlined
        color="red darken-1">
        <v-icon dark>
          mdi-trash-can-outline
        </v-icon>
      </v-btn>
    </div>
  </div>
</div>
</template>

<script>
import DetailImage from '@/components/postings/DetailImage'

export default {
  name: 'PostingDetail',
  components: {
    DetailImage,
  },
  // params로 받아서 props로 받을 수 있음
  // filename = $route.params.filename
  // imageSrc = $route.params.imageSrc
  props: {  
    filename: {
      type: String
    },
    imageSrc : {
      type: String
    }
  },

}

</script>

<style>

</style>