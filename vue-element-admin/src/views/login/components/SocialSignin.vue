<template>
  <div class="social-signup-container">
    <div class="sign-btn" @click="wechatHandleClick('wechat')">
      <span class="wx-svg-container"><svg-icon icon-class="wechat" class="icon" /></span>
      WeChat
    </div>
    <div class="sign-btn" > <!--@click="tencentHandleClick('tencent')"-->
      <span class="qq-svg-container"><svg-icon icon-class="qq" class="icon" /></span>
      QQ
    </div>
  </div>
</template>

<script>
// import openWindow from '@/utils/open-window'

export default {
  name: 'SocialSignin',
  methods: {
    wechatHandleClick(thirdpart) {
      // this.$store.commit('SET_AUTH_TYPE', thirdpart)
      /*const appid = 'wxd853562a0548a7d0'
      const redirect_uri = 'http://bugtracker.itsource.cn/wechat/QRCodeCallBack'
      const url = 'https://open.weixin.qq.com/connect/qrconnect?appid=' + appid +
        '&redirect_uri=' + redirect_uri + '&response_type=code&scope=snsapi_login#wechat_redirect'
      window.open(url, thirdpart, 340, 340)*/

      this.$http.get('/wechat/QRCode').then((res) => {
        location.href = res.data.data
      })

    }
  }
}
</script>

<style lang="scss" scoped>
  .social-signup-container {
    margin: 20px 0;
    .sign-btn {
      display: inline-block;
      cursor: pointer;
    }
    .icon {
      color: #fff;
      font-size: 24px;
      margin-top: 8px;
    }
    .wx-svg-container,
    .qq-svg-container {
      display: inline-block;
      width: 40px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      padding-top: 1px;
      border-radius: 4px;
      margin-bottom: 20px;
      margin-right: 5px;
    }
    .wx-svg-container {
      background-color: #24da70;
    }
    .qq-svg-container {
      background-color: #6BA2D6;
      margin-left: 50px;
    }
  }
</style>
