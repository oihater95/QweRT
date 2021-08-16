<template>
  <v-col cols=3>
    <button class="rounded-btn" @click="clickBtn">{{item}}</button>
  </v-col>
</template>

<script>
import "@/css/common/RoundedBtn.scss"

export default {
  name: "RoundedBtn",
  props: {
    item: {
      type: String
    },
  },
  methods:  {
    clickBtn: function () {
      this.$emit("clickCategory", this.item)
    },
  },
}
</script>

<style>

</style>