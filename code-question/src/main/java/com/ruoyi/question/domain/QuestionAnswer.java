package com.ruoyi.question.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 题库答案（支持多题型答案存储，题目删除需级联删除答案）对象 question_answer
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
public class QuestionAnswer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 答案主键ID */
    private Integer id;

    /** 关联题目 */
    @Excel(name = "关联题目")
    private Integer questionId;


    /** 关联题目名字 */
    @Excel(name = "关联题目名字")
    private String question;

    /** 答案类型：1-标准答案 2-参考答案 3-多解法 4-评分要点 */
    @Excel(name = "答案类型：1-标准答案 2-参考答案 3-多解法 4-评分要点")
    private Integer answerType;

    /** 答案内容（填空答案/简答解析/编程代码） */
    @Excel(name = "答案内容", readConverterExp = "填=空答案/简答解析/编程代码")
    private String answerContent;

    /** 答案解析（解题思路/知识点说明） */
    @Excel(name = "答案解析", readConverterExp = "解=题思路/知识点说明")
    private String explanation;

    /** 多选题正确答案编码（如"ABD"） */
    @Excel(name = "多选题正确答案编码")
    private String optionCodes;

    /** 答案权重（用于部分正确得分） */
    @Excel(name = "答案权重", readConverterExp = "用=于部分正确得分")
    private BigDecimal scoreWeight;

    /** 答案排序（多解法时使用） */
    @Excel(name = "答案排序", readConverterExp = "多=解法时使用")
    private String sort;

    /** 编程语言（如Java/Python） */
    @Excel(name = "编程语言", readConverterExp = "如=Java/Python")
    private String language;

    /** 版本号（乐观锁） */
    @Excel(name = "版本号", readConverterExp = "乐=观锁")
    private String version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setAnswerType(Integer answerType)
    {
        this.answerType = answerType;
    }

    public Integer getAnswerType() 
    {
        return answerType;
    }

    public void setAnswerContent(String answerContent) 
    {
        this.answerContent = answerContent;
    }

    public String getAnswerContent() 
    {
        return answerContent;
    }

    public void setExplanation(String explanation) 
    {
        this.explanation = explanation;
    }

    public String getExplanation() 
    {
        return explanation;
    }

    public void setOptionCodes(String optionCodes) 
    {
        this.optionCodes = optionCodes;
    }

    public String getOptionCodes() 
    {
        return optionCodes;
    }

    public void setScoreWeight(BigDecimal scoreWeight) 
    {
        this.scoreWeight = scoreWeight;
    }

    public BigDecimal getScoreWeight() 
    {
        return scoreWeight;
    }

    public void setSort(String sort) 
    {
        this.sort = sort;
    }

    public String getSort() 
    {
        return sort;
    }

    public void setLanguage(String language) 
    {
        this.language = language;
    }

    public String getLanguage() 
    {
        return language;
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
            .append("answerType", getAnswerType())
            .append("answerContent", getAnswerContent())
            .append("explanation", getExplanation())
            .append("optionCodes", getOptionCodes())
            .append("scoreWeight", getScoreWeight())
            .append("sort", getSort())
            .append("language", getLanguage())
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
