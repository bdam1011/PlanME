<template>
    <div class="index-group-box" >
        <!-- 右邊滑動箭頭 -->
        <div class="scrollX" @click="groupScroll" v-if="arrowShow">
            <img v-if="direction === 'left'" src='../assets/arrow-left-bold.jpg'/>
            <img v-else src='../assets/arrow-right-bold.jpg'/>
        </div>
        <!-- 卡⽚ -->
        <div class="index-group-boxIn " ref="groupBoxRef">
            <div
                v-for="item in groupInfo"
                :key="item.id"
                ref="groupCardRef"
                class="group-card"
            >
                <div class="card-name" >
                    <img :src="item.photo" class="card-img-top img-fluid" style="width:150px;">
                    <h6 class="card-title  my-2">{{ item.name }}</h6>
                    <span></span>
                    <button class="btn btn-secondary">收藏</button>
                    
                    
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { defineComponent, ref, reactive, onMounted, watchEffect } from 'vue';
// import PreviewExample from "@/components/PreviewExample.vue"
export default defineComponent({
    name: 'scroll',
    components:{
        // PreviewExample,
    },
    setup() {
        const groupInfo = ref([]); // 卡片list
        const direction = ref('right'); // 默認箭頭向右
        const arrowShow = ref(false); // 滾動箭頭是否顯示
        const groupBoxRef = ref(null); // 獲取外層卡⽚ref
        const groupCardRef = ref(null); // 獲取卡⽚ref
        const scrollPosition = reactive({
            left: 0,
            top: 0
        }); // 滾動比特置

  
        // 獲取卡片列錶
        const getMyGroup = async () => {
            const data = [{
                
                photo:"../../icon/合歡山.jpg",
                id: 1,
                name:'合歡山四岳三日'

            },{
                photo:"../../icon/牛奶湖.jpg",
                id: 2,
                name:'宜蘭牛奶胡包船遊'
            },{
                photo:"../../icon/蘭嶼.jpg",
                id: 3,
                name:'蘭嶼飛魚季'
            },{
                photo:"../../icon/太魯閣.jpg",
                id: 4,
                name:'花蓮太魯閣'
            },{
                photo:"../../icon/金瓜石.jpg",
                id: 5,
                name:'黃金城金瓜石'
            },{
                photo:"../../icon/SUP.jpg",
                id: 6,
                name:'墾丁SUP'
            },{
                photo:"../../icon/早午餐.jpg",
                id: 7,
                name:'THE o.z早午餐'
            },{
                photo:"../../icon/親水公園.jpg",
                id: 8,
                name:'宜蘭冬山河水道'
            },{
                photo:"../../icon/熱氣球.jpg",
                id: 9,
                name:'台東熱氣球'
            },{
                photo:"../../icon/蘭嶼.jpg",
                id: 3,
                name:'蘭嶼飛魚季'
            },{
                photo:"../../icon/太魯閣.jpg",
                id: 4,
                name:'花蓮太魯閣'
            },{
                photo:"../../icon/金瓜石.jpg",
                id: 5,
                name:'黃金城金瓜石'
            },{
                photo:"../../icon/SUP.jpg",
                id: 6,
                name:'墾丁SUP'
            }
            ]
            groupInfo.value = data;
        }
    
        // 獲取卡⽚寬度，第⼀個參數是卡⽚個數，默認是整個數組，第⼆個參數是剩餘的margin
        const getWidth = (num = groupInfo.value.length, restMargin = 16) => {
            // 如果沒有內容就返回0
            if(!groupCardRef.value) return 0;
            return num * (groupCardRef.value.offsetWidth + 16) - restMargin;
        }
        // 改變滾動⽅向
        const changeArrow = (scrollLeft) => {
            // 默認獲取scoll部分整個寬度
            const getScrollWidth = getWidth();
            // 獲取剩餘寬度
            const restWidth = getScrollWidth - scrollLeft
            if (restWidth <= groupBoxRef.value.offsetWidth) {
                direction.value = 'left'
            } else if ( scrollLeft === 0 ) {
                direction.value = 'right'
            }
        }
        // ⿏標點擊滾動
        const groupScroll = async () => {
            // 獲取滾動寬度
            const getMoveWidth = getWidth(3, 0);
            if (direction.value === 'right') {
                groupBoxRef.value.scrollLeft += getMoveWidth;
            } else {
                groupBoxRef.value.scrollLeft -= getMoveWidth;
            }
            // 滾動需要時間才能獲取最新的距離
            setTimeout(() => {
                changeArrow(groupBoxRef.value.scrollLeft);
            }, 500)
        }

        // 判斷arrow是否展示
        const checkArrowShow = () => {
            arrowShow.value = getWidth() > groupBoxRef.value?.offsetWidth ? true : false;
        }

        watchEffect(() => {
            checkArrowShow();
        })

        // 獲取scroll函數的比特置
        const handleScroll = e => {
            scrollPosition.left = e.target.scrollLeft;
            scrollPosition.top = e.target.scrollTop;
            changeArrow(scrollPosition.left);
        }

        getMyGroup();

        onMounted(() => {
            // 監聽scroll事件
            groupBoxRef.value.addEventListener('scroll', handleScroll, true);
            // 監聽窗⼝變化事件，判斷arrow的展示
            window.addEventListener('resize', checkArrowShow, true);
            // 首次檢查箭頭展示
            checkArrowShow();
        })

        return {
            // data
            groupInfo,
            direction,
            arrowShow,
            // ref
            groupBoxRef,
            groupCardRef,
            // methods
            groupScroll
        };
    },
   
}, 
);
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
    background-color: #512D6D;
    display: flex;
    justify-content: center;
    align-items: center
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
    box-sizing:border-box;
    width: 200px;
    height: 220px;
    border-radius: 4px;
    margin-right: 16px;
    flex: none;
   
    color: #54436B;
    border-style:solid;
    border-color:#285185;
    
}

.group-card span{
    color: #000000;
}

.group-card:hover{
    background: #FFFFFE;
}

.group-card:nth-last-of-type(1){
    margin-right: 0px;
}
</style>