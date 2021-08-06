<template>
    <div class="container text-align-center">
        <div id="component-row" class="input-group my-5 d-flex row">
            <!--1. @drop : 드롭 영역 영역에 항목을 드롭 하는 시점 감지
                2. @dragover : 항목이 드롭 영역 위로 드래그되는 시점 감지
                3. @dragenter : To 항목이 드롭 영역에 들어갈 때 감지
                4. @dragleave : 항목이 드롭 영역을 떠날 때 감지 -->
            <v-card id="drop-card" class="col-12">
                <div  v-if="imageSrc" class="upload-image">
                    <img id="previewImg" 
                        :src="imageSrc" 
                        class="col-12"
                        @dragover.prevent="dragover = true"
                        @dragenter.prevent="dragover = true"
                        @dragleave.prevent="dragover = false"
                        @drop.prevent="onDrop"
                        :class="{ 'grey lighten-3': dragover }"
                    >
                </div>
                <input
                    id = "dragNdrop-region"
                    v-else
                    @dragover.prevent="dragover = true"
                    @dragenter.prevent="dragover = true"
                    @dragleave.prevent="dragover = false"
                    @drop.prevent="onDrop"
                    type="text"
                    class="form-control col-12"
                    :class="{ 'grey lighten-3': dragover }"
                    placeholder="Drag & Drop or Select"
                    readonly
                >
            </v-card>
            <div class="mx-1 col-6">
                <div class="file-button d-inline me-2">
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
    </div>
</template>

<script>
import "@/css/common/dragNdrop.scss"
import axios from'axios'

const API_ENDPOINT = 'https://2b7e7mxwc9.execute-api.ap-northeast-2.amazonaws.com/default/getPresignedUrl'

export default {
  name: 'DragDrop',
  data: function () {
    return {
      dragover: false,
      filename: '',
      imageSrc: '',
    }
  },
  methods: {
    onDrop (event) {
      this.dragover = false
      this.inputImageFile(event.dataTransfer.files)
    },
    onFileChange (e) {
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
        this.filename = file.name
        // 미리보기
        this.preview(file)
      }
    },
    createImage (file) {
      let reader = new FileReader()
      reader.onload = (e) => {
        console.log('length: ', e.target.result.includes('data:image/jpeg'))
        if (!e.target.result.includes('data:image/jpeg')) {
          return alert('Wrong file type - JPG only.')
        }
        this.image = e.target.result
      }
      reader.readAsDataURL(file)
    },
    uploadImage: async function () {
      const response = await axios({
        method: 'GET',
        url: API_ENDPOINT
      })
      console.log('Response: ', response)

      let binary = atob(this.image.split(',')[1])
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
    },
    clearInput() {
      this.filename = '',
      this.imageSrc = ''
    },

    // 미리보기
    preview (file) {
      if (typeof file === 'string') {
        this.imageSrc = file
      } else {
        let vm = this
        let reader = new FileReader()
        reader.onload = () => {
          vm.imageSrc = reader.result
        }
        reader.readAsDataURL(file)
      }
    },
  }
}
</script>

<style>

</style>