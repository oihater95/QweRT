<template>
    <!-- 한 행에 3개씩 -->
  <v-col cols=4>
    <!-- elevation: 테두리 -->
    <v-card
      elevation=0
      class="v-card offset-1 feed-image__card"
    >
      <div class="feed-image__div">
        <img
          :src="printPosting"
          alt="posting_image"
          @click="clickToGoDetail"
        >
      </div>
      <div class="d-flex align-center ml-2 info-div">
        <img
          v-if="image.postingProfileImg"
          :src="image.postingProfileImg"
          alt="profile_image"
        >
        <h3 class="nickname ml-2">{{ this.nickname }}</h3>
      </div>
      <h4 class="ml-1">{{image.title}}</h4>
      <v-icon 
        class="ml-2"
        v-if="image.like_state">
        fas fa-heart
      </v-icon>
      <v-icon v-else>far fa-heart</v-icon>
      {{image.likeCnt}}
      <v-icon class="ml-2">fas fa-comment</v-icon>
      {{image.commentCnt}}
      <v-icon class="ml-2">far fa-image</v-icon>
      {{image.curatedCnt}}
    </v-card>
  </v-col>
</template>



<script>
import "@/css/postings/FeedImage.scss"
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: "MainImage",
  props: {
    image: {
      type: Object
    }
  },
  data: function() {
    return {
      nickname: '',
    }
  },

  methods: {
    clickToGoDetail: function() {
      this.$router.push({name: 'PostingDetail', params: {postingId: this.image.postingId}})
    },
    getUserNickname() {
      axios.get(`${this.host}/postings/detail/${this.image.postingId}`)
      .then(res => {
        this.nickname = res.data.nickname
      })
      .catch(err => {
        console.log(err)
      })
    },

  },
  computed: {
    printPosting () {
      return 'https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/' + this.image.postingImg
    },
    ...mapState([
      'host',
      'userInfo'
    ])
  },
  created() {
    this.getUserNickname()
  },
}
</script>

<style>

</style>