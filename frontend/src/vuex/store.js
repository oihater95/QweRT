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
      id: '',
      nickname: '',
      profileImage: '',
    },
  },
  mutations: {
    SETUSERINFO: function (state, data) {
      state.userInfo.id = data.user_id
      state.userInfo.nickname = data.nickname
      state.userInfo.profileImage = data.profile_image
    },
  },
  actions: {
    setUserInfo: function ({ commit }, data) {
      localStorage.setItem('jwtToken', data.token)
      commit('SETUSERINFO', data)
    },
  },
  modules: {
  }
})
