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
import com.ruoyi.question.domain.QuestionAnswer;
import com.ruoyi.question.service.IQuestionAnswerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 题库答案（支持多题型答案存储，题目删除需级联删除答案）Controller
 * 
 * @author ruoyi
 * @date 2025-08-06
 */
@RestController
@RequestMapping("/answer/answer")
public class QuestionAnswerController extends BaseController
{
    @Autowired
    private IQuestionAnswerService questionAnswerService;

    /**
     * 查询题库答案（支持多题型答案存储，题目删除需级联删除答案）列表
     */
    @PreAuthorize("@ss.hasPermi('answer:answer:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestionAnswer questionAnswer)
    {
        startPage();
        List<QuestionAnswer> list = questionAnswerService.selectQuestionAnswerList(questionAnswer);
        return getDataTable(list);
    }

    /**
     * 导出题库答案（支持多题型答案存储，题目删除需级联删除答案）列表
     */
    @PreAuthorize("@ss.hasPermi('answer:answer:export')")
    @Log(title = "题库答案（支持多题型答案存储，题目删除需级联删除答案）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QuestionAnswer questionAnswer)
    {
        List<QuestionAnswer> list = questionAnswerService.selectQuestionAnswerList(questionAnswer);
        ExcelUtil<QuestionAnswer> util = new ExcelUtil<QuestionAnswer>(QuestionAnswer.class);
        util.exportExcel(response, list, "题库答案（支持多题型答案存储，题目删除需级联删除答案）数据");
    }

    /**
     * 获取题库答案（支持多题型答案存储，题目删除需级联删除答案）详细信息
     */
    @PreAuthorize("@ss.hasPermi('answer:answer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(questionAnswerService.selectQuestionAnswerById(id));
    }

    /**
     * 新增题库答案（支持多题型答案存储，题目删除需级联删除答案）
     */
    @PreAuthorize("@ss.hasPermi('answer:answer:add')")
    @Log(title = "题库答案（支持多题型答案存储，题目删除需级联删除答案）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestionAnswer questionAnswer)
    {
        return toAjax(questionAnswerService.insertQuestionAnswer(questionAnswer));
    }

    /**
     * 修改题库答案（支持多题型答案存储，题目删除需级联删除答案）
     */
    @PreAuthorize("@ss.hasPermi('answer:answer:edit')")
    @Log(title = "题库答案（支持多题型答案存储，题目删除需级联删除答案）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestionAnswer questionAnswer)
    {
        return toAjax(questionAnswerService.updateQuestionAnswer(questionAnswer));
    }

    /**
     * 删除题库答案（支持多题型答案存储，题目删除需级联删除答案）
     */
    @PreAuthorize("@ss.hasPermi('answer:answer:remove')")
    @Log(title = "题库答案（支持多题型答案存储，题目删除需级联删除答案）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(questionAnswerService.deleteQuestionAnswerByIds(ids));
    }
}
