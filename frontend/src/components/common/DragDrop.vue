<template>
    <div class="container text-align-center">
        <div id="component-row" class="input-group my-5 d-flex row">
            <!--1. @drop : 드롭 영역 영역에 항목을 드롭 하는 시점 감지
                2. @dragover : 항목이 드롭 영역 위로 드래그되는 시점 감지
                3. @dragenter : To 항목이 드롭 영역에 들어갈 때 감지
                4. @dragleave : 항목이 드롭 영역을 떠날 때 감지 -->
            <v-card id="drop-card" class="col-12">
                <div  v-if="imgSrc" class="upload-image">
                    <img id="previewImg" :src="imgSrc" class="col-12" style="object-fit: contain;">
                </div>
                <div  v-else-if="imageInfo.image" class="upload-image">
                    <img id="previewImg" 
                        :src="imageInfo.image" 
                        class="col-12"
                        style="object-fit: contain;"
                        @dragover.prevent="dragover = true"
                        @dragenter.prevent="dragover = true"
                        @dragleave.prevent="dragover = false"
                        @drop.prevent="onDrop"
                        :class="{ 'grey lighten-3': dragover }"
                    >
                </div>
                <div  v-else-if="imageFile" class="upload-image">
                    <img id="previewImg" 
                        :src="imageFile" 
                        class="col-12"
                        style="object-fit: contain;"
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
            
        </div>
    </div>
</template>

<script>
import "@/css/common/dragNdrop.scss"
import { mapState } from 'vuex'


export default {
  name: 'DragDrop',
  data: function () {
    return {
      dragover: false,
      filename: '',
      imageSrc: '',
      drawing: false,
      image: '',
      file: ''
    }
  },

  props: {
    imageFile: {
      type: String,
    },
    imgSrc: {
      type: String,
    },
    deleteFlag: {
      type: Boolean,
    }
  },

  methods: {
    onDrop (event) {
      this.dragover = false
      this.createImage(event.dataTransfer.files[0])
      this.inputImageFile(event.dataTransfer.files)
      console.log('drop', this.imageInfo)
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
        this.file = file
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
        this.$store.dispatch('setImageInfo', {
          drawing: this.drawing,
          image: this.image,
          imageSrc: this.ImageSrc,
          filename: this.filename,
          file: this.file
        })
      }
      reader.readAsDataURL(file)
    },
    

    // 미리보기
    preview (file) {
      if (typeof file === 'string') {
        this.imageSrc = file
      } else {
        let reader = new FileReader()
        reader.onload = () => {
          this.imageSrc = reader.result
        }
        reader.readAsDataURL(file)
      }
    },

    // clearImage() {
    //   this.imageSrc = ''
    //   this.imgSrc = ''
    // }
  },

  mounted() {
    this.$store.dispatch('clearImageInfo')
    if(this.$route.params.imgSrc) {
      this.drawing = true
    }
  },
  computed: {
    ...mapState(['host', 'imageInfo']),
  },
  watch: {
    imageFile() {
      this.imageSrc = ''
    },
    imageInfo() {
      if(this.imageInfo.image === '') {
        this.imageSrc = ''
        this.imgSrc = ''
        this.imageFile = ''
      }
    },
    deleteFlag() {
      if(this.deleteFlag === true) {
        this.$store.dispatch('clearImageInfo')
        this.imageSrc = ''
        this.imgSrc = ''
        this.imageFile = ''
      }
    }
  }
}
</script>

<style>

</style>