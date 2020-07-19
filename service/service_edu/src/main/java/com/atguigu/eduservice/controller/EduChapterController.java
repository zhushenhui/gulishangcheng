package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */
@Api(description="课程章节信息")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 课程大纲列表，根据课程id进行查询
     */
    @ApiOperation(value = "获取课程大纲列表")
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoById(courseId);
        return R.ok().data("allChapterVideo",list);
    }


    /**
     * 添加章节
     */
    @ApiOperation(value = "新增课程章节信息")
    @PostMapping("addChapter")
    public R addChapter(@ApiParam(name = "eduChapter", value = "课程章节信息", required = true)
            @RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 获取章节
     */
    @ApiOperation(value = "获取章节")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@ApiParam(name = "chapterId", value = "章节id", required = true)
            @PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    /**
     * 修改章节
     */
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@ApiParam(name = "eduChapter", value = "章节信息", required = true)
            @RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除章节
     */
    @ApiOperation(value = "删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@ApiParam(name = "chapterId", value = "章节id", required = true)
            @PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

