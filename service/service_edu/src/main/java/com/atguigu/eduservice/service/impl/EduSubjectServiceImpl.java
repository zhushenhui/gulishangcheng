package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-21
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     * @param file
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 查询所有一级分类 parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        // 查询所有二级分类 parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        // 创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        // 封装一级分类
        for (EduSubject eduSubject:oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            // 下面这行代码等于上面两行代码
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finalSubjectList.add(oneSubject);
            // 在一级分类里循环遍历所有的二级分类
            List<TwoSubject>  twoFinalSubjectList = new ArrayList<>();
            // 遍历二级分类list集合来封装
            for (EduSubject edutwoSubject:twoSubjectList) {
                // 判断二级分类的parentid和一级分类的id是否相等
                if (edutwoSubject.getParentId().equals(oneSubject.getId()) ) {
                    // 将edutwoSubject的值复制到TwoSubject中，放到twoFinalSubjectList里
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(edutwoSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            // 把一级下面的所有二级分类放到一级分类里
            oneSubject.setChildren(twoFinalSubjectList);
        }
        // 封装二级分类

        return finalSubjectList;
    }
}
