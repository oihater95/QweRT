<template>
  <v-container fluid>
    <v-card 
    id="comment-card"
    elevation="2">
      <div
      id="comment-menu__tab"
      class="d-flex justify-center menu-tab my-0"
      >
        <div class="mt-3" @click="clickComment">Comment {{ this.nonDocentsArray.length }}</div>
        <div class="mt-3" @click="clickDocent">Docent {{ this.docentsArray.length }}</div>
      </div>
      <div class="comment-create__section" align="right">
        <v-btn 
          icon 
          class="mb-4 me-3"
          @click="clickCommentBtn">
          <v-icon>mdi-chat-plus</v-icon>
        </v-btn>
      </div>
      <v-row 
      id="scroll-target__comment"
      v-if="tab===1"
      class="overflow-y-auto comment-row pe-0">
        <v-btn
          fab
          v-show="fab"
          v-scroll:#scroll-target__comment="onScroll"
          icon
          fixed
          bottom
          right
          @click="toTopComment"
          class="comment-topBtn"
        >
          <v-icon class="dark">mdi-arrow-up-drop-circle-outline</v-icon>
        </v-btn>
        <v-list 
        class="col"
        max-height="600">

          <template v-for="(comment, idx) in nonDocentsArray">
            <v-list-item-content
            :class="`nonDocent-id__${idx}`"
            :key="idx">
              <v-list-item-subtitle class="mx-5 px-3">
                <a class="comment-nickname mb-0" href="#">{{ comment.nickname }}</a>
              </v-list-item-subtitle>
              <div>
                <p class="mx-5 mt-2 mb-1 px-3 comment-content">{{ comment.comment_content }}</p>
                <p class="mx-5 px-3 update-time">{{ displayTimeAt(comment.create_date) }}</p>
                <v-divider></v-divider>
              </div>
            </v-list-item-content>
          </template>
        </v-list>
      </v-row>
      <v-row 
      v-if="tab===2"
      class="overflow-y-auto comment-row pe-0">
        <v-btn
          v-scroll="onScroll"
          v-show="fab"
          fab
          fixed
          bottom
          right
          @click="toTopComment"
        >
          <v-icon>mdi-arrow-up-drop-circle-outline</v-icon>
        </v-btn>
        <v-list class="col" max-height="600">
          <template v-for="(comment, idx) in docentsArray">
            <v-list-item-content 
            :class="`docent-id__${idx}`"
            :key="idx">
              <v-list-item-subtitle class="mx-5 px-3">
                <a class="comment-nickname mb-0" href="#">{{ comment.nickname }}</a>
              </v-list-item-subtitle>
              <div>
                <p class="mx-5 mt-2 mb-1 px-3 comment-content">{{ comment.comment_content }}</p>
                <p class="mx-5 px-3 update-time">{{ displayTimeAt(comment.create_date) }}</p>
                <v-divider></v-divider>
              </div>
            </v-list-item-content>
          </template>
        </v-list>
      </v-row>
    </v-card>
    <br>
    <br>
    <v-card
      v-show="commentFormToggle"
      min-height="100px">
      <v-row
        justify="center"
        align="center"
        class="mt-5">
        <v-col 
        cols="11"
        class="ms-5">
          <v-text-field
          class="d-inline"
          cols="10"
          v-model="commentForm.comment_content"
          label="Comment"
          :rules="rules"
          hide-details="auto"
          ></v-text-field>
        </v-col>
        <v-col class="me-1">
          <v-btn 
          icon
          @click="clickCommentForm">
            <v-icon>mdi-check-bold</v-icon>  
          </v-btn>
        </v-col>
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
      fab: false,
      scrollPosition: 0,
      commentFormToggle: false,
      rules: [
        value => !!value || 'Required.',
      ],
      commentForm: {
        comment_content : null,
      },
      docentsArray: [],
      nonDocentsArray: []
      
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
    // 스크롤 감지 및 위로가기 버튼 보이기/숨기기
    onScroll (e) {
      this.scrollPosition = e.target.scrollTop;
      if (this.scrollPosition > 200) {
        this.fab = true
      } else {
        this.fab = false
      }
    },
    
    // 수정 필요
    toTopComment () {
      if (this.tab === 1) {
        const topComment = document.querySelector('.nonDocent-id__0')
        topComment.scrollIntoView(true)
      } else {
        const topComment = document.querySelector('.docent-id__0')
        topComment.scrollIntoView(true)
      }
    },

    // 현재시간과 비교하여 몇분, 몇시간, 며칠 전인지 출력
    displayTimeAt: function(create_date) {  // computed로
      const timeNow = new Date()
      const milliSeconds = timeNow - create_date
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
    clickCommentBtn: function(e) {
      if (this.commentFormToggle === false) {
        e.target.style.color="skyblue"
        this.commentFormToggle = true
      } else {
        e.target.style.color=""
        this.commentFormToggle = false
      }
    },

    clickCommentForm: function() {
      const commentItem = {
        comment_id: this.comments[this.comments.length-1].id + 1,
        comment_content : this.commentForm.comment_content,
        create_date : new Date(), 
        update_date : new Date(),
        user_id : 1,
        nickname : 'OiHater',
        docent_flag: this.tab - 1
      }
      if (this.tab === 1) {
        this.nonDocentsArray.push(commentItem)
      } else {
        this.docentsArray.push(commentItem)
      }
      this.comments.push(commentItem)
      this.commentForm.comment_content = ''
    }

  },

  mounted() {
    const newNonDocentArr = []
    const newDocentArr = []
    for (var i=0; i <this.comments.length; i++) {
      if (this.comments[i].docent_flag === 0) {
        newNonDocentArr.push(this.comments[i])
      } else {
        newDocentArr.push(this.comments[i])
      }
    }
    this.nonDocentsArray = newNonDocentArr
    this.docentsArray = newDocentArr
    
  },

}

</script>

<style>

</style>
