<template>
  <div class="row" id="rowrow">
    <!-- <div class="col-2" style=" background-color: #6B7A8F;"> 
  <div class="btn-group-vertical top-50 start-50 translate-middle">  -->
    <button type="button" class="btn text-light fw-bold " @click="wt(), (type = '1')">收藏行程</button>
    <button type="button" class="btn text-light fw-bold" @click="atr(), (type = '2')">收藏景點</button>
    <button type="button" class="btn text-light fw-bold" @click="atc(), (type = '3')">收藏文章</button>
    <button type="button" class="btn text-light fw-bold" @click="type = '4'">發布文章</button>
    <button type="button" class="btn text-light fw-bold" @click="all(), (type = '5')">瀏覽文章</button>
    <button type="button" class="btn text-light fw-bold" @click="mb(), mbatc(), (type = '6')">個人資訊</button>
  </div>

  <!-- 更多資訊 -->
  <div
    class="inf"
    v-if="setinf"
    style="
      width: 77%;
      height: 70%;
      z-index: 1;
      position: fixed;
      background-color: rgb(252, 252, 252);
      top: 10%;
      left: 20%;
      border-width: 3px;
      border-style: dashed;
      border-color: #ffac55;
      padding: 5px;
    "
    key="5587"
  >
    <!-- <img :src="atcinf.atcphoto" /> -->
    <img src="https://www.welcometw.com/wp-content/uploads/2020/10/%E9%AB%98%E7%BE%8E%E6%BF%95%E5%9C%B0@xteme1123-825x510.jpg">
    <br />
    <h3>{{ atcinf.atctitle }}</h3>
    <br />
    <h5>{{ atcinf.atctext }}</h5>
