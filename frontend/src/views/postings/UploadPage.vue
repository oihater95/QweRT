<template>
  <v-container>
    <v-row>
      <v-col><dragNdrop/></v-col>
      <v-col>
        <div class="container">
          <v-card class="my-5 posting-title">
            <v-text-field
              label="제목"
              :rules="rules"
              hide-details="auto"
            ></v-text-field>
          </v-card>
          <v-card class="my-5 posting-content">
            <v-text-field label="내용"></v-text-field>
          </v-card>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import dragNdrop from "@/components/common/dragNdrop"
import "@/css/postings/UploadPage.scss"

export default {
  name: 'UploadPage',
  components: {
    dragNdrop,
  }
}
</script>

<style>

</style>