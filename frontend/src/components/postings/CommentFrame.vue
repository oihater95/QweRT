<template>
  <v-container fluid>
    <v-card 
    id="comment-card"
    elevation="2">
      <div
      id="comment-menu__tab"
      class="d-flex justify-center menu-tab my-0"
      >
        <div class="mt-3" @click="clickComment">Comment {{ commentCnt }}</div>
        <div class="mt-3" @click="clickDocent">Docent {{ docentCnt }}</div>
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
            <!-- comment-id => scroll Top에 사용, commentId => 댓글 수정에 사용 -->
            <v-list-item-content
            :class="`comment-id__${idx}`"
            :key="idx">
              <v-list-item-subtitle class="mx-5 px-3 d-flex align-items-center">
                <img 
                v-if="comment.user.profileImg"
                :src="comment.user.profileImg"  
                class="profile-img__comment" 
                @click="userProfile(comment)">
                <img 
                v-else
                class="profile-img__comment"
                src="@/assets/images/profile_image_default.png"
                @click="userProfile(comment)"
                >
                <p 
                  @click="userProfile(comment)" 
                  class="comment-nickname mb-0 mt-1 ms-1 d-inline">
                  {{ comment.user.nickname }}
                </p>
              </v-list-item-subtitle>
              <div>
                <div class="ps-3 ms-5">
                  <div class="content-line d-none" :class="`commentId-editModeOn__${comment.commentId}`">
                    <div>
                      <textarea class="mt-3 editing-frame" maxlength="50" v-model="comment.content"></textarea>
                    </div>
                    <div>
                      <v-btn @click="editCommentSubmit(comment)" plain>
                        Edit
                      </v-btn>
                      <v-btn @click="cancelEditComment(comment)" plain color="error">
                        Cancel
                      </v-btn>
                    </div>
                  </div>
                  <div class="content-line my-4 d-flex" :class="`commentId-editModeOff__${comment.commentId}`">
                    <span class="ms-2 me-5 mt-2 mb-1 pe-3 comment-content">{{ comment.content }}</span>
                    <div v-if="checkCommentAuthority(comment)" class=" ms-5 d-inline">
                      <v-btn icon @click="[getCommentId(comment), editComment(comment)]">
                        <v-icon>
                          mdi-pencil
                        </v-icon>
                      </v-btn>
                      <v-btn icon @click="[getCommentId(comment), deleteComment()]">
                        <v-icon>
                          mdi-close
                        </v-icon>
                      </v-btn>
                    </div>
                  </div>
                </div>
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
              <v-list-item-subtitle class="mx-5 px-3 d-flex align-items-center">
                <img 
                v-if="comment.user.profileImg"
                :src="comment.user.profileImg"  
                class="profile-img__comment" 
                @click="userProfile(comment)">
                <img 
                v-else
                class="profile-img__comment" 
                src="@/assets/images/profile_image_default.png"
                @click="userProfile(comment)"
                >
                <p 
                  @click="userProfile(comment)" 
                  class="comment-nickname mb-0 mt-1 ms-1 d-inline">
                  {{ comment.user.nickname }}
                </p>
              </v-list-item-subtitle>
              <div>
                <div class="ps-3 ms-5">
                  <div class="content-line d-none" :class="`docentId-editModeOn__${comment.commentId}`">
                    <div>
                      <textarea class="mt-3 editing-frame" maxlength="50" v-model="comment.content"></textarea>
                    </div>
                    <div>
                      <v-btn @click="editCommentSubmit(comment)" plain>
                        Edit
                      </v-btn>
                      <v-btn @click="cancelEditComment(comment)" plain color="error">
                        Cancel
                      </v-btn>
                    </div>
                  </div>
                  <div class="content-line my-4 d-flex" :class="`docentId-editModeOff__${comment.commentId}`">
                    <span class="me-5 ms-2 mt-2 mb-1 pe-3 comment-content">{{ comment.content }}</span>
                    <div v-if="checkCommentAuthority(comment)" class=" ms-5 d-inline">
                      <v-btn icon @click="[getCommentId(comment), editComment(comment)]">
                        <v-icon>
                          mdi-pencil
                        </v-icon>
                      </v-btn>
                      <v-btn icon @click="[getCommentId(comment), deleteComment()]">
                        <v-icon>
                          mdi-close
                        </v-icon>
                      </v-btn>
                    </div>
                  </div>
                </div>
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
          counter="50"
          maxlength="50"
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
          class="me-3"
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
      profileImg: '',
      commentForm: {
        commentContent : null,
      },
      docents: [],
      comments: [],
      commentPage: 0,
      docentPage: 0,
      size: 6,
      CommentFormLabel: 'Comment',
      commentItem : {
        content: '',
        userId: 0,
        docentFlag: false
      },
      commentId: 0,
      editCommentId: 0,
      commentFormToggle: false,
      rules: [
        value => !!value || 'Required.',
      ],
      commentCnt: 0,
      docentCnt: 0,
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
              if(this.comments.length !==0 && this.comments.length < this.commentCnt) {
                if(this.commentCnt > 0 && this.commentPage < parseInt((this.commentCnt-1)/this.size)) {
                  this.commentPage = this.commentPage + 1
                  this.getComments()
                }
              }
            } 
            else {
              if(this.docents.length !==0 && this.docents.length < this.docentCnt) {
                if(this.docentCnt > 0 && this.docentPage < parseInt((this.docentCnt-1)/this.size)) {
                  this.docentPage = this.docentPage + 1
                  this.getDocents()
                }
              }
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
      const milliSeconds = timeNow - Date.parse(createDate) - 2 * 9 * 60 * 60 * 1000
      // console.log(milliSeconds)
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
      if(this.isLogon === true) {
        if (this.commentFormToggle === false) {
          e.target.style.color="skyblue"
          this.commentFormToggle = true
        } else {
          e.target.style.color=""
          this.commentFormToggle = false
        }
      } else {
        if(confirm('로그인이 필요한 서비스입니다.\n로그인페이지로 이동하시겠습니까?')){
          this.$router.push({ name: 'Login' })
        }
        
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
        url: `${this.hostUrl}/comments/${this.postingId}`,
        data: this.commentItem,
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {  
          console.log(res)
          if (this.tab === 1) {
            this.getCnt()
            if(this.commentCnt > 0 && this.commentPage < parseInt((this.commentCnt)/this.size)) {
              this.commentPage = this.commentPage + 1
            }
            this.getComments()
          } else {
            this.getCnt()
            if(this.docentCnt > 0 && this.docentPage < parseInt((this.docentCnt)/this.size)) {
              this.docentPage = this.docentPage + 1
            }
            this.getDocents()
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
      axios.get(`${this.hostUrl}/comments/${this.$route.params.postingId}/`, { params: { page: this.commentPage, size: this.size } })
      .then(res => {
        console.log(res)
        if(this.comments.length < this.commentCnt && this.comments.length % this.size === 0) {
          this.comments = this.comments.concat(res.data)
        } else if (this.comments.length < this.commentCnt && this.comments.length % this.size !== 0){
          const append = res.data.slice(this.comments.length % this.size)
          this.comments = this.comments.concat(append)
        } else if (this.commentPage === 0 && this.commentCnt === 0 && this.comments.length === 0){
          this.comments = this.comments.concat(res.data)
        }
      })
      .catch(err => {
        console.log(err)
      })
    },

    // 도슨트 댓글 불러오기
    getDocents: function() {
      // this.postingId로 받으면 부모 컴포넌트 PostingDetail에서 처리 전이라 0만 받게됨, 라우터 파라미터로 처리
      axios.get(`${this.hostUrl}/comments/${this.$route.params.postingId}/docent/`, { params: { page: this.docentPage, size: this.size } })
      .then(res => {
        if(this.docents.length < this.docentCnt && this.docents.length % this.size === 0) {
          this.docents = this.docents.concat(res.data)
        } else if (this.docents.length < this.docentCnt && this.docents.length % this.size !== 0){
          const append = res.data.slice(this.docents.length % this.size)
          this.docents = this.docents.concat(append)
        } else if (this.docentPage === 0 && this.docentCnt === 0 && this.docents.length === 0){
          this.docents = this.docents.concat(res.data)
        }
      })
      .catch(err => {
        console.log(err)
      })
    },

    getCommentsAll: function() {
      axios.get(`${this.hostUrl}/comments/${this.$route.params.postingId}/`, { params: { page: 0, size: this.commentCnt } })
      .then(res => {
        this.comments = this.comments.concat(res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },

    getDocentsAll: function() {
      axios.get(`${this.hostUrl}/comments/${this.$route.params.postingId}/docent/`, { params: { page: 0, size: this.docentCnt } })
      .then(res => {
        this.docents = this.docents.concat(res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },

    getCnt: function() {
      axios({
        method: 'get',
        url: `${this.hostUrl}/postings/detail/${this.$route.params.postingId}/`,
        })
        .then(res => {
          this.commentCnt = res.data.commentCnt
          this.docentCnt = res.data.docentCnt
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 댓글 본인 확인
    checkCommentAuthority: function(comment) {
      if(this.userInfo.userId === comment.user.userId) {
        return true 
      } else {
        return false
      }
    },
    // 댓글 Id 가져오기
    getCommentId: function(comment) {
      this.commentId = comment.commentId
    },

    deleteComment: function() {
      // 상위 컴포넌트와 겹쳐서 모달 컴포넌트가 제대로 동작하지 않음
      if(confirm('삭제하시겠습니까?')) {
        axios ({
          method: 'delete',
          url: `${this.hostUrl}/comments/${this.commentId}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {  
            console.log(res)
            if(this.tab===1) {
              this.comments = []
              this.getCommentsAll()
            } else {
              this.docents = []
              this.getDocentsAll()
            }
          })
          .catch(err => {
            console.log(err)
          })
      }
    },

    editComment: function(comment) {
      this.editCommentId = comment.commentId
      if(this.tab === 1) {
        const editModeOn = document.querySelector(`.commentId-editModeOn__${this.editCommentId}`)
        const editModeOff = document.querySelector(`.commentId-editModeOff__${this.editCommentId}`)
        editModeOn.classList.remove('d-none')
        editModeOn.classList.add('d-flex')
        editModeOff.classList.remove('d-flex')
        editModeOff.classList.add('d-none')
      } else {
        const editModeOnDocent = document.querySelector(`.docentId-editModeOn__${this.editCommentId}`)
        const editModeOffDocent = document.querySelector(`.docentId-editModeOff__${this.editCommentId}`)
        editModeOnDocent.classList.remove('d-none')
        editModeOnDocent.classList.add('d-flex')
        editModeOffDocent.classList.remove('d-flex')
        editModeOffDocent.classList.add('d-none')
      }

      
    },

    cancelEditComment: function(comment) {
      if(this.tab === 1) {
        const editModeOn = document.querySelector(`.commentId-editModeOn__${comment.commentId}`)
        const editModeOff = document.querySelector(`.commentId-editModeOff__${comment.commentId}`)
        editModeOn.classList.remove('d-flex')
        editModeOn.classList.add('d-none')
        editModeOff.classList.remove('d-none')
        editModeOff.classList.add('d-flex')
      } else {
        const editModeOnDocent = document.querySelector(`.docentId-editModeOn__${comment.commentId}`)
        const editModeOffDocent = document.querySelector(`.docentId-editModeOff__${comment.commentId}`)
        editModeOnDocent.classList.remove('d-flex')
        editModeOnDocent.classList.add('d-none')
        editModeOffDocent.classList.remove('d-none')
        editModeOffDocent.classList.add('d-flex')        
      }
    },

    editCommentSubmit: function(comment) {
      const editContent = comment.content
      console.log('editContent', editContent)
      axios ({
        method: 'put',
        url: `${this.hostUrl}/comments/${this.editCommentId}`,
        data: {content: editContent},
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {  
          console.log(res)
          if(this.tab === 1) {
            const editModeOn = document.querySelector(`.commentId-editModeOn__${this.editCommentId}`)
            const editModeOff = document.querySelector(`.commentId-editModeOff__${this.editCommentId}`)
            editModeOn.classList.remove('d-flex')
            editModeOn.classList.add('d-none')
            editModeOff.classList.remove('d-none')
            editModeOff.classList.add('d-flex')
          } else {
            const editModeOnDocent = document.querySelector(`.docentId-editModeOn__${this.editCommentId}`)
            const editModeOffDocent = document.querySelector(`.docentId-editModeOff__${this.editCommentId}`)
            editModeOnDocent.classList.remove('d-flex')
            editModeOnDocent.classList.add('d-none')
            editModeOffDocent.classList.remove('d-none')
            editModeOffDocent.classList.add('d-flex')
          }
        })
        .catch(err => {
          console.log(err)
        })
    },

    userProfile(comment) {
      this.$router.push({ name: 'Profile', params: {userId: comment.user.userId} })
    },

  },

  created() {
    this.getCnt()
    this.getComments()
    this.getDocents()
  },

  computed: {
    ...mapState([
        'hostUrl',
        'userInfo',
        'isLogon'
      ])
  },

  watch: {
    comments() {
      this.getCnt()
    },
    docents(){
      this.getCnt()
    }
  }
}

</script>

<style>

</style>
