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
          :test="test1"
          @next-page-tab1="getFollowers"
        />
        <!-- 팔로잉 목록 -->
        <Followings
          v-if="tabOnView === 2"
          class="follow-list"
          :followings="followings"
          @next-page-tab2="getFollowings"
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
      test1: {
        size: 6,
        page: 0,
        list: [],
      },
      test2: {
        size: 6,
        page: 0,
        list: [],
      },
      numbers: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
    }
  },
  methods: {
    getFollowers: function () {
      const profileId = this.$route.params.userId
      if (this.isLogon) {
        axios({
          method: 'get',
          url: `${this.host}/follow/to/${this.userInfo.userId}/${profileId}?page=${this.followers.page}&size=${this.followers.size}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {
            console.log(res)
            this.followers.list = this.followers.list.concat(res.data)
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        axios({
          method: 'get',
          url: `${this.host}/follow/to/${profileId}?page=${this.followers.page}&size=${this.followers.size}`,
        })
          .then(res => {
            console.log(res)
            this.followers.list = this.followers.list.concat(res.data)
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    getFollowings: function () {
      const profileId = this.$route.params.userId
      if (this.isLogon) {
        axios({
          method: 'get',
          url: `${this.host}/follow/from/${this.userInfo.userId}/${profileId}?page=${this.followers.page}&size=${this.followers.size}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {
            console.log(res)
            this.followings.list = this.followings.list.concat(res.data)
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        axios({
          method: 'get',
          url: `${this.host}/follow/from/${profileId}?page=${this.followers.page}&size=${this.followers.size}`,
        })
          .then(res => {
            console.log(res)
            this.followings.list = this.followings.list.concat(res.data)
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    followToggle: function (target) {
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
    },
    // 임시 함수
    loadContent: function (test) {
      test.page ++
      test.list = test.list.concat(this.numbers.map(li => 10 * test.page + li))
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
    if (this.test1.list.length === 0 && this.test2.list.length === 0) {
      this.tabOnView = this.tab
    }
  }
}
</script>

<style>

</style>