<template>
  <div class="select-color__div">
    <!-- # 누르면 랜덤한 색깔 추천 -->
    <div class="channel hash" @click="randomColor">
      <div class="digits">
        <div class="digit current"><span>#</span></div>
      </div>
    </div>
    <!-- channels는 6개, #을 제외하고 ff ff ff -->
    <!-- 각 channel에 해당되는 글자를 준다. -->
    <!-- 마우스를 움직여서 변화가 생기면 @colorize를 통해 알아챈다.-->
    <NumberColumn
      :channel="color[n]"
      :index="n"
      :key="2-idx"
      v-for="(n, idx) in channels"
      @colorize="updateColorChannel"
    />
    <h3 class="select-color__name">
      {{colorName}}
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
        // 화면 표시용
        this.colorName = ntc.name(color)[1]
        // ??
        document.documentElement.style.setProperty('--color', color)
        this.$emit('changeColor', this.color)
      },
      // 글자를 움직임에 따라서 색 업데이트 용도
      updateColorChannel: function(index, digit){
        // #과 6글자가 하나씩 분리됨
        let mutateColor = this.color.split('')
        // index 위치에 있는 것을 digit으로 교체한다. 
        mutateColor.splice(index, 1, digit)
        // 합쳐서 넣기
        this.color = mutateColor.join('')
        this.setColor(this.color)
      },
      // 랜덤하게 색을 골라준다.
      randomColor: function(){
        this.color = '#' + ( '00' + Math.floor( Math.random() * 16777216 ).toString(16) ).substr(-6)
        this.setColor(this.color)
      }
    },
    mounted: function() {
      ntc.init()
      this.randomColor()
    },
}
</script>

<style>

</style>