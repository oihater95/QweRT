<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="400"
    >
      <!-- 트리거 버튼 -->
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          id="modalBtn"
          color="red"
          dark
          v-bind="attrs"
          v-on="on"
        >
          {{ msg.triggerBtn }}
        </v-btn>
      </template>
      <v-card>
        <!-- 제목 -->
        <v-card-title class="text-h5">
          <v-spacer></v-spacer>
          {{ msg.title }}
          <v-spacer></v-spacer>
        </v-card-title>
        <!-- 내용 -->
        <v-card-text class="text-center">
          <p v-html="msg.text"></p>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <!-- 확인 버튼 -->
          <v-btn
            v-if="msg.positiveBtn"
            color="orange darken-2"
            text
            @click="[dialog = false, $emit(`${msg.name}-ok-sign`)]"
          >
            {{ msg.positiveBtn }}
          </v-btn>
          <!-- 취소 버튼 -->
          <v-btn
            v-if="msg.negativeBtn"
            color="green darken-3"
            text
            @click="dialog = false"
          >
            {{ msg.negativeBtn }}
          </v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  export default {
    name: 'Modal',
    props: {
      msg: {
        type: Object
      }
    },
    data: function () {
      return {
        dialog: false,
      }
    },
  }
</script>

<style>

</style>
