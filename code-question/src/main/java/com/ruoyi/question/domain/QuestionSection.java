package com.ruoyi.question.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 栏目对象 question_section
 * 
 * @author ruoyi
 * @date 2025-08-01
 */
public class QuestionSection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 主键 */
    private Integer id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 排序值 */
    @Excel(name = "排序值")
    private Long sortOrder;

    /** 显示/隐藏 */
    @Excel(name = "显示/隐藏")
    private Integer isHidden;

    /** 外连 */
    @Excel(name = "外连")
    private String externalUrl;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;



    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
    }

    public void setIsHidden(Integer isHidden) 
    {
        this.isHidden = isHidden;
    }

    public Integer getIsHidden() 
    {
        return isHidden;
    }

    public void setExternalUrl(String externalUrl) 
    {
        this.externalUrl = externalUrl;
    }

    public String getExternalUrl() 
    {
        return externalUrl;
    }

    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("sortOrder", getSortOrder())
            .append("isHidden", getIsHidden())
            .append("externalUrl", getExternalUrl())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
