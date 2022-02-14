<template>
<div class="PreviwExample">
    <!-- <h1>This is an about page</h1> -->
    <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal">範例行程</button>
    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl" >
            <div class="modal-content" id="aasaa">
                <div class="modal-header py-2">
                    <h5 class="modal-title mx-auto" id="exampleModalLabel">檢視行程</h5>
                    <button type="button" class="btn-close btn-sm mx-0" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body  py-0">
                    <div class="container-fluid">
                        <!-- <div class="row">
                            <div class="col"> -->
                            
                        <div class="row" >
                            <div v-for="(item,index) in travelCardLists" :key="index">
                                <div>
                                    <span class="badge bg-secondary my-1 "> {{item.day}}</span>
                                </div>
                                <div class="row " id="travelrow">
                                    <div  class="col-2 px-1" v-for="(items,indexs) in item.unit" :key="indexs">
                                    <figure class="figure" >
                                        <img :src="items.cardsrc" class="figure-img img-fluid rounded" alt="" id="pics">
                                        <figcaption class="figure-caption ">{{items.title}}</figcaption>
                                        <!-- savebtn -->
                                        <!-- <button class="savebtn"  @click="this.change=!this.change">
                                            <div v-if="change"><img src="../../public/scheduledemo/notsave.jpg" style="width: 16px;height:26px;"></div>
                                            <div v-else><img src="../../public/scheduledemo/save.jpg" style="width: 16px;height:26px;"></div>
                                        </button> -->
                                    </figure>
                                    </div>
                                </div>
                            </div>
                            
                                <br>
                                    <!-- <div class="w-100 pb-2"></div> -->
                            <!-- </div>
                            </div>   -->
                            
                        </div>
                        <!-- <hr width=100% class="my-2 ">          -->
                        
                    </div>
                </div>  <button @click="pdfBtn">存成PDF</button>
               
                <!-- <div class="modal-footer"></div> -->
            </div>
          
        </div> 
    </div>
</div>
</template>

<script>
import htmlToPdf from '@/utils/htmlToPdf'
export default {
    methods:{
         pdfBtn(){
                htmlToPdf.getPdf(this.htmlTitle,"aasaa");
            }
    },
    
    name: "PreviewExample",
data () {
     return {
            // change:true,

            travelCardLists: [{
                day:"day1",
                unit:[ 
            
            {   id: '001',title: '奇美博物館', cardsrc: require('../../public/scheduledemo/chimei.jpg') },
            {   id: '002',title: '太魯閣', cardsrc: require('../../public/scheduledemo/tarako.jpg') },
            {   id: '003',title: '妖怪村', cardsrc: require('../../public/scheduledemo/monster.jpg') },
            {   id: '004',title: '阿里山', cardsrc: require('../../public/scheduledemo/ali.jpg') },
            {   id: '005',title: '飛牛牧場', cardsrc: require('../../public/scheduledemo/deer.jpg') },
            {   id: '003',title: '妖怪村', cardsrc: require('../../public/scheduledemo/monster.jpg') },
            ],
            },{
                day:"day2",
                unit:[ 
            
            {   id: '001',title: '奇美博物館', cardsrc: require('../../public/scheduledemo/chimei.jpg') },
            {   id: '002',title: '太魯閣', cardsrc: require('../../public/scheduledemo/tarako.jpg') },
            {   id: '003',title: '妖怪村', cardsrc: require('../../public/scheduledemo/monster.jpg') },
            {   id: '004',title: '阿里山', cardsrc: require('../../public/scheduledemo/ali.jpg') },
            {   id: '005',title: '飛牛牧場', cardsrc: require('../../public/scheduledemo/deer.jpg') },
            {   id: '003',title: '妖怪村', cardsrc: require('../../public/scheduledemo/monster.jpg') },
            ],
            }]
            }
    },
};


</script>
<style scoped>
  .figure {
    width: 100%;
    height:200px;
    border-style: double;
    border-radius: 5px
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
    overflow-y: scroll ;   
  } 
  #travelrow {

        flex-wrap: nowrap;
        /* flex-direction: row; */
    }
/* .savebtn {
    height: 26px;  
    width: 16px;
    border: none;
    padding: 0;
    background: none;
} */
</style>
