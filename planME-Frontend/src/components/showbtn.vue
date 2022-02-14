<template>
<div class="showbtn">
       <button id="aaaaaaaaa" type="button" class="btn btn-primary btn-sm " data-bs-toggle="modal" data-bs-target="#articleModal">檢視</button>
    <div class="modal fade" id="articleModal" tabindex="-1" aria-labelledby="articleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl" >
            <div class="modal-content">
                <div class="modal-header py-2  ">
                    <h5 class="modal-title mx-auto" id="exampleModalLabel">被檢舉內容</h5>
                    <button type="button" class="btn-close btn-sm mx-0" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body  py-0">
                    <div class="container-fluid">
                        <div class="row"  >
                            <div class="col " style="background-color: #E8F7F3;" >
                                <h5 class=" " >尋訪被檢舉文章title</h5>
                                {{counter}}
                                <p>尋訪被檢舉文章內容</p>
                                {{postitle}}
                                <a href="#" class="card-link">Card link</a>
                                <a href="#" class="card-link">Another link</a>

                                <div class="btn-group d-flex justify-content-end py-2">
                                    <button type="button" class="btn-primary">保留</button>
                                    <button type="submit" class="btn-danger">刪除</button>
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
export default ({
    name:"showbtn",
    props:["parentItem","postItle"],
    data() {
        return{
            counter: this.parentItem,
           postitle: this.postItle,
      list: [],
      page:0,
      listss: [],
      pages:0,
        }
    }, 
    beforeCreate() {
        // this.$emit('update')
    },
    methods: {
     
    article() {
      let token = Cookies.get("authorization");
      // var q=[];
 
      var vm = this;
      axios
      
        .get(`http://localhost:8080/foodmap/articles/reported?page=${this.page}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
       
          // document.getElementById('first').innerText=response;
          for (let i = 0; i < response.data.length; i++) {
            // q.push(response.data[i]);
             
            vm.list.push(response.data[i]);
          }
        })
        .catch(function (error) {
          //請求失敗處理
          console.log(error);
        });
      // for(let i=0;i<q.length;i++){
      //     vm.list.push(q[i]);
      //   }
     this.page++; 
     console.log(this.page);
      console.log(vm.list);
      console.log(this);
    },
    comments() {
      let token = Cookies.get("authorization");
      // var q=[];
 
      var vm = this;
      axios
      
        .get(`http://localhost:8080/foodmap/messages/reported?page=${this.pages}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then(function (response) {
       
          // document.getElementById('first').innerText=response;
          for (let i = 0; i < response.data.length; i++) {
            // q.push(response.data[i]);
             
            vm.listss.push(response.data[i]);
          }
        })
        .catch(function (error) {
          //請求失敗處理
          console.log(error);
        });
      // for(let i=0;i<q.length;i++){
      //     vm.list.push(q[i]);
      //   }
     this.pages++; 
     console.log(this.page);
      console.log(vm.listss);
      console.log(this);
    },
   

  }, 
    })
    

</script>
<style>

</style>
