import http from './public'
// 登陆
export const userLogin = (params) => {
  return http.fetchPost('/wx/auth/login', params)
}
// 退出登陆
export const loginOut = (params) => {
  return http.fetchPost('/wx/auth/logout', params)
}
// 用户信息
export const checkLogin = (params) => {
  return http.fetchGet('/wx/auth/checkLogin', params)
}
// 注册账号
export const register = (params) => {
  return http.fetchPost('/wx/auth/pregister', params)
}
// 上传图片
export const upload = (params) => {
  return http.fetchPost('/member/imgaeUpload', params)
}
// 修改头像
export const updateheadimage = (params) => {
  return http.fetchPost('/member/updateheadimage', params)
}
// 捐赠列表
export const thanksList = (params) => {
  return http.fetchGet('/member/thanks', params)
}
// 首页接口
export const homeBanner = (params) => {
  return http.fetchGet('/wx/home/pbanner', params)
}
// 首页接口
export const homeFloors = (params) => {
  return http.fetchGet('/wx/home/pfloors', params)
}
// 首页接口
export const navList = (params) => {
  return http.fetchGet('/wx/home/navList', params)
}
// 推荐板块
export const recommend = (params) => {
  return http.fetchGet('/wx/goods/new', params)
}
// 捐赠板块
export const thank = (params) => {
  return http.fetchGet('/goods/thank', params)
}
// 极验验证码
export const geetest = (params) => {
  return http.fetchGet('/member/geetestInit?t=' + (new Date()).getTime(), params)
}

