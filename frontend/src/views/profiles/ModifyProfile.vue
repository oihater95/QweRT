<template>
  <v-container class="profile-container">
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
          @change="changeProfileUmage"
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
    </v-row>
    <!-- 자기소개 -->
    <v-row >
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
    <v-row align="center">
      <v-col
        cols="2"
        offset="1"
      >
        <h2>대표작</h2>
      </v-col>
      <v-col>
        대표작 선택
      </v-col>
    </v-row>
    <!-- 저장 버튼 -->
    <v-row justify="center">
      <v-col
        cols="12"
        class="text-center"
      >
        <v-btn color="#E8D48D">저장</v-btn>
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
          color="#E8D48D"
          :disabled="(!password || !newPassword || !newPasswordConfirmation || !validForm)"
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
          color="#F4A380"
          dark
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
        <v-btn color="#E8D48D">취소</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import '@/css/profiles/ModifyProfile.scss'

export default {
  name: 'ModifyProfile',
  data: function () {
    return {
      profileImageFile: null,
      profileImageSrc: '',
      nickname: 'nickname',
      introduction: 'introduction introduction introduction introduction introduction introduction',
      masterpieces: [],
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
        match: v => (v === this.newPassword || v.length === 0) || '새 비밀번호와 일치하지 않습니다.',
      }
    }
  },
  methods: {
    // 프로필 사진을 클릭하면 파일 탐색기를 열도록 하는 함수
    selectProfileImage: function () {
      const fileInput = document.querySelector('.v-file-input__text')
      fileInput.click()
    },
    // 프로필 사진을 파일 탐색기에서 선택한 이미지로 바꾸는 함수
    changeProfileUmage: function () {
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
  }
}
</script>

<style>

</style>