<template>
<div id="posting-detail__container" class="container">
  <DetailImage :postingImg="postingImg"/>
  <v-card
    v-if="editPostingFlag"
    id="posting-description"
    class="mt-5"
    color="#f1f3f5"
  >
    <v-card-title class="text-h5 ps-4 posting-first__row-edit">
      <input class="edit-posting__title my-2" type="text" maxlength="30" v-model="postingTitle">
      <div class="category-selector">
        <v-select
            :items="category"
            :label="selectedCategory"
            v-model="selectedCategory"
            dense
            solo
            height="30"
            background-color="#DCD8D7"
          ></v-select>
      </div>
    </v-card-title>
    <v-card-subtitle class="posting-content__edit">
      <textarea class="edit-posting__content my-1" maxlength="200" v-model="postingContent"></textarea>
    </v-card-subtitle>
    <v-card-subtitle class="subtitle-2 posting-edit__check">
      <v-btn @click="editPostingSubmit" plain>
        Edit
      </v-btn>
      <v-btn @click="cancelEdit" plain color="error">
        Cancel
      </v-btn>
    </v-card-subtitle>
  </v-card>
  <v-card
    v-else
    id="posting-description"
    class="mt-5"
    color="#f1f3f5"
  >
    <v-card-title class="text-h5 ps-4 posting-first__row">
      <span class="posting-title">
        {{ postingTitle }}
      </span>
      <v-sheet
      class="rounded-lg transition-swing category-box"
      color="#EDD8C3"
      height="30"
      width="50"
      >{{ selectedCategory }}</v-sheet>
    </v-card-title>
    <v-card-subtitle class="posting-content">{{ postingContent }}</v-card-subtitle>
    <v-card-subtitle @click="userProfile" class="subtitle-2 posting-nickname">
      <img 
      v-if="userPostingProfileImg()"
      :src="userPostingProfileImg()" 
      class="profile-img" @click="userProfile">
      <img 
      v-else
      src="@/assets/images/profile_image_default.png" 
      class="profile-img" @click="userProfile">
      <p class="d-inline user-profile__nickname" @click="userProfile">{{ postingUserNickname }}</p>
    </v-card-subtitle>
  </v-card>
  <div id="posting-btns">
    <div id="posting-icon__forUser">
        <v-btn
          class="mx-2 icon-button like-btn"
          fab
          small
          icon>
          <v-icon id="posting-like__btn" v-if="checkLikeState" @click="clickLike">
            mdi-heart
          </v-icon>
          <v-icon id="posting-unlike__btn" v-else @click="clickLike">
            mdi-heart-outline
          </v-icon>
        </v-btn>
        <v-btn
          class="mx-2 icon-button"
          fab
          small
          icon>
          <v-icon id="posting-add__curation" @click="addCuration">
            mdi-expand-all-outline
          </v-icon>
        </v-btn>
    </div>

    <div id="posting-icon__authority" v-if="checkPostingAuthority">
      <div class="icon-buttons">
        <v-btn
          class="mx-2 icon-button"
          fab
          small
          icon
          @click="editPosting">
          <v-icon id="posting-icon__edit">
            mdi-image-edit-outline
          </v-icon>
        </v-btn>
        <v-btn
          class="mx-2 icon-button"
          fab
          small
          icon
          @click="checkDeletePosting">
          <v-icon id="posting-icon__delete">
            mdi-trash-can-outline
          </v-icon>
        </v-btn>
      </div>
    </div>
  </div>
  <div id="cnt-group">
    <div>
      <span id="like-cnt">{{ postingLikeCnt }}</span>
      <span id="curated-cnt">{{ postingCuratedCnt }}</span>
    </div>
  </div>

  <CommentFrame :postingId="postingId" />
  <Modal
    @addCuration-no-sign="noSign"
    class="d-none"
    :msg="modalMsg"
    @checkDeletePosting-ok-sign="deletePosting"
    @checkLogin-ok-sign="goToLogin"
  />
</div>

</template>

