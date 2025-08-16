package com.ruoyi.question.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.question.mapper.QuestionCategoryMapper;
import com.ruoyi.question.domain.QuestionCategory;
import com.ruoyi.question.service.IQuestionCategoryService;

/**
 * 题库类目Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-02
 */
@Service
public class QuestionCategoryServiceImpl implements IQuestionCategoryService
{
    @Autowired
    private QuestionCategoryMapper questionCategoryMapper;

    /**
     * 查询题库类目
     * 
     * @param id 题库类目主键
     * @return 题库类目
     */
    @Override
    public QuestionCategory selectQuestionCategoryById(Long id)
    {
        return questionCategoryMapper.selectQuestionCategoryById(id);
    }

    /**
     * 查询题库类目列表
     * 
     * @param questionCategory 题库类目
     * @return 题库类目
     */
    @Override
    public List<QuestionCategory> selectQuestionCategoryList(QuestionCategory questionCategory)
    {
        return questionCategoryMapper.selectQuestionCategoryList(questionCategory);
    }

    /**
     * 新增题库类目
     * 
     * @param questionCategory 题库类目
     * @return 结果
     */
    @Override
    public int insertQuestionCategory(QuestionCategory questionCategory)
    {
        String username = SecurityUtils.getUsername();
        // 获取当前用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username1 = loginUser.getUsername();
        questionCategory.setCreateBy(username1);
        questionCategory.setCreateTime(DateUtils.getNowDate());
        return questionCategoryMapper.insertQuestionCategory(questionCategory);
    }

    /**
     * 修改题库类目
     * 
     * @param questionCategory 题库类目
     * @return 结果
     */
    @Override
    public int updateQuestionCategory(QuestionCategory questionCategory)
    {
        questionCategory.setUpdateTime(DateUtils.getNowDate());
        return questionCategoryMapper.updateQuestionCategory(questionCategory);
    }

    /**
     * 批量删除题库类目
     * 
     * @param ids 需要删除的题库类目主键
     * @return 结果
     */
    @Override
    public int deleteQuestionCategoryByIds(Long[] ids)
    {
        return questionCategoryMapper.deleteQuestionCategoryByIds(ids);
    }

    /**
     * 删除题库类目信息
     * 
     * @param id 题库类目主键
     * @return 结果
     */
    @Override
    public int deleteQuestionCategoryById(Long id)
    {
        return questionCategoryMapper.deleteQuestionCategoryById(id);
    }

    @Override
    public List<String> selectQuestionCategoryNameList()
        {
        return questionCategoryMapper.selectQuestionCategoryNameList();
        }
}