<!-- <p id="atcidis">{{atcinf.atcid}} </p> -->
    <hr />

    <p v-for="item in atcinf.atcmessage" :key="item.id">
      <img
        src="/homeimg/flag.png"
        style="width: 40.2px; height: 38px; float: left"
        :id="item.msgid"
        @click="reportms()"
      />
      {{ item.mstext }}
    </p>

    <textarea
      :id="atcinf.atcid"
      class="text"
      rows="2"
      cols="70"
      required
      autofocus
      v-model="ms"
    >
    </textarea>

    <div
      class="card-footer border-0 pt-0"
      style="background-color: rgb(252, 252, 252)"
    >
    
      <button :id="atcinf.atcid" class="btn btn-secondary mx-2" @click="poms(), closeinf(), atcc()">送出</button>
      <button class="btn btn-secondary" @click="closeinf()">關閉</button>
    </div>
  </div>

  <!-- 收藏行程 -->
  <div class="container" v-if="type === '1'">
    <h3 class="card-title">收藏行程</h3>
    <!-- <div class="card-group"> -->

    <div class="card" v-for="item in listwt" :key="item.id">
      <div class="">
      <img
        :src="item.wtphoto"
        class="card-img-top img-fluid"
        style="width: 150px; height: 100px;"
        alt="..."
      />
      </div>

      <div class="card-body mb-0">
      
        <!-- <div class="card-header"> -->
        <h5 class="card-title mb-0" id="textEllipsis">
          <h5 class="bg-info text-white" id="test2">
             {{ item.wttitle }}
          </h5>
        </h5>

        <p class="card-text" id="test">{{ item.wtintroduce }}</p>
      </div>
      <!-- <p class="card-text">{{item.wtlike}}</p> -->
      <div class="card-footer row border-0 mx-0 align-items-center" style="background-color: rgb(252, 252, 252);" >
        <div class="col-6 px-0 d-flex justify-content-end">
        <demoComponent/>
        </div>
        <div class="col-6 px-0 ">
        <img src="/homeimg/brokenheart.png" style="width: 31.2px; height: 26px;" :id="item.wtid" @click="delwt()" />
        </div>
      </div>
    </div>
  </div>

  <!-- 收藏景點 -->

  <div class="container" v-else-if="type === '2'">
    <h3 class="card-title">收藏景點</h3>
    <div class="card" v-for="item in listatr" :key="item.id">
      <div v-for="aa in item.attractionsBean.atrphotoBeans" :key="aa.id">
        <img
          :src="aa.photo"
          class="card-img-top img-fluid"
          style="width: 250; height: 120px"
          alt="..."
        />
      </div>

      <div class="card-body pb-5">
        <h5 class="card-title">
          <p class="bg-info text-white">
            {{ item.attractionsBean.atrname }}
          </p>
        </h5>

        <p class="card-text">{{ item.attractionsBean.atraddress }}</p>
        <p class="card-text">電話:    
          {{ item.attractionsBean.atrtel }}</p>
      </div>

      <div
        class="card-footer border-0 pt-0"
        style="background-color: rgb(252, 252, 252)"
      >
        <img
          src="/homeimg/brokenheart.png"
          style="width: 31.2px; height: 26px"
          :id="item.placeStorageId.atrid"
          @click="delatr()"
        />
      </div>
    </div>
  </div>

  <!-- 收藏文章 -->

  <div class="container" v-else-if="type === '3'">
    <h3 class="card-title">收藏文章</h3>
    <div class="card" v-for="item in listatc" :key="item.id">
      <img
        :src="item.articleBean.atcphoto"
        class="card-img-top img-fluid"
        style="width: 150px; height: 100px"
        alt="..."
      />

      <div class="card-body pb-5">
        <h5 class="card-title">
          <p class="bg-info text-white" id="test2">
            {{ item.articleBean.atctitle }}
          </p>
        </h5>
        <p class="card-text" id="test">{{ item.articleBean.atctext }}</p>

        <!-- 留言：<p class="card-text">{{ item.articleBean.messageBeans.mstext }}</p> -->
        <!-- <button class="" :id="item.articleStorageId.atcid" @click="atcim()">更多資訊</button>
        <button  class="breakheart" :id="item.articleStorageId.atcid" @click="delatc()">移除收藏</button> -->
      </div>

      <div
        class="card-footer border-0 pt-0"
        style="background-color: rgb(252, 252, 252)"
      >
        <p class="card-text">收藏數量：{{ item.articleBean.atclike }}</p>

        <button class="btn btn-secondary" :id="item.articleStorageId.atcid" @click="atcim()">
          更多資訊
        </button>
        <img
          src="/homeimg/flag.png"
          style="width: 40.2px; height: 38px; float: left"
          :id="item.articleStorageId.atcid"
          @click="reportatc()"
        />
        <img
          src="/homeimg/brokenheart.png"
          style="width: 31.2px; height: 26px; float: right"
          :id="item.articleStorageId.atcid"
          @click="delatc()"
        />
      </div>
    </div>

    <!-- </div> -->
  </div>

  <!-- 發布文章 -->

  <div class="container" v-else-if="type === '4'">
    <div class="article">
      <h3 class="card-title">發布文章</h3>
      <div class="card-body">
        <input type="file" @change="asdqwe()" />
        <!-- <input type="text" v-model="artimg" placeholder="請輸入圖片"><br> -->
        <p></p>
        圖片預覽<br />
        <p></p>
        
        <img :src="artimg" alt="Image preview..." /><br />
        <p></p>
        <div><b>標題</b></div>
        <input
          class="title"
          placeholder="請輸入標題"
          required
          v-model="title"
        />

        <!-- <hr class="one" /> -->
        <p></p>
        <!-- <hr size="5px" align="center" width="100%"> -->

        <div><b>內文</b></div>
        <textarea
          class="text"
          rows="7"
          cols="70"
          required
          autofocus
          v-model="area"
        >
        </textarea>
      </div>
      <button class="btn btn-secondary" @click="poart()">送出</button>
    </div>
  </div>

  <!-- 瀏覽文章 -->

  <div class="container" v-else-if="type === '5'">
    <h3 class="card-title">瀏覽文章</h3>
    <div class="card" v-for="item in listall" :key="item.id">
      <div class="card-body">
        <h5 class="card-title">
          <p class="bg-info text-white" id="test2">
            {{ item.atctitle }}
          </p>
        </h5>
        <p class="card-text" id="test">{{ item.atctext }}</p>
      </div>

      <div
        class="card-footer border-0 pt-0"
        style="background-color: rgb(252, 252, 252)"
      >
        <p class="card-text">收藏數量：{{ item.atclike }}</p>
        <button class="btn btn-secondary" @click="allim()">更多資訊</button>
        <img
          src="/homeimg/flag.png"
          style="width: 40.2px; height: 38px; float: left"
          :id="item.atcid"
          @click="reportatc()"
        />
        <img
          src="/homeimg/like.png"
          style="width: 32.2px; height: 30px; float: right"
          :id="item.atcid"
          @click="likeatc()"
        />
      </div>
    </div>
  </div>

  <!-- 基本資料 -->

  <div class="container" v-else-if="type === '6'">
    <h3 class="card-title">個人資料</h3>

    <div class="mb">
      <p>帳號：{{ member.name }}</p>
      <p>信箱：{{ member.email }}</p>
    </div>

    <h3 class="card-title">個人文章</h3>
    <hr class="one" />
    <!-- <hr size="8px" align="center" width="100%"> -->
    <div class="card" v-for="item in listmb" :key="item.id">
      <div class="card-body">
        <h5 class="card-title">
          <p class="bg-info text-white" id="test2">
            {{ item.atctitle }}
          </p>
        </h5>
        <p class="card-text" id="test">
          {{ item.atctext }}
        </p>
        <!-- <p class="card-text">{{ item.atclike }}</p> -->
        <!-- <button class="" @click="upmb()">編輯文章</button> -->
      </div>
      <div
        class="card-footer border-0 pt-0"
        style="background-color: rgb(252, 252, 252)"
      >
        <button class="btn btn-secondary" @click="mbim()">更多資訊</button>
        <img
          src="/homeimg/garbagecan.png"
          style="width: 40.2px; height: 38px; float: right;"
          :id="item.atcid"
          @click="delmb()"
        />
      </div>
      <!-- <button class="" :id="item.atcid" @click="delmb()">刪除文章</button> -->
    </div>
    <!-- <div class="card" v-for="item in list" :key="item.mbid">
      <div class="card-body">
      </div>
    </div> -->
  </div>
