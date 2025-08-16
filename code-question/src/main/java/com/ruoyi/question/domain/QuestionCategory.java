//package com.ruoyi.domain;
//
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//import com.ruoyi.common.annotation.Excel;
//import com.ruoyi.common.core.domain.TreeEntity;
//
///**
// * 题库类目对象 question_category
// *
// * @author ruoyi
// * @date 2025-08-02
// */
//public class QuestionCategory extends TreeEntity
//{
//    private static final long serialVersionUID = 1L;
//
//    /** 主键ID */
//    private Long id;
//
//    /** 类目名称 */
//    @Excel(name = "类目名称")
//    private String name;
//
//    /** 排序值 */
//    @Excel(name = "排序值")
//    private Long sort;
//
//    public void setId(Long id)
//    {
//        this.id = id;
//    }
//
//    public Long getId()
//    {
//        return id;
//    }
//
//    public void setName(String name)
//    {
//        this.name = name;
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//
//    public void setSort(Long sort)
//    {
//        this.sort = sort;
//    }
//
//    public Long getSort()
//    {
//        return sort;
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("id", getId())
//            .append("parentId", getParentId())
//            .append("name", getName())
//            .append("sort", getSort())
//            .append("createBy", getCreateBy())
//            .append("createTime", getCreateTime())
//            .append("updateBy", getUpdateBy())
//            .append("updateTime", getUpdateTime())
//            .append("remark", getRemark())
//            .toString();
//    }
//}



package com.ruoyi.question.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 题库类目对象 question_category
 *
 * @author ruoyi
 * @date 2025-08-02
 */
public class QuestionCategory extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Integer id;

    /** 类目名称 */
    @Excel(name = "类目名称")
    private String name;

    /** 排序值 */
    @Excel(name = "排序值")
    private Long sort;

    /** 父类名称（新增字段，用于接收SQL查询的parent_name） */
    private String parentName;  // 新增字段


    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }

    // 新增parentName的getter和setter
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("parentId", getParentId())
                .append("parentName", getParentName())  // 补充toString输出
                .append("name", getName())
                .append("sort", getSort())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
