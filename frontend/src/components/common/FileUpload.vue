<template>
  <v-container>
    <h1>파일 업로더</h1>
     <v-card>
      <div  v-if="imageSrc" class="upload-image">
        <img id="previewUploadImage" 
        :src="imageSrc" >
      </div>
    </v-card>
    <input type="file" @change="onFileChange">
    <v-btn @click="uploadImage" color="primary">업로드</v-btn>
  </v-container>
</template>

<script>
import axios from'axios'

const API_ENDPOINT = 'https://2b7e7mxwc9.execute-api.ap-northeast-2.amazonaws.com/default/getPresignedUrl'

export default {
  name: 'FileUpload',
  data: function () {
    return {
      imageSrc: null,
    }
  },
  methods: {
    onFileChange (e) {
      let files = e.target.files || e.dataTransfer.files
      if (!files.length) return
      this.createImage(files[0])
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
    }
  }
}
</script>

<style>

</style>