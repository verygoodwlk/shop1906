package com.qf.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import com.qf.util.AliPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IOrderService orderService;

    /**
     * 调用支付宝进行支付
     */
    @RequestMapping("/alipay")
    public void aliPay(Integer oid, HttpServletResponse response){

        //service根据oid查询订单对象
        Orders orders = orderService.queryById(oid);
        System.out.println("需要下单的订单信息：" + orders);

        //获得支付宝核心对象
        AlipayClient alipayClient = AliPayUtil.getAlipayClient();

        //调用支付接口进行支付
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://localhost:16666/order/list");//用户支付完成后同步跳转的url
        alipayRequest.setNotifyUrl("http://verygoodwlk.xicp.net/pay/payCallback");//支付完成后，异步通知商户支付结果
        //交易支付的请求参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orders.getOrderid() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + orders.getAllprice().doubleValue() + "," +
                "    \"subject\":\"" + orders.getOrderid() + "\"," +
                "    \"body\":\"" + orders.getOrderid() + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收支付宝支付结果的异步回调方法
     *
     * out_trade_no:支付的订单号
     * trade_status:交易状态
     *
     * https://商家网站通知地址?
     *      voucher_detail_list=[{"amount":"0.20","merchantContribute":"0.00","name":"5折券",
     *          "otherContribute":"0.20","type":"ALIPAY_DISCOUNT_VOUCHER",
     *          "voucherId":"2016101200073002586200003BQ4"}]&
     *      fund_bill_list=[{"amount":"0.80","fundChannel":"ALIPAYACCOUNT"},
     *          {"amount":"0.20","fundChannel":"MDISCOUNT"}]&
     *      subject=PC网站支付交易&
     *      trade_no=2016101221001004580200203978&
     *      gmt_create=2016-10-12 21:36:12&
     *      notify_type=trade_status_sync&
     *      total_amount=1.00&
     *      out_trade_no=mobile_rdm862016-10-12213600&
     *      invoice_amount=0.80&
     *      seller_id=2088201909970555&
     *      notify_time=2016-10-12 21:41:23&
     *      trade_status=TRADE_SUCCESS&
     *      gmt_payment=2016-10-12 21:37:19&
     *      receipt_amount=0.80&
     *      passback_params=passback_params123&
     *      buyer_id=2088102114562585&
     *      app_id=2016092101248425&
     *      notify_id=7676a2e1e4e737cff30015c4b7b55e3kh6&
     *      sign_type=RSA2&
     *      buyer_pay_amount=0.80&
     *      sign=***&
     *      point_amount=0.00
     *
     */
    @RequestMapping("/payCallback")
    @ResponseBody
    public String payCallback(
            String charset,
            String out_trade_no,
            String trade_status,
            String sign_type, HttpServletRequest request){

        //验签回调方法的来源
        Map<String, String> map = new HashMap<>();

        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("接收到参数：" + parameterMap);
        for(Map.Entry<String, String[]> entry : parameterMap.entrySet()){
            System.out.println(entry.getKey() + "=" + Arrays.toString(entry.getValue()));

            map.put(entry.getKey(), entry.getValue()[0]);
        }

//        map.remove("sign");
//        map.remove("sign_type");

        try {
            boolean flag = AlipaySignature.rsaCheckV1(
                    map,
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB",
                    charset,
                    sign_type);


            System.out.println("验证的结果：" + flag);


            if(flag){
                //首次验签通过
                //二次验签
                //

                //接收异步通知，修改订单状态
                if(trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")){
                    //支付成功
                    //根据订单号修改订单状态
                    orderService.updateOrderState(out_trade_no, 1);

                    return "success";
                }

            } else {
                //记录日志
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "failure";
    }
}
