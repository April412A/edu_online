package com.cyj.servicebase.exceptionhandler;


import com.cyj.commonutils.ExceptionUtil;
import com.cyj.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    //为了返回数据
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行全局统一异常处理");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    //为了返回数据
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行ArithmeticException特定异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(MyException.class)
    //为了返回数据
    @ResponseBody
    public R error(MyException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error().code(e.getCode()).message(e.getMsg());
    }


}
