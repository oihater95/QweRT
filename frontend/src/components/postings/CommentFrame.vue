<template>
  <v-container fluid>
    <v-card 
    id="comment-card"
    elevation="2">
      <div
      id="comment-menu__tab"
      class="d-flex justify-center menu-tab my-0"
      >
        <div class="mt-3" @click="clickComment">Comment {{ postingCommentCnt }}</div>
        <div class="mt-3" @click="clickDocent">Docent {{ postingDocentCnt }}</div>
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
      @scroll="onScroll"
      id="scroll-target__comment"
      v-if="tab===1"
      class="overflow-y-auto comment-row pe-0">
        <v-btn
          fab
          v-show="fab"
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

          <template v-for="(comment, idx) in comments">
            <v-list-item-content
            :class="`comment-id__${idx}`"
            :key="idx">
              <v-list-item-subtitle class="mx-5 px-3">
                <a class="comment-nickname mb-0" href="#">{{ comment.user.nickname }}</a>
              </v-list-item-subtitle>
              <div>
                <p class="mx-5 mt-2 mb-1 px-3 comment-content">{{ comment.content }}</p>
                <p class="mx-5 px-3 update-time">{{ displayTimeAt(comment.createDate) }}</p>
                <v-divider></v-divider>
              </div>
            </v-list-item-content>
          </template>
        </v-list>
      </v-row>
      <v-row 
      v-if="tab===2"
      @scroll="onScroll"
      class="overflow-y-auto comment-row pe-0">
        <v-btn
          v-show="fab"
          icon
          fab
          fixed
          bottom
          right
          @click="toTopComment"
        >
          <v-icon>mdi-arrow-up-drop-circle-outline</v-icon>
        </v-btn>
        <v-list class="col" max-height="600">
          <template v-for="(comment, idx) in docents">
            <v-list-item-content 
            :class="`docent-id__${idx}`"
            :key="idx">
              <v-list-item-subtitle class="mx-5 px-3">
                <a class="comment-nickname mb-0" href="#">{{ comment.user.nickname }}</a>
              </v-list-item-subtitle>
              <div>
                <p class="mx-5 mt-2 mb-1 px-3 comment-content">{{ comment.content }}</p>
                <p class="mx-5 px-3 update-time">{{ displayTimeAt(comment.createDate) }}</p>
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
      id="comment-create__form"
      v-show="commentFormToggle"
      min-height="100px">
      <v-row
        justify="center"
        align="center"
        class="mt-5">
        <v-col 
        id="comment-create__content"
        cols="11"
        class="ms-5">
          <v-text-field
          class="d-inline"
          cols="10"
          v-model="commentForm.commentContent"
          :label="CommentFormLabel"
          :rules="rules"
          hide-details="auto"
          @keyup.enter="CommentUpload"
          ></v-text-field>
        </v-col>
        <v-col id="comment-create__btn"
        class="me-1">
          <v-btn 
          icon
          @click="CommentUpload"
          >
            <v-icon>mdi-check-bold</v-icon>  
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
import axios from'axios'
import { mapState } from 'vuex'
import "@/css/postings/CommentFrame.scss"

export default {
  name: 'CommentFrame',
  props: {
    postingId: {
      type: Number
    },
    postingCommentCnt: {
      type: Number
    },
    postingDocentCnt: {
      type: Number
    }
  },
  data: function() {
    return {
      tab: 1,
      dummyComments: [
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
      commentForm: {
        commentContent : null,
      },
      docents: [],
      comments: [],
      commentPage: 0,
      docentPage: 0,
      size: 10,
      CommentFormLabel: 'Comment',
      commentItem : {
        content: '',
        userId: 0,
        docentFlag: false
      },
      commentFormToggle: false,
      rules: [
        value => !!value || 'Required.',
      ],
      
      // 스크롤 flag
      fab: false,
    }
  },

  methods: {
    clickComment: function (e) {
      this.tab = 1
      this.CommentFormLabel = 'Comment'
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
    },
    clickDocent: function (e) {
      this.tab = 2
      this.CommentFormLabel = 'Docent'
      e.target.style.color="skyblue"
      e.target.previousSibling.style.color="black"
    },

    // clickNickname: function (e) {
    // },

    // 스크롤 감지 및 위로가기 버튼 보이기/숨기기
    onScroll (e) {
      if (e.target.scrollTop > 200) {
        this.fab = true
      } else {
        this.fab = false
      }
      if(e.target.clientHeight+e.target.scrollTop+20>=e.target.scrollHeight){
            if(this.tab === 1) {
              if(this.comments.length % this.size === 0) {
                this.commentPage = this.commentPage + 1;
              }
              this.getComments()
            } 
            else {
              this.docentPage = this.docentPage + 1;
              this.getDocents()
            } 
        }
    },
    
    // 수정 필요
    toTopComment () {
      if (this.tab === 1) {
        const topComment = document.querySelector('.comment-id__0')
        topComment.scrollIntoView(true)
      } else {
        const topComment = document.querySelector('.docent-id__0')
        topComment.scrollIntoView(true)
      }
    },

    // 현재시간과 비교하여 몇분, 몇시간, 며칠 전인지 출력
    displayTimeAt: function(createDate) {  
      const timeNow = new Date()
      const milliSeconds = timeNow - Date.parse(createDate)
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

    CommentUpload: function() {
      this.commentItem.content = this.commentForm.commentContent,
      this.commentItem.userId = this.userInfo.userId,
      this.commentItem.docentFlag = false
      
      if (this.tab === 1) {
        this.commentItem.docentFlag = false
      } else {
        this.commentItem.docentFlag = true
      }

      axios ({
        method: 'post',
        url: `${this.host}/comments/${this.postingId}`,
        data: this.commentItem,
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {  
          console.log(res)
          if (this.tab === 1) {
            this.getComments()
            this.postingCommentCnt++
          } else {
            this.getDocents()
            this.postingDocentCnt++
          }
          this.commentForm.commentContent = ''
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 일반 댓글 불러오기
    getComments() {
      // this.postingId로 받으면 부모 컴포넌트 PostingDetail에서 처리 전이라 0만 받게됨, 라우터 파라미터로 처리
      axios.get(`${this.host}/comments/${this.$route.params.postingId}/`, { params: { page: this.commentPage, size: this.size } })
      .then(res => {
        console.log(res.data, this.commentPage)
        if (this.comments.length % this.size === 0) {
          this.comments = this.comments.concat(res.data)
        } else {
          const append = res.data.slice(this.comments.length)
          this.comments = this.comments.concat(append)
        }
      })
      .catch(err => {
        console.log(err)
      })
    },

    // 도슨트 댓글 불러오기
    getDocents: function() {
      // this.postingId로 받으면 부모 컴포넌트 PostingDetail에서 처리 전이라 0만 받게됨, 라우터 파라미터로 처리
      axios.get(`${this.host}/comments/${this.$route.params.postingId}/docent/`, { params: { page: this.docentPage, size: this.size } })
      .then(res => {
        if (this.docents.length % this.size === 0) {
          this.docents = this.docents.concat(res.data)
        } else {
          const append = res.data.slice(this.docents.length)
          this.docents = this.docents.concat(append)
        }
      })
      .catch(err => {
        console.log(err)
      })
    },

  },

  mounted() {
    this.getComments()
    this.getDocents()
  },

  computed: {
    ...mapState([
        'host',
        'userInfo'
      ])
  },

}

</script>

<style>

</style>
