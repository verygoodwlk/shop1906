<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <base href="/resources/"/>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>商品秒杀页面</title>
    <link rel="stylesheet" href="css/base.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_common.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_header.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_list.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_goods.css" type="text/css" />
    <script type="text/javascript" src="js/jquery.js" ></script>
    <script type="text/javascript" src="js/topNav.js" ></script>
    <script type="text/javascript" src="js/shop_goods.js" ></script>

    <script type="text/javascript" src="js/login.js" ></script>

    <!-- 弹出框插件的依赖 -->
    <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
    <script type="text/javascript"
            src="widget/dialog/jquery-ui-1.9.2.custom.min.js"></script>
</head>
<body>
<!-- Header  -wll-2013/03/24 -->
<div class="shop_hd">
    <!-- Header TopNav -->
    <div class="shop_hd_topNav">
        <div class="shop_hd_topNav_all">
            <!-- Header TopNav Left -->
            <div class="shop_hd_topNav_all_left">
                <p id="pid"></p>
            </div>
            <!-- Header TopNav Left End -->

            <!-- Header TopNav Right -->
            <div class="shop_hd_topNav_all_right">
                <ul class="topNav_quick_menu">

                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">我的商城<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;" >
                                <ul>
                                    <li><a title="已买到的商品" target="_top" href="#">已买到的商品</a></li>
                                    <li><a title="个人主页" target="_top" href="#">个人主页</a></li>
                                    <li><a title="我的好友" target="_top" href="#">我的好友</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">卖家中心<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;">
                                <ul>
                                    <li><a title="已售出的商品" target="_top" href="#">已售出的商品</a></li>
                                    <li><a title="销售中的商品" target="_top" href="#">销售中的商品</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">购物车<b>0</b>种商品<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;">
                                <!--
                                <ul>
                                  <li><a title="已售出的商品" target="_top" href="#">已售出的商品</a></li>
                                  <li><a title="销售中的商品" target="_top" href="#">销售中的商品</a></li>
                                </ul>
                                -->
                                <p>还没有商品，赶快去挑选！</p>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">我的收藏<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;">
                                <ul>
                                    <li><a title="收藏的商品" target="_top" href="#">收藏的商品</a></li>
                                    <li><a title="收藏的店铺" target="_top" href="#">收藏的店铺</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="topNav_menu">
                            <a href="#">站内消息</a>
                        </div>
                    </li>

                </ul>
            </div>
            <!-- Header TopNav Right End -->
        </div>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
    <!-- Header TopNav End -->

    <!-- TopHeader Center -->
    <div class="shop_hd_header">
        <div class="shop_hd_header_logo"><h1 class="logo"><a href="/"><img src="images/logo.png" alt="ShopCZ" /></a><span>ShopCZ</span></h1></div>
        <div class="shop_hd_header_search">
            <ul class="shop_hd_header_search_tab">
                <li id="search" class="current">商品</li>
                <li id="shop_search">店铺</li>
            </ul>
            <div class="clear"></div>
            <div class="search_form">
                <form method="post" action="index.php">
                    <div class="search_formstyle">
                        <input type="text" class="search_form_text" name="search_content" value="搜索其实很简单！" />
                        <input type="submit" class="search_form_sub" name="secrch_submit" value="" title="搜索" />
                    </div>
                </form>
            </div>
            <div class="clear"></div>
            <div class="search_tag">
                <a href="">李宁</a>
                <a href="">耐克</a>
                <a href="">Kappa</a>
                <a href="">双肩包</a>
                <a href="">手提包</a>
            </div>

        </div>
    </div>
    <div class="clear"></div>
    <!-- TopHeader Center End -->

    <!-- Header Menu -->
    <div class="shop_hd_menu">
        <!-- 所有商品菜单 -->

        <div id="shop_hd_menu_all_category" class="shop_hd_menu_all_category"><!-- 首页去掉 id="shop_hd_menu_all_category" 加上clsss shop_hd_menu_hover -->
            <div class="shop_hd_menu_all_category_title"><h2 title="所有商品分类"><a href="javascript:void(0);">所有商品分类</a></h2><i></i></div>
            <div id="shop_hd_menu_all_category_hd" class="shop_hd_menu_all_category_hd">
                <ul class="shop_hd_menu_all_category_hd_menu clearfix">
                    <!-- 单个菜单项 -->
                    <li id="cat_1" class="">
                        <h3><a href="" title="男女服装">男女服装</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="女装" href="">女装</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="男装" href="">男装</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <!-- 单个菜单项 End -->
                    <li id="cat_2" class="">
                        <h3><a href="" title="鞋包配饰">鞋包配饰</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="鞋子" href="">鞋子</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="包包" href="">包包</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_3" class="">
                        <h3><a href="" title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" href="">美容</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" href="">美妆</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_4" class="">
                        <h3><a href="" title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" href="">美容</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" href="">美妆</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_5" class="">
                        <h3><a href="" title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" href="">美容</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" href="">美妆</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_6" class="">
                        <h3><a href="" title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" href="">美容</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" href="">美妆</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <li id="cat_7" class="">
                        <h3><a href="" title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" href="">美容</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" href="">美妆</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <li id="cat_8" class="">
                        <h3><a href="" title="美容美妆">美容美妆</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" href="">美容</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" href="">美妆</a></dt>
                                <dd>
                                    <a href="">风衣</a>
                                    <a href="">长袖连衣裙</a>
                                    <a href="">毛呢连衣裙</a>
                                    <a href="">半身裙</a>
                                    <a href="">小脚裤</a>
                                    <a href="">加绒打底裤</a>
                                    <a href="">牛仔裤</a>
                                    <a href="">打底衫</a>
                                    <a href="">情侣装</a>
                                    <a href="">棉衣</a>
                                    <a href="">毛呢大衣</a>
                                    <a href="">毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <li class="more"><a href="">查看更多分类</a></li>
                </ul>
            </div>
        </div>
        <!-- 所有商品菜单 END -->

        <!-- 普通导航菜单 -->
        <ul class="shop_hd_menu_nav">
            <li class="current_link"><a href=""><span>首页</span></a></li>
            <li class="link"><a href=""><span>团购</span></a></li>
            <li class="link"><a href=""><span>品牌</span></a></li>
            <li class="link"><a href=""><span>优惠卷</span></a></li>
            <li class="link"><a href=""><span>积分中心</span></a></li>
            <li class="link"><a href=""><span>运动专场</span></a></li>
            <li class="link"><a href=""><span>微商城</span></a></li>
        </ul>
        <!-- 普通导航菜单 End -->
    </div>
    <!-- Header Menu End -->

