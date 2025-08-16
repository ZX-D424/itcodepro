package com.ruoyi.web.controller.www;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.domain.ProjectScoreRank;
import com.ruoyi.project.service.IProjectScoreRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 得分排名Controller
 * 
 * @author xuexin
 * @date 2025-06-28
 */
@RestController
@RequestMapping("/www/rank")
public class WwwScoreRankController extends BaseController
{
    @Autowired
    private IProjectScoreRankService projectScoreRankService;

    // 查询模块分数排名
    @GetMapping("/moduleRank/{moduleId}")
    public TableDataInfo moduleRank(@PathVariable Long moduleId)
    {
        List<ProjectScoreRank> list = projectScoreRankService.selectProjectModuleRankList(moduleId);
        return getDataTable(list);
    }

    // 用户总分排名
    @GetMapping("/totalRank")
    public TableDataInfo totalRank()
    {
        List<ProjectScoreRank> list = projectScoreRankService.selectProjectTotalRankList();
        return getDataTable(list);
    }

    // 获取个人模块排名
    @GetMapping("/{moduleId}")
    public AjaxResult getPersonalModuleRank(@PathVariable Long moduleId)
    {
        Long userId = getUserId();
//        System.out.println(projectScoreRankService.getPersonalRank(userId,moduleId));
//        System.out.println("@@@@@@@@@@@77777777777@@@@@@@@");
        return success(projectScoreRankService.getPersonalRank(userId,moduleId));
    }

    // 获取个人总排名
    @GetMapping
    public AjaxResult getPersonalTotalRank()
    {
        Long userId = getUserId();
//        System.out.println(projectScoreRankService.getPersonalTotalRank(userId));
//        System.out.println("@@@@@@@@@@@@@@@@@@@");
        return success(projectScoreRankService.getPersonalTotalRank(userId));
    }
}
