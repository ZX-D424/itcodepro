package com.ruoyi.question.service;

import java.util.List;
import com.ruoyi.question.domain.QuestionTag;

/**
 * 题目标签库Service接口
 * 
 * @author ruoyi
 * @date 2025-08-02
 */
public interface IQuestionTagService 
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
     * 批量删除题目标签库
     * 
     * @param ids 需要删除的题目标签库主键集合
     * @return 结果
     */
    public int deleteQuestionTagByIds(Long[] ids);

    /**
     * 删除题目标签库信息
     * 
     * @param id 题目标签库主键
     * @return 结果
     */
    public int deleteQuestionTagById(Long id);

    public List<String> selectQuestionTagNameList();
}
