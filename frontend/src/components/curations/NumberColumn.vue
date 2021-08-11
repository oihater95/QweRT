<template>
  <div class="channel" :class="{moving: moving}">
    <div class="digits"
      @mousedown="start" 
      @mousemove="move"
      @mouseup="stop"
      @touchstart="start" 
      @touchmove="move" 
      @touchend="stop"
    >
      <div class="digit" :class="{current: digit == newDigit}" :key="1-idx" v-for="(digit, idx) in digits">
          <span>{{digit}}</span>
      </div>
    </div>
  </div>
</template>

<script>
import "@/css/curations/NumberColumn.scss"

export default {
  name: "NumberColumn",
  props: {
    index: {
      type: [String, Number]
    },
    channel: {
        type: [String, Number]
    }
  },
  data: function(){
    return {
      moving: false,
      y: 0,
      newDigit: 0
    }
  },
  methods: {
    sameAsMove: function(){
      this.y = parseInt(this.channel, 16)
      this.getHeight()
      this.processly(this.y * -this.height)
    },
    getHeight: function(){
      this.height = this.$el.getBoundingClientRect().height
    },
    getPointerly: function(e){
      return e.touches && e.touches.length ? e.touches[0].clientY : e.clientY
    },
    processly: function(value){
      if(isNaN(value)) {
        value = 0
      }
      let stack = (this.height * (this.digits.length-1) * -1)
      this.y = value > 0 ? 0 : value < stack ? stack : value
      this.newDigit = parseInt(Math.abs(Math.round(this.y/this.height))).toString(16)
      this.$el.style.setProperty('--y', this.y + 'px')
      this.$emit('colorize', this.index, this.newDigit)
    },
    start: function(e){
      this.moving = true
      this.dirty = true
      this.getHeight()
      this.pointerly = this.getPointerly(e) - this.y
    },
    move: function(e){
      if(this.moving){
        this.processly(Math.round(this.getPointerly(e) - this.pointerly))
      }
    },
    stop: function(e){
      this.moving = false
      this.processly(Math.round((this.getPointerly(e) - this.pointerly) / this.height) * this.height)
    }
  },
  created: function(){
    this.digits = '0123456789abcdef'.split('')
    window.addEventListener('resize', this.sameAsMove);
  },
  destroyed: function() {
    window.removeEventListener('resize', this.sameAsMove);
  },
  mounted: function(){
    this.sameAsMove()
  },
  watch: {
    channel: 'sameAsMove'
  },
}
</script>

<style>

</style>