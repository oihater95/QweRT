<template>
  <v-row>
    <v-col cols="12" v-for="i in tab.contents" :key="i">
      <v-img :src="`https://picsum.photos/500/300?image=${i * 5 + 10}`"></v-img>
      <h1>{{tab.page}}</h1>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'MyFavoritePostings',
  props: {
    tab: {
      type: Object
    }
  }
}
</script>

<style>

</style>