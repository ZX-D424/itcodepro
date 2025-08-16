package com.ruoyi.question.mapper;

import java.util.List;
import com.ruoyi.question.domain.QuestionSection;

/**
 * 栏目Mapper接口
 * 
 * @author ruoyi
 * @date 2025-08-01
 */
public interface QuestionSectionMapper 
{
    /**
     * 查询栏目
     * 
     * @param id 栏目主键
     * @return 栏目
     */
    public QuestionSection selectQuestionSectionById(Long id);

    /**
     * 查询栏目列表
     * 
     * @param questionSection 栏目
     * @return 栏目集合
     */
    public List<QuestionSection> selectQuestionSectionList(QuestionSection questionSection);

    /**
     * 新增栏目
     * 
     * @param questionSection 栏目
     * @return 结果
     */
    public int insertQuestionSection(QuestionSection questionSection);

    /**
     * 修改栏目
     * 
     * @param questionSection 栏目
     * @return 结果
     */
    public int updateQuestionSection(QuestionSection questionSection);

    /**
     * 删除栏目
     * 
     * @param id 栏目主键
     * @return 结果
     */
    public int deleteQuestionSectionById(Long id);

    /**
     * 批量删除栏目
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestionSectionByIds(Long[] ids);
}
