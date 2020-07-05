package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    /**
     * 注入小节service
     */
    @Autowired
    private EduVideoService videoService;

    /**
     * 课程大纲列表，根据课程id进行查询
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {
        // 1、根据课程id查询课程里的所有章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterQueryWrapper.orderByAsc("cast(id AS SIGNED)");
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterQueryWrapper);
        // 2、根据课程id查询课程里的所有小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.orderByAsc("cast(id AS SIGNED)");
        List<EduVideo> eduVideoList = videoService.list(videoQueryWrapper);
        List<ChapterVo> finalList = new ArrayList<>();
        // 3、遍历查询章节list集合进行封装
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            List<VideoVo> videoVoList = new ArrayList<>();
            // 4、遍历查询小节list集合进行封装
            for(EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            finalList.add(chapterVo);
        }
        return finalList;
    }
}
