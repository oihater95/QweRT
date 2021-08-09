<template>
  <div id="drawing-body" class="container">
    <canvas 
    id="drawing-canvas" 
    class="canvas"
    @mousemove="onMouseMove"
    @mousedown="startPainting"
    @mouseup="stopPainting"
    @mouseleave="stopPainting"
    @click="handleCanvasClick"
    @contextmenu="handleCM"></canvas>
      <div class="controls">
        <div class="controls__range">
          <input 
          type="range" 
          id="controls-range" 
          min="1" 
          max="64"
          value="12.0"
          step="1"
          @input="handleRangeChange">
          {{ this.canvasLineWidth }}px
        </div>
        <div class="controls__btns">
          <v-row v-if="this.filling===false">
          <button 
            id="StrokeBtn" 
            @click="handleStrokeClick" 
            style="border-color: skyblue; border-width: 3px">
            Stroke
          </button>
          <button id="PaintBtn" @click="handlePaintClick">Paint</button>
          <button id="clearBtn" @click="handleClearClick">Clear</button>
          <button id="undoBtn" class="undoBtn" @click="handleUndoClick" disabled style="width:40px">
            <v-icon>
              mdi-undo
            </v-icon>
          </button>
          <button id="redoBtn" class="redoBtn" @click="handleRedoClick" disabled style="width:40px">
            <v-icon>
              mdi-redo
            </v-icon>
          </button>
          <button id="uploadBtn" @click="handleUploadClick" disabled>Upload</button>
          </v-row>

          <v-row v-else>
          <button id="StrokeBtn" @click="handleStrokeClick">Stroke</button>
          <button 
            id="PaintBtn" 
            @click="handlePaintClick" 
            style="border-color: skyblue; border-width: 3px">
            Paint
          </button>
          <button id="clearBtn" @click="handleClearClick">Clear</button>
          <button id="undoBtn" class="undoBtn" @click="handleUndoClick" disabled style="width:40px">
            <v-icon>
              mdi-undo
            </v-icon>
          </button>
          <button id="redoBtn" class="redoBtn" @click="handleRedoClick" disabled style="width:40px">
            <v-icon>
              mdi-redo
            </v-icon>
          </button>
          <button id="uploadBtn" @click="handleUploadClick" disabled>Upload</button>
          </v-row>
    
        </div>
        <div id="canvas-colors" class="controls__colors">
          <div id="current-color__box">
            <span style="color:gray">current</span>
            <div id="current-color" class="controls__color" style="background-color:black"></div>
          </div>
          <div id="select-color__box">
            <span style="color:gray">select</span>
            <input 
            id="select-color"
            class="controls__color"
            @input="selectColorChange" 
            @change="selectColorChange" 
            type="color"
            value="#A9A9A9">
            <button id="addBtn" @click="addColor">Add</button>
          </div>
          <div class="control-color__box">
            <div @click="handleColorClick" class="controls__color" style="background-color:black"></div>
            <div @click="handleColorClick" class="controls__color" style="background-color:white"></div>
            <div @click="handleColorClick" class="controls__color" style="background-color:#FF3B30"></div>
            <div @click="handleColorClick" class="controls__color" style="background-color:#FF9500"></div>
            <div @click="handleColorClick" class="controls__color" style="background-color:#FFCC00"></div>
            <div @click="handleColorClick" class="controls__color" style="background-color:#4CD963"></div>
            <div @click="handleColorClick" class="controls__color" style="background-color:#5AC8FA"></div>
            <div @click="handleColorClick" class="controls__color" style="background-color:#0579FF"></div>
            <div @click="handleColorClick" class="controls__color" style="background-color:#5856D6"></div>
          </div>
        </div>
        <div 
        v-show="pickColorCnt > 0" 
        id="add-color__box" 
        class="control-color__box controls__colors ps-1">
        </div>
      </div>
  </div>
</template>

<script>
import "@/css/postings/Drawing.scss"

