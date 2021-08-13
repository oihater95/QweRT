<template>
  <!-- 팔로잉 목록 -->
  <div
    class="profile-followings"
    @scroll="getMoreContents"
  >
    <v-row
      justify="center"
      align="center"
      class="list"
      v-for="i in test.list"
      :key="i"
    >
      <v-col cols="12">
        <v-divider></v-divider>
      </v-col>
      <v-col
        cols="3"
        class="profile-image"
      >
        <v-img :src="`https://picsum.photos/500/300?image=${i * 5 + 10}`"></v-img>
      </v-col>
      <v-col cols="8">
        <h2>following{{i}}</h2>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import '@/css/profiles/Followings.scss'

export default {
  name: 'Followings',
  props: {
    test: {
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
      const content = document.querySelector('.profile-followings')
      if (((this.test.list.length === 0) || (content.scrollTop + content.offsetHeight >= content.scrollHeight - 10)) && !this.scrollDelay) {
        // console.log(content.scrollTop, content.offsetHeight, content.scrollHeight)
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
  mounted: function () {
    this.getMoreContents()
  }
}
</script>

<style>

</style>