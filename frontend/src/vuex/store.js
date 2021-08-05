import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState(),
  ],
  state: {
    userInfo: {
      userId: '',
      nickname: '',
      profileImage: '',
    },
  },
  mutations: {
    SETUSERINFO: function (state, data) {
      state.userInfo.userId = data.userId
      state.userInfo.nickname = data.nickname
      state.userInfo.profileImage = data.profileImage
    },
  },
  actions: {
    setUserInfo: function ({ commit }, data) {
      commit('SETUSERINFO', data)
    },
  },
  modules: {
  }
})
