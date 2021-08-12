<template>
  <v-row>
    <v-col
      cols="5"
      class="d-flex"
    >
      <span class="new-curation__span">큐레이션 제목</span>
      <v-text-field
        v-model="title"
        :rules="titleRule"
        :counter="30"
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
        :counter="100"
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
        v => !!v || '큐레이션 제목을 적어주세요',
      ],
      descriptionRule: [
        v => !!v || '큐레이션 설명을 적어주세요',
      ],
    }
  },
  methods: {
    // title과 description이 바뀔 때마다 알려준다.
    changeHappen: function() {
      this.$emit('change', this.title, this.description)
      console.log("change")
    },

  },
  watch: {
    title: 'changeHappen',
    description: 'changeHappen',
  }
}
</script>

<style>

</style>