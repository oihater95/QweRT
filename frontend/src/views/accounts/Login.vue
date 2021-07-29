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
                  class="rounded-xl mb-3 login-input"
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
    login: function () {
      axios ({
        method: 'post',
        url: 'http://localhost/qwert/accounts/login/',
        data: this.credentials
      })
        .then(res => {
          console.log(res)
          this.$store.dispatch('setUserInfo', res.data)
        })
        .catch(err => {
          console.log(err)
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
  },
}
</script>

<style>

</style>