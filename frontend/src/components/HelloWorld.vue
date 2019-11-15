<template>
  <el-container style="width:600px; height: 500px; border: 1px solid #eee">
    <el-header style="text-align: right; font-size: 12px">
      <div style="text-align: left;margin-top: 20px;font-size:larger">
        <b>TimeLine</b></div>
      <span><el-button type="primary" round v-on:click="loadData" >{{newCommentNumber}}刷新</el-button></span>
    </el-header>
    <el-divider></el-divider>
    <el-main >
      <div  v-for="comment in comments" style = "border-radius: 4px">
        <el-scrollbar :native="false" wrapStyle="" wrapClass="" viewClass="" viewStyle="" noresize="false" tag="section">
      <div style= "float: left" class = "comment_detail" >
        <h5>{{comment.comment_name}}</h5>
      </div>
          <div style="float: right" class="comment_time">
            <h5>{{comment.comment_time}}</h5>
          </div>
          <br>
        <div class = "content">
          {{comment.comment_content}}
        </div>
          <div class = "img" v-if="comment.comment_img">
            <el-image
              style="width: 100px; height: 100px"
              :src="comment.comment_img"
              :fit="fit"></el-image>
          </div>
          <el-divider></el-divider>
        </el-scrollbar>
      </div>
    </el-main>
      <el-button style = "width:150px;height:50px;margin-left: 200px" type="primary" round v-on:click="loadEarlyData">获取更多</el-button>
    <el-divider></el-divider>
  </el-container>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      comments:[], newCommentNumber:0,
    }
  },
    created:function(){
      let self = this;
      console.log("create enter");
      this.$ajax.get('/api/initialize')
          .then(function(res){
              self.comments = res.data;
          })
          .catch(function(res){
              console.log(res);
          })
        },
    mounted:function(){
      this.timer = setInterval(this.refresh,5000);
    },
  methods:{
      refresh(){
          let self = this;
          console.log("refresh");
          this.$ajax.get('/api/getNumber',{
              params:{time:self.comments[0].comment_time}
          })
              .then(function(res){
                  console.log(res.data)
                  self.newCommentNumber = res.data;
              })
      },
      loadData(){
          let self = this;
          console.log("click button over");
          console.log(self.comments[0].comment_time)
          this.$ajax.get('/api/timeline',{
              params:{time:self.comments[0].comment_time}
          })
              .then(function(res){
                  console.log(res.data);
                  for(let j = 0,len=res.data.length;j<len;j++){
                      self.comments.unshift(res.data[j]);
                      console.log("push");
                  }
                 // console.log(this.comments);
                  self.newCommentNumber = 0;
              })
              .catch(function(res){
                  console.log(res);
              });
      },
      loadEarlyData(){
          let self = this;
          console.log("request early data");
          this.$ajax.get('/api/getEarly',{
              params:{time:self.comments[self.comments.length-1].comment_time}
          })
              .then(function(res){
                  console.log(res.data);
                  for(let j = 0;j<res.data.length;j++){
                      self.comments.push(res.data[j]);
                  }
              })
              .catch(function(res){
                  console.log(res);
              })
      }
  }
}
</script>
<style src="../../config/comment-board.css"></style>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
