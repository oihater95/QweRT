<template>
  <v-container>
    <v-row justify="center">
      <v-col>
        <v-sheet
          class="sheet"
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
                <h1 class="my-5">회원가입</h1>
                <!-- 이메일 -->
                <v-col
                  cols="10"
                  class="rounded-xl mb-3 input"
                >
                  <v-row
                    justify="center"
                    align="center"
                  >
                    <v-col cols="8">
                      <v-text-field
                        v-model="credentials.email"
                        :rules="[rules.required, rules.emailForm, rules.emailChecked]"
                        label="이메일"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="2">
                      <v-btn
                        :disabled="!/.+@.+/.test(credentials.email)"
                        small
                        depressed
                        color="#FDF3BD"
                        @click="emailcheck"
                      >
                        중복확인
                      </v-btn>
                      <v-checkbox
                        v-model="uniqueEmail"
                        class="d-none"
                      ></v-checkbox>
                    </v-col>
                  </v-row>
                </v-col>
                <!-- 닉네임 -->
                <v-col
                  cols="10"
                  class="rounded-xl mb-3 input"
                >
                  <v-row
                    justify="center"
                    align="center"
                  >
                    <v-col cols="8">
                      <v-text-field
                        v-model="credentials.nickname"
                        :rules="[rules.required, rules.nicknameChecked]"
                        label="닉네임"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="2">
                      <v-btn
                        :disabled="!credentials.nickname"
                        small
                        depressed
                        color="#FDF3BD"
                        @click="nicknamecheck"
                      >
                        중복확인
                      </v-btn>
                      <v-checkbox
                        v-model="uniqueNickname"
                        class="d-none"
                      ></v-checkbox>
                    </v-col>
                  </v-row>
                </v-col>
                <!-- 비밀번호 -->
                <v-col
                  cols="10"
                  class="rounded-xl mb-3 input"
                >
                  <v-row justify="center">
                    <v-col cols="10">
                      <v-text-field
                        v-model="credentials.password"
                        :rules="[rules.required, rules.min, rules.include]"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="show1 ? 'text' : 'password'"
                        label="비밀번호"
                        counter
                        @click:append="show1 = !show1"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-col>
                <!-- 비밀번호 확인 -->
                <v-col
                  cols="10"
                  class="rounded-xl input"
                >
                  <v-row justify="center">
                    <v-col cols="10">
                      <v-text-field
                        v-model="credentials.passwordConfirmation"
                        :rules="[rules.required, rules.match]"
                        :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="show2 ? 'text' : 'password'"
                        label="비밀번호 확인"
                        counter
                        @click:append="show2 = !show2"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-col>
                <!-- 약관 동의 -->
                <v-col
                  cols="10"
                  class="py-2"
                >
                  <v-row align="center">
                    <v-checkbox
                      v-model="tos"
                      :rules="[rules.required]"
                      label="이용약관에 동의합니다."
                      color="orange"
                    ></v-checkbox>
                    <v-btn
                      text
                      class="mx-2 font-weight-black"
                      @click="showTos"
                    >
                      약관 보기
                    </v-btn>
                  </v-row>
                </v-col>
                <!-- 회원가입 버튼 -->
                <v-col
                  cols="12"
                  class="mt-3"
                >
                  <v-row justify="center">
                    <v-btn
                      :disabled="!validForm"
                      x-large
                      color="#FAD280"
                      @click="signup"
                    >
                      회원가입
                    </v-btn>
                  </v-row>
                </v-col>
                <!-- 로그인 화면으로 이동 -->
                <v-col
                  cols="12"
                  class="mt-8"
                >
                  <v-row justify="center">
                    <v-btn
                      small
                      plain
                      @click="$router.push({ name: 'Login' })"
                    >
                      이미 회원이신가요? 로그인 페이지로 이동
                    </v-btn>
                  </v-row>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </v-sheet>
      </v-col>
    </v-row>
    <!-- 중복 확인 결과 오버레이 -->
    <v-overlay :value="showOverlay" opacity="0.78">
      <v-card height="100" width="400" color="#FFFFF0">
        <v-card-text class="black--text text-h5 text-center my-5">
          {{ checkResult }}
        </v-card-text>
      </v-card>
    </v-overlay>
  </v-container>
</template>

<script>
import '@/css/accounts/Signup.scss'
import axios from 'axios'

export default {
  name: 'Signup',
  data: function () {
    return {
      validForm: false,
      uniqueEmail: false,
      uniqueNickname: false,
      credentials: {
        email: '',
        nickname: '',
        password: '',
        passwordConfirmation: '',
      },
      rules: {
        required: v => !!v || '필수 사항',
        emailChecked: () => this.uniqueEmail || '중복 확인이 필요합니다.',
        nicknameChecked: () => this.uniqueNickname || '중복 확인이 필요합니다.',
        emailForm: v => /.+@.+/.test(v) || '올바른 이메일 형식이어야 합니다.',
        min: v => v.length >= 8 || '8자 이상이어야 합니다.',
        include: v => (/[a-zA-Z]/.test(v) && /[0-9]/.test(v)) || '영문, 숫자를 모두 포함해야 합니다.',
        match: v => v === this.credentials.password || '비밀번호와 일치하지 않습니다.',
      },
      show1: false,
      show2: false,
      tos: false,   //tos : terms of service (이용약관)
      showOverlay: false,
      checkResult: '',
    }
  },
  methods: {
    emailcheck: function () {
      axios ({
        method: 'get',
        url: `http://localhost/qwert/accounts/emailcheck/?email=${this.credentials.email}`
      })
        .then(res => {
          console.log(res)
          this.uniqueEmail = true
          this.credentials.email = this.credentials.email + ' '
          this.checkResult = '가입 가능한 이메일입니다.'
          this.showOverlay = true
        })
        .then(() => {
          this.credentials.email = this.credentials.email.trim()
          setTimeout(() => {
            this.showOverlay = false
          }, 1000)
        })
        .catch(err => {
          console.log(err)
          this.uniqueEmail = false
          this.credentials.email = this.credentials.email + ' '
          this.checkResult = '이미 가입된 이메일입니다.'
          this.showOverlay = true
        })
        .then(() => {
          this.credentials.email = this.credentials.email.trim()
          setTimeout(() => {
            this.showOverlay = false
          }, 1000)
        })
    },
    nicknamecheck: function () {
      axios ({
        method: 'get',
        url: `http://localhost/qwert/accounts/nicknamecheck/?nickname=${this.credentials.nickname}`
      })
        .then(res => {
          console.log(res)
          this.uniqueNickname = true
          this.credentials.nickname = this.credentials.nickname + ' '
          this.checkResult = '사용 가능한 닉네임입니다.'
          this.showOverlay = true
        })
        .then(() => {
          this.credentials.nickname = this.credentials.nickname.trim()
          setTimeout(() => {
            this.showOverlay = false
          }, 1000)
        })
        .catch(err => {
          console.log(err)
          this.uniqueNickname = false
          this.credentials.nickname = this.credentials.nickname + ' '
          this.checkResult = '이미 사용 중인 닉네임입니다.'
          this.showOverlay = true
        })
        .then(() => {
          this.credentials.nickname = this.credentials.nickname.trim()
          setTimeout(() => {
            this.showOverlay = false
          }, 1000)
        })
    },
    showTos: function () {

    },
    signup: function () {
      axios ({
        method: 'post',
        url: 'http://localhost/qwert/accounts/signup/',
        data: this.credentials
      })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
    },
  }
}
</script>

<style>

</style>
