package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 课程大纲列表，根据课程id进行查询
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoById(String courseId);
}
