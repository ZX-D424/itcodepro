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
import com.ruoyi.question.domain.QuestionManage;
import com.ruoyi.question.service.IQuestionManageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 题库管理（主，存储题目基础信息）Controller
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
@RestController
@RequestMapping("/manage/manage")
public class QuestionManageController extends BaseController
{
    @Autowired
    private IQuestionManageService questionManageService;

    /**
     * 查询题库管理（主，存储题目基础信息）列表
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestionManage questionManage)
    {
        startPage();
        List<QuestionManage> list = questionManageService.selectQuestionManageList(questionManage);
        return getDataTable(list);
    }

    /**
     * 导出题库管理（主，存储题目基础信息）列表
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:export')")
    @Log(title = "题库管理（主，存储题目基础信息）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestionManage questionManage)
    {
        List<QuestionManage> list = questionManageService.selectQuestionManageList(questionManage);
        ExcelUtil<QuestionManage> util = new ExcelUtil<QuestionManage>(QuestionManage.class);
        util.exportExcel(response, list, "题库管理（主，存储题目基础信息）数据");
    }

    /**
     * 获取题库管理（主，存储题目基础信息）详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(questionManageService.selectQuestionManageById(id));
    }

    /**
     * 新增题库管理（主，存储题目基础信息）
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:add')")
    @Log(title = "题库管理（主，存储题目基础信息）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestionManage questionManage)
    {
        return toAjax(questionManageService.insertQuestionManage(questionManage));
    }

    /**
     * 修改题库管理（主，存储题目基础信息）
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:edit')")
    @Log(title = "题库管理（主，存储题目基础信息）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestionManage questionManage)
    {
        return toAjax(questionManageService.updateQuestionManage(questionManage));
    }

    /**
     * 删除题库管理（主，存储题目基础信息）
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:remove')")
    @Log(title = "题库管理（主，存储题目基础信息）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(questionManageService.deleteQuestionManageByIds(ids));
    }


    @GetMapping("/getQuestionNameList")
    public AjaxResult getQuestionNameList()
    {
    	List<String> list = questionManageService.getQuestionNameList();
    	return success(list);
    }
}
