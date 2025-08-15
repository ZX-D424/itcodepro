package com.ruoyi.web.controller.www;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.domain.ProjectInterviewSimulation;
import com.ruoyi.project.service.IProjectInterviewSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 面试模拟记录Controller
 * 
 * @author qiuqiuzi
 * @date 2025-08-07
 */
@RestController
@RequestMapping("/www/simulation")
public class WwwInterviewSimulationController extends BaseController
{
    @Autowired
    private IProjectInterviewSimulationService projectInterviewSimulationService;

    /**
     * 查询面试模拟记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProjectInterviewSimulation projectInterviewSimulation)
    {
        startPage();
        List<ProjectInterviewSimulation> list = projectInterviewSimulationService.selectProjectInterviewSimulationList(projectInterviewSimulation);
        return getDataTable(list);
    }

    /**
     * 导出面试模拟记录列表
     */
    @Log(title = "面试模拟记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectInterviewSimulation projectInterviewSimulation)
    {
        List<ProjectInterviewSimulation> list = projectInterviewSimulationService.selectProjectInterviewSimulationList(projectInterviewSimulation);
        ExcelUtil<ProjectInterviewSimulation> util = new ExcelUtil<ProjectInterviewSimulation>(ProjectInterviewSimulation.class);
        util.exportExcel(response, list, "面试模拟记录数据");
    }

    /**
     * 获取面试模拟记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectInterviewSimulationService.selectProjectInterviewSimulationById(id));
    }

    /**
     * 新增面试模拟记录
     */
    @Log(title = "面试模拟记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectInterviewSimulation projectInterviewSimulation)
    {
        return toAjax(projectInterviewSimulationService.insertProjectInterviewSimulation(projectInterviewSimulation));
    }

    /**
     * 修改面试模拟记录
     */
    @Log(title = "面试模拟记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectInterviewSimulation projectInterviewSimulation)
    {
        return toAjax(projectInterviewSimulationService.updateProjectInterviewSimulation(projectInterviewSimulation));
    }

    /**
     * 删除面试模拟记录
     */
    @Log(title = "面试模拟记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectInterviewSimulationService.deleteProjectInterviewSimulationByIds(ids));
    }
}
