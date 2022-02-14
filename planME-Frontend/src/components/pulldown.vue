<template>
  <div
    v-infinite-scroll="loadMore"
    infinite-scroll-disabled="busy"
    infinite-scroll-distance="10"
  >
    <div v-for="item in data" :key="item.index">{{ item.name }}</div>
  </div>
</template>

<script>
// import InfiniteLoading from "vue-infinite-loading";
// import axios from "axios";

export default {
  data() {
    return {
      count: 0,
      data: [],
      busy: false,
    };
  },
 methods: {
    loadMore: function() {
      this.busy = true
      setTimeout(() => {
        for (var i = 0, j = 10; i < j; i++) {
          this.data.push({name: this.count++ })
        }
        console.log(this.data)
        this.busy = false
      }, 1000)
    },

    //改变条件条用此方法
    changeFilter() {
      this.list = [];
      this.$nextTick(() => {
        this.$refs.infiniteLoading.$emit("$InfiniteLoading:reset");
      });
    },
  },
};
</script>
-----------------------------------
©著作权归作者所有：来自51CTO博客作者JackieDYH的原创作品，请联系作者获取转载授权，否则将追究法律责任
Vue向下滚动加载更多数据-scroll-案例
https://blog.51cto.com/jackiehao/3777390