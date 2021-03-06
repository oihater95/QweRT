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
      <v-col
        cols="3" 
        lg="2"
      >
        <v-img
          v-if="!profileImageSrc && !profileImageData"
          src="@/assets/images/profile_image_default.png"
          @click="selectProfileImage"
        ></v-img>
        <v-img
          v-else
          :src="profileImageData ? profileImageData : profileImageSrc"
          @click="selectProfileImage"
        ></v-img>
        <v-file-input
          accept="image/*"
          v-model="profileImageFile"
          @change="changeProfileImage"
        ></v-file-input>
        <v-btn
          x-small
          tile
          class="ms-2 mt-3"
          @click="profileImageSrc='', profileImageFile='', profileImageData=''"
        >
          프로필 사진 기본 설정
        </v-btn>
      </v-col>
      <v-col align-self="center">
        <v-row>
          <v-col cols="12">
            <v-btn
              small
              rounded
              :disabled="!profileImageData"
              @click="uploadProfileImage"
            >
              결정완료
            </v-btn>
          </v-col>
          <v-col cols="12">
            <v-btn
              small
              rounded
              :disabled="!profileImageData"
              @click="profileImageFile='', profileImageData=''"
            >
              되돌리기
            </v-btn>
          </v-col>
          <v-col class="alert">
            <span>* 꼭 [저장]까지 클릭해야 완료됩니다.</span>
          </v-col>
        </v-row>
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
          rows="5"
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
            v-for="(masterpiece, i) in masterpieces"
            :key="i"
          >
            <div
              class="box-minus"
              @click="removeMasterpiece(i)"
            >
              <v-img
                :src="`https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/${masterpiece.postingImg}`"
                contain
              ></v-img>
              <v-icon large>fas fa-minus</v-icon>
            </div>
          </v-col>
          <v-col
            cols="4"
            v-if="masterpieces.length < 3"
          >
            <div
              class="box-add"
              @click="openMasterpieceSelecter"
            >
              <div class="content">
                <v-icon large>fas fa-plus</v-icon>
              </div>
            </div>
            <!-- 대표작 선택 모달 창 -->
            <MasterpieceModal
              v-if="masterpieceSelecterOn"
              :masterpieceIds="masterpieces.map(masterpiece => masterpiece.postingId)"
              class="d-none"
              @masterpiece-selected="addMasterpiece"
            />
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
          :disabled="((nickname !== currentNickname) && ((nickname !== validNickname) || !checkResult)) || (profileImageData.length !== 0)"
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
        <v-btn @click="$router.push({ name: 'Profile', params: {userId: $route.params.userId} })">취소</v-btn>
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
import MasterpieceModal from '@/components/profiles/MasterpieceModal'
import '@/css/profiles/ModifyProfile.scss'
import { mapState } from 'vuex'
import axios from 'axios'

export default {
  name: 'ModifyProfile',
  components: {
    Modal,
    MasterpieceModal,
  },
  data: function () {
    return {
      profileImageFile: null,
      profileImageData: '',
      profileImageSrc: '',
      nickname: '',
      currentNickname: '',
      validNickname: '',
      checkResult: false,
      showCheckResult: false,
      introduction: '',
      masterpieces: [],
      masterpieceSelecterOn: true,
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
        this.profileImageData = file
      } else {
        let reader = new FileReader()
        reader.onload = () => {
          this.profileImageData = reader.result
        }
        reader.readAsDataURL(file)
      }
    },
    // 프로필 사진을 S3에 업로드하는 함수
    uploadProfileImage: async function () {
      const API_ENDPOINT = 'https://2b7e7mxwc9.execute-api.ap-northeast-2.amazonaws.com/default/getPresignedUrl'
      const response = await axios({
        method: 'GET',
        url: API_ENDPOINT
      })
      console.log('Response: ', response)
      let binary = atob(this.profileImageData.split(',')[1])
      let array = []
      for (var i = 0; i < binary.length; i++) {
        array.push(binary.charCodeAt(i))
      }
      let blobData = new Blob([new Uint8Array(array)], {type: 'image/jpeg'})
      // Put request for upload to S3
      const result = await fetch(response.data.uploadURL, {
        method: 'PUT',
        // lambda에 적어준 내용과 일치해야 한다.
        // headers: {
        //   'Content-type': 'image/jpeg'
        // },
        body: blobData
      })
      console.log('Result: ', result)
      let fileKey = response.data.Key
      this.profileImageSrc = 'https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/' + fileKey
      this.profileImageFile = ''
      this.profileImageData = ''
    },
    // 닉네임 중복 확인 함수
    nicknameCheck: function () {
      axios ({
        method: 'get',
        url: `${this.hostUrl}/accounts/nicknamecheck/?nickname=${this.nickname}`
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
    // 대표작 선택 모달 창을 여는 함수
    openMasterpieceSelecter: function () {
      const btn = document.querySelector('#masterpieceModalBtn')
      btn.click()
    },
    // 대표작을 추가하는 함수
    addMasterpiece: function (posting) {
      this.masterpieces.push(posting)
      this.resetMasterpieceSelecter()
    },
    // 대표작을 제거하는 함수
    removeMasterpiece: function (i) {
      this.masterpieces.splice(i, 1)
      this.resetMasterpieceSelecter()
    },
    // 대표작 선택 모달 창을 초기화하는 함수
    resetMasterpieceSelecter: function () {
      return new Promise((resolve) => {
        resolve()
        this.masterpieceSelecterOn = false
      })
        .then(() => {
          this.masterpieceSelecterOn = true
        })
    },
    // 회원정보 수정 함수
    changeUserInfo: function () {
      axios({
        method: 'put',
        url: `${this.hostUrl}/accounts/${this.$route.params.userId}/info/`,
        data: {
          nickname: this.nickname,
          introduction: this.introduction,
          profileImage: this.profileImageSrc,
          masterpieceIds: this.masterpieces.map(masterpiece => masterpiece.postingId),
        },
        headers: { token: localStorage.getItem('jwtToken') }
      })
        .then(res => {
          console.log(res)
          this.currentNickname = this.nickname
          // state에 저장되어 있는 기본 유저정보 갱신
          this.$store.dispatch('setUserInfo', {
            userId: this.$route.params.userId,
            nickname: this.nickname,
            profileImage: this.profileImageSrc,
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
        url: `${this.hostUrl}/accounts/${this.$route.params.userId}/pwd/`,
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
        url: `${this.hostUrl}/accounts/${this.$route.params.userId}/`,
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
      'hostUrl',
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
          this.newPasswordConfirmation = this.newPasswordConfirmation.slice(0, this.newPasswordConfirmation.length-1)
        })
    },
  },
  // 페이지가 로드될 때 유저 정보 불러오기
  created: function () {
    axios({
      method: 'get',
      url: `${this.hostUrl}/profile/${this.$route.params.userId}/`,
    })
      .then(res => {
        console.log(res)
        this.nickname = res.data.nickname
        this.currentNickname = res.data.nickname
        this.profileImageSrc = res.data.profileImg
        this.introduction = res.data.introduction
        this.masterpieces = res.data.masterpieces
      })
      .catch(err => {
        console.log(err)
      })
  }
}
</script>

<style>

</style>