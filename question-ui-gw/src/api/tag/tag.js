import request from '@/utils/request'

// 查询题目标签库列表
export function listTag(query) {
  return request({
    url: '/www/tag/tag/list',
    method: 'get',
    params: query
  })
}

 // 查询题目标签库名称列表

export function listTagNameList() {
  return request({
    url: '/www/tag/tag/namelist',
    method: 'get'
  })
}


// // 查询题目标签库详细
// export function getTag(id) {
//   return request({
//     url: '/publictag/tag/' + id,
//     method: 'get'
//   })
// }

// 新增题目标签库
export function addTag(data) {
  return request({
    url: '/www/tag/tag',
    method: 'post',
    data: data
  })
}

// 修改题目标签库
export function updateTag(data) {
  return request({
    url: '/www/tag/tag',
    method: 'put',
    data: data
  })
}

// 删除题目标签库
export function delTag(id) {
  return request({
    url: '/www/tag/tag/' + id,
    method: 'delete'
  })
}
