<template>
  <v-app>
    <Navbar/>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import '@/css/App.scss'
import Navbar from '@/components/common/Navbar'

export default {
  name: 'App',
  components: {
    Navbar
  },
  data: () => ({
    //
  }),
};
</script>