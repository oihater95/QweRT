<template>
  <v-container class="profileModification-container">
    <!-- 헤더 장식 -->
    <v-row justify="center">
      <v-col
        cols="7"
        class="text-center"
      >
        <v-img src="@/assets/images/profile_modification_header.png"></v-img>
      </v-col>
    </v-row>
    <!-- 프로필 사진 -->
    <v-row class="profile-image__loader">
      <v-col
        cols="2"
        offset="1"
      >
        <h2>프로필 사진</h2>
      </v-col>
      <v-col>
        <v-img
          v-if="!profileImageSrc"
          src="@/assets/images/profile_image_default.png"
          @click="selectProfileImage"
        ></v-img>
        <v-img
          v-else
          :src="profileImageSrc"
          @click="selectProfileImage"
        ></v-img>
        <v-file-input
          accept="image/*"
          v-model="profileImageFile"
          @change="changeProfileImage"
        ></v-file-input>
      </v-col>
    </v-row>
    <!-- 닉네임 -->
    <v-row>
      <v-col
        cols="2"
        offset="1"
      >
        <h2>닉네임</h2>
      </v-col>
      <v-col cols="4">
        <v-text-field 
          v-model="nickname"
          clearable
          outlined
          counter="128"
        ></v-text-field>
      </v-col>
      <v-col class="mt-3">
        <v-tooltip
          top
          v-model="showCheckResult"
          :color="checkResult ? '#849EDB' : '#F4A380'"
        >
          <template v-slot:activator="{ attrs }">
            <v-btn
              v-bind="attrs"
              small
              rounded
              :disabled="nickname === currentNickname || nickname === validNickname"
              @click="nicknameCheck"
            >
              중복확인
            </v-btn>
          </template>
          <span>{{ checkResult ? '사용 가능한 닉네임입니다.' : '이미 사용 중인 닉네임입니다.' }}</span>
        </v-tooltip>
      </v-col>
    </v-row>
    <!-- 자기소개 -->
    <v-row>
      <v-col
        cols="2"
        offset="1"
      >
        <h2>자기소개</h2>
      </v-col>
      <v-col cols="7">
        <v-textarea
          v-model="introduction"
          clearable
          outlined
          counter="1000"
        ></v-textarea>
      </v-col>
    </v-row>
    <!-- 대표작 -->
    <v-row class="profile-masterpieces__loader">
      <v-col
        cols="2"
        offset="1"
      >
        <h2>대표작</h2>
      </v-col>
      <v-col cols="8">
        <v-row>
          <v-col
            cols="4"
            v-for="(sampleSrc, i) in masterpieceSamples"
            :key="i"
          >
            <div class="box-minus">
              <v-img
                :src="sampleSrc"
                contain
              ></v-img>
              <v-icon large>fas fa-minus</v-icon>
            </div>
          </v-col>
          <v-col
            cols="4"
            v-if="masterpieceSamples.length < 3"
          >
            <div class="box-add">
              <div class="content">
                <v-icon large>fas fa-plus</v-icon>
              </div>
            </div>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <!-- 저장 버튼 -->
    <v-row justify="center">
      <v-col
        cols="12"
        class="text-center"
      >
        <v-btn
          :disabled="(nickname !== currentNickname) && ((nickname !== validNickname) || !checkResult)"
          @click="changeUserInfo"
        >
          저장
        </v-btn>
      </v-col>
      <v-col cols="10">
        <v-divider></v-divider>
      </v-col>
    </v-row>
    <!-- 비밀번호 변경 -->
    <v-row>
      <v-col
        cols="2"
        offset="1"
      >
        <h2>비밀번호 변경</h2>
      </v-col>
      <v-col>
        <v-form v-model="validForm">
          <!-- 현재 비밀번호 -->
          <v-row>
            <v-col cols="3">
              <h4>현재 비밀번호</h4>
            </v-col>
            <v-col cols="5">
              <v-text-field
                v-model="password"
                :append-icon="showPW1 ? 'mdi-eye' : 'mdi-eye-off'"
                :type="showPW1 ? 'text' : 'password'"
                dense
                outlined
                @click:append="showPW1 = !showPW1"
              ></v-text-field>
            </v-col>
          </v-row>
          <!-- 새 비밀번호 -->
          <v-row>
            <v-col
              cols="3"
              class="py-0"
            >
              <h4>새 비밀번호</h4>
            </v-col>
            <v-col
              cols="5"
              class="py-0"
            >
              <v-text-field
                v-model="newPassword"
                :append-icon="showPW2 ? 'mdi-eye' : 'mdi-eye-off'"
                :type="showPW2 ? 'text' : 'password'"
                hint="영문, 숫자 포함 8자 이상이어야 합니다."
                :rules="[rules.min, rules.include]"
                dense
                outlined
                @click:append="showPW2 = !showPW2"
              ></v-text-field>
            </v-col>
          </v-row>
          <!-- 새 비밀번호 확인 -->
          <v-row>
            <v-col cols="3">
              <h4>새 비밀번호 확인</h4>
            </v-col>
            <v-col cols="5">
              <v-text-field
                v-model="newPasswordConfirmation"
                :append-icon="showPW3 ? 'mdi-eye' : 'mdi-eye-off'"
                :type="showPW3 ? 'text' : 'password'"
                :rules="[rules.match]"
                dense
                outlined
                @click:append="showPW3 = !showPW3"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-form>
      </v-col>
    </v-row>
    <!-- 변경 버튼 -->
    <v-row justify="center">
      <v-col
        cols="12"
        class="text-center"
      >
        <v-btn
          :disabled="(!password || !newPassword || !newPasswordConfirmation || !validForm)"
          @click="changePassword"
        >
          변경
        </v-btn>
      </v-col>
      <v-col cols="10">
        <v-divider></v-divider>
      </v-col>
    </v-row>
    <!-- 회원 탈퇴 -->
    <v-row>
      <v-col
        cols="2"
        offset="1"
      >
        <h2>회원 탈퇴</h2>
      </v-col>
      <!-- 탈퇴 버튼 -->
      <v-col>
        <v-btn
          class="warning-btn"
          dark
          @click="signout"
        >
          탈퇴
        </v-btn>
      </v-col>
    </v-row>
    <!-- 취소 버튼 -->
    <v-row justify="center">
      <v-col cols="10">
        <v-divider></v-divider>
      </v-col>
      <v-col
        cols="12"
        class="text-center"
      >
        <v-btn @click="$router.push({ name: 'Profile', params: {userId: userId} })">취소</v-btn>
      </v-col>
    </v-row>
    <Modal
      :msg="modalMsg"
      class="d-none"
      @changePassword-ok-sign="password = '', newPassword = '', newPasswordConfirmation = ''"
      @signout-ok-sign="deleteUser"
      @deleteUser-ok-sign="$router.push({ name: 'MainPage' })"
    />
  </v-container>
