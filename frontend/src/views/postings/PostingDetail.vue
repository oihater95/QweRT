<template>
<div id="posting-detail__container" class="container">
  <DetailImage :postingImg="postingImg"/>
  <div class="d-inline">
    <h3>{{ postingTitle }}</h3>
    <h4>{{ postingContent }}</h4>
    <h5>{{ postingUserNickname }}</h5>
  </div>
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
import { mapState } from 'vuex'
import axios from 'axios'

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

  data: function() {
    return {
      postingId: 0,
      postingImg: '',
      postingTitle: '',
      postingContent: '',
      postingCreateDate: '',
      postingUpdateDate: '',
      postingLikeCnt: 0,
      postingCommentCnt: 0,  // 일반 댓글 수
      postingDocentCnt: 0,  // 도슨트 댓글 수
      postingUserId: 0,
      postingUserNickname: '',
      postingCategoryId: 0,
    }
  },

  computed: {
    ...mapState([
        'host',
        'userInfo'
      ])
  },

  created() {
    axios({
      method: 'get',
      url: `${this.host}/postings/detail/${this.$route.params.postingId}/`,
    })
      .then(res => {
        this.postingId = res.data.postingId
        this.postingImg = 'https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/' + res.data.postingImg
        this.postingTitle = res.data.title
        this.postingContent = res.data.content
        this.postingCreateDate = res.data.createdate
        this.postingUpdateDate = res.data.updatedate
        this.postingLikeCnt = res.data.likeCnt
        this.postingCommentCnt = res.data.commentCnt
        this.postingDocentCnt = res.data.docentCnt
        this.postingUserId = res.data.userId
        this.postingUserNickname = res.data.nickname
        this.postingCategoryId = res.data.categoryId
      })
      .catch(err => {
        console.log(err)
      })
  }

}

</script>

<style>

</style>