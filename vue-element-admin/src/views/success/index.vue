<template>
</template>

<script>
import { setToken } from '../../utils/auth'

export default {
  created() {
    let getRequest = this.getRequest();
    console.log('----->',window.location.href)
    console.log('----->',getRequest)
    let user = JSON.parse(getRequest.user.replace(/%3A/g,':'))
    let sessionID = getRequest
    console.log(sessionID)
    console.log(user)
    setToken(user)
     //window.location.href = "/dashboard"
    this.$router.push("/dashboard")
  },
  mounted(){
  },
  methods: {
    getRequest(urlStr) {
      let url
      if (typeof urlStr == "undefined") {
        url = decodeURI(location.href) //获取url中"?"符后的字符串
      } else {
        url = "?" + urlStr.split("?")[1]
      }
      const theRequest = {}
      let strs
      if (url.indexOf("?") !== -1) {
        const str = url.substr(0)
        strs = str.split("&");
        for (let i = 0; i < strs.length; i++) {
          theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
        }
      }
      return theRequest;
    }
  }
}
</script>
