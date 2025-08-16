package com.ruoyi.question.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.question.mapper.QuestionAnswerMapper;
import com.ruoyi.question.domain.QuestionAnswer;
import com.ruoyi.question.service.IQuestionAnswerService;

/**
 * 题库答案（支持多题型答案存储，题目删除需级联删除答案）Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
@Service
public class QuestionAnswerServiceImpl implements IQuestionAnswerService 
{
    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    /**
     * 查询题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * 
     * @param id 题库答案（支持多题型答案存储，题目删除需级联删除答案）主键
     * @return 题库答案（支持多题型答案存储，题目删除需级联删除答案）
     */
    @Override
    public QuestionAnswer selectQuestionAnswerById(String id)
    {
        return questionAnswerMapper.selectQuestionAnswerById(id);
    }

    /**
     * 查询题库答案（支持多题型答案存储，题目删除需级联删除答案）列表
     * 
     * @param questionAnswer 题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * @return 题库答案（支持多题型答案存储，题目删除需级联删除答案）
     */
    @Override
    public List<QuestionAnswer> selectQuestionAnswerList(QuestionAnswer questionAnswer)
    {
        return questionAnswerMapper.selectQuestionAnswerList(questionAnswer);
    }

    /**
     * 新增题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * 
     * @param questionAnswer 题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * @return 结果
     */
    @Override
    public int insertQuestionAnswer(QuestionAnswer questionAnswer)
    {
        String username = SecurityUtils.getUsername();
        // 获取当前用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username1 = loginUser.getUsername();
        questionAnswer.setCreateBy(username1);
        questionAnswer.setCreateTime(DateUtils.getNowDate());
        return questionAnswerMapper.insertQuestionAnswer(questionAnswer);
    }

    /**
     * 修改题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * 
     * @param questionAnswer 题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * @return 结果
     */
    @Override
    public int updateQuestionAnswer(QuestionAnswer questionAnswer)
    {
        questionAnswer.setUpdateTime(DateUtils.getNowDate());
        return questionAnswerMapper.updateQuestionAnswer(questionAnswer);
    }

    /**
     * 批量删除题库答案（支持多题型答案存储，题目删除需级联删除答案）
     * 
     * @param ids 需要删除的题库答案（支持多题型答案存储，题目删除需级联删除答案）主键
     * @return 结果
     */
    @Override
    public int deleteQuestionAnswerByIds(String[] ids)
    {
        return questionAnswerMapper.deleteQuestionAnswerByIds(ids);
    }

    /**
     * 删除题库答案（支持多题型答案存储，题目删除需级联删除答案）信息
     * 
     * @param id 题库答案（支持多题型答案存储，题目删除需级联删除答案）主键
     * @return 结果
     */
    @Override
    public int deleteQuestionAnswerById(String id)
    {
        return questionAnswerMapper.deleteQuestionAnswerById(id);
    }
}
