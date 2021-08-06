<template>
  <v-container>
    <v-row>
      <v-col><DragDrop/></v-col>
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
import DragDrop from "@/components/common/DragDrop"
import "@/css/postings/UploadPage.scss"

export default {
  name: 'UploadPage',
  components: {
    DragDrop,
  }
}
</script>

<style>

</style>