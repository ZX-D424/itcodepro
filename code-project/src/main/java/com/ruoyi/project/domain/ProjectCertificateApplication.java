package com.ruoyi.project.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 证书申请对象 project_certificate_application
 * 
 * @author qiuqiuzi
 * @date 2025-08-12
 */
@Data
public class ProjectCertificateApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applicant;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String idCard;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;
    private String userName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 收件地址 */
    @Excel(name = "收件地址")
    private String receiveAddr;

    /** 证书类型 */
    @Excel(name = "证书类型")
    private String certType;

    /** 证书价格 */
    @Excel(name = "证书价格")
    private BigDecimal certPrice;

    /** 支付状态：0-未支付，1-已支付，2-退款中，3-已退款 */
    @Excel(name = "支付状态：0-未支付，1-已支付，2-退款中，3-已退款")
    private Long payStatus;
    private Long applicationStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setApplicant(String applicant) 
    {
        this.applicant = applicant;
    }

    public String getApplicant() 
    {
        return applicant;
    }

    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
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

    public void setReceiveAddr(String receiveAddr) 
    {
        this.receiveAddr = receiveAddr;
    }

    public String getReceiveAddr() 
    {
        return receiveAddr;
    }

    public void setCertType(String certType) 
    {
        this.certType = certType;
    }

    public String getCertType() 
    {
        return certType;
    }

    public void setCertPrice(BigDecimal certPrice) 
    {
        this.certPrice = certPrice;
    }

    public BigDecimal getCertPrice() 
    {
        return certPrice;
    }

    public void setPayStatus(Long payStatus) 
    {
        this.payStatus = payStatus;
    }

    public Long getPayStatus() 
    {
        return payStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("applicant", getApplicant())
            .append("idCard", getIdCard())
            .append("userId", getUserId())
            .append("phone", getPhone())
            .append("receiveAddr", getReceiveAddr())
            .append("certType", getCertType())
            .append("remark", getRemark())
            .append("certPrice", getCertPrice())
            .append("payStatus", getPayStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
