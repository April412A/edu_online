<template>
  <div>
  <h2 style="text-align: center;">发布新课程</h2>
  <el-steps :active="active" finish-status="success" align-center style="margin-bottom: 40px;">
    <el-step title="填写课程基本信息"></el-step>
    <el-step title="创建课程大纲"></el-step>
    <el-step title="最终发布"></el-step>
  </el-steps>

    <el-form label-width="120px">
      <el-form-item>
        <el-button @click="previous">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>

import chapter from '@/api/edu/chapter'
export default {
  data() {
    return {
      active: 1,
      courseId:'',
      ChapterVideoList:[],
    };
  },

  created() {
    //获取路由的id值
    if(this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      //根据课程id查询章节和小节
      this.getChapterVideo()
    }
  },
  methods: {
    //课程大纲列表,根据课程id进行查询
    getChapterVideo(){
      chapter.getChapterVideo(this.courseId)
         .then(response=>{
           this.ChapterVideoList = response.data.list
      })
    },


    previous() {
      this.$router.push({path:'/course/info/'+this.courseId})
    },
    next() {
      if (this.active++ > 2) this.active = 0;
      //跳转到第三步
      this.$router.push({path:'/course/publish/'+this.courseId})
    }
  }
}
</script>