export default {
  name: "Drawing",
  data: function(){
    return{
      x: 0,
      y: 0,
      last_x: 0,
      last_y: 0,
      painting: false,
      vueCanvas: null,
      canvasLineWidth: null,
      canvasFrame: null,
      filling: false,  // default는 그리기 (true => 채우기)
      pickColor: null,
      pickColorCnt: 0,
      currentColor: null,
      history: {
        undoList: [],
        redoList: [],
      },
      isInput: false,
    }
  },
  methods: {
    // mousedown
    startPainting: function() {
      this.painting = true
      
    },
    // mouseup & mouseleave
    stopPainting: function() {
      if (this.filling === false){
        let img = this.canvasFrame.toDataURL('image/jpeg')
        if(this.painting === true) {
          this.history.undoList.push(img)
          document.getElementById("undoBtn").removeAttribute("disabled")
          document.getElementById("uploadBtn").removeAttribute("disabled")
        } 
      }
      this.painting = false
    },

    // 마우스 움직이는 내내 발생
    onMouseMove: function(e) {
      this.x = e.offsetX
      this.y = e.offsetY
      
      if(!this.painting) {
        // mouseup painting false
        // mousemove => 펜 위치 움직임
        // beginpath는 클릭 전까지 마우스가 캔버스 위에 있으면 따라다님
        this.vueCanvas.beginPath()
        // 펜 위치 설정
        this.vueCanvas.moveTo(e.offsetX, e.offsetY)
      } else {
        // 마우스 시작 위치에서 펜 위치까지 선 긋기
        // mousedown painting true
        this.vueCanvas.lineTo(e.offsetX, e.offsetY)
        this.vueCanvas.stroke()
      }
    },
    // 주어진 색상 선택
    handleColorClick: function(e) {
      const selectedColor = e.target.style.backgroundColor
      const currentColor = document.getElementById("current-color")
      // 현재 색상
      currentColor.style = "background-color:" + `${selectedColor}`
      this.currentColor = selectedColor
      this.vueCanvas.strokeStyle = selectedColor
      this.vueCanvas.fillStyle = selectedColor
    },

    // custom 색상 선택
    selectColorChange: function(e) {
      let color = e.target.value
      this.pickColor = color
      // 현재 색상
      const currentColor = document.getElementById("current-color")
      currentColor.style = "background-color:" + `${color}`
      this.currentColor = color
      this.vueCanvas.strokeStyle = color
      this.vueCanvas.fillStyle = color
    },

    // 추가색상 추가
    addColor: function() {
      this.pickColorCnt += 1
      let pickColor = this.pickColor 
      let addColorBox = document.getElementById("add-color__box")
      let color = document.createElement("div")
      let closeBtn = document.createElement("button")
      color.classList.add('controls__color')
      color.classList.add('add-color')
      closeBtn.classList.add('close-btn')
      closeBtn.innerText = 'x'
      color.style = "background-color:" + `${pickColor}`
      addColorBox.appendChild(color)
      color.appendChild(closeBtn)
      color.addEventListener('click', this.handleColorClick)
      closeBtn.addEventListener('click', this.clickCloseBtn)
    },

    // 선 굵기
    handleRangeChange: function(e) {
      const strokeSize = e.target.value
      this.vueCanvas.lineWidth = strokeSize
      this.canvasLineWidth = strokeSize
    },

    // Fill / Paint Mode Btn
    handleStrokeClick: function() {
      this.filling = false
    },
    handlePaintClick: function() {
      this.filling = true
    },
    // 전체 지우기
    handleClearClick: function() {
      let canvas = document.getElementById("drawing-canvas");
      this.vueCanvas.clearRect(0, 0, canvas.width, canvas.height);
      this.vueCanvas.fillStyle = 'white'
      this.vueCanvas.fillRect(0, 0, canvas.width, canvas.height);
      document.getElementById("uploadBtn").disabled = true
    },
    // 채우기
    handleCanvasClick: function(e) {
      if (this.filling) {  // true(채우기) 일 때만 작동
        this.vueCanvas.fillRect(0, 0, e.target.width, e.target.height)
        let img = this.canvasFrame.toDataURL()
        if(this.painting === false) {
          this.history.undoList.push(img)
          document.getElementById("undoBtn").removeAttribute("disabled")
          document.getElementById("uploadBtn").removeAttribute("disabled")
        } 
      }
    },

    handleCM: function(e) {
      e.preventDefault()
    },

    // 로컬 & 로컬스토리지 저장
    handleUploadClick: function() {
      const img = this.canvasFrame.toDataURL('image/jpeg')  // jpg로 저장
      console.log(img)
      var image = {
        imagename: "QwertDrawing" + `${new Date()}`,
        imageSrc: img
      }
      this.$router.push({name: 'UploadPage', params: {imgSrc: image.imageSrc, imagename: image.imagename}})
    },
    // Undo
    handleUndoClick: function(){
      let undoListCnt = this.history.undoList.length
      if (undoListCnt >= 1) {
        this.history.redoList.push(this.history.undoList.pop())
        document.getElementById("redoBtn").removeAttribute("disabled")
        undoListCnt = this.history.undoList.length
        // 이전 캔버스 이미지
        if (undoListCnt == 0) {
          this.vueCanvas.clearRect(0, 0, 800, 600);
          document.getElementById("undoBtn").disabled = true
          document.getElementById("uploadBtn").disabled = true
        } else {
          let previousCanvas = this.history.undoList[undoListCnt - 1]
          let img = new Image()
          img.src = previousCanvas
          img.onload = () => {
            this.vueCanvas.drawImage(img, 0, 0)
          }
        }
      } else {
        this.vueCanvas.clearRect(0, 0, 800, 600);
        document.getElementById("undoBtn").disabled = true
        document.getElementById("uploadBtn").disabled = true
      }
    },
    handleRedoClick: function(){
      let redoListCnt = this.history.redoList.length
      if (redoListCnt > 0) {
        document.getElementById("undoBtn").removeAttribute("disabled")
        document.getElementById("uploadBtn").removeAttribute("disabled")
        redoListCnt = this.history.redoList.length
        // 이전 캔버스 이미지
        let previousCanvas = this.history.redoList[redoListCnt - 1]
        let img = new Image()
        img.src = previousCanvas
        img.onload = () => {
          this.vueCanvas.drawImage(img, 0, 0)
        }
        this.history.undoList.push(this.history.redoList.pop())
      } else {
        document.getElementById("redoBtn").disabled = true
      }
    },

    // 추가색상 삭제
    clickCloseBtn: function(e) {
      const closeBtn = e.target
      const color = closeBtn.parentNode
      const addColorBox = document.getElementById("add-color__box")
      addColorBox.removeChild(color)
    },

    // 추가색상 삭제 시 현재색 없어짐 방지
    maintainCurrent(currentColor) {
      this.currentColor = currentColor
      const currentColorDiv = document.getElementById("current-color")
      currentColorDiv.style.backgroundColor = currentColor
    },

  },
  mounted() {
  const canvas = document.getElementById("drawing-canvas")
  const ctx = canvas.getContext("2d")
  // var zctx = zoomed.getContext('2d')
  // const dpr = window.devicePixelRatio
  canvas.width = 800 
  canvas.height = 600 
  // ctx.scale(dpr, dpr)
  ctx.lineCap = "round"
  ctx.lineJoin = "round"
  ctx.imageSmoothingEnabled = false;
  // 배경화면 흰색
  ctx.fillStyle = "white"
  ctx.fillRect(0, 0, canvas.width, canvas.height)
  // default 색상 검정색
  ctx.strokeStyle = "black" 
  ctx.fillStyle = "black"
  ctx.lineWidth = 12.0
  ctx.filter = 'url(#remove-alpha)';
  this.canvasLineWidth = ctx.lineWidth
  this.vueCanvas = ctx
  this.canvasFrame = canvas

  if (this.$route.params.imgSrc) {
    let img = new Image()
      img.src = this.$route.params.imgSrc
      img.onload = () => {
        this.vueCanvas.drawImage(img, 0, 0)
    }
  }
  },

  watch: {
    // 현재색상 없어짐 방지
    currentColor: function() {
      if (this.currentColor === ""){
        this.maintainCurrent(this.vueCanvas.fillStyle)
      }
    },
  }
}
</script>

<style>

</style>