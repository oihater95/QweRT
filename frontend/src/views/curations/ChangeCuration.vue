<template>
  <div>
    <v-form>
      <v-container>
        <div class="d-flex flex-row-reverse">
          <span class="curation-delete__btn" @click="clickDelete">DELETE</span>
          <span class="curation-change__btn">SAVE</span>
        </div>
        <CurationInfo/>
        <div class="new-thumbnail__div">
          <div>큐레이션 썸네일</div>
          <div class="d-flex new-thumbnail_last">
            <DragDrop class="new-curation__component" />
          </div>
          <div class="new-curation__color">
          <input type="checkbox" id="checkbox" v-model="checked">
          <label for="checkbox" class="new-curation__label">배경색 고르기</label>
          <div v-if="checked">
            <SelectColor/>
          </div>
        </div>
        </div>
      </v-container>
    </v-form>
    <div class="change-curation__div">전시작품 목록</div>
    <br>    
    <br>    
    <v-row>
      <MainImage
        v-for="(image, idx) in curationImages" 
        :key="1-idx"
        :image="image"
      />
    </v-row>
    <Modal
      :msg="modalMsg"
      @deleteCuration-ok-sign="deleteCuration"
      class="d-none"
    />
  </div>
</template>

<script>
import "@/css/curations/ChangeCuration.scss"
import CurationInfo from "@/components/curations/CurationInfo"
import DragDrop from "@/components/common/DragDrop"
import MainImage from "@/components/postings/MainImage"
import SelectColor from "@/components/curations/SelectColor"
import Modal from "@/components/common/Modal"
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: "ChangeCuration",
  components: {
    CurationInfo,
    DragDrop,
    SelectColor,
    MainImage,
    Modal
  },
  data:  function () {
    return {
     checked: false,
     modalMsg: {
      name: '',
      triggerBtn: '',
      title: '',
      text: '',
      positiveBtn: '',
      negativeBtn: '',
      },
      curationImages: []
    }
  },
  methods: {
    // 삭제 버튼에 마우스 올리면 하늘색 테두리
    deleteHoverOn: function (e) {
        e.target.nextSibling.classList.add("hover-for__delete")
    },
    // 하늘색 테두리 없애기
    deleteHoverOff: function (e) {
      e.target.nextSibling.classList.remove("hover-for__delete")
    },
    deleteImage: function (e) {
      const target = e.target.parentNode.parentNode
      target.remove()
      const targetId = e.target.parentNode.dataset.postingid
      axios ({
        method: 'DELETE',
        url: `${this.host}/curations/${this.$route.params.id}/${targetId}`,
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {  
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
    },
    clickDelete:  function () {
      this.modalMsg.name='deleteCuration'
      this.modalMsg.triggerBtn = ''
      this.modalMsg.title = ''
      this.modalMsg.text = '정말로 큐레이션을 삭제하시겠습니까?'
      this.modalMsg.positiveBtn = '삭제하겠습니다.'
      this.modalMsg.negativeBtn = '아니오'
      const modalBtn = document.querySelector('#modalBtn')
      modalBtn.click()
    },
    deleteCuration:  function () {
      console.log(this.host)
      axios ({
        method: 'DELETE',
        url: `${this.host}/curations/${this.$route.params.id}`,
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {  
          console.log(res)
          this.$router.push({ name: 'CurationPage' })
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  computed: {
    ...mapState(['host']),
  },
  mounted: function () {
    axios.get(`${this.host}/curations/detail/${this.$route.params.id}`)
      .then(res => {
        console.log(res)
        this.curationImages = res.data.postings
        console.log(res.data)
      })
      .catch(err => {
        console.log(err)
      })
  },
  // 처음에는 axios를 created에 버튼 만들기를 mounted에 적었는데
  // 콘솔로 찍어보니까 axios가 비동기라서 mounted에 적힌 코드가 먼저 실행되었다. 그래서 버튼이 안 생겼고 이렇게 수정한다.
  updated: function () {
    const cards = document.querySelectorAll(".v-card__main")
        console.log(cards)
        for (let card of cards) {
            const btnForDelete = document.createElement('button')
            btnForDelete.classList.add("btn-for__delete")
            btnForDelete.innerText = "DELETE"
            card.prepend(btnForDelete)
            btnForDelete.addEventListener("mouseover", this.deleteHoverOn)
            btnForDelete.addEventListener("mouseout", this.deleteHoverOff)
            btnForDelete.addEventListener("click", this.deleteImage)
        }
  }
}
</script>

<style>

</style>