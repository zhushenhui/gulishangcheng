package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程基本信息
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo
     * @return
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    CoursePublishVo publishCourseInfo(String id);
}
