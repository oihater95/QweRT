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
            :src="profileImageSrc"
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
              <span
                class="follow"
                @click="openFollowerList"
              >
                팔로워 {{ followerCnt }}
              </span> |
              <span
                class="follow"
                @click="openFollowingList"
              >
                팔로잉 {{ followingCnt }}
              </span> |
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <span v-bind="attrs" v-on="on">
                    <v-icon small>fas fa-paint-brush</v-icon> {{ postingCnt }}
                  </span>
                </template>
                <span>업로드한 게시물 개수</span>
              </v-tooltip> |
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
          <!-- 팔로워/팔로잉 목록 모달 창 -->
          <FollowListModal
            v-if="followListModalOn"
            class="d-none"
            :tab="followListTab"
            @followList-off="resetFollowListModal"
          />
        </v-row>
      </v-col>
      <!-- 팔로우 버튼 -->
      <v-col cols="2">
        <v-btn
          v-if="isLogon && ($route.params.userId != userInfo.userId) && !followState"
          color="#AEA660"
          width="100"
          class="white--text"
          @click="followToggle"
        >
          팔로우
        </v-btn>
        <v-btn
          v-if="isLogon && ($route.params.userId != userInfo.userId) && followState"
          color="#857B1A"
          width="100"
          outlined
          @click="followToggle"
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
            v-for="(masterpiece, i) in masterpieces"
            :key="i"
          >
            <v-row
              justify="center"
              align="center"
              class="profile-masterpiece"
            >
              <v-col>
                <img :src="`https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/${masterpiece.postingImg}`">
              </v-col>
            </v-row>
          </v-carousel-item>
        </v-carousel>
      </v-col>
    </v-row>
    <!-- '더 보기' 버튼 -->
    <v-row
      v-if="!showMore"
      justify="center"
      class="profile-showMore__btn"
    >
      <v-col>
        <v-btn
          plain
          @click="showMore=true"
        >
          <h2>
            <i class="fas fa-caret-down"></i> 더 보기
          </h2>
        </v-btn>
      </v-col>
    </v-row>
    <!-- 상세 정보 -->
    <ProfileShowMore
      v-else
    />
  </v-container>
</template>

<script>
import FollowListModal from '@/components/profiles/FollowListModal'
import ProfileShowMore from '@/components/profiles/ProfileShowMore'
import '@/css/profiles/Profile.scss'
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'Profile',
  components: {
    FollowListModal,
    ProfileShowMore,
  },
  data: function () {
    return {
      nickname: '',
      profileImageSrc: '',
      introduction: '',
      followerCnt: null,
      followingCnt: null,
      postingCnt: null,
      likedCnt: null,
      curatedCnt: null,
      followListTab: null,
      followListModalOn: true,
      followState: false,
      masterpieces: [],
      // 예시로 넣어본 그림 url
      // drawings: [
      //   {src: 'https://i.ytimg.com/vi/yGqlkavU-lE/maxresdefault.jpg',},
      //   {src: 'http://www.pipo.co.kr/shopimages/pipouhwa/mobile/8/131668_represent?1506069524',},
      //   {src: 'https://artlecture.com/data/uploads/2018/8/20180818/d90ea23dc92b277105aa7c7750323cdd_thumb_770.jpg',},
      // ],
      hovered: false,
      showMore: false,
    }
  },
  methods: {
    // 프로필 페이지를 구성하는데 필요한 정보를 불러오는 함수
    loadProfile: function () {
      // 유저 정보 불러오기
      axios({
        method: 'get',
        url: `${this.hostUrl}/profile/${this.$route.params.userId}/`,
      })
        .then(res => {
          console.log(res)
          this.nickname = res.data.nickname
          this.profileImageSrc = res.data.profileImg
          this.introduction = res.data.introduction
          this.followerCnt = res.data.followerCnt
          this.followingCnt = res.data.followingCnt
          this.postingCnt = res.data.postingCnt
          this.likedCnt = res.data.likedCnt
          this.curatedCnt = res.data.curatedCnt
          this.masterpieces = res.data.masterpieces
        })
        .catch(err => {
          console.log(err)
        })
      // 로그인 상태라면 로그인한 유저가 현재 프로필의 유저를 팔로우하고 있는지 알아보기
      if (this.isLogon && this.userInfo.userId !== this.$route.params.userId) {
        axios({
          method: 'get',
          url: `${this.hostUrl}/follow/${this.userInfo.userId}/${this.$route.params.userId}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {
            console.log(res)
            this.followState = true
          })
          .catch(err => {
            console.log(err)
            this.followState = false
          })
      }
    },
    // 캐러셀에 마우스를 올려놓은 상태임을 알리는 함수
    hoverOn: function () {
      this.hovered = true
    },
    // 캐러셀에서 마우스가 떠난 상태임을 알리는 함수
    hoverOff: function () {
      this.hovered = false
    },
    // 팔로워 목록 모달 창을 여는 함수
    openFollowerList: function () {
      this.followListTab = 1
      const btn = document.querySelector('#followListModalBtn')
      btn.click()
    },
    // 팔로잉 목록 모달 창을 여는 함수
    openFollowingList: function () {
      this.followListTab = 2
      const btn = document.querySelector('#followListModalBtn')
      btn.click()
    },
    // 팔로워/팔로잉 목록 모달 창을 초기화하는 함수
    resetFollowListModal: function () {
      return new Promise((resolve) => {
        resolve()
        this.followListModalOn = false
      })
        .then(() => {
          this.followListModalOn = true
        })
    },
    // 팔로우 토글 함수
    followToggle: function () {
      axios({
        method: 'put',
        url: `${this.hostUrl}/follow/${this.userInfo.userId}/${this.$route.params.userId}`,
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {
          console.log(res)
          if (this.followState) {
            this.followState = false
            this.followerCnt -= 1
          } else {
            this.followState = true
            this.followerCnt += 1
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
  },
  computed: {
    ...mapState([
      'hostUrl',
      'isLogon',
      'userInfo',
    ])
  },
  watch: {
    // 라우터 파라미터가 변하면 (다른 유저의 프로필로 이동하면) 다시 프로필 정보 받아오기
    $route: function () {
      this.showMore = false
      this.loadProfile()
    }
  },
  // 프로필 페이지가 처음 로드될 때 프로필 정보 받아오기
  created: function () {
    this.loadProfile()
  },
}
</script>

<style>

</style>