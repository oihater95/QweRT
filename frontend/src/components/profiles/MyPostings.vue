<template>
  <v-row>
    <!-- <v-col cols="12" v-for="i in tab.contents" :key="i">
      <v-img :src="`https://picsum.photos/500/300?image=${i * 5 + 10}`"></v-img>
      <h1>{{tab.page}}</h1>
    </v-col> -->
    <v-col cols="4" v-for="(content, i) in tab.contents" :key="i">
      <!-- <v-img :src="content."></v-img> -->
    </v-col>
  </v-row>
</template>

<script>
import '@/css/profiles/MyPostings.scss'
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'MyPosting',
  props: {
    tab: {
      type: Object
    },
  },
  methods: {
    getMyPostings: function () {
      axios({
        method: 'get',
        url: `${this.host}/postings/${this.$route.params.userId}?page=${this.page}&size=${this.size}`,
      })
        .then(res => {
          console.log(res)
          this.$emit('next-page')
          this.postings.concat(res.data)
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