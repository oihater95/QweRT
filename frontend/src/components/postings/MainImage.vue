<template>
    <!-- 한 행에 3개씩 -->
  <v-col cols=4>
    <!-- elevation: 테두리 -->
    <v-card
      elevation=0
      class="v-card offset-1 v-card__main"
    >
      <div class="main-image__div">
        <img
          :src="printPosting"
          alt="posting_image"
          @click="clickToGoDetail"
        >
        <div class="bottom-div d-flex flex-column align-center">
          <img
            v-if="image.postingProfileImg"
            :src="image.postingProfileImg"
            alt="profile_image"
          >
          <h3>{{ this.nickname }}</h3>
          <h4>{{image.title}}</h4>
          <span>
            <v-icon v-if="image.like_state">fas fa-heart</v-icon>
            <v-icon v-else>far fa-heart</v-icon>
            {{image.likeCnt}}
          </span>
        </div>
      </div>
    </v-card>
  </v-col>
</template>



<script>
import "@/css/postings/MainImage.scss"
import axios from 'axios'
import { mapState } from 'vuex'

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
      console.log(this.image)
      // axios.get(`${this.host}/postings/detail/${this.image.postingId}`)
      axios.get(`${this.host}/postings/detail/1`)
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