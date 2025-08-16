package com.ruoyi.question.service;

import java.util.List;
import com.ruoyi.question.domain.QuestionAnswer;

/**
 * 题库答案（支持多题型答案存储，题目删除需级联删除答案）Service接口
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
public interface IQuestionAnswerService 
{
    /**
     * 查询题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * 
     * @param id 题库答案（支持多题型答案存储，题目删除需级联删除答案）主键
     * @return 题库答案（支持多题型答案存储，题目删除需级联删除答案）
     */
    public QuestionAnswer selectQuestionAnswerById(String id);

    /**
     * 查询题库答案（支持多题型答案存储，题目删除需级联删除答案）列表
     * 
     * @param questionAnswer 题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * @return 题库答案（支持多题型答案存储，题目删除需级联删除答案）集合
     */
    public List<QuestionAnswer> selectQuestionAnswerList(QuestionAnswer questionAnswer);

    /**
     * 新增题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * 
     * @param questionAnswer 题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * @return 结果
     */
    public int insertQuestionAnswer(QuestionAnswer questionAnswer);

    /**
     * 修改题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * 
     * @param questionAnswer 题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * @return 结果
     */
    public int updateQuestionAnswer(QuestionAnswer questionAnswer);

    /**
     * 批量删除题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * 
     * @param ids 需要删除的题库答案（支持多题型答案存储，题目删除需级联删除答案）主键集合
     * @return 结果
     */
    public int deleteQuestionAnswerByIds(String[] ids);

    /**
     * 删除题库答案（支持多题型答案存储，题目删除需级联删除答案）信息
     * 
     * @param id 题库答案（支持多题型答案存储，题目删除需级联删除答案）主键
     * @return 结果
     */
    public int deleteQuestionAnswerById(String id);
}
