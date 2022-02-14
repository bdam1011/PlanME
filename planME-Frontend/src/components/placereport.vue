<template>
  <div class="index-group-box">
    <!-- 右邊滑動箭頭 -->
    <div class="scrollX" @click="groupScroll" v-if="arrowShow">
      <img v-if="direction === 'left'" src="../assets/arrow-left-bold.jpg" />
      <img v-else src="../assets/arrow-right-bold.jpg" />
    </div>
    <!-- 卡⽚ -->
    <div class="index-group-boxIn" ref="groupBoxRef">
      <div
        v-for="item in groupInfo"
        :key="item.id"
        ref="groupCardRef"
        class="group-card"
      >
        <div class="card-name">
          <img
            src="https://cdn.pixabay.com/photo/2020/06/26/17/16/daisies-5343423_1280.jpg"
            class="card-img-top img-fluid"
            style="width: 100px"
            alt="..."
          />
          <h5 class="card-title">{{ item.name }}</h5>
          <span></span>
          <div>
            <button class="btn btn-info">詳細資訊</button>
            <button type="button">
              <img
                src="../assets/unlike.png"
                style="width: 1rem; height: 1rem"
              />
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { defineComponent, ref, reactive, onMounted, watchEffect } from "vue";
export default defineComponent({
  name: "placereport",
  setup() {
    const groupInfo = ref([]); // 卡片list
    const direction = ref("right"); // 默認箭頭向右
    const arrowShow = ref(false); // 滾動箭頭是否顯示
    const groupBoxRef = ref(null); // 獲取外層卡⽚ref
    const groupCardRef = ref(null); // 獲取卡⽚ref
    const scrollPosition = reactive({
      left: 0,
      top: 0,
    }); // 滾動比特置

    // 獲取卡片列錶
    const getMyGroup = async () => {
      const data = [
        {
          id: 1,
          name: "秋紅股賞楓",
        },
        {
          id: 2,
          name: "我家巷口吃透透",
        },
        {
          id: 3,
          name: "冬天進補之旅",
        },
        {
          id: 4,
          name: "清境農場羊肉爐",
        },
        {
          id: 5,
          name: "卡片5",
        },
        {
          id: 5,
          name: "卡片5",
        },
        {
          id: 5,
          name: "卡片5",
        },
        {
          id: 5,
          name: "卡片5",
        },
        {
          id: 5,
          name: "卡片5",
        },
      ];
      groupInfo.value = data;
    };

    // 獲取卡⽚寬度，第⼀個參數是卡⽚個數，默認是整個數組，第⼆個參數是剩餘的margin
    const getWidth = (num = groupInfo.value.length, restMargin = 16) => {
      // 如果沒有內容就返回0
      if (!groupCardRef.value) return 0;
      return num * (groupCardRef.value.offsetWidth + 16) - restMargin;
    };
    // 改變滾動⽅向
    const changeArrow = (scrollLeft) => {
      // 默認獲取scoll部分整個寬度
      const getScrollWidth = getWidth();
      // 獲取剩餘寬度
      const restWidth = getScrollWidth - scrollLeft;
      if (restWidth <= groupBoxRef.value.offsetWidth) {
        direction.value = "left";
      } else if (scrollLeft === 0) {
        direction.value = "right";
      }
    };
    // ⿏標點擊滾動
    const groupScroll = async () => {
      // 獲取滾動寬度
      const getMoveWidth = getWidth(3, 0);
      if (direction.value === "right") {
        groupBoxRef.value.scrollLeft += getMoveWidth;
      } else {
        groupBoxRef.value.scrollLeft -= getMoveWidth;
      }
      // 滾動需要時間才能獲取最新的距離
      setTimeout(() => {
        changeArrow(groupBoxRef.value.scrollLeft);
      }, 500);
    };

    // 判斷arrow是否展示
    const checkArrowShow = () => {
      arrowShow.value =
        getWidth() > groupBoxRef.value?.offsetWidth ? true : false;
    };

    watchEffect(() => {
      checkArrowShow();
    });

    // 獲取scroll函數的比特置
    const handleScroll = (e) => {
      scrollPosition.left = e.target.scrollLeft;
      scrollPosition.top = e.target.scrollTop;
      changeArrow(scrollPosition.left);
    };

    getMyGroup();

    onMounted(() => {
      // 監聽scroll事件
      groupBoxRef.value.addEventListener("scroll", handleScroll, true);
      // 監聽窗⼝變化事件，判斷arrow的展示
      window.addEventListener("resize", checkArrowShow, true);
      // 首次檢查箭頭展示
      checkArrowShow();
    });

    return {
      // data
      groupInfo,
      direction,
      arrowShow,
      // ref
      groupBoxRef,
      groupCardRef,
      // methods
      groupScroll,
    };
  },
});
</script>
<style scoped>
.index-group-box {
  position: relative;
  box-sizing: content-box;
  width: 100%;
}

.scrollX {
  width: 16px;
  position: absolute;
  top: 0;
  right: 0;
  height: 100%;
  background-color: #512d6d;
  display: flex;
  justify-content: center;
  align-items: center;
}

.scrollX:hover {
  cursor: pointer;
  background-color: #65447d;
}

.index-group-boxIn {
  display: flex;
  scroll-behavior: smooth;
  white-space: nowrap;
  overflow-x: auto;
  flex: none;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE 10+ */
}

.index-group-boxIn::-webkit-scrollbar {
  display: none; /* Chrome Safari */
}

.group-card {
  padding: 8px 16px;
  box-sizing: border-box;
  width: 200px;
  height: 150px;
  border-radius: 4px;
  margin-right: 16px;
  flex: none;
  background: #30df76;
  color: #54436b;
  border-style: solid;
  border-width: 1px;
}

.group-card span {
  color: #000000;
}

.group-card:hover {
  background: #acffad;
}

.group-card:nth-last-of-type(1) {
  margin-right: 0px;
}
</style>