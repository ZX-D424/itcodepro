package com.ruoyi.question.mapper;

import java.util.List;
import com.ruoyi.question.domain.QuestionCategory;

/**
 * 题库类目Mapper接口
 * 
 * @author ruoyi
 * @date 2025-08-02
 */
public interface QuestionCategoryMapper 
{
    /**
     * 查询题库类目
     * 
     * @param id 题库类目主键
     * @return 题库类目
     */
    public QuestionCategory selectQuestionCategoryById(Long id);

    /**
     * 查询题库类目列表
     * 
     * @param questionCategory 题库类目
     * @return 题库类目集合
     */
    public List<QuestionCategory> selectQuestionCategoryList(QuestionCategory questionCategory);

    /**
     * 新增题库类目
     * 
     * @param questionCategory 题库类目
     * @return 结果
     */
    public int insertQuestionCategory(QuestionCategory questionCategory);

    /**
     * 修改题库类目
     * 
     * @param questionCategory 题库类目
     * @return 结果
     */
    public int updateQuestionCategory(QuestionCategory questionCategory);

    /**
     * 删除题库类目
     * 
     * @param id 题库类目主键
     * @return 结果
     */
    public int deleteQuestionCategoryById(Long id);

    /**
     * 批量删除题库类目
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestionCategoryByIds(Long[] ids);

    public List<String> selectQuestionCategoryNameList();
}
