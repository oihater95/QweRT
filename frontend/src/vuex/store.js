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
    postingInfo: {
      userId: '',
      postingImage: '',
      postingTitle: '',
      postingContent: '',
      categoryId: '',
    },
  },
  mutations: {
    SETUSERINFO: function (state, data) {
      state.userInfo.userId = data.userId
      state.userInfo.nickname = data.nickname
      state.userInfo.profileImage = data.profileImage
      state.isLogon = true
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
