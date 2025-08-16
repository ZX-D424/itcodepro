package com.ruoyi.question.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.question.mapper.QuestionOptionMapper;
import com.ruoyi.question.domain.QuestionOption;
import com.ruoyi.question.service.IQuestionOptionService;

/**
 * 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
@Service
public class QuestionOptionServiceImpl implements IQuestionOptionService 
{
    @Autowired
    private QuestionOptionMapper questionOptionMapper;

    /**
     * 查询题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * 
     * @param id 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）主键
     * @return 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     */
    @Override
    public QuestionOption selectQuestionOptionById(String id)
    {
        return questionOptionMapper.selectQuestionOptionById(id);
    }

    /**
     * 查询题目选项（支持单选/多选/判断题，题目删除需级联删除选项）列表
     * 
     * @param questionOption 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * @return 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     */
    @Override
    public List<QuestionOption> selectQuestionOptionList(QuestionOption questionOption)
    {
        return questionOptionMapper.selectQuestionOptionList(questionOption);
    }

    /**
     * 新增题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * 
     * @param questionOption 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * @return 结果
     */
    @Override
    public int insertQuestionOption(QuestionOption questionOption)
    {
        String username = SecurityUtils.getUsername();
        // 获取当前用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username1 = loginUser.getUsername();
        questionOption.setCreateBy(username1);
        questionOption.setCreateTime(DateUtils.getNowDate());
        return questionOptionMapper.insertQuestionOption(questionOption);
    }

    /**
     * 修改题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * 
     * @param questionOption 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * @return 结果
     */
    @Override
    public int updateQuestionOption(QuestionOption questionOption)
    {
        questionOption.setUpdateTime(DateUtils.getNowDate());
        return questionOptionMapper.updateQuestionOption(questionOption);
    }

    /**
     * 批量删除题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * 
     * @param ids 需要删除的题目选项（支持单选/多选/判断题，题目删除需级联删除选项）主键
     * @return 结果
     */
    @Override
    public int deleteQuestionOptionByIds(String[] ids)
    {
        return questionOptionMapper.deleteQuestionOptionByIds(ids);
    }

    /**
     * 删除题目选项（支持单选/多选/判断题，题目删除需级联删除选项）信息
     * 
     * @param id 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）主键
     * @return 结果
     */
    @Override
    public int deleteQuestionOptionById(String id)
    {
        return questionOptionMapper.deleteQuestionOptionById(id);
    }


}
