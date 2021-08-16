<template>
  <v-row
    justify="center"
    class="profile-showMore"
  >
    <!-- 여백을 위한 빈칸 -->
    <v-col cols="12"></v-col>
    <v-col cols="12"></v-col>
    <!-- 프로필 상세정보 -->
    <v-col cols="11">
      <!-- 상단 탭 -->
      <v-row justify="space-around">
        <v-col
          :cols="tabOnView === 1 ? 4 : 3"
          :class="['tabs', {'tab-onView' : tabOnView === 1}]"
          @click="tabOnView=1"
        >
          <h3>게시물</h3>
        </v-col>
        <v-col
          :cols="tabOnView === 2 ? 4 : 3"
          :class="['tabs', {'tab-onView' : tabOnView === 2}]"
          @click="tabOnView=2"
        >
          <h3>좋아하는 게시물</h3>
        </v-col>
        <v-col
          :cols="tabOnView === 3 ? 4 : 3"
          :class="['tabs', {'tab-onView' : tabOnView === 3}]"
          @click="tabOnView=3"
        >
          <h3>큐레이션</h3>
        </v-col>
      </v-row>
      <!-- 내 게시물 -->
      <MyPostings
        v-if="tabOnView === 1"
        class="content"
        :tab="tab1"
        @next-page-tab1="loadNextPage(tab1)"
      />
      <!-- 내가 좋아하는 게시물 -->
      <MyFavoritePostings
        v-if="tabOnView === 2"
        class="content"
        :tab="tab2"
        @next-page-tab2="loadNextPage(tab2)"
      />
      <!-- 내 큐레이션 -->
      <MyCurations
        v-if="tabOnView === 3"
        class="content"
        :tab="tab3"
        @next-page-tab3="loadNextPage(tab3)"
      />
    </v-col>
  </v-row>
</template>

<script>
import MyPostings from '@/components/profiles/MyPostings'
import MyFavoritePostings from '@/components/profiles/MyFavoritePostings'
import MyCurations from '@/components/profiles/MyCurations'
import '@/css/profiles/ProfileShowMore.scss'
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'ProfileShowMore',
  components: {
    MyPostings,
    MyFavoritePostings,
    MyCurations,
  },
  data: function () {
    return {
      tabOnView: 1,
      tab1: {     // 내 게시물
        size: 6,
        page: 0,
        contents: [],
      },
      tab2: {     // 내가 좋아하는 게시물
        size: 6,
        page: 0,
        contents: [],
      },
      tab3: {     // 내 큐레이션
        size: 6,
        page: 0,
        contents: [],
      },
    }
  },
  methods: {
    // 다음 페이지를 불러오는 함수 (한 페이지마다 size만큼씩)
    loadNextPage: function (tab) {
      let path
      if (tab === this.tab1) {
        path = `postings/${this.$route.params.userId}`
      } else if (tab === this.tab2) {
        path = `postings/${this.$route.params.userId}/like`
      } else {
        path = `curations/${this.$route.params.userId}`
      }
      axios({
        method: 'get',
        url: `${this.host}/${path}?page=${tab.page}&size=${tab.size}`,
      })
        .then(res => {
          console.log(res)
          if (res.data.length !== 0) {
            tab.contents = tab.contents.concat(res.data)
            tab.page ++
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
  },
  computed: {
    ...mapState([
      'host',
    ])
  },
}
</script>

<style>

</style>