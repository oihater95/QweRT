<template>
  <v-row
    justify="center"
    class="profile-showMore"
  >
    <v-col cols="12"></v-col>
    <v-col cols="12"></v-col>
    <v-col cols="11">
      <v-row justify="space-around">
        <v-col
          :cols="tabOnView === 1 ? 4 : 3"
          :class="['tabs', {'tab-onView' : tabOnView === 1}]"
          @click="tabOnView=1"
        >
          <h3>게시물</h3>
        </v-col>
        <v-col
          :cols="tabOnView === 2 ? 4 : 3"
          :class="['tabs', {'tab-onView' : tabOnView === 2}]"
          @click="tabOnView=2"
        >
          <h3>좋아하는 게시물</h3>
        </v-col>
        <v-col
          :cols="tabOnView === 3 ? 4 : 3"
          :class="['tabs', {'tab-onView' : tabOnView === 3}]"
          @click="tabOnView=3"
        >
          <h3>큐레이션</h3>
        </v-col>
      </v-row>
      <MyPostings v-if="tabOnView === 1" class="content"/>
      <MyFavoritePostings v-if="tabOnView === 2" class="content"/>
      <MyCurations v-if="tabOnView === 3" class="content"/>
    </v-col>
  </v-row>
</template>

<script>
import MyPostings from '@/components/profiles/MyPostings'
import MyFavoritePostings from '@/components/profiles/MyFavoritePostings'
import MyCurations from '@/components/profiles/MyCurations'
import '@/css/profiles/ProfileShowMore.scss'

export default {
  name: 'ProfileShowMore',
  components: {
    MyPostings,
    MyFavoritePostings,
    MyCurations,
  },
  data: function () {
    return {
      tabOnView: 1,
    }
  }
}
</script>

<style>

</style>