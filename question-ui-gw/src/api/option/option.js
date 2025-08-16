import request from '@/utils/request'



// api/option/option.js
// 根据题目ID获取选项
export function getOptionByQuestionId(questionId) {
  return request({
    url: '/www/option/option/list',
    method: 'get',
    params: { questionId }
  });
}

// api/answer/answer.js
// 根据题目ID获取答案
export function getAnswerByQuestionId(questionId) {
  return request({
    url: '/www/answer/answer/list',
    method: 'get',
    params: { questionId }
  });
}
