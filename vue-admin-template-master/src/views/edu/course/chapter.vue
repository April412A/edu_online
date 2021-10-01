<template>
  <div align-center>
  <h2 style="text-align: center;">发布新课程</h2>
  <el-steps :active="active" finish-status="success" align-center style="margin-bottom: 40px;">
    <el-step title="填写课程基本信息"></el-step>
    <el-step title="创建课程大纲"></el-step>
    <el-step title="最终发布"></el-step>
  </el-steps>

    <el-button type="text" @click="openChapterDialog()" >添加章节</el-button>
    <!-- 章节 -->
    <ul class="chapterList">
      <li
        v-for="chapter in ChapterVideoList"
        :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
                    <el-button  type="text" @click="openVideo(chapter.id)">添加小节</el-button>
                    <el-button  type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
                    <el-button  type="text" @click="deleteChapter(chapter.id)">删除</el-button>

          </span>
        </p>
        <!-- 视频 -->
<!--        <ul class="chapterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>{{ video.title }}

              <span class="acts">

                    <el-button style="" type="text">编辑</el-button>
                    <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                </span>
            </p>
          </li>
        </ul>-->
      </li>
    </ul>

    <el-form label-width="120px">
      <el-form-item>
        <el-button @click="previous">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>

  <!-- 添加和修改章节表单 -->

  <el-dialog title="添加章节" :visible.sync="dialogChapterFormVisible">
    <el-form :model="chapter">
      <el-form-item label="章节标题"  label-width="120px">
        <el-input v-model="chapter.title" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="章节排序" label-width="120px">
        <el-input-number :min="0" v-model="chapter.sort" controls-position="right"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
      <el-button type="primary"@click="saveOrUpdate()">确 定</el-button>
    </div>
  </el-dialog>
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
      chapter:{ //封装章节数据
        title: '',
        sort: 0
      },
      video: {
        title: '',
        sort: 0,
        free: 0,
        videoSourceId: ''
      },
      dialogChapterFormVisible:false,//章节弹框
      dialogVideoFormVisible:false, //小节弹框

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

    //判断当前操作为添加章节还是修改章节
    saveOrUpdate() {
      if(!this.chapter.id) {
        this.addChapter()
      } else {
        this.updateChapter()
      }
    },

    //添加章节
    addChapter() {
      //设置课程id到chapter对象里面
      this.chapter.courseId = this.courseId
      chapter.addChapter(this.chapter)
        .then(response => {
          //关闭弹框
          this.dialogChapterFormVisible = false
          //提示
          this.$message({
            type: 'success',
            message: '添加章节成功!'
          });
          //刷新页面
          this.getChapterVideo()
        })
    },

    //修改章节
    updateChapter(){
      chapter.updateChapter(this.chapter)
        .then(response => {
          //关闭弹框
          this.dialogChapterFormVisible = false
          //提示
          this.$message({
            type: 'success',
            message: '修改章节成功!'
          });
          //刷新页面
          this.getChapterVideo()

        })
    },

    //弹出添加章节页面
    openChapterDialog() {
      //弹框
      this.dialogChapterFormVisible = true
      //表单数据清空
      this.chapter.title = ''
      this.chapter.sort = 0
    },

    //弹出编辑修改章节的弹框 数据回显
    openEditChapter(UpdatechapterId){
      //弹框
      this.dialogChapterFormVisible = true
      //调用接口方法getChapterInfo
      chapter.getChapterInfo(UpdatechapterId)
        .then(response => {
          this.chapter = response.data.eduChapter
        })
    },

    //删除章节
    deleteChapter(DeletechapterId){
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //调用接口方法deleteChapter
        chapter.deleteChapter(DeletechapterId)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            //刷新页面
            this.getChapterVideo()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
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
<style>
.chapterList{
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chapterList li{
  position: relative;
}
.chapterList p{
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chapterList .acts {
  float: right;
  font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}

</style>
