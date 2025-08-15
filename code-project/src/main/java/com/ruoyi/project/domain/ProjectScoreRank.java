package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 得分排名对象 project_score_rank
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@Data
public class ProjectScoreRank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 模块ID */
    @Excel(name = "模块ID")
    private Long moduleId;
    private String moduleName;

    /** 名次 */
    @Excel(name = "名次")
    private Long place;
    private Long moduleRank;
    private Long totalRank;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;
    private String nickName;
    private String userName;

    /** 用户ID */
    @Excel(name = "模块得分")
    private Double score;

    /** 用户ID */
    @Excel(name = "总得分")
    private Double totalScore;


    /** 评分时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评分时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date scoreTime;



}
