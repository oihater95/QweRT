<template>
  <v-row>
    <v-col
      cols="5"
      class="d-flex"
    >
      <span class="new-curation__span">큐레이션 이름</span>
      <v-text-field
        v-model="title"
        :rules="titleRule"
        :counter="128"
        clearable
        required
      >
      </v-text-field>
    </v-col>
    <v-col
      cols="8"
      class="d-flex"
    >
      <span class="new-curation__span">큐레이션 설명</span>
      <v-textarea
        name="input-7-1"
        v-model="description"
        :rules="descriptionRule"
        :counter="1000"
        clearable
        required
      >
      </v-textarea>
    </v-col>
  </v-row>
</template>

<script>
import "@/css/curations/CurationInfo.scss"

export default {
  name: "NewCuration",
  data:  function () {
    return {
      title: '',
      description: '',
      titleRule: [
        v => !!v || '큐레이션 이름을 적어주세요',
      ],
      descriptionRule: [
        v => !!v || '큐레이션 설명을 적어주세요',
      ],
    }
  },
}
</script>

<style>

</style>