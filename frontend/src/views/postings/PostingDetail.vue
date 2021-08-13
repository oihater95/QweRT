<template>
<div id="posting-detail__container" class="container">
  <DetailImage :postingImg="postingImg"/>
  <v-card
    id="posting-description"
    class="mt-5"
    color="#f1f3f5"
  >
    <v-card-title class="text-h5 ps-4 my-2 posting-title">{{ postingTitle }}</v-card-title>
    <v-card-subtitle class="posting-content">{{ postingContent }}</v-card-subtitle>
    <v-card-subtitle class="subtitle-2 posting-nickname">{{ postingUserNickname }}</v-card-subtitle>
  </v-card>
  <div id="posting-btns">
    <div id="posting-icon__forUser">
        <v-btn
          class="mx-2 icon-button"
          fab
          small
          icon>
          <v-icon id="posting-like__btn">
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
        })
        .catch(err => {
          console.log(err)
        })
    },
    addCuration: function() {
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
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    editPosting: function() {
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

  },

  computed: {
    // 게시물 작성자 본인인지 확인, 본인이 아니라면 false
    checkPostingAuthority() {
      if(this.userInfo.userId === this.postingUserId) {
        console.log('True')
        return true 
      } else {
        console.log('False')
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
  },
  
}

</script>

<style>

</style>