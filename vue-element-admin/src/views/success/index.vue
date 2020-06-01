<template>
</template>

<script>
  import {setToken} from '../../utils/auth'
  import Cookies from 'js-cookie'
  export default {
    created() {
      let getRequest = this.getRequest();
      console.log('----->', window.location.href)
      console.log('----->', getRequest)
      let user = JSON.parse(getRequest.user.replace(/%3A/g, ':'))
      let sessionId = getRequest.sessionId;
      sessionStorage.clear()
      sessionStorage.setItem('sessionId', sessionId);
      Cookies.set("JSESSIONID",sessionId)/*必须这样设置，否则admin-token和JSESSIONID会不一致，无法获取权限的*/
      setToken(sessionId);

      //this.$router.push({path:'/dashboard'})
      this.$router.push("/dashboard")
    },
    mounted() {
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
      },
    }
  }
</script>
