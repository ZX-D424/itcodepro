package com.ruoyi.question.mapper;

import java.util.List;
import com.ruoyi.question.domain.QuestionManage;

/**
 * 题库管理（主，存储题目基础信息）Mapper接口
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
public interface QuestionManageMapper 
{
    /**
     * 查询题库管理（主，存储题目基础信息）
     * 
     * @param id 题库管理（主，存储题目基础信息）主键
     * @return 题库管理（主，存储题目基础信息）
     */
    public QuestionManage selectQuestionManageById(String id);

    /**
     * 查询题库管理（主，存储题目基础信息）列表
     * 
     * @param questionManage 题库管理（主，存储题目基础信息）
     * @return 题库管理（主，存储题目基础信息）集合
     */
    public List<QuestionManage> selectQuestionManageList(QuestionManage questionManage);

    /**
     * 新增题库管理（主，存储题目基础信息）
     * 
     * @param questionManage 题库管理（主，存储题目基础信息）
     * @return 结果
     */
    public int insertQuestionManage(QuestionManage questionManage);

    /**
     * 修改题库管理（主，存储题目基础信息）
     * 
     * @param questionManage 题库管理（主，存储题目基础信息）
     * @return 结果
     */
    public int updateQuestionManage(QuestionManage questionManage);

    /**
     * 删除题库管理（主，存储题目基础信息）
     * 
     * @param id 题库管理（主，存储题目基础信息）主键
     * @return 结果
     */
    public int deleteQuestionManageById(String id);

    /**
     * 批量删除题库管理（主，存储题目基础信息）
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestionManageByIds(String[] ids);

    public List<String> getQuestionNameList();
}
