import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState(),
  ],
  state: {
    host: 'http://localhost:8080/qwert',
    // host: 'http://13.209.16.153:8080/qwert',
    isLogon: false,
    userInfo: {
      userId: '',
      nickname: '',
      profileImage: '',
    },
    imageInfo: {
      drawing: false,
      image: '',
      imageSrc: '',
      filename: '',
      file: '',
    }
  },
  mutations: {
    SET_USER_INFO: function (state, data) {
      state.userInfo.userId = data.userId
      state.userInfo.nickname = data.nickname
      state.userInfo.profileImage = data.profileImage
      state.isLogon = true
    },
    REMOVE_USER_INFO: function (state) {
      state.userInfo.userId = ''
      state.userInfo.nickname = ''
      state.userInfo.profileImage = ''
      state.isLogon = false
    },
    SET_IMAGE_INFO: function (state, data) {
      state.imageInfo.drawing = data.drawing
      state.imageInfo.image = data.image
      state.imageInfo.imageSrc = data.imageSrc
      state.imageInfo.filename = data.filename
      state.imageInfo.file = data.file
    },
    CLEAR_IMAGE_INFO: function (state) {
      state.imageInfo.drawing = false
      state.imageInfo.image = ''
      state.imageInfo.imageSrc = ''
      state.imageInfo.filename = ''
      state.imageInfo.file = ''
    },
  },
  actions: {
    setUserInfo: function ({ commit }, data) {
      commit('SET_USER_INFO', data)
    },
    removeUserInfo: function ({ commit }) {
      localStorage.removeItem('jwtToken')
      commit('REMOVE_USER_INFO')
    },
    setImageInfo: function ({ commit }, data) {
      commit('SET_IMAGE_INFO', data)
    },
    clearImageInfo: function ({ commit }) {
      commit('CLEAR_IMAGE_INFO')
    },
  },
  modules: {
  }
})
