<template>
  <v-form>
    <v-container>
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
      <div class="new-thumbnail__div">
        <div>선택사항: 큐레이션 썸네일</div>
        <div>큐레이션 썸네일을 업로드하지 않으면 큐레이션에 제일 먼저 담긴 세 작품이 썸네일로 보입니다.</div>
        <div class="d-flex new-thumbnail_last">
            <dragNdrop class="new-curation__component" />
      </div>
      <button class="curation-make__btn">CREATE</button>
      </div>

        

        
    </v-container>
  </v-form>
</template>

<script>
import "@/css/curations/NewCuration.scss"
import dragNdrop from "@/components/common/dragNdrop"

export default {
    name: "NewCuration",
    components: {
        dragNdrop
    },
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