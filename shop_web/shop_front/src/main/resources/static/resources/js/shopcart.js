$(function(){
    //调用ajax查询所有的购物车信息
    $.ajax({
        type: "POST",
        url: "http://localhost:16666/cart/list",
        success: function(data){

            if(data != null && data.length > 0){
                //有购物车
                $("#cartnumberid").html(data.length);

                //循环所有购物车列表
                var html = "";
                html += "<ul>";
                for(var i = 0; i < data.length; i++){
                    html += "<li style='width: 100%; height: 60px'>";
                    html += "<img style='width: 50px; height: 50px; margin-right: 10px' src='" + data[i].goods.fengmian + "'/>";
                    html += "<span style='margin-right: 10px'>" + data[i].goods.subject + "</span>";
                    html += "<span>" + data[i].goods.price + " * " + data[i].number + "</span>"
                    html += "</li>";
                }
                html += "</ul>";

                $("#cartid").html(html);
            } else {
                //没有购物车
                $("#cartid").html("<p>还没有商品，赶快去挑选！</p>");
            }
        },
        dataType: "json"
    });
});