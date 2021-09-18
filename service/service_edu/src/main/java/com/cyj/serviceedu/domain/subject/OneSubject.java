package com.cyj.serviceedu.domain.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//一级分类
public class OneSubject {
    private String id;
    private String title;

    //表示一个分类下可以有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
