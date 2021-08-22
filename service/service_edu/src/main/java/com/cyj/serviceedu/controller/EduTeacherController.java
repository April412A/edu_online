package com.cyj.serviceedu.controller;

import com.cyj.commonutils.R;
import com.cyj.serviceedu.domain.EduTeacher;
import com.cyj.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-15
 */
@Api(tags="讲师管理")
@RestController
@RequestMapping("/serviceedu/edu-teacher")
//try 3
public class EduTeacherController {

    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    //1 查询讲师表所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表",tags = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> list = teacherService.list(null);
        System.out.println("list=="+list);
        return R.ok().data("items", list);
    }

    //2 逻辑删除讲师的方法
    @ApiOperation(value = "逻辑删除讲师",tags = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        System.out.println("flag=="+flag);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

