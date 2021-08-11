<template>
  <v-row justify="center" align="center" class="profile-myPostings" @scroll="getMoreContents">
    <v-col cols="6" v-for="(content, i) in tab.contents" :key="i">
      <v-img contain :src="`https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/${content.postingImg}`"></v-img>
    </v-col>
  </v-row>
</template>

<script>
import '@/css/profiles/MyPostings.scss'

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
    getMoreContents: function () {
      const content = document.querySelector('.profile-showMore div.content')
      if (((this.tab.contents.length === 0) || (content.scrollTop + content.offsetHeight >= content.scrollHeight)) && !this.scrollDelay) {
        this.scrollDelay = true
        this.$emit('next-page')
        setTimeout(() => {
          this.scrollDelay = false
        }, 200)
      }
    },
  },
  created: function () {
    this.getMoreContents()
  }
}
</script>

<style>

</style>