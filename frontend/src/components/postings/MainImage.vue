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
          :src="image.posting_image"
          alt="posting_image"
          @click="clickToGoDetail"
        >
        <div class="bottom-div d-flex flex-column align-center">
          <img
            :src="image.profile_image"
            alt="profile_image"
          >
          <h3>{{image.nickname}}</h3>
          <h4>{{image.title}}</h4>
          <span>
            <v-icon v-if="image.like_state">fas fa-heart</v-icon>
            <v-icon v-else>far fa-heart</v-icon>
            {{image.liked_cnt}}
          </span>
        </div>
      </div>
    </v-card>
  </v-col>
</template>



<script>
import "@/css/postings/MainImage.scss"

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
    }
  }
}
</script>

<style>

</style>