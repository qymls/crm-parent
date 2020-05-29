<style scoped>
  .expand-row {
    margin-bottom: 16px;
  }
</style>
<template>
  <div>
    <div class="expand-row">
      <span>操作人: </span>
      <span>{{ row.opuser }}</span>
    </div>

    <div class="expand-row">
      <span>操作IP地址: </span>
      <span>{{ row.opip }}</span>
    </div>

    <div class="expand-row">
      <span>请求路径: </span>
      <span>{{ row.requesturi }}</span>
    </div>

    <div class="expand-row">
      <span>执行方法: </span>
      <span>{{ row.method }}</span>
    </div>

    <div class="expand-row">
      <span>请求参数: </span>
      <span>{{ row.params }}</span>
    </div>

    <div class="expand-row">
      <span>请求时间: </span>
      <span>{{ row.optime }}</span>
    </div>

    <div class="expand-row">
      <span>请求结果: </span>
      <span>{{ row.result }}</span>
    </div>




  </div>
</template>
<script>
  export default {
    props: {
      row: Object
    }
  };
</script>
