package com.ruoyi.question.mapper;

import java.util.List;
import com.ruoyi.question.domain.QuestionOption;

/**
 * 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）Mapper接口
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
public interface QuestionOptionMapper 
{
    /**
     * 查询题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * 
     * @param id 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）主键
     * @return 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     */
    public QuestionOption selectQuestionOptionById(String id);

    /**
     * 查询题目选项（支持单选/多选/判断题，题目删除需级联删除选项）列表
     * 
     * @param questionOption 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * @return 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）集合
     */
    public List<QuestionOption> selectQuestionOptionList(QuestionOption questionOption);

    /**
     * 新增题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * 
     * @param questionOption 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * @return 结果
     */
    public int insertQuestionOption(QuestionOption questionOption);

    /**
     * 修改题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * 
     * @param questionOption 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * @return 结果
     */
    public int updateQuestionOption(QuestionOption questionOption);

    /**
     * 删除题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * 
     * @param id 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）主键
     * @return 结果
     */
    public int deleteQuestionOptionById(String id);

    /**
     * 批量删除题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestionOptionByIds(String[] ids);
}
