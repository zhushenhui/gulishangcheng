package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zsh
 * @date 2020/7/2
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    /**
     * 章节中有很多小节
     */
    List<VideoVo> children = new ArrayList<>();
}
