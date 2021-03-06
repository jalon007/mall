import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/'
import VueLazyload from 'vue-lazyload'
import infiniteScroll from 'vue-infinite-scroll'
import VueCookie from 'vue-cookie'
import { checkLogin } from './api'
import { Button, Pagination, Checkbox, Icon, Autocomplete, Loading, Message, Notification, Steps, Step, Table, TableColumn, Input, Dialog, Select, Option } from 'element-ui'
// import { getStore } from '/utils/storage'
import VueContentPlaceholders from 'vue-content-placeholders'
import animated from 'animate.css'
Vue.use(VueContentPlaceholders)
Vue.use(Button)
Vue.use(Pagination)
Vue.use(Checkbox)
Vue.use(Icon)
Vue.use(Autocomplete)
Vue.use(Steps)
Vue.use(Step)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Input)
Vue.use(Dialog)
Vue.use(Select)
Vue.use(Option)
Vue.use(Loading.directive)
Vue.use(animated)
Vue.prototype.$loading = Loading.service
Vue.prototype.$notify = Notification
Vue.prototype.$message = Message
Vue.use(infiniteScroll)
Vue.use(VueCookie)
Vue.use(VueLazyload, {
  // preLoad: 1.3,
  // error: 'dist/error.png',
  // loading: '/static/images/load.gif'
  // attempt: 1
})
Vue.config.productionTip = false
// 不需要登陆的页面
const whiteList = [
  '/home',
  '/goods',
  '/login',
  '/register',
  '/goodsDetails',
  'brands', 'brandsDetails', 'topics', 'topicsDetails',
  '/thanks',
  '/search',
  '/refreshsearch',
  '/refreshbrands',
  '/refreshtopics',
  '/refreshgoods']
router.beforeEach(function (to, from, next) {
  checkLogin().then(res => {
    if (to.path) {
      window._hmt.push(['_trackPageview', '/#' + to.fullPath])
    }
    if (res.data.state === true) { // 登录状态
      store.commit('RECORD_USERINFO', {info: res.data})
      if (to.path === '/login') { //  跳转到
        next({path: '/'})
      }
      next()
    } else { // 未登录状态
      if (whiteList.indexOf(to.path) !== -1) { // 白名单
        next()
      } else {
        next('/login')
      }
    }
  })
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  render: h => h(App)
})
