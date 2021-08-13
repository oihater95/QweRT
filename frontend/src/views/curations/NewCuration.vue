<template>
  <v-form>
    <v-container>
      <CurationInfo @change="changeInfo"/>
      <div class="new-thumbnail__div">
        <div>선택사항: 큐레이션 썸네일</div>
        <div>큐레이션 썸네일을 업로드하지 않으면 큐레이션에 제일 먼저 담긴 세 작품이 썸네일로 보입니다.</div>
        <div class="d-flex new-thumbnail_last">
          <DragDrop class="new-curation__component" :imageFile="imageFile" :deleteFlag="deleteFlag"/>

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
          <label for="checkbox" class="new-curation__label">선택사항: 배경색 고르기</label>
          <span>
          (배경색을 고르지 않으면 흰 색으로 설정됩니다.)

          </span>
          <div v-if="checked">
            <SelectColor @changeColor="changeColor"/>
          </div>
        </div>
        <div v-if="errorActivated" class="curation-error-message">제목과 설명은 필수입니다.</div>
        <button class="curation-make__btn" @click.prevent="clickCreate">CREATE</button>
      </div>
    </v-container>
  </v-form>
</template>

<script>
import "@/css/curations/NewCuration.scss"
import CurationInfo from "@/components/curations/CurationInfo"
import DragDrop from "@/components/common/DragDrop"
import SelectColor from "@/components/curations/SelectColor"

import axios from 'axios'
import { mapState } from 'vuex'

const API_ENDPOINT = 'https://2b7e7mxwc9.execute-api.ap-northeast-2.amazonaws.com/default/getPresignedUrl'

export default {
  name: "NewCuration",
  components: {
    CurationInfo,
    DragDrop,
    SelectColor
  },
  data: function () {
    return {
      checked: false,
      btnActivated : false,
      errorActivated: false,
      title: "",
      description: "",
      color: "",
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
  methods:  {
    clickCreate: async function () {
      if (this.btnActivated === true) {
        this.color = this.color.slice(1,7)
        // 체크 박스 선택이 안 되어있다면
        if (!this.checked) {
          this.color = ""
        }
        // 이미지가 있다면 이미지 업로드하기
        if (this.imageData.filename) {
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

          console.log(fileImageSrc)
          axios ({
            method: 'POST',
            url: `${this.host}/curations/${this.userInfo.userId}`,
            headers: { token: localStorage.getItem('jwtToken') },
            data: {
              title: this.title,
              content: this.description,
              thumbnailImg: fileImageSrc,
              color: this.color,
            }
          })
            .then(res => {
              console.log(res)
              this.$router.push({name: 'CurationPage'})
            })
            .catch(err => {
              console.log(err)
            })
        // 이미지가 없다면
        } else {
          axios ({
            method: 'POST',
            url: `${this.host}/curations/${this.userInfo.userId}`,
            headers: { token: localStorage.getItem('jwtToken') },
            data: {
              title: this.title,
              content: this.description,
              thumbnailImg: null,
              color: this.color,
            }
          })
            .then(res => {
              console.log(res)
              this.$router.push({name: 'CurationPage'})
            })
            .catch(err => {
              console.log(err)
            })
        }
      } else {
        this.errorActivated = true
      }
    },
    changeInfo: function (title, description) {
      // 둘 다 내용이 있다면
      if (title && description) {
        this.title = title
        this.description = description
        this.btnActivated = true
      } else {
        this.btnActivated = false
      }
    },
    changeColor: function (color) {
        this.color = color
    },
    fileChange: function (e) {
      this.$store.dispatch('clearImageInfo')
      let files = e.target.files || e.dataTransfer.files
      if (!files.length) return
      this.createImage(files[0])
      this.inputImageFile(e.target.files)
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
    ...mapState(['host', 'imageInfo', 'userInfo'])
  },
}
</script>

<style>

</style>