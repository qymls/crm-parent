import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import Element from 'element-ui'
import './styles/element-variables.scss'
import '@/styles/index.scss' // global css
/* iview*/
import ViewUI from 'view-design'
import 'view-design/dist/styles/iview.css'

import App from './App'
import store from './store'
import router from './router'
import i18n from './lang' // internationalization
import './icons' // icon
import './permission' // permission control
import './utils/error-log' // error log
import * as filters from './filters' // global filters
import $ from 'jquery'
Vue.prototype.$ = $/* 添加原型*/
Vue.prototype.ajax = $.ajax/* 添加原型*/

/*Vue.ajax.interceptors.push(function(request, next) {
// modify headers
  request.headers.set('Accept', 'application/json');
  request.headers.set('Authorization', 'Bearer '+ JSON.parse(localStorage.getItem('token')));
// continue to next interceptor
  next();
});*/

import axios from 'axios'/* 引入axios*/
// axios.defaults.baseURL = 'http://localhost:8080/SSM/'
// 把axios赋值给vue里面的$http这个变量
Vue.prototype.$http = axios

//拦截器
/*axios.interceptors.request.use(config => {
  if (sessionStorage.getItem('sessionId')) {
    // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
    config.headers['X-Token'] = sessionStorage.getItem('sessionId')
  }
  console.log('config',config)
  return config
}, error => {
  // Do something with request error
  Promise.reject(error)
})*/

/*
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}
*/

Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})

Vue.use(ViewUI)

// register global utility filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)
})
