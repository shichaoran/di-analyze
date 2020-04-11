//package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;
//
//import com.alibaba.fastjson.JSON;
//import com.vd.canary.data.common.kafka.consumer.impl.Function;
//
//import com.vd.canary.data.common.es.index.ShopTO;
//import com.vd.canary.obmp.customer.api.feign.booth.BoothBusinessFeignClient;
//import com.vd.canary.obmp.customer.api.response.booth.BoothBusinessVO;
//import com.vd.canary.obmp.customer.api.response.customer.vo.CustomerBusinessInfoVO;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @Author shichaoran
// * @Date 2020/4/9 16:49
// * @Version
// */
//public class CustomerBusinessInfo implements Function {
//    @Override
//    public void performES(String msg) {
//
////    private static final Logger logger = LoggerFactory.getLogger(BoothBusiness.class);
////    /**
////     * 通过店铺->coustemer->展位编号
////     */
////    @Autowired
////    private CustomerBusinessInfo customerBusinessInfo;
////
////
//    @Override
//    public void performES(String msg) {
//        logger.info("BoothBusinessBoothCode.msg"+msg);
//        ResponseBO<CustomerBusinessVO> res = customerBusinessInfo.get("");
//        CustomerBusinessVO customerBusinessVO = (customerBusinessVO)res.getData();
////        boothBusinessVO.getCustomerName();
//        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
//        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
//        ShopVo shopVo = new ShopVo();
//        shopVo.setBoothCode( customerBusinessVO.getBoothCode());
//        shopVo.setCustomerId( customerBusinessVO.getCustomerId());
//
//    }
//}