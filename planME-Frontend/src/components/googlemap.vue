<script>
/* eslint-disable no-undef */
import { computed, ref, onMounted, onUnmounted, watch } from "vue";
import { useGeolocation } from "../useGeolocation";
import { Loader } from "@googlemaps/js-api-loader";
import axios from 'axios';
import Cookies from "js-cookie";
// import ligthbox from "@/components/ligthbox.vue";
// import mapsearch from "@/components/mapsearch.vue";
// import placereport from "@/components/placereport.vue";
const GOOGLE_MAPS_API_KEY = "AIzaSyDX1QepNfBvng2zNu5dwUfQHd6elCNSyGU";
export default {
  name: "App",
  // components: { mapsearch },
  setup() {
    const { coords } = useGeolocation();
    const currPos = computed(() => ({
      lat: coords.value.latitude,
      lng: coords.value.longitude,
    }));

    // var ppid = null;
    const otherPos = ref(null);
    const loader = new Loader({ apiKey: GOOGLE_MAPS_API_KEY });
    const mapDiv = ref(null);
    let map = ref(null);

    const placecard = ref(null);
    const placeclick = ref(null);
    let clickListener = null;
    let clickidListener = null;
    // const corsany = "https://cors-anywhere.herokuapp.com/";
    const corsany = "http://localhost:8082/";
    onMounted(async () => {
      await loader.load();
      placecard.value = [];
      placeclick.value = [];
      map.value = new google.maps.Map(mapDiv.value, {
        center: currPos.value,
        zoom: 7,
      });
      clickListener = map.value.addListener(
        "click",
        ({ latLng: { lat, lng } }) =>
          (otherPos.value = { lat: lat(), lng: lng() })
      );
      clickidListener = map.value.addListener("click", (isIconMouseEvent) => {
        // ppid = isIconMouseEvent.placeId;
        
        getdetail(isIconMouseEvent.placeId)
          .then((data) => {
            console.log("data::");
            console.log(data.result);
            let asd = data.result;
            if (typeof data.result != "undefined") {
              getphoto(data.result.photos[0].photo_reference).then((data) => {
                asd.ppo = data;
                console.log(data);
              });
              placecard.value.push(asd);
              placeclick.value[0] = asd;
              
            
            } else {
              placeclick.value.splice(0, 1);
            }
            console.log("cards::");
            console.log(placecard.value);
            console.log("point::");
            console.log(placeclick.value);
            
          })
          .catch();
      });
    });
    const getphoto = async (placeref) => {
      var a = `https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photo_reference=${placeref}&key=${GOOGLE_MAPS_API_KEY}`;
      return a;
    };

    const getdetail = async (placeid) => {
      var a;
      await axios
        .get(
          `${corsany}https://maps.googleapis.com/maps/api/place/details/json?place_id=${placeid}&key=${GOOGLE_MAPS_API_KEY}`,
          {}
        )
        .then(function (response) {
          a = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });

      return a;
    };
    onUnmounted(async () => {
      if (clickListener) clickListener.remove();
      if (clickidListener) clickidListener.remove();
    });
    // let line = null;
    watch(
      [placeclick],
      // () => {alert(placeclick.value);}
    );
    const haversineDistance = (pos1, pos2) => {
      const R = 3958.8; // Radius of the Earth in miles
      const rlat1 = pos1.lat * (Math.PI / 180); // Convert degrees to radians
      const rlat2 = pos2.lat * (Math.PI / 180); // Convert degrees to radians
      const difflat = rlat2 - rlat1; // Radian difference (latitudes)
      const difflon = (pos2.lng - pos1.lng) * (Math.PI / 180); // Radian difference (longitudes)
      const d =
        2 *
        R *
        Math.asin(
          Math.sqrt(
            Math.sin(difflat / 2) * Math.sin(difflat / 2) +
              Math.cos(rlat1) *
                Math.cos(rlat2) *
                Math.sin(difflon / 2) *
                Math.sin(difflon / 2)
          )
        );
      return d;
    };
    const distance = computed(() =>
      otherPos.value === null
        ? 0
        : haversineDistance(currPos.value, otherPos.value)
    );
const aaa = computed(() =>
      placeclick.value == null
        ? 0
        : placeclick.value
    );
    // var wholetravel = computed(store.state.travelplant )

    return { currPos, otherPos, distance, mapDiv, placecard, placeclick,aaa };
  },
  data() {
    return {
      a: "",
      b: "",
      date: "",
      setday:false,
      setunit:false,
      dtsequence:1,
      unitday:"",
      days:{
        starttime:"",
      },
      unit:{
        movingtime:"",
        staytime:""
      },
    };
  },
  computed: {
    istaitle() {
      return this.$store.state.travelplant.wttitle == "";
    },
    travelplant() {
      return this.$store.state.travelplant;
    },
    wtid() {
      return this.$store.state.wtid;
    },
  },
  methods: {
    showaddunit(){
      this.setunit=true;
    },
    newtw() {
      if (this.a == "") {
        alert("標題不能為空");
      } else {
        this.$store.commit("wttaitle", "[" + this.a + "]" + this.b);
        this.$store.commit("wtdata", this.date.toISOString().substring(0, 10));
      }
    },
    save() {

      let token = Cookies.get("authorization");
       axios.post("http://localhost:8080/foodmap/wts", {
          "isPublic": true, 
          "wttitle": this.travelplant.wttitle,
          "deleted": false,
          "wtstartT": this.days.starttime,
          "wtdays": this.dtsequence,
          "wtintroduce": "wtone",
          "wtphoto": "https://image-resizer.cwg.tw/resize/uri/https%3A%2F%2Fstorage.googleapis.com%2Fwww-cw-com-tw%2Farticle%2F201909%2Farticle-5d8595b6de5ef.jpg/?w=1600&format=webp"
      }, {headers: {
            Authorization: `Bearer ${token}`,
          }}).then(alert("新增成功")).catch()

    },
    addday(){
      var ff={
        starttime:"",
        dttitle:"1",
        dtintroduce:"ssss",
        dtsequence:"",//自動
        daydetal:[],
      }
      ff.dtsequence=this.dtsequence;
      ff.starttime=this.days.starttime;
      this.dtsequence= this.dtsequence+1;
      console.log(ff);
      this.$store.commit("addday",ff);
      this.setday=false;
    },
    shownewday() {
      this.setday=true;
    },
    addunit() {
      var twunit={
      "movingtime": "",
      "staytime": "",
      "tutitle": "",
      "transportation": "車",
      "tusequence": "",//自動
      "atrid":""
      }
      
      let token = Cookies.get("authorization");
      // var vm= this;
      twunit.tutitle=document.getElementById("asd").innerText;
      axios.post("http://localhost:8080/foodmap/atrs", {
        "atrname": twunit.tutitle,
        "atraddress": "",
        "atrlike": 50,
        "atrintroduce": "1",
        "atropentime": "1",
        "atrtel": "886-3-3656555",
        "atrlantitude": 24.943325,
        "atrlongitude": 121.297187,
        "atrgm": null
      }, {headers: {
            Authorization: `Bearer ${token}`,
          }}).then(alert("新增成功")).catch()


      // twunit.
      // twunit.
     

      this.$store.commit("inputtwuni", [(this.unitday-1), twunit]);
      this.setunit=false;
    },
  },
};
</script>