<script>
import DetailImage from '@/components/postings/DetailImage'
import CommentFrame from '@/components/postings/CommentFrame'
import Modal from '@/components/common/Modal'
import '@/css/postings/PostingDetail.scss'
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'PostingDetail',
  components: {
    DetailImage,
    CommentFrame,
    Modal,
  },

  data: function() {
    return {
      postingId: 0,
      postingImg: '',
      postingTitle: '',
      postingContent: '',
      postingCreateDate: '',
      postingUpdateDate: '',
      postingLikeCnt: 0,
      postingCommentCnt: 0,  // 일반 댓글 수
      postingDocentCnt: 0,  // 도슨트 댓글 수
      postingUserId: 0,
      postingUserNickname: '',
      postingCategoryId: 0,
      postingCuratedCnt: 0,
      modalMsg: {
        name: '',
        triggerBtn: '',
        title: '',
        text: '',
        positiveBtn: '',
        negativeBtn: '',
      },
      page: 0,
      size: 10,
      category: ['동물', '사물', '풍경', '인물', '건물', '식물', '캐릭터', '기타'],
      selectedCategory: '',
      editPostingFlag: false,
      likeState: false,
      profileImg: '',
    }
  },

  methods: {
    getDetails: function() {
      axios({
        method: 'get',
        url: `${this.host}/postings/detail/${this.$route.params.postingId}/`,
        })
        .then(res => {
          this.postingId = res.data.postingId
          this.postingImg = 'https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/' + res.data.postingImg
          this.postingTitle = res.data.title
          this.postingContent = res.data.content
          this.postingCreateDate = res.data.createdate
          this.postingUpdateDate = res.data.updatedate
          this.postingLikeCnt = res.data.likeCnt
          this.postingCommentCnt = res.data.commentCnt
          this.postingDocentCnt = res.data.docentCnt
          this.postingUserId = res.data.userId
          this.postingUserNickname = res.data.nickname
          this.postingCategoryId = res.data.categoryId
          this.postingCuratedCnt = res.data.curatedCnt
          this.selectedCategory = this.category[this.postingCategoryId-1]
        })
        .catch(err => {
          console.log(err)
        })
    },
    addCuration: function() {
      if(this.isLogon === true) {
        axios.get(`${this.host}/curations/${this.userInfo.userId}`, { params: { page: this.page, size: this.size } })
          .then(res => {
            const curations = res.data
            this.modalMsg.text = curations
            if (this.modalMsg.text.length === 0) {
              this.modalMsg.title = '만든 큐레이션이 없습니다. 큐레이션을 만들어주세요.'
            } else {
              this.modalMsg.title = '그림을 넣을 큐레이션을 선택하세요'
            }
          })
          .catch(err => {
            console.log(err)
          })
  
        this.modalMsg.name='addCuration'
        this.modalMsg.triggerBtn = ''
        this.modalMsg.positiveBtn = '돌아가기'
        this.modalMsg.negativeBtn = '추가하기'
  
        const modalBtn = document.querySelector('#modalBtn')
        modalBtn.click()
      } else {
        this.checkLogin()
      }
    },
    noSign: function (array) {
      for (const id of array) {
        axios ({
          method: 'POST',
          headers: { token: localStorage.getItem('jwtToken') },
          url: `${this.host}/curations/${id}/${this.$route.params.postingId}/`,
        })
          .then(res => {
            console.log(res)
            this.getDetails()
          })
          .catch(err => {
            console.log(err)
          })
      } 
    },

    editPosting: function() {
      this.editPostingFlag = true
    },

    cancelEdit: function() {
      this.editPostingFlag = false
    },

    editPostingSubmit: function() {
      if (this.userInfo.userId === this.postingUserId) {
        axios ({
          method: 'put',
          url: `${this.host}/postings/${this.postingId}`,
          data: {
            categoryId: this.category.indexOf(this.selectedCategory) + 1,
            postingTitle: this.postingTitle,
            postingContent: this.postingContent
          },
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {  
            console.log(res)
            this.editPostingFlag = false
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        alert('작성자 본인만 가능합니다')
      }
    },

  
    checkDeletePosting: function() {
      this.modalMsg = {
        name: 'checkDeletePosting',
        triggerBtn: '',
        title: '게시물 삭제',
        text: '해당 게시물을 삭제하시겠습니까?',
        positiveBtn: '확인',
        negativeBtn: '취소',
      }
      const modalBtn = document.querySelector('#modalBtn')
      modalBtn.click()
    },

    deletePosting: function() {
      if (this.userInfo.userId === this.postingUserId) {
        axios ({
          method: 'delete',
          url: `${this.host}/postings/${this.postingId}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {  
            console.log(res)
            this.$router.push({ name: 'MainPage' })
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        alert('작성자 본인만 가능합니다')
      }
    },

    userProfile() {
      this.$router.push({ name: 'Profile', params: {userId: this.postingUserId} })
    },

    // 로그인한 사용자가 좋아요 눌렀을 경우 200, 아닐 경우 404코드
    getLikeState: function(){
      if(this.isLogon === true) {
        axios ({
          method: 'get',
          url: `${this.host}/like/${this.$route.params.postingId}/${this.userInfo.userId}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {  
            console.log(res)
            this.likeState = true
          })
          .catch(err => {
            if (err.response.status === 404) {
              this.likeState = false
            } else {
              console.log(err)
            }
          })
      } else {
        this.likeState = false
      }
    },

    checkLogin: function() {
      this.modalMsg = {
        name: 'checkLogin',
        triggerBtn: '',
        title: '로그인 필요',
        text: '해당 기능은 로그인이 필요한 기능입니다.<br/>로그인 하시겠습니까?',
        positiveBtn: '로그인 하러 가기',
        negativeBtn: '취소',
      }
      const modalBtn = document.querySelector('#modalBtn')
      modalBtn.click()
    },

    goToLogin: function() {
      this.$router.push({ name: 'Login' })
    },

    clickLike: function() { 
      if (this.isLogon === true) {
        axios ({
          method: 'put',
          url: `${this.host}/like/${this.$route.params.postingId}/${this.userInfo.userId}`,
          headers: { token: localStorage.getItem('jwtToken') }
        })
          .then(res => {  
            console.log(res)
            this.getLikeState()
            this.getDetails()
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        this.checkLogin()
      }
    },

    getPostingProfileImg() {
      this.getDetails()
      axios ({
          method: 'get',
          url: `${this.host}/profile/${this.postingUserId}/`,
        })
          .then(res => {  
            this.profileImg = res.data.profileImg
          })
          .catch(err => {
            console.log(err)
          })
    },

    userPostingProfileImg(){
      this.getPostingProfileImg()
      if(this.profileImg === '') {
        return false
      } else {
        return this.profileImg
      }
    },


  },

  computed: {
    // 게시물 작성자 본인인지 확인, 본인이 아니라면 false
    checkPostingAuthority() {
      if(this.userInfo.userId === this.postingUserId) {
        return true 
      } else {
        return false
      }
    },
    checkLikeState() {
      if(this.likeState === true) {
        return true
      } else {
        return false
      }
    },

      ...mapState([
        'host',
        'userInfo',
        'isLogon'
      ])
  },

  created() {
    this.getDetails()
    this.getLikeState()
    this.getPostingProfileImg()
  },
  
}

</script>

<style>

</style>