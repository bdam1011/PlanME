<template>
  <div class="home" >
    <HelloWorld msg="Welcome to Your Vue.js App" />
    <Homeserch />
      
     

  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import Homeserch from "@/components/Homeserch.vue";




export default {
  name: "Home",
  components: {
    HelloWorld,
    Homeserch,

   

  },
};
</script>
