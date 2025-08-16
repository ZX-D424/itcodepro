import request from '@/utils/request'; // 确保路径正确

export function listSection(query) {
  return request({
    url: '/www/section/section/list',
    method: 'get',
    params: query
  });
}

// // 查询题库类目列表
// export function listCategoryNameList() {
//   return request({
//     url: '/category/category/namelist',
//     method: 'get',
//   })
// }

// // 查询题库类目详细
// export function getCategory(id) {
//   return request({
//     url: '/category/category/' + id,
//     method: 'get'
//   })
// }

// // 新增题库类目
// export function addCategory(data) {
//   return request({
//     url: '/category/category',
//     method: 'post',
//     data: data
//   })
// }

// // 修改题库类目
// export function updateCategory(data) {
//   return request({
//     url: '/category/category',
//     method: 'put',
//     data: data
//   })
// }

// // 删除题库类目
// export function delCategory(id) {
//   return request({
//     url: '/category/category/' + id,
//     method: 'delete'
//   })
// }
