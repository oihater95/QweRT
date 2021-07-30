<template>
  <div id="drawing-body">
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
          min="0.1" 
          max="8.0"
          value="4.0"
          step="0.1"
          @input="handleRangeChange">
        </div>
        <div class="controls__btns">
          <button id="ModeBtn" @click="handleModeClick">Fill</button>
          <button id="saveBtn" @click="handleSaveClick">Save</button>
        </div>
        <div id="canvas-colors" class="controls__colors">
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
      painting: false,
      vueCanvas: null,
      canvasFrame: null,
      brushColor: null,
      filling: false,  // default는 그리기 (true => 채우기)
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
        this.vueCanvas.moveTo(this.x, this.y)
      } else {
        // 마우스 시작 위치에서 펜 위치까지 선 긋기
        // mouseup stop painting
        this.vueCanvas.lineTo(this.x, this.y)
        this.vueCanvas.stroke()
      }
    },
    // 색상 선택
    handleColorClick: function(e) {
      const selectedColor = e.target.style.backgroundColor
      this.vueCanvas.strokeStyle = selectedColor
      this.vueCanvas.fillStyle = selectedColor
    },

    // 선 굵기
    handleRangeChange: function(e) {
      const strokeSize = e.target.value
      this.vueCanvas.lineWidth = strokeSize
    },

    // Fill / Paint Mode Btn
    handleModeClick: function(e) {
      if(this.filling === true) {
        this.filling = false
        e.target.innerText = "Fill"
      } else {
        this.filling = true
        e.target.innerText = "Paint"
        
      }
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
    }
  },
  mounted() {
  const canvas = document.getElementById("drawing-canvas")
  const ctx = canvas.getContext("2d")
  const colors = document.getElementsByClassName("controls__color")
  const DEFAULT_COLOR = "000000"  // black

  canvas.width = 700
  canvas.height = 700
  // 배경화면 흰색
  ctx.fillStyle = "white"
  ctx.fillRect(0, 0, canvas.width, canvas.height)

  // default 색상 검정색
  ctx.strokeStyle = DEFAULT_COLOR  
  ctx.fillStyle = DEFAULT_COLOR 
  ctx.lineWidth = 3.0
  this.vueCanvas = ctx
  this.brushColor = colors
  this.canvasFrame = canvas
},
}
</script>

<style>

</style>