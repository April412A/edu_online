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
                <ul class="chapterList videoList">
                  <li
                    v-for="video in chapter.children"
                    :key="video.id">
                    <p>{{ video.title }}

                      <span class="acts">

                            <el-button type="text" @click="openEditVideo(video.id)">编辑</el-button>
                            <el-button type="text" @click="deleteVideo(video.id)">删除</el-button>
                        </span>
                    </p>
                  </li>
                </ul>
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

    <!-- 添加和修改小节表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/servicevod/video/uploadVideo'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo()">确 定</el-button>
      </div>
    </el-dialog>

  </div>

</template>
<script>

import chapter from '@/api/edu/chapter'
import video from "@/api/edu/video";
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

      fileList: [],//上传文件列表
      BASE_API: process.env.VUE_APP_BASE_API // 接口API地址
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

    //==============================章节操作====================================
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
    },

    //==============================小节操作====================================
    saveOrUpdateVideo(){
      if(!this.video.id) {
        this.addVideo()
      } else {
        this.updateVideo()
      }
    },

    //添加小节
    addVideo(){
      //设置课程id
      this.video.courseId = this.courseId
      video.addVideo(this.video)
        .then(response=>{
          //关闭弹框
          this.dialogVideoFormVisible = false
          //提示
          this.$message({
            type: 'success',
            message: '添加小节成功!'
          });
          //刷新页面
          this.getChapterVideo()
        })
    },

    //修改小节
    updateVideo(){
      video.updateVideo(this.video)
        .then(response => {
          //关闭弹窗
          this.dialogVideoFormVisible = false
          //提示
          this.$message({
            type: 'success',
            message: '修改小节成功!'
          });
          //刷新页面
          this.getChapterVideo()
        })
    },

    //弹出添加章节页面
    openVideo(chapterId) {
      //弹框
      this.dialogVideoFormVisible = true
      //表单数据清空
      this.video.title = ''
      this.video.sort = 0
      this.video.free = 0
      this.video.videoSourceId = ''
      //设置章节id
      this.video.chapterId = chapterId
    },

    //弹出编辑修改小节的弹框 数据回显
    openEditVideo(VideoId){
      //弹框
      this.dialogVideoFormVisible = true
      //调用接口方法getVideoInfo
      video.getVideoInfo(VideoId)
        .then(response => {
          this.video = response.data.eduVideo
        })
    },

    //删除小节
    deleteVideo(id){
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //调用接口方法deleteVideo
        video.deleteVideo(id)
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
    //上传视频成功调用的方法
    handleVodUploadSuccess(response, file, fileList) {
      //上传视频id赋值
      this.video.videoSourceId = response.data.videoId
      //上传视频名称赋值
      this.video.videoOriginalName = file.name
    },
    handleUploadExceed() {
      this.$message.warning('想要重新上传视频，请先删除已上传的视频')
    },
    //上传视频后点击×移除文件
    handleVodRemove(file, fileList) {
      //调用接口删除视频的方法
      video.deleteAliyunVideo(this.video.videoSourceId)
        .then(response => {
          //提示信息
          this.$message({
            type: 'success',
            message: '删除视频成功!'
          });
          //把文件列表清空
          this.fileList = []
          //把video视频id和视频名称值清空
          //上传视频id赋值
          this.video.videoSourceId = ''
          //上传视频名称赋值
          this.video.videoOriginalName = ''
        })
    },
    //阻止文件移除
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
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
