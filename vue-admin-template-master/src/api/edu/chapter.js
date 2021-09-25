import request from '@/utils/request'

export default {
  //课程大纲列表,根据课程id进行查询
  getChapterVideo(courseId) {
    return request({
      url: '/serviceedu/edu-chapter/getChapterVideo/${courseId}',
      method: 'get',
    })
  }
}
