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
          v-if="$route.params.userId == userInfo.userId"
          color="#AEA660"
          small
          depressed
          @click="$router.push({ name: 'ModifyProfile', params: {userId: $route.params.userId} })"
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
            v-if="!profileImageSrc"
            src="@/assets/images/profile_image_default.png"
            class="content"
          ></v-img>
          <v-img
            v-else
            src="profileImageSrc"
            class="content"
          ></v-img>
        </v-img>
      </v-col>
      <!-- 이름 & 팔로워/팔로잉 등등 수치 -->
      <v-col
        cols="4"
        class="profile-userInfo"
      >
        <v-row>
          <!-- 이름 -->
          <v-col
            cols="12"
            class="pt-0"
          >
            <h1>{{ nickname }}</h1>
          </v-col>
          <!-- 팔로워/팔로잉 등등 수치 -->
          <v-col
            cols="12"
            class="pa-0"
          >
            <h5>
              <span class="follow">
                팔로워 {{ followerCnt }}
              </span> |
              <span class="follow">
                팔로잉 {{ followingCnt }}
              </span> |
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <span v-bind="attrs" v-on="on">
                    <v-icon small>fas fa-heart</v-icon> {{ likedCnt }}
                  </span>
                </template>
                <span>게시물이 좋아요 받은 횟수</span>
              </v-tooltip> |
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <span v-bind="attrs" v-on="on">
                    <v-icon small>far fa-image</v-icon> {{ curatedCnt }}
                  </span>
                </template>
                <span>게시물이 큐레이팅된 횟수</span>
              </v-tooltip>
            </h5>
          </v-col>
        </v-row>
      </v-col>
      <!-- 팔로우 버튼 -->
      <v-col cols="2">
        <v-btn
          v-if="($route.params.userId != userInfo.userId) && !followState"
          color="#AEA660"
          width="100"
          class="white--text"
          @click="follow"
        >
          팔로우
        </v-btn>
        <v-btn
          v-if="($route.params.userId != userInfo.userId) && followState"
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
import axios from 'axios'

export default {
  name: 'Profile',
  data: function () {
    return {
      nickname: '',
      profileImageSrc: '',
      introduction: '',
      followerCnt: null,
      followingCnt: null,
      likedCnt: null,
      curatedCnt: null,
      followState: false,
      masterpieces: [],
      // 예시로 넣어본 그림 url
      drawings: [
        {src: 'https://i.ytimg.com/vi/yGqlkavU-lE/maxresdefault.jpg',},
        {src: 'http://www.pipo.co.kr/shopimages/pipouhwa/mobile/8/131668_represent?1506069524',},
        {src: 'https://artlecture.com/data/uploads/2018/8/20180818/d90ea23dc92b277105aa7c7750323cdd_thumb_770.jpg',},
      ],
      hovered: false,
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
      this.followState = !this.followState
    },
  },
  // 임시로 state에서 정보 받아오기
  computed: {
    ...mapState([
      'host',
      'userInfo',
    ])
  },
  created: function () {
    axios({
      method: 'get',
      url: `${this.host}/profile/${this.$route.params.userId}/`,
    })
      .then(res => {
        console.log(res)
        this.nickname = res.data.nickname
        this.profileImageSrc = res.data.profileImg
        this.introduction = res.data.introduction
        this.followerCnt = res.data.followerCnt
        this.followingCnt = res.data.followingCnt
        this.likedCnt = res.data.likedCnt
        this.curatedCnt = res.data.curatedCnt
        this.masterpieces = res.data.masterpieces
      })
      .catch(err => {
        console.log(err)
      })
  },
}
</script>

<style>

</style>