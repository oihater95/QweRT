<template>
  <div>
    <div class="navbar">
      <v-app-bar
        app
        color="#FFFFF0"
      >
        <v-layout class="d-flex justify-space-between align-center">
          <div>
            <v-icon @click="toggleNavbar">mdi-chevron-up</v-icon>
          </div>
          <div @click="clickHome">
            <v-img
              @click.native="clickHome"
              alt="Logo"
              class="logo shrink mr-2"
              contain
              src="../../assets/images/logo.jpg"
              transition="scale-transition"
              width="130"
            />
          </div>
          <div v-if="!isLogon" class="icon-div">
            <v-icon @click.native="clickSignup">fas fa-user-plus</v-icon>
            <v-icon @click.native="clickLogin">mdi-login</v-icon>
            <v-icon @click.native="clickCuration">far fa-images</v-icon>
            <v-icon @click.native="clickHome">mdi-home</v-icon>
            <div class="text-center">
              <!-- offset-y가 있어야 클릭 시에 드롭다운이 아래로 내려온다. -->
              <v-menu offset-y open-on-hover :close-on-content-click="false">
                <template v-slot:activator="{ on, attrs }">
                  <v-icon class="icon"
                    v-bind="attrs"
                    v-on="on"
                  >
                    far fa-plus-square
                  </v-icon>
                </template>
                <v-list>
                  <v-list-group>
                    <v-list-item slot="activator">
                      <v-list-item-title class="tab">그림 업로드</v-list-item-title>
                    </v-list-item>
                    <v-list-item :to="{ path: '/postings/drawing'}" >
                      <v-list-item-title class="tab ms-8">그리기</v-list-item-title>
                    </v-list-item>
                    <v-list-item :to="{ path: '/postings'}">
                      <v-list-item-title class="tab ms-8">파일 업로드</v-list-item-title>
                    </v-list-item>
                  </v-list-group>
                  
                  <v-list-item>
                    <v-list-item-title class="tab ms-4">새 큐레이션</v-list-item-title>
                  </v-list-item>
                  
                </v-list>
              </v-menu>
            </div>
            <div class="text-center">
              <!-- offset-y가 있어야 클릭 시에 드롭다운이 아래로 내려온다. -->
              <v-menu offset-y open-on-hover>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    v-bind="attrs"
                    v-on="on"
                    class="icon"
                  >
                    far fa-user-circle
                  </v-icon>
                </template>
                <v-list>
                  <v-list-item @click.native="clickProfile">
                    <v-list-item-title class="tab">내 프로필</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click.native="clickNotify">
                    <v-list-item-title class="tab">알림</v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title class="tab">로그아웃</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
            <v-icon @click.native="clickSearch">fas fa-search</v-icon>
          </div>
          <div v-else class="icon-div">
            <v-icon @click.native="clickSignup">fas fa-user-plus</v-icon>
            <v-icon @click.native="clickLogin">mdi-login</v-icon>
            <v-icon @click.native="clickSearch">fas fa-search</v-icon>
          </div>
        </v-layout>
      </v-app-bar>
    </div>
    <div>
      <v-icon 
        @click="toggleNavbar"
        class="down-icon"
      >
      mdi-chevron-down
      </v-icon>
    </div>
  </div>
</template>

<script>
import "@/css/common/Navbar.scss"
import { mapState } from 'vuex'

export default {
  name: 'Navbar',
  data: function () {
    return {
    }
  },
  methods: {
    toggleNavbar: function () {
      const navbar = document.querySelector('.navbar')
      if (navbar.style.display == "none") {
        navbar.style.display = "block"
      } else {
        navbar.style.display = "none"
      }
    },
    clickCuration: function () {
      this.$router.push({ name: 'CurationPage' })
    },
    clickHome: function () {
      this.$router.push({ name: 'MainPage', query: { t: new Date().getTime() }})
    },
    clickSearch: function () {
      this.$router.push({ name: 'SearchPage' })
    },
    clickNotify: function () {
      this.$router.push({ name: 'NotifyPage' })
    },
    clickProfile: function () {
      this.$router.push({ name: 'Profile' })
    },
    clickSignup: function () {
      this.$router.push({ name: 'Signup' })
    },
    clickLogin: function () {
      this.$router.push({ name: 'Login' })
    },
  },
  computed: {
    ...mapState([
      'isLogon',
      'userInfo',
    ])
  },
}
</script>