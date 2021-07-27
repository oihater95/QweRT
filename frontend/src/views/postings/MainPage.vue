<template>
  <v-container fluid>
    <div class="d-flex justify-center menu-tab">
      <div @click="clickMy">My</div>
      <div @click="clickPopular">인기</div>
      <div @click="clickNew">최신</div>
    </div>
     <v-row v-if="tab===1">
      <FeedImage
        v-for="(image, idx) in myImages" 
        :key="1-idx"
        :image="image"
      />
    </v-row>
    <v-row v-if="tab===2">
      <MainImage
        v-for="(image, idx) in myImages" 
        :key="2-idx"
        :image="image"
      />
    </v-row>
    <v-row v-if="tab===3">
      <MainImage
        v-for="(image, idx) in myImages" 
        :key="3-idx"
        :image="image"
      />
    </v-row>

  </v-container>
</template>

<script>
import "@/css/postings/MainPage.scss"
import FeedImage from "@/components/postings/FeedImage"
import MainImage from "@/components/postings/MainImage"

export default {
  name: "MainPage",
  components: {
    FeedImage,
    MainImage
  },
  data:  function () {
    return {
      tab: 1,
      // myImages: []
      myImages: [
        {
        posting_image: "http://weekly.chosun.com/up_fd/wc_news/2116/bimg_org/2116_74_01.jpg",
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
        posting_image: "https://i.pinimg.com/474x/d6/8a/c4/d68ac49173eff89c2c6f4ecb81389ba4.jpg",
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
        posting_image: "https://www.sciencetimes.co.kr/wp-content/uploads/2017/12/%EA%B3%A0%ED%9D%902.jpg",
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
        posting_image: "https://images.chosun.com/resizer/sSQl4eaMeNGMJUZzTvTiGpYX7T4=/464x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/XKDKYF6W3XNBOFWOXQRPUI4UQQ.jpg",
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
    getMyImages: function () {
      // myImages에 이미지 집어넣기
    },
    clickMy: function (e) {
      this.tab= 1
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
      e.target.nextSibling.nextSibling.style.color="black"
    },
    clickPopular: function (e) {
      this.tab= 2
      e.target.style.color="skyblue"
      e.target.previousSibling.style.color="black"
      e.target.nextSibling.style.color="black"

    },
    clickNew: function (e) {
      this.tab= 3
      e.target.style.color="skyblue"
      e.target.previousSibling.style.color="black"
      e.target.previousSibling.previousSibling.style.color="black"
    },
  },
  // 처음엔 피드 이미지
  created() {
    this.getMyImages()
  }, 
}
</script>

<style>

</style>