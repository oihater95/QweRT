<template>
  <v-container>
    <h1>파일 업로더</h1>
     <v-card>
      <div  v-if="imageSrc" class="upload-image">
        <img id="previewUploadImage" 
        :src="imageSrc" >
      </div>
    </v-card>
    <input id="file-selector" ref="file" type="file" @change="handleFileUploader">
    <v-btn @click="uploadFiles" color="primary">업로드</v-btn>
    <h1>파일 리스트</h1>
    <div v-for="(file, idx) in fileList" :key=file.Key>
      #{{idx+1}}: {{file.Key}}
      <v-btn @click="deleteFile(file.Key)" color="red">Delete</v-btn>
    </div>
  </v-container>
</template>

<script>
import AWS from 'aws-sdk'
export default {
  name: 'FileUpload',
  data: function () {
    return {
      file: null,
      // env 파일에 넣어야 한다
      bucketName: process.env.VUE_APP_BUCKET_NAME,
      bucketRegion: process.env.VUE_APP_BUCKET_REGION,
      IdentityPoolId: process.env.VUE_APP_I_P_ID,
      fileList: [],
      imageSrc: null,
    }
  },
  methods: {
    handleFileUploader: function () {
     this.file = this.$refs.file.files[0]
     this.preview(this.file)
    },
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

    uploadFiles: function(){
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
        IdentityPoolId: this.IdentityPoolId
        })
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: { Bucket: this.bucketName }
      });

      // 파일명대로 S3에 저장이 된다.
      let photoKey = this.file.name

      s3.upload({
          Key: photoKey,
          Body: this.file,
          ACL: 'public-read'
      }, (err, data) => {
        if (err) {
          console.log(err)
          return alert("There was an error uploading your photo: ", err.message)
        }
        alert("Successfully uploaded photo.")
        console.log(data)
        this.getFiles()
      })

    },
    getFiles: function(){
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId
        })
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: { Bucket: this.bucketName }
      });
      

      s3.listObjects({ Delimiter: "/"
      },(err, data) => {
        if (err) {
          return alert("There was an error listing your albums: " + err.message);
        } else {
          this.fileList = data.Contents
        }
      });
    },
    deleteFile: function(key){
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId
        })
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: { Bucket: this.bucketName }
      });

      s3.deleteObject({ Key: key }, (err, data) => {
        if (err) {
          return alert("There was an error deleting your photo: ", err.message)
        }
        alert("Successfully deleted photo.")
        console.log(data)
        this.getFiles()
      });
    }
  },
  created: function () {
    this.getFiles()
  },

}
</script>

<style>

</style>
