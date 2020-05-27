// eslint-disable-next-line no-unused-vars
import {login, logout, getInfo, getMenus} from '@/api/user'
import {getToken, setToken, removeToken} from '@/utils/auth'
import {resetRouter} from '@/router'
import Layout from '@/layout/index'

const state = {
  token: getToken(),
  name: '',
  avatar: ''
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_MENUS: (state, menus) => {
    state.menus = menus
  }

}

const actions = {
  // user login
  login({commit}, userInfo) {
    // eslint-disable-next-line no-unused-vars
    const {username, password} = userInfo
    return new Promise((resolve, reject) => {
       login({ username: username.trim(), password: password }).then(response => {
        commit('SET_TOKEN', response.data.sessionId)
        /* sessionStorage.setItem('user', JSON.stringify(response.data.user));
         //存储sessionID
         sessionStorage.setItem('sessionId', response.data.sessionId);*/
        setToken(response.data.sessionId)
        resolve()
      }).catch(error => {
        reject(error)
      })
      /*const data = {token: 'admin-token'}
      commit('SET_TOKEN', data.token)
      setToken(data.token)
      resolve()*/
    })
  },

  // get user info
  getInfo({commit, state}) {
    return new Promise((resolve, reject) => {
      /*getInfo(state.token).then(response => {
       const { data } = response
       console.log(data)
     }).catch(error => {
       reject(error)
     })*/
      getMenus({id: state.token}).then(response => {
         var menudata = response.resrult
         formatData(menudata)
         const data = { name: '娃哈哈', avatar: '123', menus: menudata }
         const { name, avatar, menus } = data
         commit('SET_NAME', name)
         commit('SET_AVATAR', avatar)
         commit('SET_MENUS', menus)
         resolve(data)

      }).catch(error => {
        reject(error)
      })
    })
  },
  // user logout
  logout({commit, state}) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        removeToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({commit}) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  }

}

function formatData(data) {
  for (let i = 0; i < data.length; i++) {
    if (data[i].children && data[i].children.length > 0) {
      var pathdata = data[i].url.split("/");
      var finalldata = pathdata[pathdata.length-1];
      data[i] = $.extend({}, data[i], {path:finalldata, component: data[i].url, meta: {title: data[i].name, icon: data[i].icon}})
      if (data[i].firstmenuid ==0 ){
        data[i].component = 'Layout'
        data[i].path = '/'+finalldata
      }
      formatData(data[i].children)
    } else {
      var pathdata = data[i].url.split("/");
      var finalldata = pathdata[pathdata.length-1];
      data[i] = $.extend({}, data[i], {path: finalldata, component: data[i].url, meta: {title: data[i].name, icon: data[i].icon}})
      if (data[i].firstmenuid ==0 ){/*一级菜单添加Layout*/
        data[i].component = 'Layout'
        data[i].path = '/'+finalldata
      }
    }
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

