import request from '@/utils/request'

// 查询题库答案（支持多题型答案存储，题目删除需级联删除答案）列表
export function listAnswer(query) {
  return request({
    url: '/www/answer/answer/list',
    method: 'get',
    params: query
  })
}

// 查询题库答案（支持多题型答案存储，题目删除需级联删除答案）详细
export function getAnswer(id) {
  return request({
    url: '/www/answer/answer/' + id,
    method: 'get'
  })
}

// 新增题库答案（支持多题型答案存储，题目删除需级联删除答案）
export function addAnswer(data) {
  return request({
    url: '/www/answer/answer',
    method: 'post',
    data: data
  })
}

// 修改题库答案（支持多题型答案存储，题目删除需级联删除答案）
export function updateAnswer(data) {
  return request({
    url: '/www/answer/answer',
    method: 'put',
    data: data
  })
}

// 删除题库答案（支持多题型答案存储，题目删除需级联删除答案）
export function delAnswer(id) {
  return request({
    url: '/www/answer/answer/' + id,
    method: 'delete'
  })
}
