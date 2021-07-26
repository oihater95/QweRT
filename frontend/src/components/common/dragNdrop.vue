<template>
  <div>
  <div class="input-group my-5">
    <v-card>
      <input
        id = "dragNdrop-region"
        type="text"
        class="form-control"
        placeholder="Drag & Drop or Select"
        v-model="filename"
        @dragover.prevent
        @dragenter.prevent
        @drop.prevent="onDrop">
    </v-card>
    <div class="input-group-append">
        <button
          class="btn btn-outline-info"
          @click="onClickUpload">Upload</button>
    </div>
    <div class="file-button">
      <label for="ex_file"><i class="fa fa-paperclip">Search</i></label>
      <input id="ex_file" type=file class="file-input"  accept="image/*" ref="fileInput" @change="onFileChange">
    </div>

  </div>
  <div v-show="imageSrc" class="upload-image">
    <img :src="imageSrc">
  </div>
</div>
</template>

<script>
// import axios from'axios'
import "@/components/css/common/dragNdrop.scss"

export default {
  name: "dragNdrop",
  data () {
      return {
        filename: '',
        imageSrc: '',
      }
  },
  methods: {
    // 드래그 앤 드랍 이미지 입력하기
    onDrop (event) {
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
        var image = {
          filename: this.filename + `${new Date().getMonth() + 1}/${new Date().getDate()}`,
          imageSrc: this.imageSrc
        }
        localStorage.setItem(this.filename, JSON.stringify(image))
        console.log('OK')
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

<style scoped>

</style>