<template>
  <div>
    <v-form>
      <v-container>
        <div class="d-flex flex-row-reverse">
          <span class="curation-delete__btn" @click="clickDelete">삭제하기</span>
          <span class="curation-change__btn" @click="changeCuration">저장하기</span>
        </div>
        <CurationInfo
          :curationTitle="curationTitle"
          :curationContent="curationContent"
          @change="changeInfo"
        />
          <!-- @change="changeInfo" -->
        <div class="new-thumbnail__div">
          <div>큐레이션 썸네일</div>
          <div class="d-flex new-thumbnail_last">
            <DragDrop
              class="new-curation__component"
              :imageFile="thumbnail"
              :deleteFlag="deleteFlag"
              />
            <div class="curation-thumbnail__btn">
              <div class="file-button d-inline me-2">
                <label for="curation-thumbnail">
                  <i class="fa fa-paperclip drag-search__icon "></i>
                </label>
                <input 
                  id="curation-thumbnail" 
                  type=file 
                  class="file-input"  
                  accept="image/*" 
                  ref="fileInput" 
                  @change="fileChange">
              </div>
              <div class="input-group-append d-inline">
                <v-btn
                  class="mb-2 mx-2 mt-1 posting-btns"
                  @click.stop="clearFile"
                  icon>
                  <v-icon id="posting-clear__btn">
                    mdi-trash-can-outline
                  </v-icon>
                </v-btn>
              </div>
            </div>
          </div>
          <div class="new-curation__color">
          <input type="checkbox" id="checkbox" v-model="checked">
          <label for="checkbox" class="new-curation__label">배경색 고르기</label>
          <span class="ml-3 mr-1">(현재 배경색:</span>
          <span>#{{this.color}})</span>
          <div v-if="checked">
            <SelectColor @changeColor="changeColor"/>
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

const API_ENDPOINT = 'https://2b7e7mxwc9.execute-api.ap-northeast-2.amazonaws.com/default/getPresignedUrl'

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
      curationImages: [],
      color: "흰 색",
      curationTitle: "",
      curationContent: "",
      imageLoaded: false,
      thumbnail: "",
      newColor: "",
      title: "",
      content: "",
      btnActivated: true,

      deleteFlag: false,
      imageFile: null,
      imageData: {
        drawing: false,
        image: '',
        imageSrc: '',
        fliename: '',
        file: '',
      },
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
    },
    changeColor: function (color) {
      this.newColor = color
    },
    changeInfo: function (title, description) {
      // 둘 다 내용이 있다면
      if (title && description) {
        this.title = title
        this.content = description
        this.btnActivated = true
      } else {
        this.btnActivated = false
      }
    },
    changeCuration: async function () {
      if (this.btnActivated) {
        // 체크되어있다면 색 바꾸기
        if (this.checked) {
          this.color = this.newColor
          this.color = this.color.slice(1,7)
        }
        // 썸네일을 바꿨다면 이미지 업로드하기
        if (this.imageInfo.image) {
          const response = await axios ({
            method: 'GET',
            url: API_ENDPOINT
          })
          let binary = atob(this.imageInfo.image.split(',')[1])
          let array = []
          for (var i = 0; i < binary.length; i++) {
            array.push(binary.charCodeAt(i))
          }
          let blobData = new Blob([new Uint8Array(array)], {type: 'image/jpeg'})

          const result = await fetch(response.data.uploadURL, {
            method: 'PUT',
            body: blobData
          })
          console.log(result)
          let fileKey = response.data.Key
          let fileImageSrc = 'https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/' + fileKey

          axios ({
            method: 'put',
            url: `${this.host}/curations/${this.$route.params.id}`,
            data: {
              color: this.color,
              title: this.title,
              content: this.content,
              thumbnailImg: fileImageSrc,
            },
            headers: { token: localStorage.getItem('jwtToken') }
          })
            .then(res => {  
              console.log(res)
              this.$router.push({name: 'CurationPage'})
            })
            .catch(err => {
              console.log(err)
            })
        } else {
          axios ({
            method: 'put',
            url: `${this.host}/curations/${this.$route.params.id}`,
            data: {
              color: this.color,
              title: this.title,
              content: this.content,
              thumbnailImg: this.thumbnail,
            },
            headers: { token: localStorage.getItem('jwtToken') }
          })
            .then(res => {  
              console.log(res)
              this.$router.push({name: 'CurationPage'})
            })
            .catch(err => {
              console.log(err)
            })
        }
      }
    },
    createImage (file) {
      let reader = new FileReader()
      reader.onload = (e) => {
        console.log('length: ', e.target.result.includes('data:image/jpeg'))
        if (!e.target.result.includes('data:image/jpeg')) {
          return alert('Wrong file type - JPG only.')
        }
        this.imageData.image = e.target.result
        this.$store.dispatch('setImageInfo', this.imageData)
        this.imageFile = this.imageInfo.image
      }
      reader.readAsDataURL(file)
    },
    inputImageFile (files) {
      if (files.length) {
        let file = files[0]
        // drag & drop시 파일 유형 이미지로 제한
        if (!/^image\//.test(file.type)) {
          alert('이미지 파일만 등록이 가능합니다')
          return false
        }
        this.imageData.filename = file.name
        this.imageData.file = file
      }
    },
    fileChange: function (e) {
      this.$store.dispatch('clearImageInfo')
      let files = e.target.files || e.dataTransfer.files
      if (!files.length) return
      this.createImage(files[0])
      this.inputImageFile(e.target.files)
    },
    clearFile: function () {
      this.$store.dispatch('clearImageInfo')
      this.imageFile = ''
      this.imgSrc = ''
      this.deleteFlag = true
      this.checkState()
    },
    checkState() {
      if(this.imageInfo.image === '') {
        this.deleteFlag = false
      }
    },
  },
  computed: {
    ...mapState(['host' ,'imageInfo' ,'userInfo' ]),
  },
  mounted: function () {
    axios.get(`${this.host}/curations/detail/${this.$route.params.id}`)
      .then(res => {
        this.curationImages = res.data.postings
        this.thumbnail = res.data.thumbnail
        this.curationTitle = res.data.title
        this.title = res.data.title
        this.curationContent = res.data.content
        this.content = res.data.content
        if (res.data.color) {
          this.color = res.data.color
        } 
      })
      .catch(err => {
        console.log(err)
      })
  },
  // 처음에는 axios를 created에 버튼 만들기를 mounted에 적었는데
  // 콘솔로 찍어보니까 axios가 비동기라서 mounted에 적힌 코드가 먼저 실행되었다. 그래서 버튼이 안 생겼고 이렇게 수정한다.
  updated: function () {
    if (!this.imageLoaded) {
      this.imageLoaded = true
      const cards = document.querySelectorAll(".v-card__main")
        for (let card of cards) {
          const btnForDelete = document.createElement('button')
            btnForDelete.classList.add("btn-for__delete")
            btnForDelete.innerText = "삭제하기"
            card.prepend(btnForDelete)
            btnForDelete.addEventListener("mouseover", this.deleteHoverOn)
            btnForDelete.addEventListener("mouseout", this.deleteHoverOff)
            btnForDelete.addEventListener("click", this.deleteImage)
        }
    }
  }
}
</script>

<style>

</style>