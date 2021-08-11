<template>
  <v-container
    class="container"
    fluid>
    <div class="d-flex justify-center curation-menu__tab">
      <div @click="clickMy">My</div>
      <div @click="clickNew">최신</div>
    </div>
    <v-row v-if="tab===1">
      <!-- tab을 통해서 my curation을 식별하고 수정버튼을 만들겠다. -->
      <CurationImage
        v-for="(curation, idx) in myCurationImages" 
        :key="1-idx"
        :curation="curation"
        :tab="tab"
      />
    </v-row>
    <v-row v-if="tab===2">
      <CurationImage
        v-for="(curation, idx) in myCurationImages" 
        :key="2-idx"
        :curation="curation"
        :tab="tab"
      />
    </v-row>
  </v-container>
</template>

<script>
import "@/css/curations/CurationPage.scss"
import CurationImage from "@/components/curations/CurationImage"

export default {
  name: "CurationPage",
  components: {
    CurationImage,
  },
  data:  function () {
    return {
      tab: 1,
      // myCurationImages: []
      myCurationImages: [
        {
        user_name : "연필사랑",
        id : 11,
        title : "흑백그림전",
        content : "그 어떤 작품보다 화려한 흑백그림 모음 큐레이션",
        thumbnail : "",
        images: ["https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample11.jpg",
                "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample5.jpg",
                "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample14.jpg",
        ],
        created_date : "2021-07-27",
        },
        {
          user_name : "피카소덕후",
          id : 22,
          title : "야옹이展",
          content : "귀여운 야옹이 작품들 모아봤습니다. 힐링하고 가세요.",
          thumbnail : "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/thumb1.jpg",
          images: ["http://weekly.chosun.com/up_fd/wc_news/2116/bimg_org/2116_74_01.jpg",
                  "https://i.pinimg.com/474x/d6/8a/c4/d68ac49173eff89c2c6f4ecb81389ba4.jpg",
                  "https://images.chosun.com/resizer/sSQl4eaMeNGMJUZzTvTiGpYX7T4=/464x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/XKDKYF6W3XNBOFWOXQRPUI4UQQ.jpg",
          ],
          created_date : "2021-04-25",
        },
        {
        user_name : "퀄트초기유저",
        id : 22,
        title : "봄을 담은 그림들",
        content : "저는 봄을 참 좋아합니다. 봄이 지나가서 너무 아쉬운 마음에 봄을 담은 그림들로 큐레이션을 만들어봤습니다. 재밌게 봐주시면 좋겠습니다.",
        thumbnail : "",
        images: ["https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample4.jpg",
                "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample9.jpg",
                "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample10.jpg",
        ],
        created_date : "2021-03-02",
        },
      ]
    }
  },
  methods: {
    getMyCurationImages: function () {
      // myCurationImages에 이미지 집어넣기
    },
    clickMy: function (e) {
      this.tab= 1
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
    },
    clickNew: function (e) {
      this.tab= 2
      e.target.style.color="skyblue"
      e.target.previousSibling.style.color="black"
    },
  },
  // 처음엔 내 큐레이션 이미지
  created() {
    this.getMyCurationImages()
  }, 
}
</script>

<style>

</style>