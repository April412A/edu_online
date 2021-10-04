import request from '@/utils/request'

export default {
  //添加课程信息
  addCourseInfo(courseInfo) {
    return request({
      url: '/serviceedu/edu-course/addCourseInfo',
      method: 'post',
      data:courseInfo
    })
  },
  //查询所有讲师
  getListTeacher() {
    return request({
      url: '/serviceedu/edu-teacher/findAll',
      method: 'get'
    })
  },
  //根据课程id查询课程基本信息
  getCourseInfo(courseId) {
    return request({
      url: '/serviceedu/edu-course/getCourseInfo/'+courseId,
      method: 'get'
    })
  },
  //修改课程信息
  updateCourseInfo(courseQuery){
    return request({
      url:'/serviceedu/edu-course/updateCourseInfo',
      method:'post',
      data:courseQuery
    })
  },
  //根据课程id查询课程确认信息（CoursePublishQuery)
  getCoursePublishQuery(courseId){
    return request({
      url:'/serviceedu/edu-course/getCoursePublishQuery/'+courseId,
      method:'get',
    })
  },
  //最终发布
  publishCourse(id){
    return request({
      url:'/serviceedu/edu-course/publishCourse/'+id,
      method:'post',
    })
  },
  //条件分页查询课程列表
  pageCourseCondition(current,limit,EduCourse){
    return request({
      url:'/serviceedu/edu-course/pageCourseCondition/'+current+'/'+limit,
      method:'post',
      data:EduCourse
    })
  },
  //查询所有课程
  findAll() {
    return request({
      url: '/serviceedu/edu-course/findAll',
      method: 'get',
    })
  },
  ////删除课程
  deleteCourse(id){
    return request({
      url: '/serviceedu/edu-course/'+id,
      method: 'delete',
    })
  }

}
