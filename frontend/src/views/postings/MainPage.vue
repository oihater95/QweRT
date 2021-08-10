<template>
  <v-container fluid>
    <div class="d-flex justify-center menu-tab">
      <div v-if="isLogon" @click="clickFeed">피드</div>
      <div @click="clickPopular">인기</div>
      <div @click="clickNew">최신</div>
    </div>
    <v-row 
    v-if="tab===1"
    
    >
    <!-- @scroll="scrollloadMore"
    v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="size" -->
      <FeedImage
        v-for="(image, idx) in feedImages" 
        :key="1-idx"
        :image="image"
      />
    </v-row>
    <v-row v-if="tab===2">
      <MainImage
        v-for="(image, idx) in popularImages" 
        :key="2-idx"
        :image="image"
      />
    </v-row>
    <v-row v-if="tab===3">
      <MainImage
        v-for="(image, idx) in newImages" 
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
import axios from 'axios'
import { mapState } from 'vuex'



export default {
  name: "MainPage",
  components: {
    FeedImage,
    MainImage
  },
  data:  function () {
    return {
      tab: 1,
      feedImages: [],
      feedDummyImages: [
        {
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample1.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample2.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample3.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample4.jpg",
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
        {
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample5.jpg",
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
        {
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample6.jpg",
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
      ],
      popularImages: [
        {
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample5.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample6.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample7.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample8.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample9.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample1.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample2.jpg",
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
      ],
      newImages: [
        {
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample3.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample4.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample1.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample2.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample5.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample6.jpg",
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
        posting_image: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/sample7.jpg",
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
      ],
      page: 0,
      size: 9,
      busy: false,
      loading: false,
      offsetTop: 0,
    }
  },
  methods: {
    getFeedImages: function () {
      // feedImages에 이미지 집어넣기
      axios.get(`${this.host}/postings/${this.userInfo.userId}/`, { params: { page: this.page, size: this.size } })
      .then(res => {
        const append = res.data.slice(this.feedImages.length, this.feedImages.length + this.size)
        this.feedImages = this.feedImages.concat(append)
      })
      .catch(err => {
        console.log(err)
      })

    },
    clickFeed: function (e) {
      this.tab= 1
      e.target.style.color="skyblue"
      e.target.nextSibling.style.color="black"
      e.target.nextSibling.nextSibling.style.color="black"
    },
    clickPopular: function (e) {
      if (this.isLogon) {
        e.target.previousSibling.style.color="black"
        e.target.nextSibling.style.color="black"
      } else {
        e.target.nextSibling.style.color="black"
      }
      this.tab= 2
      e.target.style.color="skyblue"
    },
    clickNew: function (e) {
      if (this.isLogon) {
        e.target.previousSibling.style.color="black"
        e.target.previousSibling.previousSibling.style.color="black"
      } else {
        e.target.previousSibling.style.color="black"
      }
      this.tab= 3
      e.target.style.color="skyblue"
    },

    // 윈도우 이벤트 리스너
    scrollloadMore() {
        var clientHeight = document.documentElement.clientHeight
        var scrollTop = document.documentElement.scrollTop
        var scrollHeight = document.documentElement.scrollHeight
        console.log(clientHeight, scrollTop, scrollHeight)
        if(clientHeight+scrollTop+20>=scrollHeight){
            this.page++;
            this.getFeedImages()
        }
      
		},

    loadMore() {
      console.log("scrolling");
      this.busy = true;   
      axios.get(`${this.host}/postings/${this.userInfo.userId}/`, { params: { page: this.page, size: this.size } })
      .then(res => {
        const append = res.data.slice(this.feedImages.length, this.feedImages.length + this.size)
        this.feedImages = this.feedImages.concat(append)
        this.page = this.page + 1
      })
      .catch(err => {
        console.log(err)
        this.busy = false;
      })
    },


  },
  computed: {
    ...mapState([
      'host',
      'isLogon',
      'userInfo'
    ]),
  },
  created() {
    // this.loadMore()
    this.getFeedImages()
    window.addEventListener('scroll', this.scrollloadMore, true)
  },


}
</script>

<style>

</style>
