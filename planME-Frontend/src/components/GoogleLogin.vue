<template>
  
    <!-- <h1>IsInit: {{ Vue3GoogleOauth.isInit }}</h1>
  <h1>IsAuthorized: {{ Vue3GoogleOauth.isAuthorized }}</h1>
  <h2 v-if="user">signed user: {{user}}</h2> -->
    <button @click="handleClickSignIn" v-show="!Vue3GoogleOauth.isAuthorized">
      <img src="/icon/googleSingInIcon.png" style="width:40%;hight:65%;">
    </button>
    <!-- <button @click="handleClickGetAuthCode" v-show="!Vue3GoogleOauth.isAuthorized">get authCode</button> -->
    <button @click="handleClickSignOut" v-show="Vue3GoogleOauth.isAuthorized" >
      google sign out
    </button>
    <!-- <button @click="handleClickDisconnect" v-show="Vue3GoogleOauth.isAuthorized">disconnect</button> -->
  
</template>

<script>
import { inject, toRefs } from "vue";
const axios = require("axios");
import Cookies from "js-cookie";

export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
  data() {
    return {
      user: "",
    };
  },
  methods: {
    async handleClickSignIn() {
      try {
        const googleUser = await this.$gAuth.signIn();
        if (!googleUser) {
          return null;
        }
        // console.log("googleUser", googleUser);
        this.user = googleUser.getBasicProfile().getEmail();
        // console.log("getId", this.user);
        // console.log("getBasicProfile", googleUser.getBasicProfile());
        // console.log("getAuthResponse", googleUser.getAuthResponse());
        console.log(
          "getAuthResponse",
          this.$gAuth.instance.currentUser.get().getAuthResponse().id_token
        );
       this.signUp();
        this.signInn();
      } catch (error) {
        //on fail do something
        console.error(error);
        return null;
      }
    },
    async handleClickGetAuthCode() {
      try {
        const authCode = await this.$gAuth.getAuthCode();
        console.log("authCode", authCode);
      } catch (error) {
        //on fail do something
        console.error(error);
        return null;
      }
    },
    async handleClickSignOut() {
      try {
        await this.$gAuth.signOut();
        console.log("isAuthorized", this.Vue3GoogleOauth.isAuthorized);
        Cookies.set("authorization", "", 14400 * 3);
        this.user = "";
      } catch (error) {
        console.error(error);
      }
    },
    handleClickDisconnect() {
      window.location.href = `https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=${window.location.href}`;
    },
   async signUp() {
     await axios
        .post(
          `http://localhost:8080/auth/login/googlesignup?token=${
            this.$gAuth.instance.currentUser.get().getAuthResponse().id_token
          }`,
          {}
        )
        .then(function (response) {
          console.log(response);
        
          // window.history.back();
        })
        .catch(

          
        );
    },
    signInn() {
      axios
        .post(
          `http://localhost:8080/auth/login/googlesignin?token=${
            this.$gAuth.instance.currentUser.get().getAuthResponse().id_token
          }`,
          {}
        )
        .then(function (response) {
          console.log(response);
          Cookies.set("authorization", response.data.accessToken, 14400 * 3);
          alert("登入成功");
          // window.history.back();
          
        })
        .catch(function (error) {
          // 请求失败处理
          console.log(error);
        });
        this.$router.push({name: 'Member'});
    },
  },
  setup(props) {
    const { isSignIn } = toRefs(props);
    const Vue3GoogleOauth = inject("Vue3GoogleOauth");
    const handleClickLogin = () => {};
    return {
      Vue3GoogleOauth,
      handleClickLogin,
      isSignIn,
    };
  },
};
</script>

<style scoped>
button {

  line-height: 1;
  white-space: nowrap;
  cursor: pointer;
  background: #fff;
  text-align: center;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  border:none;
  -webkit-transition: 0.1s;
  transition: 0.1s;
  font-weight: 500;
  font-size: 14px;

}
button:disabled {
  background: #fff;
  color: #ddd;
  cursor: not-allowed;
}
</style>