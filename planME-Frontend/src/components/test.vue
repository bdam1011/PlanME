<template>
  <div class="newtravel">
    <h1 id="test">This is an about page</h1>
  </div>
  <button @click="sended"></button>
</template>
<script>
const axios = require('axios');
import Cookies from 'js-cookie';

export default {
  setup() {},
  methods: {
    sended() {
     let token =Cookies.get("authorization")
      axios
        .get(
          "http://localhost:8080/foodmap/articles/reported",
          {
            headers: {
              "Authorization":`Bearer ${token}`
            },
          }
        )
        .then(function (response) {
          document.getElementById('test').innerText=response;
          console.log(response);
        })
        .catch(function (error) {
          // 请求失败处理
          console.log(error);
        });
    },
  },
};
</script>
