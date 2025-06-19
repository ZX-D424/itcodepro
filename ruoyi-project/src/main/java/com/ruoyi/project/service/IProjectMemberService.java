package com.ruoyi.project.service;

import java.util.List;
import com.ruoyi.project.domain.ProjectMember;

/**
 * 项目成员Service接口
 * 
 * @author xuexin
 * @date 2025-06-19
 */
public interface IProjectMemberService 
{
    /**
     * 查询项目成员
     * 
     * @param id 项目成员主键
     * @return 项目成员
     */
    public ProjectMember selectProjectMemberById(Long id);

    /**
     * 查询项目成员列表
     * 
     * @param projectMember 项目成员
     * @return 项目成员集合
     */
    public List<ProjectMember> selectProjectMemberList(ProjectMember projectMember);

    /**
     * 新增项目成员
     * 
     * @param projectMember 项目成员
     * @return 结果
     */
    public int insertProjectMember(ProjectMember projectMember);

    /**
     * 修改项目成员
     * 
     * @param projectMember 项目成员
     * @return 结果
     */
    public int updateProjectMember(ProjectMember projectMember);

    /**
     * 批量删除项目成员
     * 
     * @param ids 需要删除的项目成员主键集合
     * @return 结果
     */
    public int deleteProjectMemberByIds(Long[] ids);

    /**
     * 删除项目成员信息
     * 
     * @param id 项目成员主键
     * @return 结果
     */
    public int deleteProjectMemberById(Long id);
}
