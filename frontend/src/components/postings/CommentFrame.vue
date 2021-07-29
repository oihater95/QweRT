<template>
  <v-container fluid>
    <v-card 
    id="comment-card"
    color="#FFFFF0"
    elevation="2">
      <div class="d-flex justify-center menu-tab">
        <div @click="clickComment">Comment</div>
        <div @click="clickDocent">Docent</div>
      </div>
      <v-row v-if="tab===1">
        <v-list 
        id="scroll-target"
        class="col overflow-y-auto scroll-y"
        max-height="600">

          <template v-for="(comment, idx) in comments">
            <v-list-item-content
            v-show="comment.docent_flag==0"
            :key="idx">
              <v-list-item-subtitle class="mx-5 px-3">
                <a class="comment-nickname mb-0" href="#">{{ comment.nickname }}</a>
              </v-list-item-subtitle>
              <div>
                <p class="mx-5 mt-2 mb-1 px-3 comment-content">{{ comment.comment_content }}</p>
                <p class="mx-5 px-3 update-time">{{ displayTimeAt(comment.update_date) }}</p>
                <v-divider></v-divider>
              </div>
            </v-list-item-content>
          </template>
        </v-list>
      </v-row>
      <v-row v-if="tab===2">
        <v-list class="col">
          <template v-for="(comment, idx) in comments">
            <v-list-item-content 
            v-show="comment.docent_flag==1"
            :key="idx">
              <v-list-item-subtitle class="mx-5 px-3">
                <a class="comment-nickname mb-0" href="#">{{ comment.nickname }}</a>
              </v-list-item-subtitle>
              <div>
                <p class="mx-5 mt-2 mb-1 px-3 comment-content">{{ comment.comment_content }}</p>
                <p class="mx-5 px-3 update-time">{{ displayTimeAt(comment.update_date) }}</p>
                <v-divider></v-divider>
              </div>
            </v-list-item-content>
          </template>
        </v-list>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
// import axios from'axios'
import "@/css/postings/CommentFrame.scss"

export default {
  name: 'CommentFrame',
  data: function() {
    return {
      tab: 1,
      comments: [
        {
          comment_id : 1,
          comment_content : '잘그리셨네요',
          create_date : new Date(2021, 6, 20, 20, 24, 0),  // 2021-07-20-20:24:00, 월은 0부터
          update_date : new Date(2021, 6, 20, 20, 24, 0),
          user_id : 1,
          nickname : 'OiHater',
          docent_flag: 0
        },
        {
          comment_id : 2,
          comment_content : '전체적으로 붉은 계열의 색감을 사용하여 나타낸 따뜻한 느낌을 주었습니다. 특히 태양은 거친 텍스처로 표현해 강렬하고 뜨거운 느낌입니다. 근데 이 댓글 엄청 길게 쓰면 어떻게 될까요??? 카드 끝에서 줄바꿈을 알아서 해주는군요! 근데 왜 카드 앞부분에서는 안해주지...',
          create_date : new Date(2021, 6, 28, 10, 0, 0),
          update_date : new Date(2021, 6, 28, 18, 0, 0),
          user_id : 1,
          nickname : 'OiHater',
          docent_flag: 1
        },
        {
          comment_id : 3,
          comment_content : '야호!',
          create_date : new Date(2020, 6, 26, 15, 40, 0),
          update_date : new Date(2020, 6, 26, 15, 40, 0),
          user_id : 2,
          nickname : 'moon',
          docent_flag: 0
        },
        {
          comment_id : 4,
          comment_content : '이얏호!',
          create_date : new Date(2021, 3, 27, 15, 40, 0),
          update_date : new Date(2021, 3, 27, 15, 40, 0),
          user_id : 3,
          nickname : 'kim',
          docent_flag: 0
        },
        {
          comment_id : 5,
          comment_content : '댓글',
          create_date : new Date(2021, 6, 20, 15, 40, 0),
          update_date : new Date(2021, 6, 28, 18, 40, 0),
          user_id : 3,
          nickname : 'kim',
          docent_flag: 0
        },
        {
          comment_id : 6,
          comment_content : '댓글22',
          create_date : new Date(2021, 6, 26, 15, 40, 0),
          update_date : new Date(2021, 6, 26, 21, 40, 0),
          user_id : 4,
          nickname : 'userrr',
          docent_flag: 0
        },
        {
          comment_id : 7,
          comment_content : '댓글333',
          create_date : new Date(2019, 6, 26, 15, 40, 0),
          update_date : new Date(2019, 6, 26, 21, 40, 0),
          user_id : 4,
          nickname : 'userrr',
          docent_flag: 0
        },
        {
          comment_id : 8,
          comment_content : '댓글4444',
          create_date : new Date(2021, 1, 26, 15, 40, 0),
          update_date : new Date(2021, 1, 26, 21, 40, 0),
          user_id : 4,
          nickname : 'userrr',
          docent_flag: 0
        },
        {
          comment_id : 9,
          comment_content : '댓글55555',
          create_date : new Date(2021, 6, 6, 15, 40, 0),
          update_date : new Date(2021, 6, 6, 21, 40, 0),
          user_id : 4,
          nickname : 'userrr',
          docent_flag: 0
        },
      ],
    }
  },

  methods: {
    clickComment: function (e) {
      this.tab= 1
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
    },
    clickDocent: function (e) {
      this.tab= 2
      e.target.style.color="skyblue"
      e.target.previousSibling.style.color="black"
    },
    // clickNickname: function (e) {
    // },
    // onScroll (e) {
    //   if (typeof window === 'undefined') return
    //   const top = window.pageYOffset ||   e.target.scrollTop || 0
    //   this.fab = top > 20
    // },
    // toTop () {
    //   this.$vuetify.goTo(0)
    // },
    displayTimeAt: function(update_date) {  // computed로
      const timeNow = new Date()
      const milliSeconds = timeNow - update_date
      const seconds = milliSeconds / 1000
      if (seconds < 60) return `방금 전`
      const minutes = seconds / 60
      if (minutes < 60) return `${Math.floor(minutes)}분 전`
      const hours = minutes / 60
      if (hours < 24) return `${Math.floor(hours)}시간 전`
      const days = hours / 24
      if (days < 7) return `${Math.floor(days)}일 전`
      const weeks = days / 7
      if (weeks < 5) return `${Math.floor(weeks)}주 전`
      const months = days / 30
      if (months < 12) return `${Math.floor(months)}개월 전`
      const years = days / 365
      return `${Math.floor(years)}년 전`
    },

    getComments() {
      // 게시글의 댓글 가져오기
    }
  },
  
  created() {
    this.getComments()
  }, 


}

</script>

<style>

</style>