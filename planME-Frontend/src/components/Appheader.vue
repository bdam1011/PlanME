<template>
  <nav class="navbar navbar-light bg-light" style="padding:0px;" key="appheader" >
    <div class="container-fluid" style="background-color: #285185;" key="appheaderr">
        <router-link   to="/" style="color:white;">
        <img src="../../public/homeimg/LOGO.jpg" alt="" width="55" height="55"><b>首頁</b></router-link> 
        <router-link to="/newtravel"><a class="nav-link active fw-bold text-light" aria-current="page" href="">建立旅遊計畫</a></router-link>
        <router-link to="/member"><a class="nav-link  fw-bold text-light" href="" >個人頁面</a></router-link>
        <router-link to="/login"> <a class="nav-link  fw-bold text-light" href="" v-if="!islogin" >登入/註冊</a></router-link>
        <router-link to="/"><a href="" class="nav-link  fw-bold text-light" @click="logout" v-if="islogin">登出</a> </router-link>
        <router-link to="/back"> <a class="nav-link  fw-bold text-light" href="" v-if="loginname">後台</a></router-link>
    </div>
  </nav>
</template>

<script>
import Cookies from "js-cookie";
export default {
  computed:{
    loginname(){
       return (this.$store.state.loginname=="one");
    },
    islogin(){
      return this.$store.state.islogin;
    },
  },
  methods:{
    logout(){
      Cookies.set("authorization", "", 14400 * 3);
      Cookies.set("name", "", 14400 * 3);
      this.$store.commit("inputislogin",false);

    }
  }
 
};
</script>
<style>
.nav-link{
  text-decoration:none}
</style>