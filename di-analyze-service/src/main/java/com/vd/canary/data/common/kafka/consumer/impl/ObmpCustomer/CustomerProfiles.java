package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.core.bo.ResponsePageVO;
import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.customer.api.feign.agreement.AgreementFeignClient;
import com.vd.canary.obmp.customer.api.feign.booth.BoothBusinessFeignClient;
import com.vd.canary.obmp.customer.api.feign.customer.CustomerClient;
import com.vd.canary.obmp.customer.api.feign.customer.CustomerInvoiceFeignClient;
import com.vd.canary.obmp.customer.api.request.agreement.AgreementQueryReq;
import com.vd.canary.obmp.customer.api.request.booth.BoothBusinessReq;
import com.vd.canary.obmp.customer.api.response.agreement.AgreementInfoResp;
import com.vd.canary.obmp.customer.api.response.agreement.AgreementInfoVO;
import com.vd.canary.obmp.customer.api.response.agreement.ProtocolAgreementAccessoryVO;
import com.vd.canary.obmp.customer.api.response.booth.BoothBusinessInfoResp;
import com.vd.canary.obmp.customer.api.response.customer.vo.CustomerProfilesVO;
import com.vd.canary.obmp.order.api.request.PurchaseOrderListReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author shichaoran
 * @Date 2020/4/16 15:33
 * @Version
 */
public class CustomerProfiles implements Function {
    private static final Logger logger = LoggerFactory.getLogger(BoothBusiness.class);

    @Autowired
    private CustomerClient customerClient;

    @Override
    public void performES(String msg) {
        logger.info("BoothBusinessBoothCode.msg" + msg);
        ResponseBO<CustomerProfilesVO> res = customerClient.queryCustomerProfilesByStoreId("");
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        CustomerProfilesVO customerProfilesVO = (CustomerProfilesVO) res.getData();
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ShopTO shopTO = new ShopTO();
        for (Map.Entry<String, String> entry : entries) {

            shopTO.setLevel(customerProfilesVO.getLevel());

        }
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(msg, map.getClass());
        String goodsid = (String) map.get("goods_id");
        ShopESServiceImpl shopESService = new ShopESServiceImpl();
        if (goodsid.equals("insert")) {
            try {
                shopESService.saveShop(shopTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (goodsid.equals("updata")) {
            try {
                shopESService.findById(shopTO.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                shopESService.updateShop(shopTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (goodsid.equals("delete")) {
            try {
                shopESService.deletedShopById(shopTO.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
