<template>
  <div class="select-color__div">
    <div class="channel hash" @click="randomColor">
      <div class="digits">
        <div class="digit current"><span>#</span></div>
      </div>
    </div>
    <NumberColumn
      :channel="color[n]"
      :index="n"
      :key="2-idx"
      v-for="(n, idx) in channels"
      @colorize="updateColorChannel"
    />
    <h3 class="select-color__name">
      {{colorName}}<br />
      <h4>{{color}}</h4>
    </h3>
  </div>
</template>

<script>
import NumberColumn from '@/components/curations/NumberColumn'
import "@/css/curations/SelectColor.scss"
import ntc from '@/assets/js/ntc.js'

const _digits = '0123456789abcdef'.split('')

export default {
    name: "SelectColor",
    components: {
      NumberColumn
    },
    data : function () {
      return {
        digits: _digits,
        channels: 6,
        color: '',
        colorName: ''
      }
    },
    methods: {
      setColor: function(color){
        ntc.init();
        this.colorName = ntc.name(color)[1]
        document.documentElement.style.setProperty('--color', color)
      },
      updateColorChannel: function(index, digit){
        let mutateColor = this.color.split('')
        mutateColor.splice(index, 1, digit)
        this.color = mutateColor.join('')
        this.setColor(this.color)
      },
      randomColor: function(){
        this.color = '#' + ( '00' + Math.floor( Math.random() * 16777216 ).toString(16) ).substr(-6)
        this.setColor(this.color)
        console.log(this.color)
      }
    },
    mounted: function() {
      this.randomColor()
    },
}
</script>

<style>

</style>