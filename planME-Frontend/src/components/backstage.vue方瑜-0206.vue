<template>
  <div class="row">
    <!-- <div class="col-2" style=" background-color: #6B7A8F;"> 
  <div class="btn-group-vertical top-50 start-50 translate-middle">  -->
    <button
      type="button"
      class="btn text-light fw-bold"
      @click="atc(), (type = '1')"
    >
      被檢舉文章
    </button>
    <button
      type="button"
      class="btn text-light fw-bold"
      @click="msg(), (type = '2')"
    >
      被檢舉留言
    </button>
    <button
      type="button"
      class="btn text-light fw-bold"
      @click="home(), (type = '3')"
    >
      首頁管理
    </button>
    <button
      type="button"
      class="btn text-light fw-bold"
      @click="pmm(), (type = '4')"
    >
      人員管理
    </button>
    <button
      type="button"
      class="btn text-light fw-bold"
      @click="report(), (type = '5')"
    >
      報表
    </button>
  </div>

  <!-- 被檢舉文章 -->

  <!-- <div class="container" v-if="type === '1'">
    <div class="card" v-for="item in listatc" :key="item.id">
      <div class="card-body">
        <h5 class="card-title">{{item.atctitle}}</h5>
        <p class="card-text">{{item.atctext}}</p>
        <button class="btn btn-info mx-2" >保留</button>
        <button class="btn btn-info" @click="delatc()">刪除</button>
      </div>
    </div>
  </div> -->
  <!-- <div class="container" v-if="type === '1'">
    <div class="card" v-for="item in listatc" :key="item.id">
      <div class="card-header">
        {{ item.atctitle }}
      </div>
      <div class="card-body">
        <h5 class="card-title"></h5>
        <p class="card-text">{{ item.atctext }}</p>
        <button class="btn btn-info mx-2">保留</button>
        <button class="btn btn-info" @click="delatc()">刪除</button>
      </div>
    </div>
  </div> -->
  <div class="aaa" v-if="type === '1'">
    <div class="header" v-for="item in listatc" :key="item.id">
      <div class="card-header">
        {{ item.atctitle }}
        <p class="card-text">{{ item.atctext }}</p>
        <button class="btn btn-info mx-2" @click="saveAtc()" :id="item.atcid">保留</button>
        <button class="btn btn-info" @click="delatc()" :id="item.atcid" >刪除</button>
      </div>
    </div>
  </div>

  <!-- 被檢舉留言 -->

  <!-- <div class="aaa" v-if="type === '2'">
    <div class="card" v-for="item in listmsg" :key="item.id">
      <div class="card-body">
        <h5 class="card-title"></h5>
        <p class="card-text">{{ item.mstext }}</p>
        <button class="btn btn-info" @click="del()">保留</button>
        <button class="btn btn-info" @click="del()">刪除</button>
      </div>
    </div>
  </div> -->
   <div class="aaa" v-if="type === '2'">
    <div class="header" v-for="item in listmsg" :key="item.id">
      <div class="card-header">
        {{ item.atctitle }}
        <p class="card-text">{{ item.mstext  }}</p>
        <button class="btn btn-info mx-2"  @click="saveMsg()" :id="item.msgid">保留</button>
        <button class="btn btn-info" @click="delmsg()" :id="item.msgid">刪除</button>
      </div>
    </div>
  </div>


  <!-- 首頁管理 -->

  <div class="container" v-else-if="type === '3'"></div>

  <!-- 人員管理 -->

  <div class="container" v-else-if="type === '4'"></div>

  <!-- 報表 -->

  <div class="container" v-if="type === '5'"></div>
</template>

