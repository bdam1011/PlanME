<template>
<div class="PublishArticle">
    <button type="button" class="btn btn-primary btn-sm " data-bs-toggle="modal" data-bs-target="#articleModal">發表文章</button>
    <div class="modal fade" id="articleModal" tabindex="-1" aria-labelledby="articleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl" >
            <div class="modal-content">
                <div class="modal-header py-2  ">
                    <h5 class="modal-title mx-auto" id="exampleModalLabel">發表文章</h5>
                    <button type="button" class="btn-close btn-sm mx-0" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body  py-0">
                    <div class="container-fluid">
                        <div class="row" >
                            <div class="col " style="background-color: #E8F7F3;">
                                <h5 class=" ">Title</h5>
                                <p>Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                <a href="#" class="card-link">Card link</a>
                                <a href="#" class="card-link">Another link</a>

                                <div class="btn-group d-flex justify-content-end py-2">
                                    <button type="button" class="btn-primary">檢舉</button>
                                    <button type="submit" class="btn-danger">刪除</button>
                                </div>
                            </div>
                        </div>
                            
                        <div class="row">
                            <div class="col border-top border-2" style="background-color: #E8F7F3;">
                                <h5 class=" text-center mb-0 py-1">留言</h5>
                                <div class="d-flex bd-highlight">
                                    <div class="flex-shrink-0 text-center">
                                        <img class="border mx-1 my-1" src="../../public/icon/googlelogo.png" style="height: 60px;" alt="..."><br>
                                        <a href="#" class="">9527</a>
                                    </div>
                                    <div class="flex-grow-1 ms-1 ">
                                        This is some content from a media component. You can replace this with any content and adjust it as needed.
                                        <div class="btn-group d-flex justify-content-end py-2">
                                            <button type="button" class="btn-primary ">檢舉</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">			
                            <div class="col border-top border-2" style="background-color: #E8F7F3;">

                            <!-- <div class="d-flex ">
                                <div class="flex-shrink-0 text-center">
                                    <img class="border mx-1 my-1" src="img/logo.png" style="height: 60px;" alt="..."><br>
                                    <a href="#" class="">9527</a>
                                </div>
                                <div class="flex-grow-1 ms-1 ">
                                    This is some content from a media component. You can replace this with any content and adjust it as needed.
                                </div>
                            </div> -->

                            <form action="/action_page.php">
                                <h5 class=" text-center mb-0 py-1">留言區</h5>
                                <div class="form-group">
                                    <input type="text" class="form-control form-control" placeholder="留言區">
                                </div>
                                <div class="btn-group d-flex justify-content-end py-2">
                                <button type="submit" class="btn-primary">送出</button>
                                <button type="button" class="btn-danger">清空</button>
                                </div>
                            </form>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<script>
export default {
    name:"PublishArticle",
    data() {
        return{
        }


    },
};
</script>
<style>

</style>
