package com.ruoyi.question.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 题库管理（主，存储题目基础信息）对象 question_manage
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
public class QuestionManage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 题目主键ID */
    private Integer id;

    /** 关联题库类目 */
    @Excel(name = "关联题库类目")
    private Integer categoryId;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /** 题库类目 */
    @Excel(name = "题库类目")
    private String category;

    /** 题目标签 */
    @Excel(name = "题目标签")
    private Integer tagIds;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /** 标签 */
    @Excel(name = "标签")
    private String tag;



    /** 题目名称/标题 */
    @Excel(name = "题目名称/标题")
    private String title;

    /** 题目内容（题干、填空描述等） */
    @Excel(name = "题目内容", readConverterExp = "题=干、填空描述等")
    private String content;

    /** 题目类型*/
    @Excel(name = "题目类型")
    private Long questionType;


    /** 排序值（同类目下排序） */
    @Excel(name = "排序值", readConverterExp = "同=类目下排序")
    private Long sort;

    /** 状态：1-启用 2-禁用 */
    @Excel(name = "状态：1-启用 2-禁用")
    private Long status;

    /** 题目来源 */
    @Excel(name = "题目来源")
    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTagIds() {
        return tagIds;
    }

    public void setTagIds(Integer tagIds) {
        this.tagIds = tagIds;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setQuestionType(Long questionType) 
    {
        this.questionType = questionType;
    }

    public Long getQuestionType() 
    {
        return questionType;
    }

    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public void setSource(String source) 
    {
        this.source = source;
    }

    public String getSource() 
    {
        return source;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryId", getCategoryId())
            .append("tagIds", getTagIds())
            .append("title", getTitle())
            .append("content", getContent())
            .append("questionType", getQuestionType())
            .append("sort", getSort())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("source", getSource())
            .toString();
    }


}
