import request from '@/utils/request'

// 查询题库管理（主，存储题目基础信息）列表
export function listManage(query) {
  return request({
    url: '/www/manage/manage/list',
    method: 'get',
    params: query
  })
}

// 查询题库管理（主，存储题目基础信息）详细
export function getManage(id) {
  return request({
    url: '/www/manage/manage/' + id,
    method: 'get'
  })
}

// 查询题库管理（主，存储题目基础信息）选项列表
export function listManageTitle() {
  return request({
    url: '/www/manage/manage/getQuestionNameList',
    method: 'get',
  })
}



// 新增题库管理（主，存储题目基础信息）
export function addManage(data) {
  return request({
    url: '/www/manage/manage',
    method: 'post',
    data: data
  })
}

// 修改题库管理（主，存储题目基础信息）
export function updateManage(data) {
  return request({
    url: '/www/manage/manage',
    method: 'put',
    data: data
  })
}

// 删除题库管理（主，存储题目基础信息）
export function delManage(id) {
  return request({
    url: '/www/manage/manage/' + id,
    method: 'delete'
  })
}



