<template>
<div>
  <div
    class="fff"
    style="background-color: #285185; width: 100%"
    key="q"
    id="aaaa"
  >
    <div class="col">
      <select
        class="form-select mb-3"
        aria-label=".form-select-lg example"
        v-model="tag"
      >
        <option value="">選擇天數</option>
        <option value="1">1天</option>
        <option value="2">2天</option>
        <option value="3">3天</option>
        <option value="4">4天</option>
        <option value="5">5天</option>
        <option value="6">6天</option>
        <option value="7">7天</option>
        <option value="8">8天</option>
        <option value="9">9天</option>
        <option value="10">10天</option>
      </select>
    </div>
    <div class="col">
      <select class="form-select mb-3" v-model="stars">
        <option value="">選擇收藏數</option>
        <option value="10">大於10</option>
        <option value="50">大於50</option>
        <option value="100">大於100</option>
      </select>
    </div>
    <div class="col">
      <div class="input-group mb-3 justify-content-center" id="searchbtn">
        <input
          type="text"
          class="form-control"
          placeholder="行程標題搜尋"
          aria-label=""
          aria-describedby="button-addon2"
          v-model.lazy="count"
        />
        <button
          class="btn btn-outline-light"
          type="button"
          id="button-addon2"
          style="background-color: #285185"
        >
          搜尋
        </button>
      </div>
    </div>
  </div>
  <div id="test" style="width: 100%;background-color:#FFFFE" class="my-3">
   <!-- <h1>範例行程<h1> -->
    <swiper />
  </div> 
  <div class="container">
    <div class="card" v-for="item in travelcards" :key="item.wtid">
      <div class="card-body" style="background-color:#FFFFE" >
        <img
          :src="item.wtphoto"
          class="card-img-top img-fluid"
          style="width: 190px; height: 135px"
    
        />
        <h5 class="card-title" id="ttt">{{ item.wttitle }}</h5>
        <p class="card-text">
          <button  class="btn btn-secondary"
          :id="item.wtid"
            @click="like"
          >
            收藏</button 
          >
          <button  class="btn btn-secondary"
          :id="item.wtid"
            @click="getid"
          >
            檢視</button 
          >
        </p>
      </div>
    </div>
  </div>

        <div class="modal-dialog modal-xl" v-if="view" style=" z-index: 1; position: fixed;top: 0%;
      left: 30%;" >
            <div class="modal-content"  >
                <div class="modal-header py-2">
                    <h5 class="modal-title mx-auto" id="exampleModalLabel">檢視行程</h5>
                    <button type="button" class="btn-close btn-sm mx-0" data-bs-dismiss="modal" aria-label="Close" @click="clos"></button>
                </div>
                <div class="modal-body " style="overflow: ;" >
                    <div class="container-fluid" id="aasaa">                          
                        <div class="row">
                            <div v-for="item in cardList" :key="item.id">
                                <div>
                                    <span class="badge bg-secondary my-1 "> Day{{item.dtsequence}}</span>
                                    <!-- <p>當天出發時間: {{item.dtbegin}}</p> -->
                                </div>
                                <div class="row " id="travelrow">
                                    <div  class="col-3 px-1" v-for="i in item.detal" :key="i.id">
                                        <figure class="figure text-nowrap" >
                                            <div v-for="photosrc in i.attractionsBean.atrphotoBeans" :key="photosrc.id">
                                                <img :src="photosrc.photo" class="figure-img img-fluid rounded" alt="" id="pics">
                                            </div>
                                            <div >
                                                <figcaption class="figure-caption ">{{i.tutitle}}</figcaption>
                                                <figcaption class="figure-caption text-center">預計停留時間: {{i.staytime}}</figcaption>
                                                <figcaption class="figure-caption text-center">預計移動時間: {{i.movingtime}}</figcaption> 
                                                <figcaption class="figure-caption text-center">預計到下一站時間: {{i.ETD}}</figcaption>
                                            </div>
                                        </figure>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                   
                </div><button @click="pdfBtn">下載PDF</button>
            </div> 
        </div>
   
  
 </div> 
  <!-- <pulldown/> -->
