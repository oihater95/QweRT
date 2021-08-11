<template>
  <v-row
    justify="center"
    class="profile-showMore"
  >
    <v-col cols="12"></v-col>
    <v-col cols="12"></v-col>
    <v-col cols="11">
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
      <MyPostings
        v-if="tabOnView === 1"
        class="content"
        :tab="tab1"
        @scroll.native="loadContent"
      />
      <MyFavoritePostings
        v-if="tabOnView === 2"
        class="content"
        :tab="tab2"
        @scroll.native="loadContent"
      />
      <MyCurations
        v-if="tabOnView === 3"
        class="content"
        :tab="tab3"
        @scroll.native="loadContent"
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
// import axios from 'axios'

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
      tab1: {
        size: 12,
        page: 1,
        contents: [1],
      },
      tab2: {
        size: 12,
        page: 100,
        contents: [100],
      },
      tab3: {
        size: 9,
        page: 200,
        contents: [200],
      },
      scrollDelay: false,
    }
  },
  methods: {
    loadContent: function () {
      const content = document.querySelector('.profile-showMore div.content')
      console.log('window:', window.scrollY, window.innerHeight, document.body.offsetHeight)
      console.log('content:', content.scrollTop, content.offsetHeight, content.scrollHeight)
      if ((content.scrollTop + content.offsetHeight) >= content.scrollHeight && !this.scrollDelay) {
        this.scrollDelay = true
        if (this.tabOnView === 1) {
          this.tab1.page += 1
          this.tab1.contents.push(this.tab1.page)
        } else if (this.tabOnView === 2) {
          this.tab2.page += 1
          this.tab2.contents.push(this.tab2.page)
        } else {
          this.tab3.page += 1
          this.tab3.contents.push(this.tab3.page)
        }
        setTimeout(() => {
          this.scrollDelay = false
        }, 500)
      }
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