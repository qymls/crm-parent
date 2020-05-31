<style scoped>
  .expand-row {
    margin-bottom: 16px;
  }
</style>
<template>
  <div>
    <Row>
      <Col span="6">
        <div class="expand-row">
          <span>基本信息</span>
        </div>
        <div class="expand-row">
          <span>客户姓名:  {{row.name}}</span>
        </div>
        <div class="expand-row">
          <span>客户年龄:  {{row.age}}</span>
        </div>
        <div class="expand-row">
          <span v-if="row.sex == 'true' || row.sex==true">性别：男</span>
          <span v-if="row.sex == 'false' || row.sex ==false">性别：女</span>
        </div>
      </Col>

      <Col span="6">
        <div class="expand-row">
          <span>联系方式</span>
        </div>
        <div class="expand-row">
          <span>电话号码:  {{row.tel}}</span>
        </div>
        <div class="expand-row">
          <span>邮箱:  {{row.email}}</span>
        </div>
        <div class="expand-row">
          <span>QQ:  {{row.qq}}</span>
        </div>
        <div class="expand-row">
          <span>微信:  {{row.wechat}}</span>
        </div>
      </Col>

      <Col span="6">
        <div class="expand-row">
          <span>扩展信息</span>
        </div>
        <div class="expand-row">
          <span v-if="row.seller">营销人员:  {{row.seller.username}}</span>
        </div>

        <div class="expand-row">
          <span v-if="row.job">职业:  {{row.job.name}}</span>
        </div>
        <div class="expand-row">
          <span>收入水平：{{row.salarylevel}}元</span>
        </div>
        <div class="expand-row">
          <span>客户来源：{{row.customersource}}</span>
        </div>
      </Col>

      <Col span="6">
        <div class="expand-row">
          <span>其他信息</span>
        </div>
        <div class="expand-row">
          <span v-if="row.tenant">所属租户:  {{row.tenant.companyName}}</span>
        </div>
        <div class="expand-row">
          <span>顾客状态：{{row.status}}</span>
        </div>
        <div class="expand-row">
          <span>备注：{{row.remark}}</span>
        </div>
        <div class="expand-row">
          <span>成功率：{{row.successrate}}%</span>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
  export default {
    props: {
      row: Object
    }
  };
</script>
