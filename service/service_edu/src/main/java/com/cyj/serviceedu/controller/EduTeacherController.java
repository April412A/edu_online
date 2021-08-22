package com.cyj.serviceedu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    //3 分页查询讲师的方法
    //current 当前页 ; limit 每页记录数
    @ApiOperation(value = "分页查询讲师",tags = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //调用方法实现分页;调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

        /*  --第二种方法也可以--
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);*/

        return R.ok().data("total",total).data("rows",records);
    }

}

