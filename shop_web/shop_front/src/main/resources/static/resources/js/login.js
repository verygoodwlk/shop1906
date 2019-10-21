//ajax -> sso
$.ajax({
    type: "POST",
    url: "http://localhost:16666/sso/isLogin",
    success: function(data){
        if(data.code == "0000"){
            //已经登录
            $("#pid").html(data.data.nickname + "您好，欢迎来到<b>ShopCZ商城</b>  <a href='/sso/logout'>注销</a>");
        } else {
            //未登录
            $("#pid").html(
                "[<a onclick=\"mylogin();\">登录</a>]" +
                "[<a href=\"http://localhost:16666/sso/toRegister\">注册</a>]");
        }
    },
    dataType: "json"
});

function mylogin(){

    //获得当前页面的请求路径
    var returnUrl = location.href;

    //对URL进行编码
    returnUrl = encodeURIComponent(returnUrl);

    //跳转到登录页
    location.href = "http://localhost:16666/sso/toLogin?returnUrl=" + returnUrl;
}