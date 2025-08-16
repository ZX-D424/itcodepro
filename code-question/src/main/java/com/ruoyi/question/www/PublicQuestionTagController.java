package com.ruoyi.question.www;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.question.domain.QuestionTag;
import com.ruoyi.question.service.IQuestionTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 题目标签库Controller
 * 
 * @author ruoyi
 * @date 2025-08-02
 */
@RestController
@RequestMapping("/www/tag/tag")
public class PublicQuestionTagController extends BaseController
{
    @Autowired
    private IQuestionTagService questionTagService;

    /**
     * 查询题目标签库列表
     */
//    @PreAuthorize("@ss.hasPermi('tag:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestionTag questionTag)
    {
        startPage();
        List<QuestionTag> list = questionTagService.selectQuestionTagList(questionTag);
        return getDataTable(list);
    }

    /**
     * 导出题目标签库列表
     */
//    @PreAuthorize("@ss.hasPermi('tag:tag:export')")
    @Log(title = "题目标签库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestionTag questionTag)
    {
        List<QuestionTag> list = questionTagService.selectQuestionTagList(questionTag);
        ExcelUtil<QuestionTag> util = new ExcelUtil<QuestionTag>(QuestionTag.class);
        util.exportExcel(response, list, "题目标签库数据");
    }

    /**
     * 获取题目标签库详细信息
     */
//    @PreAuthorize("@ss.hasPermi('tag:tag:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(questionTagService.selectQuestionTagById(id));
    }

    /**
     * 新增题目标签库
     */
//    @PreAuthorize("@ss.hasPermi('tag:tag:add')")
    @Log(title = "题目标签库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestionTag questionTag)
    {
        return toAjax(questionTagService.insertQuestionTag(questionTag));
    }

    /**
     * 修改题目标签库
     */
//    @PreAuthorize("@ss.hasPermi('tag:tag:edit')")
    @Log(title = "题目标签库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestionTag questionTag)
    {
        return toAjax(questionTagService.updateQuestionTag(questionTag));
    }

    /**
     * 删除题目标签库
     */
//    @PreAuthorize("@ss.hasPermi('tag:tag:remove')")
    @Log(title = "题目标签库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(questionTagService.deleteQuestionTagByIds(ids));
    }



    /**
     * 查询标签页下拉框列表
     */
//    @PreAuthorize("@ss.hasPermi('tag:tag:namelist')")
    @GetMapping("/namelist")
    public  AjaxResult namelist()
    {
        List<String> list = questionTagService.selectQuestionTagNameList();
        return success(list);
    }

}
