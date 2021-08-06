import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState(),
  ],
  state: {
    isLogon: false,
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
      state.isLogon = true
    },
    REMOVEUSERINFO: function (state) {
      state.userInfo.userId = ''
      state.userInfo.nickname = ''
      state.userInfo.profileImage = ''
      state.isLogon = false
    },
  },
  actions: {
    setUserInfo: function ({ commit }, data) {
      commit('SETUSERINFO', data)
    },
    removeUserInfo: function ({ commit }) {
      localStorage.removeItem('jwtToken')
      commit('REMOVEUSERINFO')
    },
  },
  modules: {
  }
})
