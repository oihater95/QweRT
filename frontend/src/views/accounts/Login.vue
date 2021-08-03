<template>
  <v-container>
    <v-row justify="center">
      <v-col>
        <v-sheet
          class="login-sheet"
          elevation="20"
          rounded="xl"
          color="#FDF3BD"
        >
          <v-form
            v-model="validForm"
            class="m-5"
          >
            <v-container>
              <v-row justify="center">
                <h1 class="my-5">로그인</h1>
                <!-- 이메일 -->
                <v-col
                  cols="10"
                  class="rounded-xl mb-3 login-input"
                >
                  <v-row justify="center">
                    <v-col cols="10">
                      <v-text-field
                        v-model="credentials.email"
                        :rules="[rules.required, rules.emailForm]"
                        label="이메일"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-col>
                <!-- 비밀번호 -->
                <v-col
                  cols="10"
                  class="rounded-xl login-input"
                >
                  <v-row justify="center">
                    <v-col cols="10">
                      <v-text-field
                        v-model="credentials.password"
                        :rules="[rules.required]"
                        :append-icon="showPW ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="showPW ? 'text' : 'password'"
                        label="비밀번호"
                        counter
                        @click:append="showPW = !showPW"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-col>
                <!-- 이메일 기억하기 -->
                <v-col
                  cols="10"
                  class="py-2"
                >
                  <v-row align="center">
                    <v-checkbox
                      v-model="wannaSave"
                      label="이메일 저장"
                      color="orange"
                    ></v-checkbox>
                  </v-row>
                </v-col>
                <!-- 로그인 버튼 -->
                <v-col
                  cols="12"
                  class="mt-3"
                >
                  <v-row justify="center">
                    <v-btn
                      :disabled="!validForm"
                      x-large
                      color="#FAD280"
                      @click="login"
                    >
                      로그인
                    </v-btn>
                  </v-row>
                </v-col>
                <!-- 회원가입 화면으로 이동 -->
                <v-col
                  cols="12"
                  class="mt-8"
                >
                  <v-row justify="center">
                    <v-btn
                      small
                      plain
                      @click="$router.push({ name: 'Signup' })"
                    >
                      아직 계정이 없으신가요? 회원가입 페이지로 이동
                    </v-btn>
                  </v-row>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </v-sheet>
      </v-col>
    </v-row>
    <!-- 모달 -->
    <Modal
      :msg="modalMsg"
      class="d-none"
    />
  </v-container>
</template>

<script>
import Modal from '@/components/common/Modal'
import '@/css/accounts/Login.scss'
import axios from 'axios'

export default {
  name: 'Login',
  components: {
    Modal,
  },
  data: function () {
    return {
      credentials: {
        email: '',
        password: '',
      },
      rules: {
        required: v => !!v || '필수 사항',
        emailForm: v => /.+@.+/.test(v) || '이메일 형식이 아닙니다.'
      },
      validForm: false,
      showPW: false,
      wannaSave: false,
      modalMsg: {
        name: '',
        triggerBtn: '',
        title: '',
        text: '',
        positiveBtn: '',
        negativeBtn: '',
      },
    }
  },
  methods: {
    // 로그인 함수
    login: function () {
      axios ({
        method: 'post',
        url: 'http://localhost/qwert/accounts/login/',
        data: this.credentials
      })
        .then(res => {
          console.log(res)
          // 유저정보 state에 저장
          this.$store.dispatch('setUserInfo', res.data)
          this.$router.push({ name: 'MainPage' })
          // 이메일을 cookie에 90일 동안 저장 또는 삭제
          if (this.wannaSave) {
            this.rememberEmail(90)
          } else {
            this.rememberEmail(0)
          }
        })
        .catch(err => {
          console.log(err)
          // 로그인 실패 모달 창
          this.modalMsg.name='loginFailure'
          this.modalMsg.triggerBtn = ''
          this.modalMsg.title = ''
          this.modalMsg.text = '가입하지 않은 이메일이거나<br/>비밀번호가 잘못되었습니다.'
          this.modalMsg.positiveBtn = '확인'
          this.modalMsg.negativeBtn = ''
          const modalBtn = document.querySelector('#modalBtn')
          modalBtn.click()
        })
    },
    // 이메일을 cookie에 저장하는 함수
    rememberEmail: function (term) {
      const expirationDate = new Date()
      expirationDate.setDate(expirationDate.getDate() + term)
      document.cookie = `email=${escape(this.credentials.email)}; path=/; expires=${expirationDate.toGMTString()};`
    },
    // cookie에서 저장된 이메일을 불러오는 함수
    recallEmail: function () {
      const target = 'email='
      if (document.cookie.length > 0) {
        const offset = document.cookie.indexOf(target)
        if (offset != -1) {
          const start = offset + target.length
          let end = document.cookie.indexOf(';', start)
          if (end == -1) {
            end = document.cookie.length
          }
          return unescape(document.cookie.substring(start, end))
        }
      }
    },
  },
  // cookie에 저장된 이메일이 있다면 자동으로 불러오기
  created: function () {
    if (this.recallEmail()) {
      this.credentials.email = this.recallEmail()
      this.wannaSave = true
    }
  }
}
</script>

<style>

</style>