<template>
<img src="/icon/3.jpg" class="downImg" style="padding-top:5px">
<div class="signUp rounded-3 border border-warning bg-light  align-self-center" style="margin-left:35%;margin-right:35%;margin-top:10px" >
<div class="singup">
帳號 :
<input v-model="username" type="text" placeholder="Username" class="my-1 rounded-3 border border-success" />
<br>
信箱 :
<input v-model="Email" type="text" placeholder="E-Mail"  class="my-1 rounded-3 border border-success" />
<br>
<button class="btn btn-secondary my-1" @click="emailfind">寄送驗證信</button><input v-if="ismail" type="text" v-model="aa" placeholder="輸入4位數字"><p v-if="aaa">驗證成功</p>
<br>
密碼 :  
<input v-model="password" type="text" placeholder="Password" class="my-1 rounded-3 border border-success">
<br>
<button :disabled="!aaa" @click="submit()" class="btn btn-secondary">註冊</button>
  </div>
</div>
</template>

<script>
// @ is an alias to /src
import axios from 'axios'

export default {
  name: 'singup',
  data(){
    return{
      username:"",
      Email:"",
      password:"",
      ismail:false,
      emailpass:"1",
      aa:"",
    }
  },
  components: {
    
  },
  computed:{
    aaa(){
      return this.emailpass ==this.aa;
    },
  },
  methods:{
    emailfind(){
      var vm=this;
      this.ismail=true;
      axios.post("http://localhost:8082/http://localhost:5003/mail",{mail:`${this.Email}`}).then(function (response) {
          console.log(response);
          vm.emailpass=response.data.password;
        }

      ).catch()
      
    },
    submit(){
      var vm=this;
      axios.post("http://localhost:8080/auth/login/signup",{
        username:`${this.username}`,
        email:`${this.Email}`,
        password:`${this.password}`
        }).then(function (response) {
          console.log(response);
          vm.$router.push({ name: 'About' });
          
        }

      ).catch()

    }


  }
}
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
