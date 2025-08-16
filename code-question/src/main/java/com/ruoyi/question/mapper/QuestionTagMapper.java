package com.ruoyi.question.mapper;

import java.util.List;
import com.ruoyi.question.domain.QuestionTag;

/**
 * 题目标签库Mapper接口
 * 
 * @author ruoyi
 * @date 2025-08-02
 */
public interface QuestionTagMapper 
{
    /**
     * 查询题目标签库
     * 
     * @param id 题目标签库主键
     * @return 题目标签库
     */
    public QuestionTag selectQuestionTagById(Long id);

    /**
     * 查询题目标签库列表
     * 
     * @param questionTag 题目标签库
     * @return 题目标签库集合
     */
    public List<QuestionTag> selectQuestionTagList(QuestionTag questionTag);

    /**
     * 新增题目标签库
     * 
     * @param questionTag 题目标签库
     * @return 结果
     */
    public int insertQuestionTag(QuestionTag questionTag);

    /**
     * 修改题目标签库
     * 
     * @param questionTag 题目标签库
     * @return 结果
     */
    public int updateQuestionTag(QuestionTag questionTag);

    /**
     * 删除题目标签库
     * 
     * @param id 题目标签库主键
     * @return 结果
     */
    public int deleteQuestionTagById(Long id);

    /**
     * 批量删除题目标签库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestionTagByIds(Long[] ids);

    public List<String> selectQuestionTagNameList();
}
