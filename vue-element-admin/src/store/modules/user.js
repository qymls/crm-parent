// eslint-disable-next-line no-unused-vars
import { login, logout, getInfo ,getMenus} from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
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
  login({ commit }, userInfo) {
    // eslint-disable-next-line no-unused-vars
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      /* login({ username: username.trim(), password: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })*/
      const data = { token: 'admin-token' }
      commit('SET_TOKEN', data.token)
      setToken(data.token)
      resolve()
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      /*getInfo(state.token).then(response => {
       const { data } = response
       console.log(data)
     }).catch(error => {
       reject(error)
     })*/
      getMenus({ id: '2'}).then(response => {
        console.log(response)
      }).catch(error => {
        reject(error)
      })
      const menusDate = [{
        path: '/nested',
        component: 'Layout',
        redirect: '/nested/menu1',
        name: 'Nested',
        meta: {
          title: 'Nested',
          icon: 'nested'
        },
        children: [
          {
            path: 'menu1',
            component: 'nested/menu1/index',
            name: 'menu1',
            meta: { title: 'menu1' },
            children: [
              {
                path: 'menu1-1',
                component: 'nested/menu1/menu1-1',
                name: 'Menu1-1',
                meta: { title: 'Menu1-1' }
              },
              {
                path: 'menu1-2',
                component: 'nested/menu1/menu1-2',
                name: 'Menu1-2',
                meta: { title: 'Menu1-2' },
                children: [
                  {
                    path: 'menu1-2-1',
                    component: 'nested/menu1/menu1-2/menu1-2-1',
                    name: 'Menu1-2-1',
                    meta: { title: 'Menu1-2-1' }
                  },
                  {
                    path: 'menu1-2-2',
                    component: 'nested/menu1/menu1-2/menu1-2-2',
                    name: 'Menu1-2-2',
                    meta: { title: 'Menu1-2-2' }
                  }
                ]
              },
              {
                path: 'menu1-3',
                component: 'nested/menu1/menu1-3',
                name: 'Menu1-3',
                meta: { title: 'Menu1-3' }
              }
            ]
          },
          {
            path: '/menu2',
            component: 'nested/menu2',
            name: 'menu2',
            meta: { title: 'menu2', icon: 'dashboard' }
          }
        ]
      },
        {
          path: 'external-link',
          component: 'Layout',
          children: [
            {
              path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
              meta: { title: 'External Link', icon: 'link' }
            }
          ]
        }]
      const data = { name: '娃哈哈', avatar: '123', menus: menusDate }
      const { name, avatar, menus } = data
      commit('SET_NAME', name)
      commit('SET_AVATAR', avatar)
      commit('SET_MENUS', menus)
      resolve(data)
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      /*      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        removeToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })*/
      commit('SET_TOKEN', '')
      removeToken()
      resetRouter()
      resolve()
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  }

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