</template>

<script>
const axios = require("axios");
import Cookies from "js-cookie";
import demoComponent from "./demoComponent.vue";
export default {
  components: {
    demoComponent,
  },
  data() {
    return {
      type: "7",
      isShow: true,

      // list: [],
      // page: 0,

      member: {
        name: "",
        email: "",
      },

      //行程
      listwt: [],

      //景點
      listatr: [],
      pageatr: 0,

      //文章
      listatc: [],
      pageatc: 0,
      artimg:
        "https://hotelcozzi.com/wp-content/uploads/2021/01/banner_taipei101_phone.jpeg",

      //瀏覽
      listall: [],
      pageall: 0,

      //個人
      listmb: [],

      title: "",
      area: "",

      setinf: false,

      atcinf: {
        atcphoto: "",
        atctitle: "",
        atctext: "",
        atcmessage: [],
        atcis:""
      },

      ms: "",
    };
  },

  methods: {
    
    closeinf() {
      this.setinf = false;
    },

    //收藏文章更多資訊

    atcim() {
      this.setinf = true;
      let token = Cookies.get("authorization");
      var atc = this;
      this.atcinf.atcmessage=[];
      axios
        .get(`http://localhost:8080/foodmap/articles/${event.srcElement.id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          // for (let i = 0; i < response.data.articleStorages.length; i++) {
          //   atc.listatc.push(response.data.articleStorages[i]);
          // }
          atc.atcinf.atcphoto = response.data.atcphoto;
          atc.atcinf.atctitle = response.data.atctitle;
          atc.atcinf.atctext = response.data.atctext;
          atc.atcinf.atcid = response.data.atcid;
          // atc.atcinf.atcmessage=response.data.messageBeans;
          for (let i = 0; i < response.data.messageBeans.length; i++) {
            atc.atcinf.atcmessage.push(response.data.messageBeans[i]);
          }
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });

      console.log(atc.listatc);
      console.log(this);
    },

    //瀏覽文章更多資訊

    allim() {

    },

    //個人文章更多資訊

    mbim() {

    },

    //收藏行程

    wt() {
      let token = Cookies.get("authorization");
      var wt = this;
      axios
        .get("http://localhost:8080/foodmap/myself", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          for (let i = 0; i < response.data.tripStorages.length; i++) {
            wt.listwt.push(response.data.tripStorages[i].wholeTravelBean);
          }
        })
        .catch(function (error) {
          console.log(error);
        });

      console.log(this);
    },

    //刷新收藏行程

    wtt() {
      let token = Cookies.get("authorization");
      this.listwt = [];
      var wt = this;
      axios
        .get("http://localhost:8080/foodmap/myself", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          console.log(response.data.wholeTravelBeans[0].wttitle);
          for (let i = 0; i < response.data.tripStorages.length; i++) {
            wt.listwt.push(response.data.tripStorages[i].wholeTravelBean);
          }
        })
        .catch(function (error) {
          console.log(error);
        });

      console.log(this);
    },

    //收藏景點

    async atr() {
      let token = Cookies.get("authorization");
      // var q=[];
      var atr = this;
      await axios
        .get("http://localhost:8080/foodmap/myself", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          // "s":"s",
        })
        .then(function (response) {
          console.log();
          // document.getElementById('first').innerText=response;
          for (let i = 0; i < response.data.placeStorages.length; i++) {
            // q.push(response.data[i]);
            atr.listatr.push(response.data.placeStorages[i]);
            console.log(response.data.placeStorages[i]);
          }
        })
        .catch(function (error) {
          // 请求失败处理
          console.log(error);
        });
      // for(let i=0;i<q.length;i++){
      //     vm.list.push(q[i]);
      //
    },

    //刷新收藏景點

    async atrr() {
      let token = Cookies.get("authorization");
      // var q=[];
      this.listatr = [];
      var atr = this;
      await axios
        .get("http://localhost:8080/foodmap/myself", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          // "s":"s",
        })
        .then(function (response) {
          console.log();
          // document.getElementById('first').innerText=response;
          for (let i = 0; i < response.data.placeStorages.length; i++) {
            // q.push(response.data[i]);
            atr.listatr.push(response.data.placeStorages[i]);
            console.log(response.data.placeStorages[i]);
          }
        })
        .catch(function (error) {
          // 请求失败处理
          console.log(error);
        });
      // for(let i=0;i<q.length;i++){
      //     vm.list.push(q[i]);
      //
    },

    //收藏文章

    atc() {
      let token = Cookies.get("authorization");
      var atc = this;
      axios
        .get("http://localhost:8080/foodmap/myself", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          for (let i = 0; i < response.data.articleStorages.length; i++) {
            atc.listatc.push(response.data.articleStorages[i]);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log(atc.listatc);
      console.log(this);
    },

    //刷新收藏文章

    atcc() {
      let token = Cookies.get("authorization");
      this.listatc = [];
      var atc = this;
      axios
        .get("http://localhost:8080/foodmap/myself", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          for (let i = 0; i < response.data.articleStorages.length; i++) {
            atc.listatc.push(response.data.articleStorages[i]);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log(atc.listatc);
      console.log(this);
    },

    //發布留言 actid=3
    //發布留言 actid=3
    //發布留言 actid=3

    poms() {
      let token = Cookies.get("authorization");
      var ss = this;
      console.log(event.srcElement.id);
      axios
        .post(
          `http://localhost:8080/foodmap/messages?actid=${this.atcinf.atcid}`,
          {
            isReported: false,
            deleted: false,
            mslike: 3,
            mstext: this.ms,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          console.log(response);
          ss.ms = "";
          ss.$toast("留言成功！", {
            duration: 3000,
            class: "huge",
            positionX: "center",
            positionY: "top",
            styles: {
              color: "#7ca1e6",
              backgroundColor: "#faf8e6",
            },
          });
          // this.$forceUpdate();
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    //發布文章

    poart() {
      let token = Cookies.get("authorization");
      var sr = this;
      console.log(token);
      axios({
        method: "post",
        url: "http://localhost:8080/foodmap/articles",
        headers: {
          Authorization: `Bearer ${token}`,
        },
        data: {
          atctitle: this.title,
          atctext: this.area,
        },
      })
        .then(function (response) {
          console.log(response);
          sr.area = "";
          sr.title = "";
          sr.$toast("發布成功！", {
            duration: 3000,
            class: "huge",
            positionX: "center",
            positionY: "top",
            styles: {
              color: "#7ca1e6",
              backgroundColor: "#faf8e6",
            },
          });
          // this.$forceUpdate();
        })
        .catch(function (error) {
          console.log(error);
        });

      console.log(this);
    },

    //瀏覽文章

    all() {
      let token = Cookies.get("authorization");
      var all = this;
      axios
        .get("http://localhost:8080/foodmap/articles", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          for (let i = 0; i < response.data.length; i++) {
            all.listall.push(response.data[i]);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log(all.listall);
      console.log(this);
    },

    // 個人資料

    mb() {
      let token = Cookies.get("authorization");
      var vm = this;
      axios
        .get("http://localhost:8080/foodmap/myself", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          console.log(response);
          vm.member.name = response.data.mbname;
          vm.member.email = response.data.mbemail;
          // document.getElementById('first').innerText=response;
        })
        .catch(function (error) {
          // 请求失败处理
          console.log(error);
        });
      // for(let i=0;i<q.length;i++){
      //     vm.list.push(q[i]);
      //   }
      // this.page++;

      console.log(vm.list);
      console.log(this);
    },

    // 個人文章

    mbatc() {
      let token = Cookies.get("authorization");
      var mbatc = this;
      var name = Cookies.get("name");
      axios
        .get(`http://localhost:8080/foodmap/articles/name?name=${name}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          for (let i = 0; i < response.data.length; i++) {
            mbatc.listmb.push(response.data[i]);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log(this);
    },

    //移除行程

    delwt() {
      let token = Cookies.get("authorization");
      var delwt = this;
      axios
        .delete(
          `http://localhost:8080/foodmap/tripstorage?wtid=${event.srcElement.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          console.log(response);
          delwt.wtt();
          delwt.$toast("移除成功！", {
            duration: 3000,
            class: "huge",
            positionX: "center",
            positionY: "top",
            styles: {
              color: "#7ca1e6",
              backgroundColor: "#faf8e6",
            },
          });

          // alert("移除成功");
        })
        .catch(function (error) {
          console.log(error);
        });

      console.log(delwt);
    },

    //移除景點

    delatr() {
      let token = Cookies.get("authorization");
      // var q=[];
      var delatr = this;
      axios
        .delete(
          `http://localhost:8080/foodmap/placestorage?atrid=${event.srcElement.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          console.log(response);
          delatr.atrr();
          delatr.$toast("移除成功！", {
            duration: 3000,
            class: "huge",
            positionX: "center",
            positionY: "top",
            styles: {
              color: "#7ca1e6",
              backgroundColor: "#faf8e6",
            },
          });

          // alert("移除成功");
        })
        .catch(function (error) {
          // 请求失败处理
          console.log(error);
        });

      console.log(delatr);
    },

    //移除文章

    delatc() {
      let token = Cookies.get("authorization");
      var delatc = this;
      axios
        .delete(
          `http://localhost:8080/foodmap/articlestorage?atcid=${event.srcElement.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          console.log(response);
          delatc.atcc();
          delatc.$toast("移除成功！", {
            duration: 3000,
            class: "huge",
            positionX: "center",
            positionY: "top",
            styles: {
              color: "#7ca1e6",
              backgroundColor: "#faf8e6",
            },
          });
          // alert("移除成功");
        })
        .catch(function (error) {
          console.log(error);
        });

      console.log(delatc);
    },

    //刪除個人文章

    delmb() {
      let token = Cookies.get("authorization");
      var delmb = this;

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
          delmb.mbatcc();
          delmb.$toast("刪除成功！", {
            duration: 3000,
            class: "huge",
            positionX: "center",
            positionY: "top",
            styles: {
              color: "#7ca1e6",
              backgroundColor: "#faf8e6",
            },
          });
        })
        .catch(function (error) {
          console.log(error);
        });

      console.log(delmb);
    },

    //刷新個人文章

    mbatcc() {
      let token = Cookies.get("authorization");

      this.listmb = [];
      var mbatc = this;
      var name = Cookies.get("name");
      axios
        .get(`http://localhost:8080/foodmap/articles/name?name=${name}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
          for (let i = 0; i < response.data.length; i++) {
            mbatc.listmb.push(response.data[i]);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log(mbatc.listmb);
    },

    //檢舉文章

    reportatc() {
      let token = Cookies.get("authorization");
      var reportatc = this;
      axios
        .put(
          `http://localhost:8080/foodmap/articles/reported?atcid=${event.srcElement.id}&reported=true`,
          {},
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          console.log(response);

          reportatc.$toast("檢舉成功！", {
            duration: 3000,
            class: "huge",
            positionX: "center",
            positionY: "top",
            styles: {
              color: "#7ca1e6",
              backgroundColor: "#faf8e6",
            },
          });
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log(reportatc);
    },

    //檢舉留言

    reportms() {
      let token = Cookies.get("authorization");
      var reportms = this;
      axios
        .put(
          `http://localhost:8080/foodmap/messages/reported?msgid=${event.srcElement.id}&reported=true`,
          {},
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          console.log(response);

          reportms.$toast("檢舉成功！", {
            duration: 3000,
            class: "huge",
            positionX: "center",
            positionY: "top",
            styles: {
              color: "#7ca1e6",
              backgroundColor: "#faf8e6",
            },
          });
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log(reportms);
    },

    //收藏文章

    likeatc() {
      let token = Cookies.get("authorization");
      var vs = this;
      axios
        .post(
          `http://localhost:8080/foodmap/articlestorage?atcid=${event.srcElement.id}`,
          {},
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function () {
          vs.$toast("收藏成功", {
            duration: 3000,
            type: "success",
            positionX: "center",
            positionY: "top",
          });
        })
        .catch();
    },
    asdqwe(){
      console.log(event.target.files[0]);
       const file = event.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    var vm=this;
    reader.onload=function () {
      console.log(reader.result)
      vm.artimg=reader.result;
    }
    
    
    },
  },
};



</script>
    
<style scoped>

.card {
  width: 300px;
  height: 350px;
  margin: 10px;
  float: left;
  background-color: rgb(250, 252, 252);
  border-width: 2px;
  border-color: #285185;
}

#rowrow {
  /* position: fixed; */
  
  background-color: #6b7a8f;
  float: left;
  width: 20%;
  height: 86vh;
}

.container {
  /* position: relative; */
  background-color: #f0f4fa;
  width: 80%;
  height: 86vh;
  overflow: scroll;
  overflow-x: hidden;
}

.b {
  margin-left: 30px;
  vertical-align: top;
}

.title {
  width: 600px;
  overflow: hidden;
}

.text {
  width: 600px;
  overflow: hidden;
}

.article {
  vertical-align: top;
}

.mb {
  text-align: left;
}

.one {
  border: 0;
  padding: 3px;
  background: repeating-linear-gradient(
    135deg,
    #7ca1e6 0px,
    #0a1222 1px,
    transparent 1px,
    transparent 6px
  );
}

.huge {
  padding: 50px;
}

#test {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
}

#test2 {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}

.inf {
  overflow: scroll;
  overflow-x: hidden;
}

#textEllipsis {
  display: -webkit-box;
  overflow: hidden;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;

}

</style>