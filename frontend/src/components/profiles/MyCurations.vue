<template>
  <!-- 내 큐레이션 목록 -->
  <v-row
    class="profile-myCurations"
    @scroll="getMoreContents"
  >
    <v-col
      cols="6"
      v-for="(content, i) in tab.contents"
      :key="i"
    >
      <v-row
        no-gutters
        class="box"
        :style="`border: 2px solid #${content.color};`"
      >
        <!-- 제목 및 설명 -->
        <v-col class="text">
          <h1># {{ content.title }}</h1>
          <p>{{ content.content }}</p>
        </v-col>
        <!-- 썸네일 -->
        <v-col class="image">
          <v-img
            v-if="content.thumbnail"
            :src="content.thumbnail"
            contain
            class="thumbnail"
          ></v-img>
          <v-row
            v-else
            justify="center"
            align="center"
            class="sub-images"
          >
            <v-col
              cols="4"
              v-for="(image, i) in content.images"
              :key="i"
            >
              <v-img
                :src="`https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/${image}`"
                contain
              ></v-img>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
import '@/css/profiles/MyCurations.scss'

export default {
  name: 'MyCurations',
  props: {
    tab: {
      type: Object
    }
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
        // console.log(content.scrollTop, content.offsetHeight, content.scrollHeight)
        this.scrollDelay = true
        this.$emit('next-page-tab3')
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