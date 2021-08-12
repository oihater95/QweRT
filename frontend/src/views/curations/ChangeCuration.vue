<template>
  <div>
    <v-form>
      <v-container>
        <div class="d-flex flex-row-reverse">
          <span class="curation-delete__btn">DELETE</span>
          <span class="curation-change__btn">SAVE</span>
        </div>
        <CurationInfo/>
        <div class="new-thumbnail__div">
          <div>큐레이션 썸네일</div>
          <div class="d-flex new-thumbnail_last">
            <DragDrop class="new-curation__component" />
          </div>
          <div class="new-curation__color">
          <input type="checkbox" id="checkbox" v-model="checked">
          <label for="checkbox" class="new-curation__label">배경색 고르기</label>
          <div v-if="checked">
            <SelectColor/>
          </div>
        </div>
        </div>
      </v-container>
    </v-form>
    <div class="change-curation__div">전시작품 목록</div>
    <br>    
    <br>    
    <v-row>
      <MainImage
        v-for="(image, idx) in curationImages" 
        :key="1-idx"
        :image="image"
      />
    </v-row>
  </div>
</template>

<script>
import "@/css/curations/ChangeCuration.scss"
import CurationInfo from "@/components/curations/CurationInfo"
import DragDrop from "@/components/common/DragDrop"
import MainImage from "@/components/postings/MainImage"
import SelectColor from "@/components/curations/SelectColor"


export default {
  name: "ChangeCuration",
  components: {
    CurationInfo,
    DragDrop,
    SelectColor,
    MainImage,
  },
  data:  function () {
    return {
     checked: false,
      curationImages: [
        {
        postingImg: "sample2.jpg",
        profile_image: "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        nickname: "호랑이1", 
        title: "고흐의 해바라기",
        comment_cnt: "11",
        like_state: "true",
        liked_cnt: "1",
        curated_cnt: "1",
        create_date: "1111처음",
        update_date: "11111수정",
        },
        {
        postingImg: "sample3.jpg",
        profile_image: "https://imgnews.pstatic.net/image/293/2021/07/27/0000035724_001_20210727102309284.jpg?type=w647",
        nickname: "닉네임2",
        title: "고흐의 자화상",
        comment_cnt: "2",
        like_state: "1",
        liked_cnt: "2",
        curated_cnt: "22",
        create_date: "2222처음",
        update_date: "2222수정",
        },
        {
        postingImg: "sample4.jpg",
        profile_image: "https://imgnews.pstatic.net/image/293/2021/07/27/0000035728_001_20210727122509659.jpg?type=w647",
        nickname: "닉네임3",
        title: "작품1",
        comment_cnt: "3",
        like_state: "",
        liked_cnt: "3",
        curated_cnt: "3",
        create_date: "33333처음",
        update_date: "33333수정",
        },
        {
        postingImg: "sample5.jpg",
        profile_image: "https://im-media.voltron.voanews.com/Drupal/01live-211/styles/892x501/s3/2019-08/C479B173-9839-43CA-B441-0735785B95C3.png?itok=rshkbR3A",
        nickname: "호랑이4",
        title: "고흐1",
        comment_cnt: "4",
        like_state: "4",
        liked_cnt: "1",
        curated_cnt: "4",
        create_date: "4444처음",
        update_date: "",
        },
      ]
    }
  },
  methods: {
    // 삭제 버튼에 마우스 올리면 하늘색 테두리
    deleteHoverOn: function (e) {
        e.target.nextSibling.classList.add("hover-for__delete")
    },
    // 하늘색 테두리 없애기
    deleteHoverOff: function (e) {
      e.target.nextSibling.classList.remove("hover-for__delete")
    },
    deleteImage: function (e) {
      const target = e.target.parentNode.parentNode
      target.remove()
    },
  },
  mounted: function () {
    const cards = document.querySelectorAll(".v-card__main")  
      for (let card of cards) {
          const btnForDelete = document.createElement('button')
          btnForDelete.classList.add("btn-for__delete")
          btnForDelete.innerText = "DELETE"
          card.prepend(btnForDelete)
          btnForDelete.addEventListener("mouseover", this.deleteHoverOn)
          btnForDelete.addEventListener("mouseout", this.deleteHoverOff)
          btnForDelete.addEventListener("click", this.deleteImage)
      }
  }
}
</script>

<style>

</style>