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
import com.ruoyi.question.domain.QuestionCategory;
import com.ruoyi.question.service.IQuestionCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 题库类目Controller
 * 
 * @author ruoyi
 * @date 2025-08-02
 */
@RestController
@RequestMapping("/category/category")
public class QuestionCategoryController extends BaseController
{
    @Autowired
    private IQuestionCategoryService questionCategoryService;

    /**
     * 查询题库类目列表
     */
    @PreAuthorize("@ss.hasPermi('category:category:list')")
    @GetMapping("/list")
    public AjaxResult list(QuestionCategory questionCategory)
    {
        List<QuestionCategory> list = questionCategoryService.selectQuestionCategoryList(questionCategory);
        return success(list);
    }


    /**
     * 查询父类题库类目名字集合列表
     */
    @PreAuthorize("@ss.hasPermi('category:category:namelist')")
    @GetMapping("/namelist")
    public  AjaxResult namelist()
    {
        List<String> list = questionCategoryService.selectQuestionCategoryNameList();
        return success(list);
    }


    /**
     * 导出题库类目列表
     */
    @PreAuthorize("@ss.hasPermi('category:category:export')")
    @Log(title = "题库类目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestionCategory questionCategory)
    {
        List<QuestionCategory> list = questionCategoryService.selectQuestionCategoryList(questionCategory);
        ExcelUtil<QuestionCategory> util = new ExcelUtil<QuestionCategory>(QuestionCategory.class);
        util.exportExcel(response, list, "题库类目数据");
    }

    /**
     * 获取题库类目详细信息
     */
    @PreAuthorize("@ss.hasPermi('category:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(questionCategoryService.selectQuestionCategoryById(id));
    }

    /**
     * 新增题库类目
     */
    @PreAuthorize("@ss.hasPermi('category:category:add')")
    @Log(title = "题库类目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestionCategory questionCategory)
    {
        return toAjax(questionCategoryService.insertQuestionCategory(questionCategory));
    }

    /**
     * 修改题库类目
     */
    @PreAuthorize("@ss.hasPermi('category:category:edit')")
    @Log(title = "题库类目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestionCategory questionCategory)
    {
        return toAjax(questionCategoryService.updateQuestionCategory(questionCategory));
    }

    /**
     * 删除题库类目
     */
    @PreAuthorize("@ss.hasPermi('category:category:remove')")
    @Log(title = "题库类目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(questionCategoryService.deleteQuestionCategoryByIds(ids));
    }
}
