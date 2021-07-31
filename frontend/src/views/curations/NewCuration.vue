<template>
  <v-form>
    <v-container>
      <CurationInfo/>
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
import CurationInfo from "@/components/curations/CurationInfo"
import dragNdrop from "@/components/common/dragNdrop"

export default {
    name: "NewCuration",
    components: {
      CurationInfo,
        dragNdrop,
    },
}
</script>

<style>

</style>