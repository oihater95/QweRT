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
          <div>
            <v-img
              alt="Logo"
              class="logo shrink mr-2"
              contain
              src="../../assets/images/logo.jpg"
              transition="scale-transition"
              width="130"
            />
          </div>
          <div class="icon-div">
            <v-icon>far fa-images</v-icon>
            <v-icon>mdi-home</v-icon>
            <div class="text-center">
              <!-- offset-y가 있어야 클릭 시에 드롭다운이 아래로 내려온다. -->
              <v-menu offset-y open-on-hover>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon class="icon"
                    v-bind="attrs"
                    v-on="on"
                  >
                    far fa-plus-square
                  </v-icon>
                </template>
                <v-list>
                  <v-list-item>
                    <v-list-item-title>그림 업로드</v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title>새 큐레이션</v-list-item-title>
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
                  <v-list-item>
                    <v-list-item-title>내 프로필</v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title>알림</v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title>로그아웃</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
            <v-icon>fas fa-search</v-icon>
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
import "../../css/common/Navbar.scss"

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
    }
  }
}
</script>