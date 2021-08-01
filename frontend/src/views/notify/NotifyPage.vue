<template>
  <v-container>
    <h1 class="notify-title">알림함</h1>
    <v-select
      class="notify-dropdown"
      :items="items"
      label="미확인 알림"
      solo
    ></v-select>
    <div class="d-flex notify-content__div">
      <h3 class="notify-content">5개의 미확인 알림이 있습니다.</h3>
      <v-icon class="notify-content__icon">far fa-square</v-icon>
    </div>
    <v-card
      class="notify-card"
      elevation="2"
    >
      <v-list 
        id="scroll-target"
        class="col overflow-y-auto scroll-y"
        max-height="400">
          <template
            v-for="(notification, idx) in notifications">
            <v-list-item-content 
            :key="1-idx">
              <div class="d-flex">
                <p class="notify-content__p">{{ notification.notify_content }}</p>
                <!-- far fa-check-square이랑 토글하기 -->
                <!-- 누르면 체크되고 몇 초후에 삭제 -->
                <v-icon class="notify-square__icon">far fa-square</v-icon>
              </div>
                <p class="notify-time__p">{{ notification.create_date }}</p>
                <br>
                <v-divider></v-divider>
            </v-list-item-content>
          </template>
        </v-list>
    </v-card>
  </v-container>
</template>

<script>
import "@/css/notify/NotifyPage.scss"

export default {
  name: "NotifyPage",
  data:  function () {
    return {
      items: ["미확인 알림","모든 알림","팔로우", "좋아요", "관리자"],
      notifications: [
        {
          notify_id : 1,
          notify_type : 1,
          notify_content : '그림사랑님이 회원님을 팔로우했습니다.',
          create_date : "15분 전"
        },
        {
          notify_id : 2,
          notify_type : 2,
          notify_content : '그림사랑님이 회원님의 게시글에 좋아요를 눌렀습니다.',
          create_date : "3시간 전"
        },
        {
          notify_id : 3,
          notify_type : 3,
          notify_content : '그림사랑님이 회원님의 게시글을 큐레이션에 담았습니다.',
          create_date : "1주 전"
        },
        {
          notify_id : 4,
          notify_type : 4,
          notify_content : '그림사랑님이 회원님의 게시글을 큐레이션에 담았습니다.',
          create_date : "1주 전"
        },
        {
          notify_id : 5,
          notify_type : 5,
          notify_content : '그림사랑님이 회원님의 게시글을 큐레이션에 담았습니다.',
          create_date : "3주 전"
        },
      ]
    }
  },
}
</script>

<style>

</style>