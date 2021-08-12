<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      max-width="900px"
    >
      <!-- 트리거 버튼 -->
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          id="masterpieceModalBtn"
          color="primary"
          dark
          v-bind="attrs"
          v-on="on"
        ></v-btn>
      </template>
      <!-- 모달 창 -->
      <v-card class="masterpiece-modal">
        <!-- 헤더 -->
        <v-card-title class="header d-flex justify-space-between">
          <h3>내 그림</h3>
          <v-btn
            icon
            @click="dialog = false"
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-divider></v-divider>
        <!-- 게시물 목록 -->
        <v-card-text
          class="posting-list"
          @scroll="getMyPostings"
        >
          <v-row>
            <v-col cols="12"></v-col>
            <v-col
              cols="4"
              max-height="300"
              v-for="(posting, i) in postings"
              :key="i"
            >
              <v-img
                :src="`https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/${posting.postingImg}`"
                contain
                @click="dialog = false, $emit('masterpiece-selected', posting)"
              ></v-img>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import '@/css/profiles/MasterpieceModal.scss'
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'MasterpieceModal',
  data: function () {
    return {
      dialog: false,
      scrollDelay: false,
      size: 9,
      page: 0,
      postings: [],
    }
  },
  methods: {
    // 인피니티스크롤로 게시물 목록 불러오는 함수
    getMyPostings: function () {
      const content = document.querySelector('.posting-list')
      if (((this.postings.length === 0) || (content.scrollTop + content.offsetHeight >= content.scrollHeight)) && !this.scrollDelay) {
        console.log(content.scrollTop, content.offsetHeight, content.scrollHeight)
        axios({
          method: 'get',
          url: `${this.host}/postings/${this.$route.params.userId}?page=${this.page}&size=${this.size}`
        })
          .then(res => {
            console.log(res)
            if (res.data.length !== 0) {
              this.postings = this.postings.concat(res.data)
              this.page ++
            }
          })
          .catch(err => {
            console.log(err)
          })
      }
    }
  },
  computed: {
    ...mapState([
      'host',
    ])
  },
  // 모달 창이 처음 열렸을 때 한번 게시물 목록 첫 페이지를 불러온다
  updated: function () {
    if (this.postings.length === 0) {
      this.getMyPostings()
    }
  }
}
</script>

<style>

</style>