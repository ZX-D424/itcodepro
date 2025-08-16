package com.ruoyi.question.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.question.mapper.QuestionSectionMapper;
import com.ruoyi.question.domain.QuestionSection;
import com.ruoyi.question.service.IQuestionSectionService;

/**
 * 栏目Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-01
 */
@Service
public class QuestionSectionServiceImpl implements IQuestionSectionService 
{
    @Autowired
    private QuestionSectionMapper questionSectionMapper;

    /**
     * 查询栏目
     * 
     * @param id 栏目主键
     * @return 栏目
     */
    @Override
    public QuestionSection selectQuestionSectionById(Long id)
    {
        return questionSectionMapper.selectQuestionSectionById(id);
    }

    /**
     * 查询栏目列表
     * 
     * @param questionSection 栏目
     * @return 栏目
     */
    @Override
    public List<QuestionSection> selectQuestionSectionList(QuestionSection questionSection)
    {
        return questionSectionMapper.selectQuestionSectionList(questionSection);
    }

    /**
     * 新增栏目
     * 
     * @param questionSection 栏目
     * @return 结果
     */
    @Override
    public int insertQuestionSection(QuestionSection questionSection)
    {
        String username = SecurityUtils.getUsername();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username1 = loginUser.getUsername();
        questionSection.setCreateBy(username1);
        questionSection.setCreateTime(DateUtils.getNowDate());
        return questionSectionMapper.insertQuestionSection(questionSection);
    }

    /**
     * 修改栏目
     * 
     * @param questionSection 栏目
     * @return 结果
     */
    @Override
    public int updateQuestionSection(QuestionSection questionSection)
    {
        String username = SecurityUtils.getUsername();
        // 获取当前用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username1 = loginUser.getUsername();
        questionSection.setUpdateBy(username1);
        questionSection.setUpdateTime(DateUtils.getNowDate());
        return questionSectionMapper.updateQuestionSection(questionSection);
    }

    /**
     * 批量删除栏目
     * 
     * @param ids 需要删除的栏目主键
     * @return 结果
     */
    @Override
    public int deleteQuestionSectionByIds(Long[] ids)
    {
        return questionSectionMapper.deleteQuestionSectionByIds(ids);
    }

    /**
     * 删除栏目信息
     * 
     * @param id 栏目主键
     * @return 结果
     */
    @Override
    public int deleteQuestionSectionById(Long id)
    {
        return questionSectionMapper.deleteQuestionSectionById(id);
    }
}
