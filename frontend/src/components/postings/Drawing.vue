<template>
  <div id="drawing-body" class="container">
    <svg width="0" height="0" style="position:absolute;z-index:-1;">
      <defs>
        <filter id="remove-alpha" x="0" y="0" width="100%" height="100%">
          <feComponentTransfer>
            <feFuncA type="discrete" tableValues="0 1"></feFuncA>
          </feComponentTransfer>
          </filter>
      </defs>
    </svg>
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
          <button id="saveBtn" @click="handleSaveClick">Save</button>
          <button id="clearBtn" @click="handleClearClick">Clear</button>
          </v-row>

          <v-row v-else>
          <button id="StrokeBtn" @click="handleStrokeClick" >Stroke</button>
          <button 
            id="PaintBtn" 
            @click="handlePaintClick" 
            style="border-color: skyblue; border-width: 3px">
            Paint
          </button>
          <button id="clearBtn" @click="handleClearClick">Clear</button>
          <button id="saveBtn" @click="handleSaveClick">Save</button>
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
          
          <div v-show="pickColorCnt > 0" id="add-color__box" class="control-color__box">
          </div>
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
      brushColor: null,
      filling: false,  // default는 그리기 (true => 채우기)
      pickColor: null,
      pickColorCnt: 0,
    }
  },
  methods: {
    // 마우스 움직이는 내내 발생
    onMouseMove: function(e) {
      this.x = e.offsetX
      this.y = e.offsetY
      
      if(!this.painting) {
        // mousedown start painting
        // mousemove => 펜 위치 움직임
        // beginpath는 클릭 전까지 마우스가 캔버스 위에 있으면 따라다님
        this.vueCanvas.beginPath()
        // 펜 위치 설정
        this.vueCanvas.moveTo(e.offsetX, e.offsetY)
      } else {
        // 마우스 시작 위치에서 펜 위치까지 선 긋기
        // mouseup stop painting
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
      this.vueCanvas.strokeStyle = selectedColor
      this.vueCanvas.fillStyle = selectedColor
    },

    // custom 색상 선택
    selectColorChange: function(e) {
      var color = e.target.value
      this.pickColor = color
      const currentColor = document.getElementById("current-color")
      // 현재 색상
      currentColor.style = "background-color:" + `${color}`
      this.vueCanvas.strokeStyle = color
      this.vueCanvas.fillStyle = color
    },

    addColor: function() {
      this.pickColorCnt += 1
      var pickColor = this.pickColor 
      var addColorBox = document.getElementById("add-color__box")
      var color = document.createElement("div")
      color.classList.add('controls__color')
      color.classList.add('add-color')
      color.style = "background-color:" + `${pickColor}`
      addColorBox.appendChild(color)
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
      var canvas = document.getElementById("drawing-canvas");
      this.vueCanvas.clearRect(0, 0, canvas.width, canvas.height);
    },

    handleCanvasClick: function(e) {
      if (this.filling) {  // true(채우기) 일 때만 작동
        this.vueCanvas.fillRect(0, 0, e.target.width, e.target.height)
      }
    },

    handleCM: function(e) {
      e.preventDefault()
    },

    handleSaveClick: function() {
      const img = this.canvasFrame.toDataURL()
      const link = document.createElement("a")
      link.href = img

      // a 태그에 다운로드를 붙이면 브라우저가 링크로 이동하지 않고 로컬에 저장함
      link.download = "QwertDrawing" + `${new Date()}`  
      link.click()  // 링크 클릭
      var image = {
        filename: "QwertDrawing" + `${new Date()}`,
        imageSrc: img
      }
      localStorage.setItem(image.filename, image.imageSrc)
    },


    stopPainting: function() {
      this.painting = false
    },
    startPainting: function() {
      this.painting = true
    },
  },
  mounted() {
  const canvas = document.getElementById("drawing-canvas")
  const ctx = canvas.getContext("2d")
  const colors = document.getElementsByClassName("controls__color")
  // var zctx = zoomed.getContext('2d')
  // const dpr = window.devicePixelRatio
  canvas.width = 700 
  canvas.height = 700 
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
  this.brushColor = colors
  this.canvasFrame = canvas

},
}
</script>

<style>

</style>