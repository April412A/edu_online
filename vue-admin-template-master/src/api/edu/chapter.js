import request from '@/utils/request'

export default {
  //课程大纲列表,根据课程id进行查询
  getChapterVideo(courseId) {
    return request({
      url: '/serviceedu/edu-chapter/getChapterVideo/'+courseId,
      method: 'get',
    })
  },

  //添加章节
  addChapter(addeduChapter) {
    return request({
      url: '/serviceedu/edu-chapter/addChapter',
      method: 'post',
      data: addeduChapter
    })
  },

  //根据章节id查询
  getChapterInfo(UpdatechapterId) {
    return request({
      url: '/serviceedu/edu-chapter/getChapterInfo/'+UpdatechapterId,
      method: 'get',
    })
  },

  //修改章节
  updateChapter(updateeduChapter) {
    return request({
      url: '/serviceedu/edu-chapter/updateChapter',
      method: 'post',
      data: updateeduChapter
    })
  },

  //删除章节
  deleteChapter(DeletechapterId) {
    return request({
      url: '/serviceedu/edu-chapter/'+DeletechapterId,
      method: 'delete',
    })
  },
}
