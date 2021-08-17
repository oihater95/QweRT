<template>
    <!-- 한 행에 3개씩 -->
  <v-col class="col-12 col-sm-6 col-lg-4">
    <!-- elevation: 테두리 -->
    <v-card
      elevation=0
      class="v-card offset-1 v-card__main"
      :data-postingId="image.postingId"
    >
      <div class="main-image__div">
        <img
          :src="printPosting"
          alt="posting_image"
          @click="clickToGoDetail"
        >
        <div class="bottom-div d-flex flex-column align-center">
          <img
            v-if="getProfileImg"
            :src="getProfileImg"
            alt="profile_image"
            @click="goUserProfile"
          >
          <img 
            v-else 
            src="@/assets/images/profile_image_default.png" 
            alt="default-profile__image"
            @click="goUserProfile"
          >
          <h3 @click="goUserProfile">{{ getNickname }}</h3>
          <h4>{{image.title}}</h4>
          <span>
            <v-icon>fas fa-heart</v-icon>
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
      userId: 0,
      profileImg: '',
    }
  },
  methods: {
    clickToGoDetail: function() {
      this.$router.push({name: 'PostingDetail', params: {postingId: this.image.postingId}})
    },
    getDetail() {
      axios.get(`${this.hostUrl}/postings/detail/${this.image.postingId}`)
      .then(res => {
        this.nickname = res.data.nickname
        this.userId = res.data.userId
      })
      .catch(err => {
        console.log(err)
      })
    },

    getProfile(){
      axios.get(`${this.hostUrl}/profile/${this.userId}`)
      .then(res => {
        this.profileImg = res.data.profileImg
      })
      .catch(err => {
        console.log(err)
      })
    },

    goUserProfile: function() {
      this.$router.push({ name: 'Profile', params: {userId: this.userId} })
    },


  },
  computed: {
    printPosting () {
      return 'https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/' + this.image.postingImg
    },
    getProfileImg() {
      this.getDetail()
      if (this.userId !== 0) {
        this.getProfile()
      }
      return this.profileImg
    },

    getNickname() {
      this.getDetail()
      return this.nickname
    },

    ...mapState([
        'hostUrl',
        'userInfo',
        'isLogon'
      ])
  },

  mounted() {
    this.getDetail()
  },

}
</script>

<style>

</style>