<script>
const axios = require("axios");
import Cookies from "js-cookie";
export default {
  data() {
    return {
      type: "3",

      //文章
      listatc: [],
      pageatc: 0,

      //留言
      listmsg: [],
      pagemsg: 0,
    };
  },
  computed: {
    reversedMessage: function () {
      return this.listatc.atctitle;
    },
  },

  methods: {
    //被檢舉文章

    atc() {
      let token = Cookies.get("authorization");
      var vm = this;
      axios
        .get(
          `http://localhost:8080/foodmap/articles/reported?page=${this.pageatc}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          for (let i = 0; i < response.data.length; i++) {
            vm.listatc.push(response.data[i]);
          }
        })
        .catch(function (error) {
          //請求失敗處理
          console.log(error);
        });

      this.pageatc++;
      console.log(this.pageatc);
      console.log(vm.listatc);
      console.log(this);
    },



    //刷新被檢舉文章

    async atcNew() {
      let token = Cookies.get("authorization");
      this.listatc = [];
      var atc = this;
      await axios
        .get(`http://localhost:8080/foodmap/articles/reported`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          console.log();
          for (let i = 0; i < response.data.length; i++) {
            atc.listatc.push(response.data[i]);
          }
        })
        .catch(function (error) {
          // 請求失敗處理
          console.log(error);
        });
    },

    //被檢舉留言

    msg() {
      let token = Cookies.get("authorization");
      var vm = this;
      axios
        .get(
          `http://localhost:8080/foodmap/messages/reported?page=${this.pagemsg}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          for (let i = 0; i < response.data.length; i++) {
            vm.listmsg.push(response.data[i]);
          }
        })
        .catch(function (error) {
          //請求失敗處理
          console.log(error);
        });
      this.pagemsg++;
      console.log(this.pagemsg);
      console.log(vm.listmsg);
      console.log(this);
    },
    //刷新被檢舉留言

    async msgNew() {
      let token = Cookies.get("authorization");
      this.listmsg = [];
      var msg = this;
      await axios
        .get(`http://localhost:8080/foodmap/messages/reported`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          console.log();
          for (let i = 0; i < response.data.length; i++) {
            msg.listmsg.push(response.data[i]);
          }
        })
        .catch(function (error) {
          // 請求失敗處理
          console.log(error);
        });
    },


    //刪除文章

    delatc() {
      let token = Cookies.get("authorization");
      var delatc = this;
      axios
        .delete(
          `http://localhost:8080/foodmap/articles/${event.srcElement.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          console.log(response);
          delatc.atcNew();

          alert("刪除成功");
        })
        .catch(function (error) {
          // 請求失敗處理
          console.log(error);
        });

      console.log(delatc);
    },

    //保留文章

    saveAtc() {
      let token = Cookies.get("authorization");
      var saveAtc = this;
      axios
        .put(
          `http://localhost:8080/foodmap/articles/reported?atcid=${event.srcElement.id}&reported=false`,{},
          {
            headers: {
              Authorization:`Bearer ${token}`
            }
          }
        )
        .then(function (response) {
         console.log(response);
         saveAtc.atcNew();

          alert("已成功");
        })
        .catch(function (error) {
          // 請求失敗處理
          console.log(error);
        });

      console.log(saveAtc);
    },

    //刪除留言
     delmsg() {
      let token = Cookies.get("authorization");
      var delmsg = this;
      axios
        .delete(
          `http://localhost:8080/foodmap/messages/${event.srcElement.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          console.log(response);
          delmsg.msgNew();

          alert("刪除成功");
        })
        .catch(function (error) {
          // 請求失敗處理
          console.log(error);
        });

      console.log(delmsg);
    },
    //保留留言
    saveMsg() {
      let token = Cookies.get("authorization");
      var saveMsg = this;
      axios
        .put(
          `http://localhost:8080/foodmap/messages/reported?msgid=${event.srcElement.id}&reported=false`,{},
          {
            headers: {
              Authorization:`Bearer ${token}`
            }
          }
        )
        .then(function (response) {
         console.log(response);
         saveMsg.msgNew();

          alert("已成功");
        })
        .catch(function (error) {
          // 請求失敗處理
          console.log(error);
        });

      console.log(saveMsg);
    },
  },
};
</script>
    
<style>
.card {
  width: 200px;
  height: 300px;
  margin: 5px;
  background-color: rgb(216, 253, 253);
}

/* .cardtwo {
  width: 900px;
  height: 180px;
  margin: 10px;
  float: left;
  background-color: rgb(250, 238, 223);
} */
.row {
  background-color: #6b7a8f;
  float: left;
  width: 20%;
  height: 30rem;
}

.container {
  background-color: #c1d2e7;
  width: 80%;
  height: 30rem;
  overflow: scroll;
  overflow-x: hidden;
}
.aaa {
  background-color: #c1d2e7;
  width: 80%;
  height: 30rem;
  overflow: scroll;
  overflow-x: hidden;
}

/* .b {
  margin-left: 30px;
  vertical-align: top;
} */

/* .title {
  width: 600px;
  overflow: hidden;
}

.text {
  width: 600px;
  overflow: hidden;
}

.article {
  vertical-align: top;
} */
</style>