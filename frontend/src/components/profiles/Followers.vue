<template>
  <!-- 팔로워 목록 -->
  <div
    class="profile-followers"
    @scroll="getMoreContents"
  >
    <v-row
      justify="center"
      align="center"
      class="list"
      v-for="(follower, i) in followers.list"
      :key="i"
    >
      <v-col cols="12">
        <v-divider></v-divider>
      </v-col>
      <!-- 프로필 이미지 -->
      <v-col
        v-if="isLogon"
        cols="2"
        class="profile-image"
      >
        <v-img
          v-if="follower.user.profileImg"
          :src="follower.user.profileImg"
        ></v-img>
        <v-img
          v-else
          src="@/assets/images/profile_image_default.png"
        ></v-img>
      </v-col>
      <v-col
        v-else
        cols="2"
        class="profile-image"
      >
        <v-img
          v-if="follower.profileImg"
          :src="follower.profileImg"
        ></v-img>
        <v-img
          v-else
          src="@/assets/images/profile_image_default.png"
        ></v-img>
      </v-col>
      <!-- 닉네임 -->
      <v-col
        v-if="isLogon"
        cols="7"
        class="nickname"
      >
        <h2>{{ follower.user.nickname }}</h2>
      </v-col>
      <v-col
        v-else
        cols="7"
        class="nickname"
      >
        <h2>{{ follower.nickname }}</h2>
      </v-col>
      <!-- 팔로우/언팔로우 버튼 -->
      <v-col
        v-if="isLogon"
        cols="2"
        class="follow-button"
      >
        <v-btn
          v-if="!follower.followFlag"
          small
          depressed
          color="#F5DA81"
          @click="$emit('follow-toggle', {follower, i})"
        >
          팔로우
        </v-btn>
        <v-btn
          v-else
          small
          outlined
          color="#FFBF00"
          @click="$emit('follow-toggle', {follower, i})"
        >
          언팔로우
        </v-btn>
      </v-col>
      <v-col
        v-else
        cols="2"
      ></v-col>
    </v-row>
  </div>
</template>

<script>
import '@/css/profiles/Followers.scss'
import { mapState } from 'vuex'

export default {
  name: 'Followers',
  props: {
    followers: {
      type: Object
    },
  },
  data: function () {
    return {
      scrollDelay: false,
    }
  },
  methods: {
    // 처음 로드할 때와 인피니티스크롤을 할 때를 감지하는 함수
    getMoreContents: function () {
      const content = document.querySelector('.follow-list')
      if (((this.followers.list.length === 0) || (content.scrollTop + content.offsetHeight >= content.scrollHeight - 10)) && !this.scrollDelay) {
        // console.log(content.scrollTop, content.offsetHeight, content.scrollHeight)
        this.scrollDelay = true
        this.$emit('next-page-tab1')
        // 인피니티스크롤이 한꺼번에 많이 일어나지 않도록 0.2초 텀을 둔다.
        setTimeout(() => {
          this.scrollDelay = false
        }, 200)
      }
    },
  },
  computed: {
    ...mapState([
      'isLogon',
    ])
  },
  // 처음 로드되는 상황이라면 첫 페이지를 불러오기 위해서
  mounted: function () {
    this.getMoreContents()
  }
}
</script>

<style>

</style>