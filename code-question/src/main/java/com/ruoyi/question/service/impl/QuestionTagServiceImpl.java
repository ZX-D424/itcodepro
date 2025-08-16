package com.ruoyi.question.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.question.mapper.QuestionTagMapper;
import com.ruoyi.question.domain.QuestionTag;
import com.ruoyi.question.service.IQuestionTagService;

/**
 * 题目标签库Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-08-02
 */
@Service
public class QuestionTagServiceImpl implements IQuestionTagService 
{
    @Autowired
    private QuestionTagMapper questionTagMapper;

    /**
     * 查询题目标签库
     * 
     * @param id 题目标签库主键
     * @return 题目标签库
     */
    @Override
    public QuestionTag selectQuestionTagById(Long id)
    {
        return questionTagMapper.selectQuestionTagById(id);
    }

    /**
     * 查询题目标签库列表
     * 
     * @param questionTag 题目标签库
     * @return 题目标签库
     */
    @Override
    public List<QuestionTag> selectQuestionTagList(QuestionTag questionTag)
    {
        return questionTagMapper.selectQuestionTagList(questionTag);
    }

    /**
     * 新增题目标签库
     * 
     * @param questionTag 题目标签库
     * @return 结果
     */
    @Override
    public int insertQuestionTag(QuestionTag questionTag)
    {
        String username = SecurityUtils.getUsername();
        // 获取当前用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username1 = loginUser.getUsername();
        questionTag.setCreateBy(username1);
        questionTag.setCreateTime(DateUtils.getNowDate());
        return questionTagMapper.insertQuestionTag(questionTag);
    }

    /**
     * 修改题目标签库
     * 
     * @param questionTag 题目标签库
     * @return 结果
     */
    @Override
    public int updateQuestionTag(QuestionTag questionTag)
    {
        questionTag.setUpdateTime(DateUtils.getNowDate());
        return questionTagMapper.updateQuestionTag(questionTag);
    }

    /**
     * 批量删除题目标签库
     * 
     * @param ids 需要删除的题目标签库主键
     * @return 结果
     */
    @Override
    public int deleteQuestionTagByIds(Long[] ids)
    {
        return questionTagMapper.deleteQuestionTagByIds(ids);
    }

    /**
     * 删除题目标签库信息
     * 
     * @param id 题目标签库主键
     * @return 结果
     */
    @Override
    public int deleteQuestionTagById(Long id)
    {
        return questionTagMapper.deleteQuestionTagById(id);
    }


    @Override
    public List<String> selectQuestionTagNameList(){
        return questionTagMapper.selectQuestionTagNameList();
    }
}
