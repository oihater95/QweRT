<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="650"
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
        <!-- 큐레이션에 그림 넣을 때 체크박스를 넣어야 해서 분기했습니다. -->
        <v-card-text v-if="msg.name==='addCuration'" class="text-body-1 font-weight-medium ms-3">
          <div
            v-for="(curation, idx) in msg.text"
            :key="idx"
          >
            <input type="checkbox" :id="curation.curationId" class="checkbox">
            <label :for="curation.curationId" class="ml-3">
              {{curation.title}}
            </label>
          </div>
        </v-card-text>
        <!-- 내용 -->
        <v-card-text v-else class="text-center text-body-1">
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
            @click="clickPositiveBtn"
          >
            {{ msg.positiveBtn }}
          </v-btn>
          <!-- 취소 버튼 -->
          <v-btn
            v-if="msg.negativeBtn"
            color="green darken-3"
            text
            @click="clickNegativeBtn"
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
        checkArray: [],
      }
    },
    methods: {
      clickPositiveBtn: function () {
        this.dialog = false
        
        // 체크박스 초기화하기
        if (this.msg.name === 'addCuration') {
          const checkboxes = document.querySelectorAll(".checkbox")
          for (const checkbox of checkboxes) {
            if (checkbox.checked) {
              checkbox.checked = false
            }
          }
          this.checkArray = []
        } else {
          this.$emit(`${this.msg.name}-ok-sign`)
        }
      },
      clickNegativeBtn: function () {
        this.dialog = false
        if (this.msg.name === 'addCuration') {
          const checkboxes = document.querySelectorAll(".checkbox")
          for (const checkbox of checkboxes) {
            if (checkbox.checked) {
              this.checkArray.push(checkbox.id)
              checkbox.checked = false
            }
          }
          this.$emit(`${this.msg.name}-no-sign`, this.checkArray)
          this.checkArray = []
        } else if (this.msg.negativeBtn === "지금 만들기") {
          this.$router.push({name: 'NewCuration'})
        } else {
          this.$emit(`${this.msg.name}-no-sign`)
        }
      }
    },
  }
</script>

<style>

</style>
