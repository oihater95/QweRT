<template>
  <v-container>
    <v-row>
      <v-col><DragDrop imgSrc="imgSrc"/></v-col>
      <v-col>
        <div class="container">
          <v-card class="my-5 posting-title">
            <v-text-field
              label="Title"
              :rules="rules"
              hide-details="auto"
            ></v-text-field>
          </v-card>
          <v-card class="my-5 posting-content">
            <v-textarea
            >
              <template v-slot:label>
                <div>
                  content <small></small>
                </div>
              </template>
            </v-textarea>
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
  },
  props: {
    imgSrc: {
      type: String,
    }
  }
}
</script>

<style>

</style>