package com.ruoyi.question.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.question.mapper.QuestionManageMapper;
import com.ruoyi.question.domain.QuestionManage;
import com.ruoyi.question.service.IQuestionManageService;

/**
 * 题库管理（主，存储题目基础信息）Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
@Service
public class QuestionManageServiceImpl implements IQuestionManageService 
{
    @Autowired
    private QuestionManageMapper questionManageMapper;

    /**
     * 查询题库管理（主，存储题目基础信息）
     * 
     * @param id 题库管理（主，存储题目基础信息）主键
     * @return 题库管理（主，存储题目基础信息）
     */
    @Override
    public QuestionManage selectQuestionManageById(String id)
    {
        return questionManageMapper.selectQuestionManageById(id);
    }

    /**
     * 查询题库管理（主，存储题目基础信息）列表
     * 
     * @param questionManage 题库管理（主，存储题目基础信息）
     * @return 题库管理（主，存储题目基础信息）
     */
    @Override
    public List<QuestionManage> selectQuestionManageList(QuestionManage questionManage)
    {
        return questionManageMapper.selectQuestionManageList(questionManage);
    }

    /**
     * 新增题库管理（主，存储题目基础信息）
     * 
     * @param questionManage 题库管理（主，存储题目基础信息）
     * @return 结果
     */
    @Override
    public int insertQuestionManage(QuestionManage questionManage)
    {
        String username = SecurityUtils.getUsername();
        // 获取当前用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username1 = loginUser.getUsername();
        questionManage.setCreateBy(username1);
        questionManage.setCreateTime(DateUtils.getNowDate());
        return questionManageMapper.insertQuestionManage(questionManage);
    }

    /**
     * 修改题库管理（主，存储题目基础信息）
     * 
     * @param questionManage 题库管理（主，存储题目基础信息）
     * @return 结果
     */
    @Override
    public int updateQuestionManage(QuestionManage questionManage)
    {
        questionManage.setUpdateTime(DateUtils.getNowDate());
        return questionManageMapper.updateQuestionManage(questionManage);
    }

    /**
     * 批量删除题库管理（主，存储题目基础信息）
     * 
     * @param ids 需要删除的题库管理（主，存储题目基础信息）主键
     * @return 结果
     */
    @Override
    public int deleteQuestionManageByIds(String[] ids)
    {
        return questionManageMapper.deleteQuestionManageByIds(ids);
    }

    /**
     * 删除题库管理（主，存储题目基础信息）信息
     * 
     * @param id 题库管理（主，存储题目基础信息）主键
     * @return 结果
     */
    @Override
    public int deleteQuestionManageById(String id)
    {
        return questionManageMapper.deleteQuestionManageById(id);
    }


    @Override
    public List<String> getQuestionNameList() {
        return questionManageMapper.getQuestionNameList();
    }
}
