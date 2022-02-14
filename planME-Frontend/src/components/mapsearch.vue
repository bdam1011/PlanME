<template>
  <div class="fff" style="background-color: #285185; width: 100%" key="q">
    <div class="col">
      <select
        class="form-select mb-3"
        aria-label=".form-select-lg example"
        v-model="tag"
      >
        <option value="">選擇標籤</option>
        <option value="1">One</option>
        <option value="2">Two</option>
        <option value="3">Three</option>
      </select>
    </div>
    <div class="col">
      <select class="form-select mb-3" v-model="stars">
        <option value="">星數</option>
        <option value="1">One</option>
        <option value="2">Two</option>
        <option value="3">Three</option>
      </select>
    </div>
    <div class="col">
      <div class="input-group mb-3 justify-content-center" id="searchbtn">
        <input
          type="text"
          class="form-control"
          placeholder="行程標題搜尋"
          aria-label=""
          aria-describedby="button-addon2"
          v-model.lazy="count"
        />
        <button
          class="btn btn-outline-light"
          type="button"
          id="button-addon2"
          style="background-color: #285185"
        >
          搜尋
        </button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  setup() {},
};
</script>
<style>
</style>