<template>
<div class="container">
  <DetailImage filename="filename" imageSrc="imageSrc"/>
  <div id="posting-icon__buttons">
    <div class="me-5 my-5 icon-buttons">
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

  <CommentFrame />
</div>

</template>

<script>
import DetailImage from '@/components/postings/DetailImage'
import '@/css/postings/PostingDetail.scss'
import CommentFrame from '@/components/postings/CommentFrame'

export default {
  name: 'PostingDetail',
  components: {
    DetailImage,
    CommentFrame,
  },
  // params로 받아서 props로 받을 수 있음
  // filename = $route.params.filename
  // imageSrc = $route.params.imageSrc
  // api 구현되면 /postings/{posting_id}로 axios get 요청 이미지 불러오기
  // api 구현되면 /comments/{posting_id}/ : page로 axios get 요청 댓글 불러오기
  // /comments/{posting_id}/docent/ : page => 도슨트 댓글(docent_flag)
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