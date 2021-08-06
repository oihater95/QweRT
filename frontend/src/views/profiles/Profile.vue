<template>
  <v-container class="profile-container">
    <v-row
      justify="center"
      align="center"
    >
      <!-- 헤더 장식 -->
      <v-col
        cols="7"
        offset="2"
        class="pb-0"
      >
        <v-img src="@/assets/images/profile_certificate.png"></v-img>
      </v-col>
      <!-- 정보 수정 버튼 -->
      <v-col cols="2" class="text-end">
        <v-btn
          color="#AEA660"
          small
          depressed
          @click="$router.push({ name: 'ModifyProfile', params: {userId: userId} })"
        >
          정보 수정
        </v-btn>
      </v-col>
    </v-row>
    <!-- 프로필 정보 -->
    <v-row
      justify="center"
      align="center"
    >
      <!-- 프로필 사진 -->
      <v-col
        cols="2"
        class="profile-image"
      >
        <v-img
          src="@/assets/images/profile_image_border.png"
          class="border"
        >
          <v-img
            src="@/assets/images/profile_image_default.png"
            class="content"
          ></v-img>
        </v-img>
      </v-col>
      <!-- 이름 & 팔로워/팔로잉 등등 수치 -->
      <v-col cols="4">
        <v-row>
          <v-col
            cols="12"
            class="pt-0"
          >
            <h1 class="text-center">{{ nickname }}</h1>
          </v-col>
          <v-col
            cols="12"
            class="pa-0"
          >
            <h5 class="text-center">팔로워 100 / 팔로잉 100 / 좋아요 1200 / 큐레이션 500</h5>
          </v-col>
        </v-row>
      </v-col>
      <!-- 팔로우 버튼 -->
      <v-col cols="2">
        <v-btn
          v-if="!followed"
          color="#AEA660"
          width="100"
          class="white--text"
          @click="follow"
        >
          팔로우
        </v-btn>
        <v-btn
          v-else
          color="#857B1A"
          width="100"
          outlined
          @click="follow"
        >
          언팔로우
        </v-btn>
      </v-col>
    </v-row>
    <!-- 중간 장식 -->
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
    <!-- 자기소개 & 대표작 -->
    <v-row justify="center">
      <!-- 자기소개 -->
      <v-col cols="6">
        <h3 class="text-center">{{ introduction }}</h3>
      </v-col>
      <!-- 대표작 -->
      <v-col
        cols="10"
        @mouseenter="hoverOn"
        @mouseleave="hoverOff"
      >
        <v-carousel
          light
          height="700"
          show-arrows-on-hover
          hide-delimiter-background
          :hide-delimiters="!hovered"
          :cycle="!hovered"
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
    <!-- '더 보기' 버튼 -->
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
import { mapState } from 'vuex'
// import axios from 'axios'

export default {
  name: 'Profile',
  data: function () {
    return {
      userId: null,
      nickname: '',
      profileImage: '',
      introduction: '자기소개를 입력해주세요',
      // 예시로 넣어본 그림 url
      drawings: [
        {src: 'https://i.ytimg.com/vi/yGqlkavU-lE/maxresdefault.jpg',},
        {src: 'http://www.pipo.co.kr/shopimages/pipouhwa/mobile/8/131668_represent?1506069524',},
        {src: 'https://artlecture.com/data/uploads/2018/8/20180818/d90ea23dc92b277105aa7c7750323cdd_thumb_770.jpg',},
      ],
      hovered: false,
      followed: false,
    }
  },
  methods: {
    // 캐러셀에 마우스를 올려놓은 상태임을 알리는 함수
    hoverOn: function () {
      this.hovered = true
    },
    // 캐러셀에서 마우스가 떠난 상태임을 알리는 함수
    hoverOff: function () {
      this.hovered = false
    },
    // 임시로 작성한 팔로우 함수 (현재는 버튼이 바뀌는 것만 구현한 상태)
    follow: function () {
      this.followed = !this.followed
    },
  },
  // 임시로 state에서 정보 받아오기
  computed: {
    ...mapState([
      'isLogon',
      'userInfo',
    ])
  },
  created: function () {
    this.userId = this.userInfo.userId
    this.nickname = this.userInfo.nickname
  },
  // created: function () {
  //   axios ({
  //     method: 'get',
  //     url: `http://localhost/qwert/profile/${this.$route.params.userId}/`,
  //   })
  //     .then(res => {
  //       console.log(res)
  //     })
  //     .catch(err => {
  //       console.log(err)
  //     })
  // },
}
</script>

<style>

</style>