</template>
<script>
import Cookies from "js-cookie";
const axios = require("axios");
import htmlToPdf from "@/utils/htmlToPdf";
import swiper from "@/components/swiper.vue";
const moment = require ('moment')
// import pulldown from "@/components/pulldown.vue";
export default {
  name: "Homeserch",
  components: {
    swiper,

    // pulldown,
  },
  data: function () {
    return {
      cardList: [],
            
      view:false,
      count: "",
      tag: "",
      stars: "",
      travelcards: [],
    };
  },
  methods: {
    clos(){
      this.view=false;
    },
    getid(){
      this.view=true;
      this.$store.commit("inputwtid",event.srcElement.id);
      let token = Cookies.get("authorization");
    var vm = this;
    this.cardList=[];
    axios
    .get(`http://localhost:8080/foodmap/wts/${event.srcElement.id}`,{
            headers: {
            Authorization: `Bearer ${token}`,
        }
        })
        .then(function (response) {
        // const moment = require ('moment')
        console.log(response.data);
        // var travel=[];
        for(let i=0; i<response.data.dayTravelBeans.length; i++){
            var day={};
            var timebar=new Date(response.data.dayTravelBeans[i].dtbegin);
            var t_s=timebar.getTime();
            vm.cardList.push(day);
            day.detal=[];
            day.dtsequence=response.data.dayTravelBeans[i].dtsequence;
            day.dtbegin=moment(response.data.dayTravelBeans[i].dtbegin).format('HH:mm');
            // var t_s=response.data.dayTravelBeans[i].dtbegin.getTime();
            for(let j=0; j<response.data.dayTravelBeans[i].travelUnitBeans.length; j++){
                var movingtime=response.data.dayTravelBeans[i].travelUnitBeans[j].movingtime;
                var staytime=response.data.dayTravelBeans[i].travelUnitBeans[j].staytime;
                let o =response.data.dayTravelBeans[i].travelUnitBeans[j];
                // estimated time of departure，簡稱ETD
                t_s=t_s+1000*60*(movingtime+staytime)
                timebar.setTime(t_s)
                o.ETD=moment(timebar.setTime(t_s)).format('HH:mm');
                day.detal.push(o);
            }

        }
        console.log(vm.cardList);
        })
        .catch(function (error) {
          //請求失敗處理
        console.log(error);
        });
      
      
    },
    like: function () {
      var vm=this;
      let token = Cookies.get("authorization");
       axios.post(`http://localhost:8080/foodmap/tripstorage?wtid=${event.srcElement.id}`,{},{ 
         headers: {
            Authorization: `Bearer ${token}`,
          }}).then(
            function(){
              vm.$toast("收藏成功", {
              duration: 1000,
              type: "success",
              positionX: "center",
              positionY: "top",
            })
            vm.$router.push({ name: "newtravel" });
            }
          ).catch();

     

    },
    settag: function (aa) {
      console.log(aa);
      alert("Hello " + this.name + "!" + aa);
    },

    pdfBtn() {
      htmlToPdf.getPdf(this.htmlTitle, "aasaa");
    },
  },
  computed: {
    listenchange() {
      const { count, tag, stars } = this;
      return { count, tag, stars };
    },
  },
  watch: {
    listenchange: function (val) {
      let token = Cookies.get("authorization");
      console.log("listenchange :" + val);
      var vm = this;
      this.travelcards = [];

      axios
        .get(
          `http://localhost:8080/foodmap/wts/multi?WTtitle=${val.count}&WTdays=${val.tag}&WTlike=${val.stars}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(function (response) {
          for (let i = 0; i < response.data.length; i++) {
            // q.push(response.data[i]);
            vm.travelcards.push(response.data[i]);
          }
          console.log(response);
          console.log(vm.travelcards);
        })
        .catch(function (error) {
          // 请求失败处理
          console.log(error);
        });
    },
  },
  amount() {
    // axios
    //     .get(
    //       "http://localhost:8080/foodmap/atrs/page/1",
    //       {
    //       },
    //     )
    //     .then(function (response) {
    //       document.getElementById('test').innerText=response;
    //       console.log(response);
    //     })
    //     .catch(function (error) {
    //       // 请求失败处理
    //       console.log(error);
    //     });
  },
};
</script>
<style scoped>
.fff {
  display: flex;
  height: 60px;
  padding: 15px;
  background-color: #61a0f8;
  flex-direction: row;
}
.mb-3 {
  width: 90%;
  margin: 0 auto;
}

.card {
  width: 225px;
  height: 300px;
  margin: 10px;
  float: left;
  background-color: #FFFFFE;
  border-color:#FF981E;
  border-width:2px;
}
.container {
  background-color: #f0f4fa;
  width: 100%;
  
 
}

#ttt{
  display: -webkit-box;
  overflow: hidden;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.figure {
    width: 100%;
    height:100%;
    border-style: double;
    border-radius: 5px;
    border-color: #285185;
    border-width: 2px;
}

#pics {
    width: 100%;
    height: 127px;
    object-fit: cover;
}
.modal-dialog,   
.modal-content {   
    height: 80%;   
}   
.modal-body {   
    max-height: calc(100% - 120px);   
    overflow-x: scroll;
    overflow-y: scroll;   
} 
#travelrow {

    flex-wrap: nowrap;
    /* flex-direction: row; */
    }
</style>
