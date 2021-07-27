<template>
<div>
  <div class="container">
    <ImageSection filename="filename" imageSrc="imageSrc"/>
  </div>
</div>
</template>

<script>
import ImageSection from '@/components/postings/ImageSection'

export default {
  name: 'PostingDetail',
  components: {
    ImageSection,
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