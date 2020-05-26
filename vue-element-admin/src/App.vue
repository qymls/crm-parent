<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>
<style>
  /*美化滚动条*/
  ::-webkit-scrollbar {
    width: 7px;
  }
/*  ::-webkit-scrollbar-track {
    background-color: #f5f5f5;
    -webkit-box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
  }*/
  ::-webkit-scrollbar-thumb {
    background-color: rgba(0, 0, 0, 0.2);
    border-radius: 5px;
  }
  ::-webkit-scrollbar-button {
    background-color: #eee;
    display: none;
  }
  ::-webkit-scrollbar-corner {
    background-color: black;
  }

  .ivu-table-border td, .ivu-table-border th {
    border-right: 1px solid #e8eaec;
  }
  #app .hideSidebar .el-submenu>.el-submenu__title {
    text-align: center;
  }
</style>
