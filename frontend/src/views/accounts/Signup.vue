<template>
  <v-container>
    <v-row justify="center">
      <v-col>
        <v-sheet
          class="signup-sheet"
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
                  class="rounded-xl mb-3 signup-input"
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
                    </v-col>
                  </v-row>
                </v-col>
                <!-- 닉네임 -->
                <v-col
                  cols="10"
                  class="rounded-xl mb-3 signup-input"
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
                    </v-col>
                  </v-row>
                </v-col>
                <!-- 비밀번호 -->
                <v-col
                  cols="10"
                  class="rounded-xl mb-3 signup-input"
                >
                  <v-row justify="center">
                    <v-col cols="10">
                      <v-text-field
                        v-model="credentials.password"
                        :rules="[rules.required, rules.min, rules.include]"
                        :append-icon="showPW1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="showPW1 ? 'text' : 'password'"
                        label="비밀번호"
                        counter
                        @click:append="showPW1 = !showPW1"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-col>
                <!-- 비밀번호 확인 -->
                <v-col
                  cols="10"
                  class="rounded-xl signup-input"
                >
                  <v-row justify="center">
                    <v-col cols="10">
                      <v-text-field
                        v-model="credentials.passwordConfirmation"
                        :rules="[rules.required, rules.match]"
                        :append-icon="showPW2 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="showPW2 ? 'text' : 'password'"
                        label="비밀번호 확인"
                        counter
                        @click:append="showPW2 = !showPW2"
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
    <!-- 모달 -->
    <!-- 회원가입 성공 시 모달 창에서 버튼을 클릭하면 로그인 창으로 이동 -->
    <Modal
      :msg="modalMsg"
      @signup-ok-sign="$router.push({ name: 'Login' })"
      class="d-none"
    />
  </v-container>
</template>

<script>
import Modal from '@/components/common/Modal'
import '@/css/accounts/Signup.scss'
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'Signup',
  components: {
    Modal,
  },
  data: function () {
    return {
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
      validForm: false,
      uniqueEmail: false,
      uniqueNickname: false,
      showPW1: false,
      showPW2: false,
      tos: false,   //tos : terms of service (이용약관)
      showOverlay: false,
      checkResult: '',
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
    // 이메일 중복확인하는 함수
    emailcheck: function () {
      axios ({
        method: 'get',
        url: `${this.hostUrl}/accounts/emailcheck/?email=${this.credentials.email}`
      })
        .then(res => {
          console.log(res)
          // 중복확인 rules를 갱신하기 위한 꼼수 (뒤에 공백을 붙였다가)
          this.uniqueEmail = true
          this.credentials.email = this.credentials.email + ' '
          // 중복되지 않음을 오버레이로 알림
          this.checkResult = '가입 가능한 이메일입니다.'
          this.showOverlay = true
        })
        .catch(err => {
          console.log(err)
          // 중복확인 rules를 갱신하기 위한 꼼수 (뒤에 공백을 붙였다가)
          this.uniqueEmail = false
          this.credentials.email = this.credentials.email + ' '
          // 중복되었음을 오버레이로 알림
          this.checkResult = '이미 가입된 이메일입니다.'
          this.showOverlay = true
        })
        .then(() => {
          // 중복확인 rules를 갱신하기 위한 꼼수 (다시 공백을 지워서 email 입력을 갱신)
          this.credentials.email = this.credentials.email.slice(0, this.credentials.email.length-1)
          // 1초 후 오버레이 내리기
          setTimeout(() => {
            this.showOverlay = false
          }, 1000)
        })
    },
    // 닉네임 중복확인하는 함수 (이메일 중복확인 함수와 같은 원리)
    nicknamecheck: function () {
      axios ({
        method: 'get',
        url: `${this.hostUrl}/accounts/nicknamecheck/?nickname=${this.credentials.nickname}`
      })
        .then(res => {
          console.log(res)
          this.uniqueNickname = true
          this.credentials.nickname = this.credentials.nickname + ' '
          this.checkResult = '사용 가능한 닉네임입니다.'
          this.showOverlay = true
        })
        .catch(err => {
          console.log(err)
          this.uniqueNickname = false
          this.credentials.nickname = this.credentials.nickname + ' '
          this.checkResult = '이미 사용 중인 닉네임입니다.'
          this.showOverlay = true
        })
        .then(() => {
          this.credentials.nickname = this.credentials.nickname.slice(0, this.credentials.nickname.length-1)
          setTimeout(() => {
            this.showOverlay = false
          }, 1000)
        })
    },
    // 이용약관을 모달 창으로 보여주는 함수
    showTos: function () {
      this.modalMsg.name='showTos'
      this.modalMsg.triggerBtn = ''
      this.modalMsg.title = 'QweRT 이용약관'
      this.modalMsg.text = '1. 그림을 도용하면 안 돼요. 자신이 그린 그림만 공유해주세요.<br/>2. 불건전한 내용의 그림은 자제해주세요. 모두가 이용하는 공간입니다.<br/>3. "그림"만 공유해주세요. "그림" 외의 사진은 되도록 삼가주세요.'
      this.modalMsg.positiveBtn = ''
      this.modalMsg.negativeBtn = '닫기'
      const modalBtn = document.querySelector('#modalBtn')
      modalBtn.click()
    },
    // 회원가입 함수
    signup: function () {
      axios ({
        method: 'post',
        url: `${this.hostUrl}/accounts/signup/`,
        data: this.credentials
      })
        .then(res => {
          console.log(res)
          // 회원가입 성공 모달 창
          this.modalMsg.name='signup'
          this.modalMsg.triggerBtn = ''
          this.modalMsg.title = ''
          this.modalMsg.text = '회원가입이 완료되었습니다.'
          this.modalMsg.positiveBtn = '로그인'
          this.modalMsg.negativeBtn = ''
          const modalBtn = document.querySelector('#modalBtn')
          modalBtn.click()
        })
        .catch(err => {
          console.log(err)
          // 회원가입 실패 모달 창
          this.modalMsg.name='signupFailure'
          this.modalMsg.triggerBtn = ''
          this.modalMsg.title = ''
          this.modalMsg.text = '회원가입에 실패했습니다.'
          this.modalMsg.positiveBtn = '확인'
          this.modalMsg.negativeBtn = ''
          const modalBtn = document.querySelector('#modalBtn')
          modalBtn.click()
        })
    },
  },
  computed: {
    ...mapState([
      'hostUrl',
    ])
  },
  watch: {
    // '비밀번호'와 '비밀번호 확인'이 일치하여 경고가 없는 상태에서 '비밀번호'를 바꾸면 일치하지 않는다는 경고가 뜨지 않는 문제 해결
    // '비밀번호'를 바꿨을 때 '비밀번호 확인'에서도 즉각 반응하여 경고를 나타낼 수 있도록 하는 일종의 꼼수
    'credentials.password': function () {
      return new Promise((resolve) => {
        resolve()
        this.credentials.passwordConfirmation = (this.credentials.passwordConfirmation + ' ')
      })
        .then(() => {
          this.credentials.passwordConfirmation = this.credentials.passwordConfirmation.slice(0, this.credentials.passwordConfirmation.length-1)
        })
    },
  },
}
</script>

<style>

</style>
