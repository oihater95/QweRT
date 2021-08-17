<template>
  <!-- 한 행에 4개씩 -->
  <v-col class="col-12 col-sm-6 col-lg-3">
    <v-card
      elevation=0
      class="v-card offset-1 v-card__main"
    >
      <div class="artist-image__div d-flex flex-column align-center">
        <img
          v-if="artist.profileImg"
          alt="artist_image"
          :src="artist.profileImg"
          @click="clickProfile"
        >
        <img
          v-else
          alt="artist_image"
          src="@/assets/images/profile_image_default.png"
          @click="clickProfile"
        >
        <div class="bottom-div d-flex flex-column align-center">
          <h3>{{ artist.nickname }}</h3>
          <h4 v-if="tab===1">팔로워 수: {{ artist.followerCnt }}</h4>
        </div>
      </div>
    </v-card>
  </v-col>
</template>

<script>
import "@/css/search/ArtistImage.scss"

export default {
  name: 'ArtistImage',
  props: {
    artist: {
      type: Object
    },
    tab: {
      type: Number
    }
  },
  methods: {
    clickProfile: function() {
      this.$router.push({ name: 'Profile', params: {userId: this.artist.userId} })
    },
  },
}
</script>

<style>

</style>