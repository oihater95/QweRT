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
          :src="image.posting_image"
          alt="posting_image"
          @click="clickToGoDetail"
        >
      </div>
      <div class="d-flex align-center ml-2 info-div">
        <img
          :src="image.profile_image"
          alt="profile_image"
        >
        <h3 class="nickname ml-2">{{image.nickname}}</h3>
      </div>
      <h4 class="ml-1">{{image.title}}</h4>
      <v-icon 
        class="ml-2"
        v-if="image.like_state">
        fas fa-heart
      </v-icon>
      <v-icon v-else>far fa-heart</v-icon>
      {{image.liked_cnt}}
      <v-icon class="ml-2">fas fa-comment</v-icon>
      {{image.comment_cnt}}
      <v-icon class="ml-2">far fa-image</v-icon>
      {{image.curated_cnt}}
    </v-card>
  </v-col>
</template>



<script>
import "@/css/postings/FeedImage.scss"

export default {
  name: "MainImage",
  props: {
    image: {
      type: Object
    }
  },
  methods: {
    clickToGoDetail: function() {
      this.$router.push({name: 'PostingDetail', params: {filename: this.image.title, imageSrc: this.image.posting_image}})
    },
  }
}
</script>

<style>

</style>