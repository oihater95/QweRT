<template>
  <v-container fluid>
    <div class="d-flex justify-center menu-tab">
      <div @click="clickMy">My</div>
      <div @click="clickNew">최신</div>
    </div>
     <v-row v-if="tab===1">
      
    </v-row>
    <v-row v-if="tab===2">
      
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "CurationPage",
}
</script>

<style>

</style>