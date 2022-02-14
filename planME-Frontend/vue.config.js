module.exports = {
    
    //配置跨域請求
    devServer: {
     open: true, //是否自動打開瀏覽器
     https: false, //是否開啟https
     hotOnly: false,
     proxy: { // 配置跨域
      '/auth/login/signin': {
       target: 'http://localhost:8080', //請求接口域名 
       ws: true,
       secure: false,
       changOrigin: true, //是否允許跨越
       pathRewrite: {
        '^/auth/login/signin': '',
       },
       "/foodmap":{
        target: 'http://localhost:8080', //請求接口域名 
        ws: true,
        secure: false,
        changOrigin: true, //是否允許跨越
        pathRewrite: {
            "^/foodmap": '',
        }

       },
      }
     },
     before: app => { }
    }
   }