<template>
  <!-- 這邊是輸入行程標題 -->
  <div
    v-if="istaitle"
    class="123"
    style="
      width: 300px;
      height: 50%;
      z-index: 1;
      position: fixed;
      background-color: rgb(180, 205, 205);
      top: 30%;
      left: 30%;
      
    "
    key="558798S"
  >
    <h1>建立行程</h1> 

    <div style="position: absolute; left: 18px">
      請輸入行程標題:
      <select name="" id="" v-model="a" class="mx-2 rounded border border-secondary">
        <option>台中</option>
        <option>台北</option>
        <option>高雄</option>
      </select>
      <input type="text" v-model="b" class="rounded border border-secondary" />
    </div>
    <br />

    <br />
    
    <div class="row my-1 mx-5" style="position: absolute; left: 35px">
      行程開始日期:
      <Datepicker 
        v-model="date"
        style="width: 150px; height: 20px; left: 50%"
      ></Datepicker>
    </div>
    <button class="rounded btn btn-secondary" style="position: absolute; bottom: 20%;margin-left:-20px" @click="newtw()">確認</button>
  </div>
  <!--這邊是輸入新增天數-->
  <div v-if="setday"
    style="
      width: 50%;
      height: 50%;
      z-index: 1;
      position: fixed;
       background-color: rgb(180, 205, 205);
      top: 30%;
      left: 30%;
    "
    key="55879">


    <div class="row my-5 mx-5" style="position: absolute; left: 100px;margin-left:60px">
      開始時間:
      <Datepicker class=""
        v-model="days.starttime"
        style="width: 150px; height: 20px; left: 50%"
      ></Datepicker>
      <button class="rounded btn btn-secondary" @click="addday()" style="margin-top:30px;">新增</button>
    </div>

  </div>
  <!-- 這邊輸入景點停留時間 -->
  <div v-if="setunit"
    style="
      width: 50%;
      height: 50%;
      z-index: 1;
      position: fixed;
      background-color: rgb(216, 253, 253);
      top: 30%;
      left: 30%;
    "
    key="5587">
    要加入第幾天
    <input type="text" v-model="unitday">
    <br>
    請輸入停留時間
    <input type="text" v-model="unit.staytime">
    <button  @click="addunit(1)">新增</button>
  </div>

  <div class="googlemap" style="position: fixed; width: 100%">
    <div style="width: 100%">
      <!-- <div class="d-flex text-center" style="height: 10vh">
        <mapsearch />
      </div> -->

      <div
        ref="mapDiv"
        style="width: 100%; height: 90vh; position: relative; z-index: 0"
        key="134568"
      />
    </div>
    
      <div class="lightbox" v-for="item in placeclick" :key="item.id">
        <div
          style="
            width: 100%;
            height: 90vh;
            background-color: rgb(216, 253, 253);
            z-index: 2;
            position: relative;
          "
        >
          <img :src="item.ppo" alt="" />
          <h1 id="asd">{{ item.name }}</h1>
          <h2 >{{ item.formatted_address }}</h2>
          <button style="bottom: 75px; right: 0px" @click="showaddunit()">
            加入行程
          </button>
        </div>
      </div>
    <div style="width: 20%; position: relative;background-color: #F7F2F0">
      <h5 style="background-color: #FFA101">{{travelplant.wttitle}}</h5>
      <div>
      <h6 v-for="(item,index) in travelplant.days" :key="index" style="
text-align:left;">
        day{{ index+1}}
        <li v-for="(items,indexs) in item.daydetal" :key="items.id">
          {{ indexs+1}}.{{items.tutitle}}
        </li>
      </h6>

      </div>
      
      <button class="rounded btn btn-secondary"
        @click="save()"
        style="position: absolute; bottom: 95px; right: 0px"
        key="222"
      >
        儲存
      </button>
      <button class="rounded btn btn-secondary"
        @click="shownewday()"
        style="position: absolute; bottom: 95px; right: 100px"
        key="55555"
      >
        新增天數
      </button>
    </div>
  </div>
  <div></div>
</template>
<style>
.googlemap {
  display: flex;
  background-color: #61a0f8;
  flex-direction: row;
}
</style>