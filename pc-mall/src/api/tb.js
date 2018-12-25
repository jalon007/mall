import http from './public'
// 商品列表
export const goodsCoupons = (params) => {
  return http.fetchGet('/tb/goods/coupons', params)
}
