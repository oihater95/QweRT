<template>
  <div class="container text-align-center">
  <div id="component-row" class="input-group my-5 d-flex row">
    <!--1. @drop : 드롭 영역 영역에 항목을 드롭 하는 시점 감지
        2. @dragover : 항목이 드롭 영역 위로 드래그되는 시점 감지
        3. @dragenter : To 항목이 드롭 영역에 들어갈 때 감지
        4. @dragleave : 항목이 드롭 영역을 떠날 때 감지 -->
    <v-card id="drop-card" class="col-6">
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
        >
    </v-card>
    <div class="col-2 mx-3">
      <div class="file-button">
        <label for="ex_file">
          <i class="fa fa-paperclip"> Search</i>
        </label>
        <input 
          id="ex_file" 
          type=file 
          class="file-input"  
          accept="image/*" 
          ref="fileInput" 
          @change="onFileChange">
      </div>
      <br>
      <div class="input-group-append">
        <v-btn 
          color="error"
          elevation="1"
          class="mb-2"
          @click.stop="clearInput">CLEAR
        </v-btn>
        <br>
        <v-btn
          color="primary"
          elevation="1"
          class="my-2"
          @click="onClickUpload">Upload
        </v-btn>
      </div>
    </div>

  </div>
</div>
</template>

<script>
// import axios from'axios'
import "@/css/common/dragNdrop.scss"

export default {
  name: "dragNdrop",
  data () {
      return {
        dragover: false,
        filename: '',
        imageSrc: '',
      }
  },
  methods: {
    // 드래그 앤 드랍 이미지 입력하기
    onDrop (event) {
      this.dragover = false
      this.inputImageFile(event.dataTransfer.files)
    },
    // 파일 선택으로 이미지 입력하기
    onFileChange(event) {
      this.inputImageFile(event.target.files)
    },
    // 이미지 입력하기
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
    // 업로드
    onClickUpload () {
      // 로컬 스토리지 저장
      if (0 < this.imageSrc.length) {
        // var image = {
        //   filename: this.filename + `${new Date().getMonth() + 1}/${new Date().getDate()}`,
        //   imageSrc: this.imageSrc
        // }
        // localStorage.setItem(this.filename, JSON.stringify(image))
        localStorage.setItem(this.filename, this.imageSrc)
        console.log('OK')
        this.$router.push({name: 'PostingDetail', params: {filename: this.filename, imageSrc: this.imageSrc}})
        this.clearInput()
        } else {
          alert('파일이 없습니다')
      }
    },
    // 업로드 후 입력칸 비우기
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

    // jwt, axios api 요청
    // setToken: function () {
    //   const token = localStorage.getItem('jwt')
    //   const config = {
    //     Authorization: `JWT ${token}`
    //   }
    //   return config
    // },
    // createTodo: function () {
    //   const img = {
    //     id: this.id,
    //     imageSrc: this.imageSrc,
    //   }

    //   if (img.id) {
    //     axios({
    //       method: 'post',
    //       url: 'http://127.0.0.1:8000/postings',
    //       data: img,
    //       headers: this.setToken()
    //     })
    //       .then((res) => {
    //         console.log(res)
    //         this.$router.push({ name: 'postings detail' })  상세페이지로 이동
    //       })
    //       .catch((err) => {
    //         console.log(err)
    //       })
    //     }
    // },
  }
}
</script>