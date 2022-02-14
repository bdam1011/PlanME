<template>
<img src="/icon/loginPic2.jpg" class="downImg" style="margin-top:-8px">
<div class="rounded-3 border border-warning bg-light  align-self-center" style="margin-left:35%;margin-right:35%;margin-top:13px" >

  <div class="">
    帳號:<input class="my-1 rounded-3 border border-success" v-model="loginForm.username" type="text" placeholder="Username" />
    <br />
    密碼:<input class="rounded-3 border border-success"
      v-model="loginForm.password"
      type="password"
      placeholder="Password"
    />
    <br />
    <button type="primary " class="mx-2 my-1 btn btn-secondary " @click="handleLogin">登入</button>
    <button class="btn btn-secondary" @click="gosingup()">註冊</button>
    <br />
    <GoogleLogin />
    <!-- <button @click="linelog()"> -->
      <img src="/icon/lineLogin.png" style="width:35%;margin-top:5px">
      <!-- </button> -->
    
  </div>
  </div>
</template>
<script>
import axios from 'axios'
import Cookies from "js-cookie";
import GoogleLogin from "@/components/GoogleLogin.vue";
// import { useRouter } from "vue-router";
// const router = useRouter();
export default {
  name: "login",
  

  data() {
    return {
      loginForm: {
        username: "",
        password: "",
      },
    };
  },
  components: {
    GoogleLogin,
  },
  methods: {
    linelog(){
      axios.get("https://access.line.me/oauth2/v2.1/authorize?response_type=code&client_id=1656821177&redirect_uri=http://localhost:8080/auth/login/linesign&state=123&scope=profile%20openid%20email"
      ).then(
        function(res){
          console.log(res);
        }
      ).catch();
    },
    async handleLogin() {
      const vm = this;
      var states;
      let username = this.loginForm.username;
      let password = this.loginForm.password;
      if (username !== "" && password !== "") {
        await axios
          .post("http://localhost:8080/auth/login/signin", {usernameOrEmail: username,password: password})
          .then(function (response) {
            Cookies.set("authorization", response.data.accessToken, 14400 * 3);
            Cookies.set("name", username, 14400 * 3);
            Cookies.set("password", password, 14400 * 3);
            Cookies.set("islogin", "yes",  "60s");
            console.log(response);
            
            vm.$toast("登入成功", {
              duration: 1000,
              type: "success",
              positionX: "center",
              positionY: "top",
            });
            console.log(response);
            states = true;
            // window.history.back();
            console.log("states=" + states);
vm.$store.commit("setloginname",username);
          })
          .catch(function (error) {
            states = false;
            // 请求失败处理
            console.log("states=" + states);
            console.log(error);
             vm.$toast("登入失敗", {
              duration: 1000,
              type: "success",
              positionX: "center",
              positionY: "top",
            });
          });
        if (states) {
          this.$store.commit("inputislogin",true);
          this.$router.push({ name: "Member" });
        }
      } else {
         vm.$toast("帳號密碼不得為空", {
              duration: 1000,
              type: "success",
              positionX: "center",
              positionY: "top",
            });
      }

      // Cookies.set('login', JSON.stringify(this.loginForm), { expires: 1 })
      // console.log(this.loginForm)
      // if (Cookies.get("authorization")) {
      //   alert(this.loginForm);
      // }
    },
    removeCookie() {
      Cookies.remove("login");
    },
    gosingup(){
       this.$router.push({ name: "singup" });
        
    }
  },
};

</script>

<style>
.downImg{
      width: 100%;
      height: 100%;
      z-index: -1;
      position: absolute;
      left: 0px;
      background-position: 10%;
}


</style>