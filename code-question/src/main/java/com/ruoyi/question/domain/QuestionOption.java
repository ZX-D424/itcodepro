package com.ruoyi.question.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）对象 question_option
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
public class QuestionOption extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 选项主键ID */
    private Integer id;

    /** 关联题目 */
    @Excel(name = "关联题目")
    private Integer questionId;

    /** 关联题目名字 */
    @Excel(name = "关联题目名字")
    private String question;

    /** 选项编码（如"A"、"B"、"√"、"×"） */
    @Excel(name = "选项编码")
//    readConverterExp = "如="A"、"B"、"√"、"×"")
    private String optionCode;

    /** 选项内容（支持富文本/公式等） */
    @Excel(name = "选项内容", readConverterExp = "支=持富文本/公式等")
    private String optionContent;

    /** 是否正确答案：1-是 0-否 */
    @Excel(name = "是否正确答案：1-是 0-否")
    private Integer isCorrect;

    /** 分值权重（正确选项为正值，干扰项可为负值） */
    @Excel(name = "分值权重", readConverterExp = "正=确选项为正值，干扰项可为负值")
    private BigDecimal scoreWeight;

    /** 是否部分正确（用于复杂评分模型） */
    @Excel(name = "是否部分正确", readConverterExp = "用=于复杂评分模型")
    private Integer isPartiallyCorrect;

    /** 选项排序（升序排列，0最小） */
    @Excel(name = "选项排序", readConverterExp = "升=序排列，0最小")
    private String sort;

    /** 选项组（如"材料1"关联选项） */
    @Excel(name = "选项组")
//    , readConverterExp = "如="材料1"关联选项"
    private String optionGroup;

    /** 版本号（乐观锁） */
    @Excel(name = "版本号", readConverterExp = "乐=观锁")
    private String version;





    public void setOptionCode(String optionCode) 
    {
        this.optionCode = optionCode;
    }

    public String getOptionCode() 
    {
        return optionCode;
    }

    public void setOptionContent(String optionContent) 
    {
        this.optionContent = optionContent;
    }

    public String getOptionContent() 
    {
        return optionContent;
    }

    public void setIsCorrect(Integer isCorrect) 
    {
        this.isCorrect = isCorrect;
    }

    public Integer getIsCorrect() 
    {
        return isCorrect;
    }

    public void setScoreWeight(BigDecimal scoreWeight) 
    {
        this.scoreWeight = scoreWeight;
    }

    public BigDecimal getScoreWeight() 
    {
        return scoreWeight;
    }

    public void setIsPartiallyCorrect(Integer isPartiallyCorrect) 
    {
        this.isPartiallyCorrect = isPartiallyCorrect;
    }

    public Integer getIsPartiallyCorrect() 
    {
        return isPartiallyCorrect;
    }

    public void setSort(String sort) 
    {
        this.sort = sort;
    }

    public String getSort() 
    {
        return sort;
    }

    public void setOptionGroup(String optionGroup) 
    {
        this.optionGroup = optionGroup;
    }

    public String getOptionGroup() 
    {
        return optionGroup;
    }

    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("questionId", getQuestionId())
            .append("optionCode", getOptionCode())
            .append("optionContent", getOptionContent())
            .append("isCorrect", getIsCorrect())
            .append("scoreWeight", getScoreWeight())
            .append("isPartiallyCorrect", getIsPartiallyCorrect())
            .append("sort", getSort())
            .append("optionGroup", getOptionGroup())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("version", getVersion())
            .toString();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
