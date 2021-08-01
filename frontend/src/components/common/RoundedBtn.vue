<template>
  <v-col cols=3>
    <button class="rounded-btn">{{item}}</button>
  </v-col>
</template>

<script>
import "@/css/common/RoundedBtn.scss"

export default {
  name: "RoundedBtn",
  props: {
    item: {
      type: String
    }
  },
}
</script>

<style>

</style>