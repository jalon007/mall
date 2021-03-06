import http from './public'
// 商品列表
export const getpptm = (params) => {
  return http.fetchGet('/tb/goods/getpptm', params)
}
// 获取购物车列表
export const getCartList = (params) => {
  return http.fetchGet('/wx/cart/index', params)
}
// 加入购物车
export const addCart = (params) => {
  return http.fetchPost('/wx/cart/add', params)
}
// 删除购物车
export const delCart = (params) => {
  return http.fetchPost('/member/delCart', params)
}
// 删除购物车勾选商品
export const delCartChecked = (params) => {
  return http.fetchPost('/member/delCartChecked', params)
}
// 编辑购物车
export const cartEdit = (params) => {
  return http.fetchPost('/member/cartEdit', params)
}
// 全选
export const editCheckAll = (params) => {
  return http.fetchPost('/member/editCheckAll', params)
}
// 删除整条购物车
export const cartDel = (params) => {
  return http.fetchPost('/member/cartDel', params)
}
// 获取用户地址
export const addressList = (params) => {
  return http.fetchGet('/wx/address/list', params)
}
// 通过id获取地址
export const getAddress = (params) => {
  return http.fetchPost('/member/address', params)
}
// 修改收货地址
export const addressUpdate = (params) => {
  return http.fetchPost('/member/updateAddress', params)
}
// 添加收货地址
export const addressAdd = (params) => {
  return http.fetchPost('/member/addAddress', params)
}
// 删除收货地址
export const addressDel = (params) => {
  return http.fetchPost('/member/delAddress', params)
}
// 生成订单
export const submitOrder = (params) => {
  return http.fetchPost('/member/addOrder', params)
}
// 支付
export const payMent = (params) => {
  return http.fetchPost('/member/payOrder', params)
}
// 获取用户订单
export const orderList = (params) => {
  return http.fetchGet('/member/orderList', params)
}
// 获取单个订单详情
export const getOrderDet = (params) => {
  return http.fetchGet('/member/orderDetail', params)
}
// 取消订单
export const cancelOrder = (params) => {
  return http.fetchPost('/member/cancelOrder', params)
}
// 商品详情
export const productDet = (params) => {
  return http.fetchGet('/wx/goods/detail', params)
}
// 删除订单
export const delOrder = (params) => {
  return http.fetchGet('/member/delOrder', params)
}
// 商品列表
export const getSearch = (params) => {
  return http.fetchGet('/goods/search', params)
}
export const getSearchGoods = (params) => {
  return http.fetchGet('/wx/goods/list', params)
}
// 快速搜索
export const getQuickSearch = (params) => {
  return http.fetchGet('/wx/search/helper', params)
}
// 商品分类
export const catagory = (params) => {
  return http.fetchGet('/wx/catalog/index', params)
}
// 品牌列表
export const getBrandList = (params) => {
  return http.fetchGet('/wx/brand/list', params)
}
// 品牌详情
export const getBrandDetail = (params) => {
  return http.fetchGet('/wx/brand/detail', params)
}
// 专题列表
export const getTopicList = (params) => {
  return http.fetchGet('/wx/topic/list', params)
}
// 专题详情
export const getTopicDetail = (params) => {
  return http.fetchGet('/wx/topic/detail', params)
}
// 左侧导航
export const getCategory = (params) => {
  return http.fetchGet('/wx/catalog/pall', params)
}
export const getNotions = (params) => {
  return http.fetchGet('/wx/home/pnotions', params)
}
