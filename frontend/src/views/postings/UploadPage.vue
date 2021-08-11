<template>
  <v-container>
    <v-row>
      <v-col><DragDrop imgSrc="imgSrc" :imageFile="imageFile" :deleteFlag="deleteFlag"/></v-col>
      <v-col>
        <div class="container">
          <v-card class="my-5 posting-title">
            <v-text-field
              label="제목"
              :rules="[rules.required]"
              hide-details="auto"
              counter="30"
              maxlength="30"
              v-model="postingData.postingTitle"
            ></v-text-field>
          </v-card>
          
          <v-textarea
          class="my-5 pt-5 posting-content"
          solo
          height="180px"
          maxlength="200"
          counter="200"
          v-model="postingData.postingContent"
          :rules="[rules.required]"
          :placeholder="'설명\n\n도용 및 저작권 침해에 주의해주세요'"
          >
          </v-textarea>
          <div class="mx-1 button-group">
              <div v-if="$route.params.imgSrc" class="d-inline">
                  <v-btn
                      class="mb-2 mx-2 mt-1 posting-btns"
                      @click="backToDrawing"
                      icon>
                      <i id="posting-back__btn" class="fas fa-reply"></i>
                  </v-btn>
              </div>
              <div v-else class="file-button d-inline me-2">
                  <label for="ex_file">
                      <i class="fa fa-paperclip drag-search__icon "></i>
                  </label>
                  <input 
                  id="ex_file" 
                  type=file 
                  class="file-input"  
                  accept="image/*" 
                  ref="fileInput" 
                  @change="onFileChange">
              </div>
              <div class="input-group-append d-inline">
                  <v-btn
                      class="mb-2 mx-2 mt-1 posting-btns"
                      @click.stop="clearInput"
                      icon>
                      <v-icon id="posting-clear__btn">
                          mdi-trash-can-outline
                      </v-icon>
                  </v-btn>
                  <v-btn
                      class="mb-2 mx-2 mt-1 posting-btns"
                      @click="uploadImage"
                      icon>
                      <v-icon id="posting-upload__btn">
                          mdi-upload-outline
                      </v-icon>
                  </v-btn>
              </div>
          </div>
        </div>
      </v-col>
    </v-row>
    <v-row>
      <RoundedBtn
          v-for="(item, idx) in category" 
          :key="idx"
          :item="item"
          class="category-btns"
          :id="`category-id__${idx}`"
        />
    </v-row>
  </v-container>
</template>

<script>
import DragDrop from "@/components/common/DragDrop"
import RoundedBtn from "@/components/common/RoundedBtn"
import "@/css/postings/UploadPage.scss"
import axios from'axios'
import { mapState } from 'vuex'

const API_ENDPOINT = 'https://2b7e7mxwc9.execute-api.ap-northeast-2.amazonaws.com/default/getPresignedUrl'

export default {
  name: 'UploadPage',
  components: {
    DragDrop,
    RoundedBtn,
  },
  data() {
    return {
      rules: {
        required: value => !!value || 'Required.',
      },
      category: ['동물', '사물', '풍경', '인물', '건물', '식물', '캐릭터', '기타'],
      toggleCategoryId: [],
      imageData: {
        drawing: false,
        image: '',
        imageSrc: '',
        fliename: '',
        file: '',
      },
      imageFile: null,
      deleteFlag: false,
      postingData: {
        userId: '',
        postingTitle: '',
        postingContent: '',
        postingImage: '',
        categoryId: 0,
      }
    }
  },
  props: {
    imgSrc: {
      type: String,
    },
    imagename: {
      type: String
    }
  },
  methods: {
    clickCategory: function(e) {
      for (var i=0; i < this.category.length; i++) {
        if (e.target.innerText === this.category[i]) {
          if (this.toggleCategoryId.length === 0) {
            this.toggleCategoryId.push(`${i}`)
          } else {
            if (i == this.toggleCategoryId[0]) {
              while (this.toggleCategoryId.length > 0) {
                this.toggleCategoryId.pop()
              }
            } else {
              while (this.toggleCategoryId.length > 0) {
                this.toggleCategoryId.pop()
              }
              this.toggleCategoryId.push(`${i}`)
            }
          }
        }
      }
      // 토글 ON/OFF
      for (var j=0; j < this.category.length; j++) {
        if (j == this.toggleCategoryId[0]) {
          let toggleOn = document.querySelector(`.category-btn__${j}`)
          toggleOn.style = "color: skyblue; border-color: skyblue"
        } else {
          let toggleOff = document.querySelector(`.category-btn__${j}`)
          toggleOff.style = "color: black; border-color: black"
        }
      } 
    },

    onFileChange (e) {
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

    uploadImage: async function () {
      if (this.toggleCategoryId.length < 1) {
        alert('카테고리를 선택해주세요')
      } else {
        if (this.imageInfo.filename.length > 0 || this.imageInfo.drawing === true) {
            const response = await axios({
              method: 'GET',
              url: API_ENDPOINT
            })
            console.log('Response: ', response)
            let binary = atob(this.imageInfo.image.split(',')[1])
            let array = []
            for (var i = 0; i < binary.length; i++) {
              array.push(binary.charCodeAt(i))
            }
            let blobData = new Blob([new Uint8Array(array)], {type: 'image/jpeg'})
            // Put request for upload to S3
            const result = await fetch(response.data.uploadURL, {
              method: 'PUT',
              // lambda에 적어준 내용과 일치해야 한다.
              // headers: {
              //   'Content-type': 'image/jpeg'
              // },
              body: blobData
            })
            console.log('Result: ', result)
      
            let fileKey = response.data.Key
            let fileImageSrc = 'https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/' + fileKey
            this.postingData.postingImage = fileKey
            let categoryid = Number(this.toggleCategoryId[0]) + 1
            this.postingData.categoryId = categoryid
            this.postingData.userId = this.userInfo.userId
  
  
            axios ({
              method: 'post',
              url: `${this.host}/postings`,
              data: this.postingData,
              headers: { token: localStorage.getItem('jwtToken') }
            })
              .then(res => {  
                console.log(res)
                this.$router.push({name: 'PostingDetail', params: {filename: fileKey, imageSrc: fileImageSrc}})
              })
              .catch(err => {
                console.log(err)
              })
            
        } else {
          alert('파일이 없습니다')
        }
      }
      

    },
    // 데이터 삭제
    clearInput() {
      this.$store.dispatch('clearImageInfo')
      this.deleteFlag = true
    },

    // 그리기에서 넘어왔을 때 뒤로가기
    backToDrawing() {
      this.$router.push({name: 'Drawing', params: {imgSrc: this.$route.params.imgSrc}})
    }
  },
  mounted() {
    let categories = document.getElementsByClassName('rounded-btn')
    for (var i=0; i < categories.length; i++) {
      categories[i].classList.add(`category-btn__${i}`)
      categories[i].addEventListener('click', this.clickCategory)

    if(this.$route.params.imgSrc) {
      this.imageData.drawing = true
      this.imageData.image = this.$route.params.imgSrc
      this.imageData.filename = this.$route.params.imagename
      this.$store.dispatch('setImageInfo', this.imageData)
    }
    }
  },
  computed: {
    ...mapState(['host', 'imageInfo', 'userInfo'])
  },
}
</script>

<style>

</style>