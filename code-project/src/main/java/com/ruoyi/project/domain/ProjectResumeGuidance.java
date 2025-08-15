package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 简历指导记录对象 project_resume_guidance
 * 
 * @author qiuqiuzi
 * @date 2025-08-07
 */
@Data
public class ProjectResumeGuidance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户 */
    @Excel(name = "用户")
    private Long userId;
    private String nickName;
    private String userName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 指导人 */
    @Excel(name = "指导人")
    private Long guideId;

    /** 简历类型 */
    @Excel(name = "简历类型")
    private Long resumeType;

    /** 指导状态 */
    @Excel(name = "指导状态")
    private Long guidanceStatus;

    /** 指导预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "指导预约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date appointmentTime;

    /** 指导完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "指导完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;

    /** 支付状态 */
    @Excel(name = "支付状态")
    private Long paymentStatus;

    /** 指导反馈 */
    @Excel(name = "指导反馈")
    private String feedback;

    /** 创建者 */
    @Excel(name = "创建者")
    private String creator;

    /** 修改者 */
    @Excel(name = "修改者")
    private String modifier;
    private String appendix;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setGuideId(Long guideId) 
    {
        this.guideId = guideId;
    }

    public Long getGuideId() 
    {
        return guideId;
    }

    public void setResumeType(Long resumeType) 
    {
        this.resumeType = resumeType;
    }

    public Long getResumeType() 
    {
        return resumeType;
    }

    public void setGuidanceStatus(Long guidanceStatus) 
    {
        this.guidanceStatus = guidanceStatus;
    }

    public Long getGuidanceStatus() 
    {
        return guidanceStatus;
    }

    public void setAppointmentTime(Date appointmentTime) 
    {
        this.appointmentTime = appointmentTime;
    }

    public Date getAppointmentTime() 
    {
        return appointmentTime;
    }

    public void setCompleteTime(Date completeTime) 
    {
        this.completeTime = completeTime;
    }

    public Date getCompleteTime() 
    {
        return completeTime;
    }

    public void setPaymentStatus(Long paymentStatus) 
    {
        this.paymentStatus = paymentStatus;
    }

    public Long getPaymentStatus() 
    {
        return paymentStatus;
    }

    public void setFeedback(String feedback) 
    {
        this.feedback = feedback;
    }

    public String getFeedback() 
    {
        return feedback;
    }

    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }

    public void setModifier(String modifier) 
    {
        this.modifier = modifier;
    }

    public String getModifier() 
    {
        return modifier;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("phone", getPhone())
            .append("guideId", getGuideId())
            .append("resumeType", getResumeType())
            .append("guidanceStatus", getGuidanceStatus())
            .append("appointmentTime", getAppointmentTime())
            .append("completeTime", getCompleteTime())
            .append("paymentStatus", getPaymentStatus())
            .append("feedback", getFeedback())
            .append("creator", getCreator())
            .append("createTime", getCreateTime())
            .append("modifier", getModifier())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