</template>

<script>
import Modal from '@/components/common/Modal'
import '@/css/profiles/ModifyProfile.scss'
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'ModifyProfile',
  components: {
    Modal,
  },
  data: function () {
    return {
      userId: null,
      profileImageFile: null,
      profileImageSrc: '',
      nickname: '',
      currentNickname: '',
      validNickname: '',
      checkResult: false,
      showCheckResult: false,
      introduction: '자기소개를 입력해주세요',
      masterpieces: [],
      masterpieceSamples: [
        'https://i.ytimg.com/vi/yGqlkavU-lE/maxresdefault.jpg',
        'http://www.pipo.co.kr/shopimages/pipouhwa/mobile/8/131668_represent?1506069524',
      ],
      password: '',
      newPassword: '',
      newPasswordConfirmation: '',
      showPW1: false,
      showPW2: false,
      showPW3: false,
      validForm: false,
      rules: {
        min: v => (v.length >= 8 || v.length === 0) || '8자 이상이어야 합니다.',
        include: v => ((/[a-zA-Z]/.test(v) && /[0-9]/.test(v)) || v.length === 0) || '영문, 숫자를 모두 포함해야 합니다.',
        match: v => ( v === this.newPassword || v.length === 0) || '새 비밀번호와 일치하지 않습니다.',
      },
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
    // 프로필 사진을 클릭하면 파일 탐색기를 열도록 하는 함수
    selectProfileImage: function () {
      const fileInput = document.querySelector('.v-file-input__text')
      fileInput.click()
    },
    // 프로필 사진을 파일 탐색기에서 선택한 이미지로 바꾸는 함수
    changeProfileImage: function () {
      const file = this.profileImageFile
      if (typeof file === 'string') {
        this.profileImageSrc = file
      } else {
        let reader = new FileReader()
        reader.onload = () => {
          this.profileImageSrc = reader.result
        }
        reader.readAsDataURL(file)
      }
    },
    // 닉네임 중복 확인 함수
    nicknameCheck: function () {
      axios ({
        method: 'get',
        url: `http://13.209.16.153/qwert/accounts/nicknamecheck/?nickname=${this.nickname}`
      })
        .then(res => {
          console.log(res)
          this.validNickname = this.nickname
          this.checkResult = true
          this.showCheckResult = true
          setTimeout(() => {
            this.showCheckResult = false
          }, 1500)
        })
        .catch(err => {
          console.log(err)
          this.checkResult = false
          this.showCheckResult = true
          setTimeout(() => {
            this.showCheckResult = false
          }, 1500)
        })
    },
    // 회원정보 수정 함수
    changeUserInfo: function () {
      axios({
        method: 'put',
        url: `http://localhost/qwert/accounts/${this.userId}/info/`,
        data: {
          nickname: this.nickname,
          introduction: this.introduction,
          profileImage: '',
          masterpieceIds: this.masterpieces,
        },
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {
          console.log(res)
          this.currentNickname = this.nickname
          // state에 저장되어 있는 기본 유저정보 갱신
          this.$store.dispatch('setUserInfo', {
            user_id: this.userId,
            nickname: this.nickname,
            profile_image: this.profileImageSrc,
          })
          // 회원정보 수정 성공 모달 창
          this.modalMsg = {
            name: 'changeUserInfo',
            triggerBtn: '',
            title: '',
            text: '회원정보 수정이 완료되었습니다.',
            positiveBtn: '확인',
            negativeBtn: '',
          }
          const modalBtn = document.querySelector('#modalBtn')
          modalBtn.click()
        })
        .catch(err => {
          console.log(err)
          // 회원정보 수정 실패 모달 창 (상태 코드에 따라)
          if (err.response.status === 401) {
            this.modalMsg = {
              name: 'changeUserInfoFailure401',
              triggerBtn: '',
              title: `${err.response.status} error`,
              text: '인증되지 않은 사용자입니다.<br/>혹은<br/>현재 로그인된 상태인지 확인해주세요.',
              positiveBtn: '확인',
              negativeBtn: '',
            }
          } else if (err.response.status === 404) {
            this.modalMsg = {
              name: 'changeUserInfoFailure404',
              triggerBtn: '',
              title: `${err.response.status} error`,
              text: '확인할 수 없는 사용자에 대한 요청입니다.',
              positiveBtn: '확인',
              negativeBtn: '',
            }
          } else {
            this.modalMsg = {
              name: 'changeUserInfoFailure',
              triggerBtn: '',
              title: `${err.response.status} error`,
              text: '제한된 접근입니다.<br/>현재 로그인된 상태인지 확인해주세요.',
              positiveBtn: '확인',
              negativeBtn: '',
            }
          }
          const modalBtn = document.querySelector('#modalBtn')
          modalBtn.click()
        })
    },
    // 비밀번호 변경 함수
    changePassword: function () {
      axios({
        method: 'put',
        url: `http://localhost/qwert/accounts/${this.userId}/pwd/`,
        data: {
          password: this.password,
          newPassword: this.newPassword
        },
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {
          console.log(res)
          // 비밀번호 변경 성공 모달 창
          this.modalMsg = {
            name: 'changePassword',
            triggerBtn: '',
            title: '',
            text: '비밀번호 변경이 완료되었습니다.',
            positiveBtn: '확인',
            negativeBtn: '',
          }
          const modalBtn = document.querySelector('#modalBtn')
          modalBtn.click()
        })
        .catch(err => {
          console.log(err)
          // 비밀번호 변경 실패 모달 창 (상태 코드에 따라)
          if (err.response.status === 401) {
            this.modalMsg = {
              name: 'changePasswordFailure401',
              triggerBtn: '',
              title: `${err.response.status} error`,
              text: '현재 비밀번호가 일치하지 않습니다.<br/>혹은<br/>현재 로그인된 상태인지 확인해주세요.',
              positiveBtn: '확인',
              negativeBtn: '',
            }
          } else if (err.response.status === 404) {
            this.modalMsg = {
              name: 'changePasswordFailure404',
              triggerBtn: '',
              title: `${err.response.status} error`,
              text: '확인할 수 없는 사용자에 대한 요청입니다.',
              positiveBtn: '확인',
              negativeBtn: '',
            }
          } else {
            this.modalMsg = {
              name: 'changePasswordFailure403',
              triggerBtn: '',
              title: `${err.response.status} error`,
              text: '제한된 접근입니다.<br/>현재 로그인된 상태인지 확인해주세요.',
              positiveBtn: '확인',
              negativeBtn: '',
            }
          }
          const modalBtn = document.querySelector('#modalBtn')
          modalBtn.click()
        })
    },
    // 회원 탈퇴 확인을 위한 모달 창을 띄우는 함수
    signout: function () {
      this.modalMsg = {
        name: 'signout',
        triggerBtn: '',
        title: '회원 탈퇴',
        text: '정말로 회원 탈퇴를 진행하고<br/>계정을 삭제하시겠습니까?',
        positiveBtn: '확인',
        negativeBtn: '취소',
      }
      const modalBtn = document.querySelector('#modalBtn')
      modalBtn.click()
    },
    // 회원 탈퇴 함수
    deleteUser: function () {
      axios({
        method: 'delete',
        url: `http://localhost/qwert/accounts/${this.userId}/`,
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {
          console.log(res)
          // jwt 토큰과 state의 회원 정보 삭제
          this.$store.dispatch('removeUserInfo')
          // 회원 탈퇴 성공 모달 창
          this.modalMsg = {
            name: 'deleteUser',
            triggerBtn: '',
            title: '',
            text: '회원 탈퇴가 완료되었습니다.',
            positiveBtn: '확인',
            negativeBtn: '',
          }
          const modalBtn = document.querySelector('#modalBtn')
          modalBtn.click()
        })
        .catch(err => {
          console.log(err)
          // 회원 탈퇴 실패 모달 창 (상태 코드에 따라)
          if (err.response.status === 401) {
            this.modalMsg = {
              name: 'deleteUserFailure401',
              triggerBtn: '',
              title: `${err.response.status} error`,
              text: '인증되지 않은 사용자입니다.<br/>혹은<br/>현재 로그인된 상태인지 확인해주세요.',
              positiveBtn: '확인',
              negativeBtn: '',
            }
          } else if (err.response.status === 404) {
            this.modalMsg = {
              name: 'deleteUserFailure404',
              triggerBtn: '',
              title: `${err.response.status} error`,
              text: '확인할 수 없는 사용자에 대한 요청입니다.',
              positiveBtn: '확인',
              negativeBtn: '',
            }
          } else {
            this.modalMsg = {
              name: 'deleteUserFailure',
              triggerBtn: '',
              title: `${err.response.status} error`,
              text: '제한된 접근입니다.<br/>현재 로그인된 상태인지 확인해주세요.',
              positiveBtn: '확인',
              negativeBtn: '',
            }
          }
          const modalBtn = document.querySelector('#modalBtn')
          modalBtn.click()
        })
    },
  },
  computed: {
    ...mapState([
      'isLogon',
      'userInfo',
    ])
  },
  watch: {
    // '새 비밀번호'와 '새 비밀번호 확인'이 일치하여 경고가 없는 상태에서 '새 비밀번호'를 바꾸면 일치하지 않는다는 경고가 뜨지 않는 문제 해결
    // '새 비밀번호'를 바꿨을 때 '새 비밀번호 확인'에서도 즉각 반응하여 경고를 나타낼 수 있도록 하는 일종의 꼼수
    newPassword: function () {
      return new Promise((resolve) => {
        resolve()
        this.newPasswordConfirmation = (this.newPasswordConfirmation + ' ')
      })
        .then(() => {
          this.newPasswordConfirmation = this.newPasswordConfirmation.trim()
        })
    },
  },
  created: function () {
    this.userId = this.userInfo.userId
    this.nickname = this.userInfo.nickname
    this.currentNickname = this.userInfo.nickname
    if (this.userInfo.profileImage) {
      this.profileImageSrc = this.userInfo.profileImage
    }
  }
}
</script>

<style>

</style>