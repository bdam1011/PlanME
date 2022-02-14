<template>
  <div class="newtravel" key="newtravel">
    <div class="leftlist">

    </div>
    <div class="rightmap" key="newtravelrightmap">
      <googlemap v-if="fal" />
    </div>
  </div>
</template>
<script>
// const axios = require('axios');
// import Cookies from 'js-cookie';
import googlemap from "@/components/googlemap.vue";

export default {
  data() {
    return {
      fal: true,
    };
  },
  setup() {},
  methods: {},
  components: {
    googlemap,
  },
};
</script>
<style >
.rightmap{
width: 100%;
}
</style>
