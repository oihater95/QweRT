<template>
  <v-container class="profile-container">
    <v-row justify="center">
      <v-col
        cols="7"
        class="pb-0"
      >
        <v-img src="@/assets/images/profile_certificate.png"></v-img>
      </v-col>
    </v-row>
    <v-row
      justify="center"
      align="center"
    >
      <v-col
        cols="2"
        class="profile-image"
      >
        <v-img src="@/assets/images/profile_image_border.png"></v-img>
      </v-col>
      <v-col cols="4">
        <v-row>
          <v-col
            cols="12"
            class="pt-0"
          >
            <h1 class="text-center">nickname</h1>
          </v-col>
          <v-col
            cols="12"
            class="pa-0"
          >
            <h5 class="text-center">팔로워 100 / 팔로잉 100 / 좋아요 1200 / 큐레이션 500</h5>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="2"></v-col>
    </v-row>
    <v-row
      justify="center"
      class="my-5"
    >
      <v-col
        cols="3"
        class="pb-0"
      >
        <v-img src="@/assets/images/profile_divider.png"></v-img>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col cols="6">
        <h3 class="text-center">introduction introduction introduction introduction introduction introduction</h3>
      </v-col>
      <v-col
        cols="10"
        @mouseenter="showDelimiters"
        @mouseleave="hideDelimiters"
      >
        <v-carousel
          light
          height="700"
          show-arrows-on-hover
          hide-delimiter-background
          :hide-delimiters="!hovered"
          cycle
          interval="4000"
        >
          <v-carousel-item
            v-for="(drawing, i) in drawings"
            :key="i"
          >
            <v-row
              justify="center"
              align="center"
              class="profile-masterpiece"
            >
              <v-col>
                <img :src="drawing.src">
              </v-col>
            </v-row>
          </v-carousel-item>
        </v-carousel>
      </v-col>
    </v-row>
    <v-row
      justify="center"
      class="profile-showMore__btn"
    >
      <v-col>
        <v-btn plain>
          <h2>
            <i class="fas fa-caret-down"></i> 더 보기
          </h2>
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import '@/css/profiles/Profile.scss'

export default {
  name: 'Profile',
  data: function () {
    return {
      drawings: [
        {src: 'https://i.ytimg.com/vi/yGqlkavU-lE/maxresdefault.jpg',},
        {src: 'http://www.pipo.co.kr/shopimages/pipouhwa/mobile/8/131668_represent?1506069524',},
        {src: 'https://artlecture.com/data/uploads/2018/8/20180818/d90ea23dc92b277105aa7c7750323cdd_thumb_770.jpg',},
      ],
      hovered: false,
    }
  },
  methods: {
    showDelimiters: function () {
      this.hovered = true
    },
    hideDelimiters: function () {
      this.hovered = false
    },
  },
}
</script>

<style>

</style>