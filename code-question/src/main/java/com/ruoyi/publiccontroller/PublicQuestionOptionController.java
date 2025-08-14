package com.ruoyi.publiccontroller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.QuestionOption;
import com.ruoyi.service.IQuestionOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 题目选项（支持单选/多选/判断题，题目删除需级联删除选项）Controller
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
@RestController
@RequestMapping("/public/option/option")
public class PublicQuestionOptionController extends BaseController
{
    @Autowired
    private IQuestionOptionService questionOptionService;

    /**
     * 查询题目选项（支持单选/多选/判断题，题目删除需级联删除选项）列表
     */
//    @PreAuthorize("@ss.hasPermi('option:option:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestionOption questionOption)
    {
        startPage();
        List<QuestionOption> list = questionOptionService.selectQuestionOptionList(questionOption);
        return getDataTable(list);
    }

    /**
     * 导出题目选项（支持单选/多选/判断题，题目删除需级联删除选项）列表
     */
//    @PreAuthorize("@ss.hasPermi('option:option:export')")
    @Log(title = "题目选项（支持单选/多选/判断题，题目删除需级联删除选项）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestionOption questionOption)
    {
        List<QuestionOption> list = questionOptionService.selectQuestionOptionList(questionOption);
        ExcelUtil<QuestionOption> util = new ExcelUtil<QuestionOption>(QuestionOption.class);
        util.exportExcel(response, list, "题目选项（支持单选/多选/判断题，题目删除需级联删除选项）数据");
    }

    /**
     * 获取题目选项（支持单选/多选/判断题，题目删除需级联删除选项）详细信息
     */
//    @PreAuthorize("@ss.hasPermi('option:option:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(questionOptionService.selectQuestionOptionById(id));
    }

    /**
     * 新增题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     */
//    @PreAuthorize("@ss.hasPermi('option:option:add')")
    @Log(title = "题目选项（支持单选/多选/判断题，题目删除需级联删除选项）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestionOption questionOption)
    {
        return toAjax(questionOptionService.insertQuestionOption(questionOption));
    }

    /**
     * 修改题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     */
//    @PreAuthorize("@ss.hasPermi('option:option:edit')")
    @Log(title = "题目选项（支持单选/多选/判断题，题目删除需级联删除选项）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestionOption questionOption)
    {
        return toAjax(questionOptionService.updateQuestionOption(questionOption));
    }

    /**
     * 删除题目选项（支持单选/多选/判断题，题目删除需级联删除选项）
     */
//    @PreAuthorize("@ss.hasPermi('option:option:remove')")
    @Log(title = "题目选项（支持单选/多选/判断题，题目删除需级联删除选项）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(questionOptionService.deleteQuestionOptionByIds(ids));
    }
}
