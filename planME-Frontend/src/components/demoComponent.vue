<template>
<div class="demoComponent" key="wtttt"  style="width: 100%">
    <!-- <h1>This is an about page</h1> -->
<button type="button" class="mx-0 btn btn-secondary " data-bs-toggle="modal" @click="cardopen()" data-bs-target="#exampleModal">檢視行程</button>
    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"  >
        <div class="modal-dialog modal-xl" >
            <div class="modal-content">
                <div class="modal-header py-2">
                    <h5 class="modal-title mx-auto" id="exampleModalLabel">檢視行程</h5>
                    <button type="button" class="btn-close btn-sm mx-0" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body  py-0" >
                    <div class="container-fluid">                          
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
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script>
const axios = require("axios");
import Cookies from "js-cookie";
const moment = require ('moment')
export default {
name: "demoComponent",
data () {
    return {

        cardList: [],
            };
    },
methods:{
    cardopen() {
    let token = Cookies.get("authorization");
    var vm = this;
    
    axios
    .get('http://localhost:8080/foodmap/wts/50',{
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
    
    },

};


</script>
<style scoped>
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
   
    height: 100%;   
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
