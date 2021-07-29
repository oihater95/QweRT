<template>
  <div id="drawing-body">
    <canvas 
    id="drawing-canvas" 
    class="canvas"
    @mousemove="onMouseMove"
    @mousedown="startPainting"
    @mouseup="stopPainting"
    @mouseleave="stopPainting"></canvas>
      <div class="controls">
        <div class="controls__range">
          <input 
          type="range" 
          id="controls-range" 
          min="0.1" 
          max="5.0"
          value="2.5"
          step="0.1">
        </div>
        <div class="controls__btns">
          <button id="fillBtn">Fill</button>
          <button id="saveBtn">Save</button>
        </div>
        <div id="canvas-colors" class="controls__colors">
          <div class="controls__color" style="background-color:black"></div>
          <div class="controls__color" style="background-color:white"></div>
          <div class="controls__color" style="background-color:#FF3B30"></div>
          <div class="controls__color" style="background-color:#FF9500"></div>
          <div class="controls__color" style="background-color:#FFCC00"></div>
          <div class="controls__color" style="background-color:#4CD963"></div>
          <div class="controls__color" style="background-color:#5AC8FA"></div>
          <div class="controls__color" style="background-color:#0579FF"></div>
          <div class="controls__color" style="background-color:#5856D6"></div>
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
    }
  },
  methods: {
    onMouseMove: function(e) {
      this.x = e.offsetX
      this.y = e.offsetY
      if(!this.painting) {
        this.vueCanvas.beginPath()
        this.vueCanvas.moveTo(this.x, this.y)
      } else {
        this.vueCanvas.lineTo(this.x, this.y)
        this.vueCanvas.stroke()
      }
    },

    stopPainting: function() {
      this.painting = false
    },
    startPainting: function() {
      this.painting = true
    }
  },
  mounted() {
  var canvas = document.getElementById("drawing-canvas")
  var ctx = canvas.getContext("2d")
  canvas.width = 700
  canvas.height = 700
  ctx.strokeStyle = "#2C2C2C"   
  ctx.lineWidth = 2.5;
  this.vueCanvas = ctx;
},
}
</script>

<style>

</style>