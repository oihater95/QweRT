<template>
  <!-- 내가 좋아하는 게시물 목록 -->
  <v-row
    class="profile-myFavoritePostings"
    @scroll="getMoreContents"
  >
    <v-col
      cols="6"
      v-for="(content, i) in tab.contents"
      :key="i"
    >
      <v-img
        :src="`https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/${content.postingImg}`"
        contain
      ></v-img>
    </v-col>
  </v-row>
</template>

<script>
import '@/css/profiles/MyFavoritePostings.scss'

export default {
  name: 'MyPosting',
  props: {
    tab: {
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
      const content = document.querySelector('.profile-showMore div.content')
      if (((this.tab.contents.length === 0) || (content.scrollTop + content.offsetHeight >= content.scrollHeight)) && !this.scrollDelay) {
        this.scrollDelay = true
        this.$emit('next-page-tab2')
        // 인피니티스크롤이 한꺼번에 많이 일어나지 않도록 0.2초 텀을 둔다.
        setTimeout(() => {
          this.scrollDelay = false
        }, 200)
      }
    },
  },
  // 처음 로드되는 상황이라면 첫 페이지를 불러오기 위해서
  created: function () {
    this.getMoreContents()
  }
}
</script>

<style>

</style>