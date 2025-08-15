package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 面试模拟记录对象 project_interview_simulation
 * 
 * @author qiuqiuzi
 * @date 2025-08-07
 */
@Data
public class ProjectInterviewSimulation extends BaseEntity
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

    /** 面试官 */
    @Excel(name = "面试官")
    private Long interviewerId;

    /** 面试类型 */
    @Excel(name = "面试类型")
    private Long interviewType;

    /** 面试状态 */
    @Excel(name = "面试状态")
    private Long interviewStatus;

    /** 面试预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "面试预约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date appointmentTime;

    /** 面试完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "面试完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;

    /** 支付状态 */
    @Excel(name = "支付状态")
    private Long paymentStatus;

    /** 面试结果/评价 */
    @Excel(name = "面试结果/评价")
    private String interviewResult;

    /** 创建者 */
    @Excel(name = "创建者")
    private String creator;

    /** 修改者 */
    @Excel(name = "修改者")
    private String modifier;

    @Excel(name = "面试附件")
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

    public void setInterviewerId(Long interviewerId) 
    {
        this.interviewerId = interviewerId;
    }

    public Long getInterviewerId() 
    {
        return interviewerId;
    }

    public void setInterviewType(Long interviewType) 
    {
        this.interviewType = interviewType;
    }

    public Long getInterviewType() 
    {
        return interviewType;
    }

    public void setInterviewStatus(Long interviewStatus) 
    {
        this.interviewStatus = interviewStatus;
    }

    public Long getInterviewStatus() 
    {
        return interviewStatus;
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

    public void setInterviewResult(String interviewResult) 
    {
        this.interviewResult = interviewResult;
    }

    public String getInterviewResult() 
    {
        return interviewResult;
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
            .append("interviewerId", getInterviewerId())
            .append("interviewType", getInterviewType())
            .append("interviewStatus", getInterviewStatus())
            .append("appointmentTime", getAppointmentTime())
            .append("completeTime", getCompleteTime())
            .append("paymentStatus", getPaymentStatus())
            .append("interviewResult", getInterviewResult())
            .append("creator", getCreator())
            .append("createTime", getCreateTime())
            .append("modifier", getModifier())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