</div>
<div class="clear"></div>
<!-- 面包屑 注意首页没有 -->
<div class="shop_hd_breadcrumb">
    <strong>当前位置：</strong>
    <span>
			<a href="">首页</a>&nbsp;›&nbsp;
			<a href="">商品分类</a>&nbsp;›&nbsp;
			<a href="">男装女装</a>&nbsp;›&nbsp;
			<a href="">男装</a>
		</span>
</div>
<div class="clear"></div>
<!-- 面包屑 End -->

<!-- Header End -->

<!-- Goods Body -->
<div class="shop_goods_bd clear">

    <!-- TODO 商品展示 -->
    <div class="shop_goods_show">
        <div class="shop_goods_show_left">
            <!-- 京东商品展示 -->
            <link rel="stylesheet" href="css/shop_goodPic.css" type="text/css" />
            <script type="text/javascript" src="js/shop_goodPic_base.js"></script>
            <script type="text/javascript" src="js/lib.js"></script>
            <script type="text/javascript" src="js/163css.js"></script>
            <div id="preview">
                <div class=jqzoom id="spec-n1" onClick="window.open('/')">
                    <!-- 显示的大图 -->
                    <IMG height="350" src="${goods.fengmian}" jqimg="${goods.fengmian}" width="350">
                </div>
                <div id="spec-n5">
                    <div class=control id="spec-left">
                        <img src="images/left.gif" />
                    </div>
                    <div id="spec-list">
                        <ul class="list-h">
                            <!-- 底部的一组小图 -->
                            <li><img src="${goods.fengmian}"> </li>

                            <#list goods.otherImg as img>
                                <li><img src="${img}"> </li>
                            </#list>

                        </ul>
                    </div>
                    <div class=control id="spec-right">
                        <img src="images/right.gif" />
                    </div>

                </div>
            </div>

            <SCRIPT type=text/javascript>
                $(function(){
                    $(".jqzoom").jqueryzoom({
                        xzoom:400,
                        yzoom:400,
                        offset:10,
                        position:"right",
                        preload:1,
                        lens:1
                    });
                    $("#spec-list").jdMarquee({
                        deriction:"left",
                        width:350,
                        height:56,
                        step:2,
                        speed:4,
                        delay:10,
                        control:true,
                        _front:"#spec-right",
                        _back:"#spec-left"
                    });
                    $("#spec-list img").bind("mouseover",function(){
                        var src=$(this).attr("src");
                        $("#spec-n1 img").eq(0).attr({
                            src:src.replace("\/n5\/","\/n1\/"),
                            jqimg:src.replace("\/n5\/","\/n0\/")
                        });
                        $(this).css({
                            "border":"2px solid #ff6600",
                            "padding":"1px"
                        });
                    }).bind("mouseout",function(){
                        $(this).css({
                            "border":"1px solid #ccc",
                            "padding":"2px"
                        });
                    });
                })
            </SCRIPT>
            <!-- 京东商品展示 End -->

        </div>
        <div class="shop_goods_show_right">
            <ul>
                <li>
                    <strong style="font-size:14px; font-weight:bold;"><font color="red">【秒杀】</font> ${goods.subject}</strong>
                </li>
                <li>
                    <label>商品价格：</label>
                    <span><del>${goods.price?string("￥#,###.##")}</del>元</span>
                </li>
                <li>
                    <label>秒杀价：</label>
                    <span><strong style="color: red;">${goods.goodsMiaosha.miaoshaPrice?string("￥#,###.##")}</strong>元</span>
                </li>
                <li>
                    <label>剩余库存：</label>
                    <span>${goods.goodsMiaosha.miaoshaSave}件</span>
                </li>
                <li>
                    <label>评价：</label>
                    <span>0条评论</span>
                </li>
                <li class="goods_num">
                    <label>购买数量：</label>
                    <span>
                        <!-- 减号 -->
                       <#-- <a class="good_num_jian" id="good_num_jian" href="javascript:void(0);"></a>-->
                        <!-- 数量的输入框 -->
                        <input type="text" value="1" id="good_nums" class="good_nums" disabled="disabled"/>
                        <!-- 加号 -->
                       <#-- <a href="javascript:void(0);" id="good_num_jia" class="good_num_jia"></a>(当前库存0件)-->
                    </span>
                </li>
                <li style="padding:20px 0;">
                    <label>&nbsp;</label>
                    <span>
                        <button id="tixing" onclick="msg_tixing();">提醒我</button>
                        <button id="untixing" onclick="msg_quxiaotixing();" style="display: none;">取消提醒</button>
                    </span><br/>
                    <label>&nbsp;</label>
                    <span><strong style="color: red">距离秒杀开始还剩：<a id="djs">00:00:00</a></strong></span><br/>
                    <label>&nbsp;</label>
                    <span><button id="startBtn" disabled="disabled">即将开始</button></span><br/>
                </li>
            </ul>
        </div>
    </div>

    <script type="text/javascript">

        /*$(function(){
           //获得cookie
           var cookies = document.cookie;
           var cookiesArray = cookies.split(";");
           for(var i = 0; i < cookiesArray.length; i++){
               var cookie = cookiesArray[i];//key=value
               var key = cookie.split("=")[0];
               var value = cookie.split("=")[1];

               if(key == "tixing" && value == ${goods.id}){
                   //已经设置过提醒
                   //修改提醒按钮
                   $("#tixing").hide();
                   $("#untixing").show();
                   break;
               }
           }

        });*/

        /**
         * 秒杀提醒
         */
        function msg_tixing(){

            //ajax通知后台
            var gid = ${goods.id};

            $.ajax({
                url: "/msg/tixing",
                data: {"gid":gid},
                success: function(data){
                    if(data == "succ"){
                        //提醒设置成功
                        //将当前商品提醒的信息保存到用户的cookie中
                        document.cookie = "tixing="+gid;

                        //修改提醒按钮
                        $("#tixing").hide();
                        $("#untixing").show();
                    }
                }
            });
        }


        //设置浏览器页面监听
        document.addEventListener('visibilitychange',function(){ //浏览器切换事件
            if(document.visibilityState=='hidden') { //状态判断
                // console.log("隐藏了！！");
                //停止定时器
                clearTimeout(timeout);
            }else {
                // console.log("可见了！！");
                //同步服务器时间
                getServerNow();
                //调用倒计时方法 - 开启了定时器
                djs();
            }
        });


        //实现倒计时的方案

        //当前时间
        var now;//获得服务器的当前时间

        //秒杀开始时间
        var start = new Date('${goods.goodsMiaosha.startTime?datetime}');

        //开始倒计时
        var count = 0;//计数器

        //定时器对象
        var timeout;

        function djs(){
            //获得倒计时的毫秒差
            var djsms = start.getTime() - now.getTime();

            if(djsms <= 0){
                //秒杀已经开始
                // alert("秒杀已经开始！");

                //开放按钮
                //给按钮一个点击时间
                $("#startBtn").click(function(){
                    //弹出等待框...
                    // alert("正在抢购中....");

                    //抢购
                    //location.href="/miaosha/qiangGou?gid=${goods.id}";

                    //申请验证码
                    $("#code_img").attr("src", "/miaosha/getCode");

                    //弹出验证码的弹出框
                    $("#code_div").dialog({
                        width: 300,
                        height: 160,
                        title: "验证码",
                        modal: true
                    });
                });

                $("#startBtn").html("立即抢购");
                $("#startBtn").removeAttr("disabled");

                return;
            }

            //通过倒计时的时间差计算距离开始的时间

            //毫秒转小时 - parseInt取整数
            var h = parseInt(djsms/1000/60/60);
            //毫秒转分钟
            var m = parseInt(djsms/1000/60%60);
            //毫秒转秒
            var s = parseInt(djsms/1000%60);
            //剩余的毫秒数
            var ms = parseInt(djsms%1000);

            console.log("当前剩余的毫秒数：" + ms);

            //倒计时的时间
            var djsStr = formatTime(h) + ":" + formatTime(m) + ":" + formatTime(s);
            $("#djs").html(djsStr);

            //循环递归调用
            timeout = setTimeout(function () {

                if(count < 60){
                    //手动计算
                    //当前时间 + 1
                    now.setSeconds(now.getSeconds() + 1);
                    now.setMilliseconds(now.getMilliseconds() + ms);
                    count++;
                } else {
                    //大概一分钟，主动同步服务器时间
                    getServerNow();
                    count = 0;
                }

                //一秒之后又调用一次倒计时方法
                djs();

            }, 1000 + ms);
        }

        //获得服务器的当前时间
        function getServerNow(){
            $.ajax({
                type:"POST",
                async:false,//设置当前ajax为同步请求
                url:"/miaosha/getNow",
                success:function(time){
                    now = new Date(time);
                }
            });
        }

        //处理时间格式，保证显示两位数
        function formatTime(ele){
            if(ele < 10){
                return "0" + ele;
            }
            return ele;
        }

        //页面加载后触发
        $(function(){
            //获得当前时间
            getServerNow();

            //开始倒计时
            djs();
        });

    </script>

    <!-- 验证码的弹出框 -->
    <div id="code_div" style="display: none;">
        <form action="/miaosha/qiangGou">
            <input name="gid" type="hidden" value="${goods.id}"/>
            请输入验证码：<input name="code" style="width: 60px"/><img onclick="this.src='/miaosha/getCode?p=' + new Date()" id="code_img"/><br/>
            <button type="submit">确认</button>
        </form>
    </div>

    <!-- 商品展示 End -->
    <div class="clear mt15"></div>
    <!-- Goods Left -->
    <div class="shop_bd_list_left clearfix">
        <!-- 左边商品分类 -->
        <div class="shop_bd_list_bk clearfix">
            <div class="title">商品分类</div>
            <div class="contents clearfix">
                <dl class="shop_bd_list_type_links clearfix">
                    <dt><strong>女装</strong></dt>
                    <dd>
                        <span><a href="">风衣</a></span>
                        <span><a href="">长袖连衣裙</a></span>
                        <span><a href="">毛呢连衣裙</a></span>
                        <span><a href="">半身裙</a></span>
                        <span><a href="">小脚裤</a></span>
                        <span><a href="">加绒打底裤</a></span>
                        <span><a href="">牛仔裤</a></span>
                        <span><a href="">打底衫</a></span>
                        <span><a href="">情侣装</a></span>
                        <span><a href="">棉衣</a></span>
                        <span><a href="">毛呢大衣</a></span>
                        <span><a href="">毛呢短裤</a></span>
                    </dd>
                </dl>
            </div>
        </div>
        <!-- 左边商品分类 End -->

        <!-- 热卖推荐商品 -->
        <div class="shop_bd_list_bk clearfix">
            <div class="title">热卖推荐商品</div>
            <div class="contents clearfix">
                <ul class="clearfix">

                    <li class="clearfix">
                        <div class="goods_name"><a href="">Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a href=""><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a href="">去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="goods_name"><a href="">Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a href=""><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a href="">去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="goods_name"><a href="">Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a href=""><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a href="">去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                </ul>
            </div>
        </div>
        <!-- 热卖推荐商品 -->
        <div class="clear"></div>

        <!-- 浏览过的商品 -->
        <div class="shop_bd_list_bk clearfix">
            <div class="title">浏览过的商品</div>
            <div class="contents clearfix">
                <ul class="clearfix">

                    <li class="clearfix">
                        <div class="goods_name"><a href="">Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a href=""><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a href="">去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="goods_name"><a href="">Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a href=""><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a href="">去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="goods_name"><a href="">Gap经典弹力纯色长袖T恤|000891347|原价149元</a></div>
                        <div class="goods_pic"><span class="goods_price">¥ 279.00 </span><a href=""><img src="images/89a6d6466b00ae32d3c826b9ec639084.jpg_small.jpg" /></a></div>
                        <div class="goods_xiaoliang">
                            <span class="goods_xiaoliang_link"><a href="">去看看</a></span>
                            <span class="goods_xiaoliang_nums">已销售<strong>99</strong>笔</span>
                        </div>
                    </li>

                </ul>
            </div>
        </div>
        <!-- 浏览过的商品 -->

    </div>
    <!-- Goods Left End -->

    <!-- 商品详情 -->
    <script type="text/javascript" src="js/shop_goods_tab.js"></script>
    <div class="shop_goods_bd_xiangqing clearfix">
        <div class="shop_goods_bd_xiangqing_tab">
            <ul>
                <li id="xiangqing_tab_1" onmouseover="shop_goods_easytabs('1', '1');" onfocus="shop_goods_easytabs('1', '1');" onclick="return false;"><a href=""><span>商品详情</span></a></li>
                <li id="xiangqing_tab_2" onmouseover="shop_goods_easytabs('1', '2');" onfocus="shop_goods_easytabs('1', '2');" onclick="return false;"><a href=""><span>商品评论</span></a></li>
                <li id="xiangqing_tab_3" onmouseover="shop_goods_easytabs('1', '3');" onfocus="shop_goods_easytabs('1', '3');" onclick="return false;"><a href=""><span>商品咨询</span></a></li>
            </ul>
        </div>
        <div class="shop_goods_bd_xiangqing_content clearfix">
            <div id="xiangqing_content_1" class="xiangqing_contents clearfix">
                <p>${goods.info}</p>
            </div>
            <div id="xiangqing_content_2" class="xiangqing_contents clearfix">
                <p>商品评论----22222</p>
            </div>

            <div id="xiangqing_content_3" class="xiangqing_contents clearfix">
                <p>商品自诩---3333</p>
            </div>
        </div>
    </div>
    <!-- 商品详情 End -->

</div>
<!-- Goods Body End -->

<!-- Footer - wll - 2013/3/24 -->
<div class="clear"></div>
<div class="shop_footer">
    <div class="shop_footer_link">
        <p>
            <a href="">首页</a>|
            <a href="">招聘英才</a>|
            <a href="">广告合作</a>|
            <a href="">关于ShopCZ</a>|
            <a href="">关于我们</a>
        </p>
    </div>
    <div class="shop_footer_copy">
        <p>Copyright 2004-2013 itcast Inc.,All rights reserved.</p>
    </div>
</div>
<!-- Footer End -->

</body>
</html>