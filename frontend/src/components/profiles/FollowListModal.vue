<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="500px"
    >
      <!-- 트리거 버튼 -->
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          id="followListModalBtn"
          color="primary"
          dark
          v-bind="attrs"
          v-on="on"
        ></v-btn>
      </template>
      <!-- 모달 창 -->
      <v-card class="followList-modal">
        <!-- 헤더 -->
        <v-card-title class="header d-flex justify-space-between">
          <div>
            <b
              :class="['tabs', {'tab-onView' : tabOnView === 1}]"
              @click="tabOnView = 1"
            >
              팔로워 목록
            </b> |
            <b
              :class="['tabs', {'tab-onView' : tabOnView === 2}]"
              @click="tabOnView = 2"
            >
              팔로잉 목록
            </b>
          </div>
          <v-btn
            icon
            @click="dialog = false, $emit('followList-off')"
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-divider></v-divider>
        <!-- 팔로워 목록 -->
        <Followers
          v-if="tabOnView === 1"
          class="follow-list"
          :followers="followers"
          @next-page-tab1="getFollowers"
          @other-profile="goToOtherProfile"
          @follow-toggle="followToggle"
        />
        <!-- 팔로잉 목록 -->
        <Followings
          v-if="tabOnView === 2"
          class="follow-list"
          :followings="followings"
          @next-page-tab2="getFollowings"
          @other-profile="goToOtherProfile"
          @follow-toggle="followToggle"
        />
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import Followers from '@/components/profiles/Followers'
import Followings from '@/components/profiles/Followings'
import '@/css/profiles/FollowListModal.scss'
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'FollowListModal',
  components: {
    Followers,
    Followings,
  },
  props: {
    tab: {
      type: Number
    }
  },
  data: function () {
    return {
      dialog: false,
      tabOnView: null,
      followers: {
        size: 10,
        page: 0,
        list: [],
      },
      followings: {
        size: 10,
        page: 0,
        list: [],
      },
    }
  },
  methods: {
    // 팔로워 목록을 불러와 저장하는 함수
    getFollowers: function () {
      const profileId = this.$route.params.userId
      if (this.isLogon) {
        // 로그인 시
        axios({
          method: 'get',
          url: `${this.host}/follow/to/${this.userInfo.userId}/${profileId}?page=${this.followers.page}&size=${this.followers.size}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {
            console.log(res)
            this.followers.page ++
            this.followers.list = this.followers.list.concat(res.data)
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        // 비로그인 시
        axios({
          method: 'get',
          url: `${this.host}/follow/to/${profileId}?page=${this.followers.page}&size=${this.followers.size}`,
        })
          .then(res => {
            console.log(res)
            this.followers.page ++
            this.followers.list = this.followers.list.concat(res.data)
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    // 팔로잉 목록을 불러와 저장하는 함수
    getFollowings: function () {
      const profileId = this.$route.params.userId
      if (this.isLogon) {
        // 로그인 시
        axios({
          method: 'get',
          url: `${this.host}/follow/from/${this.userInfo.userId}/${profileId}?page=${this.followings.page}&size=${this.followings.size}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {
            console.log(res)
            this.followings.page ++
            this.followings.list = this.followings.list.concat(res.data)
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        // 비로그인 시
        axios({
          method: 'get',
          url: `${this.host}/follow/from/${profileId}?page=${this.followings.page}&size=${this.followings.size}`,
        })
          .then(res => {
            console.log(res)
            this.followings.page ++
            this.followings.list = this.followings.list.concat(res.data)
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    // 다른 유저의 프로필로 이동하는 함수
    goToOtherProfile: function (userId) {
      this.dialog = false
      this.$emit('followList-off')
      this.$router.push({ name: 'Profile', params: {userId: userId} })
    },
    // 팔로우 토글 함수
    followToggle: function (target) {
      if (target.follower) {
        // 팔로워 목록의 유저를 토글한 경우
        axios({
          method: 'put',
          url: `${this.host}/follow/${this.userInfo.userId}/${target.follower.user.userId}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {
            console.log(res)
            this.followers.list[target.i].followFlag = !this.followers.list[target.i].followFlag
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        // 팔로잉 목록의 유저를 토글한 경우
        axios({
          method: 'put',
          url: `${this.host}/follow/${this.userInfo.userId}/${target.following.user.userId}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {
            console.log(res)
            this.followings.list[target.i].followFlag = !this.followings.list[target.i].followFlag
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
  },
  computed: {
    ...mapState([
      'isLogon',
      'userInfo',
      'host',
    ])
  },
  // 모달 창이 처음 열렸을 때 팔로워 목록을 연 것인지 팔로잉 목록을 연 것인지 확인
  updated: function () {
    if (this.followers.list.length === 0 && this.followings.list.length === 0) {
      this.tabOnView = this.tab
    }
  }
}
</script>

<style>

</style>