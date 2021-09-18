package com.cyj.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyj.serviceedu.domain.EduSubject;
import com.cyj.serviceedu.domain.excel.SubjectData;
import com.cyj.serviceedu.domain.subject.OneSubject;
import com.cyj.serviceedu.domain.subject.TwoSubject;
import com.cyj.serviceedu.listener.SubjectExcelListener;
import com.cyj.serviceedu.mapper.EduSubjectMapper;
import com.cyj.serviceedu.service.EduSubjectService;
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
 * @since 2021-09-14
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllSubject() {
        //查询一级分类所有 parent_id=0   wrapper.eq
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        //baseMapper是MybatisPlus自动封装好的Mapper
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //另一种写法，效果同baseMapper.selectList()
        // this.list(wrapperOne);

        //查询二级分类所有 parent_id!=o  wrapper.ne
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装数据
        List<OneSubject> SubjectList = new ArrayList<>();

        //封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        //封装到要求的list集合里面 List<OneSubject> finalSubjectList
        for (int i=0;i<oneSubjectList.size();i++){
            //遍历oneSubjectList集合
            //得到oneSubjectList每个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);

            //把eduSubject里面值获取出来，放到OneSubject对象里面
            OneSubject oneSubject = new OneSubject();

            //eduSubject值复制到对应oneSubject对象里面
            BeanUtils.copyProperties(eduSubject,oneSubject);

            //多个OneSubject放到finalSubjectList里面
            SubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> tSubjectList = new ArrayList<>();
            //遍历二级分类list集合
            for (int m=0; m<twoSubjectList.size(); m++) {
                //获取每个二级分类
                EduSubject tSubject = twoSubjectList.get(m);
                //判断二级分类parentid和一级分类id是否一样
                if(tSubject.getParentId().equals(eduSubject.getId())) {
                    //把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    tSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(tSubjectList);
        }


        return SubjectList;
    }
}
