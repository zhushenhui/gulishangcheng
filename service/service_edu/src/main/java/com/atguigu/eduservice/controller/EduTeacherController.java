package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;

import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-04-20
 */
@Api(description="讲师管理")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduTeacherMapper eduTeacherMapper;

    // 查询讲师表所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher() {
        // 调用service的方法实现查询所有数据块操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    // 按照id删除讲师数据
    @ApiOperation(value = "根据id逻辑删除讲师")
    @DeleteMapping("/deleteTeacher/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        int n = eduTeacherMapper.deleteById(id);
        if (n == 1) {
            return R.ok();
        } else {
            return R.error();
        }

    }

//    // 按照id查询讲师数据
//    @ApiOperation(value = "根据ID查找讲师")
//    @GetMapping("/findOne{id}")
//    public R findTeacher(
//            @ApiParam(name = "id", value = "讲师ID", required = true)
//            @PathVariable String id) {
//        // 调用service的方法实现查询所有数据块操作
//        EduTeacher teacher = teacherService.getById(id);
//        return R.ok().data("items",teacher);
//    }

    /**
     * 分页查询讲师的方法
     *  current 当前页
     *  limit 每页记录数
     */
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到对象里
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal(); // 总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); // 数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    //4 条件查询带分页方法
    @ApiOperation(value = "条件查询分页讲师列表")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageListTeacherCondition(
              @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageParam = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //动态sql
        //判断是否为空，如果不为空则拼接
        String name = teacherQuery.getName();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        // 排序
        wrapper.orderByDesc("gmt_modified","gmt_create");
        //调用方法实现条件查询分页
        teacherService.page(pageParam,wrapper);
        long total = pageParam.getTotal(); // 总记录数
        List<EduTeacher> records = pageParam.getRecords(); // 数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    //添加讲师接口
    @ApiOperation(value = "新增讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){
        boolean flag = teacherService.save(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 根据id查找讲师接口
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //修改讲师接口
    @ApiOperation(value = "根据id修改讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

