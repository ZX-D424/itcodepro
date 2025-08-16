package com.ruoyi.question.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.question.domain.QuestionSection;
import com.ruoyi.question.service.IQuestionSectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 栏目Controller
 * 
 * @author ruoyi
 * @date 2025-08-01
 */
@RestController
@RequestMapping("/section/section")
public class QuestionSectionController extends BaseController
{
    @Autowired
    private IQuestionSectionService questionSectionService;

    /**
     * 查询栏目列表
     */
    @PreAuthorize("@ss.hasPermi('section:section:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestionSection questionSection)
    {
        startPage();
        List<QuestionSection> list = questionSectionService.selectQuestionSectionList(questionSection);
        return getDataTable(list);
    }

    /**
     * 导出栏目列表
     */
    @PreAuthorize("@ss.hasPermi('section:section:export')")
    @Log(title = "栏目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestionSection questionSection)
    {
        List<QuestionSection> list = questionSectionService.selectQuestionSectionList(questionSection);
        ExcelUtil<QuestionSection> util = new ExcelUtil<QuestionSection>(QuestionSection.class);
        util.exportExcel(response, list, "栏目数据");
    }

    /**
     * 获取栏目详细信息
     */
    @PreAuthorize("@ss.hasPermi('section:section:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(questionSectionService.selectQuestionSectionById(id));
    }

    /**
     * 新增栏目
     */
    @PreAuthorize("@ss.hasPermi('section:section:add')")
    @Log(title = "栏目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestionSection questionSection)
    {
        return toAjax(questionSectionService.insertQuestionSection(questionSection));
    }

    /**
     * 修改栏目
     */
    @PreAuthorize("@ss.hasPermi('section:section:edit')")
    @Log(title = "栏目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestionSection questionSection)
    {
        return toAjax(questionSectionService.updateQuestionSection(questionSection));
    }

    /**
     * 删除栏目
     */
    @PreAuthorize("@ss.hasPermi('section:section:remove')")
    @Log(title = "栏目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(questionSectionService.deleteQuestionSectionByIds(ids));
    }
}
