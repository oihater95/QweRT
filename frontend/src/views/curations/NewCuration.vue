<template>
  <v-form>
    <v-container>
      <CurationInfo/>
      <div class="new-thumbnail__div">
        <div>선택사항: 큐레이션 썸네일</div>
        <div>큐레이션 썸네일을 업로드하지 않으면 큐레이션에 제일 먼저 담긴 세 작품이 썸네일로 보입니다.</div>
        <div class="d-flex new-thumbnail_last">
            <DragDrop class="new-curation__component"/>
        </div>
        <div class="new-curation__color">
          <input type="checkbox" id="checkbox" v-model="checked">
          <label for="checkbox">배경색 고르기</label>
          <div v-if="checked">
            <SelectColor/>
          </div>
        </div>
        <button class="curation-make__btn">CREATE</button>
      </div>
    </v-container>
  </v-form>
</template>

<script>
import "@/css/curations/NewCuration.scss"
import CurationInfo from "@/components/curations/CurationInfo"
import DragDrop from "@/components/common/DragDrop"
import SelectColor from "@/components/curations/SelectColor"

export default {
  name: "NewCuration",
  components: {
    CurationInfo,
    DragDrop,
    SelectColor
  },
  data: function () {
    return {
     checked: false
    }
  },
}
</script>

<style>

</style>