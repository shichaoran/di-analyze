package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.alibaba.fastjson.JSON;
import com.vd.canary.core.bo.RequestBO;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.service.es.impl.ProductsTO;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;
import com.vd.canary.obmp.product.api.request.spu.*;
import com.vd.canary.obmp.product.api.response.sku.ProductSkuCreateResp;
import com.vd.canary.obmp.product.api.response.spu.*;
import com.vd.canary.obmp.product.api.response.spu.vo.ProductSpuPageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author WangRuilin
 * @Date 2020/4/9 14:22
 */
public class ProductSpu implements Function {
    private static final Logger logger = LoggerFactory.getLogger(ProductSpu.class);
    @Autowired
    private ProductSpuFeign productspu;
    @Override
    public void performES(String msg) {
        logger.info("ProductSpu.msg"+msg);
        ProductsTO productsTO = new ProductsTO();

        ResponseBO<ProductSpuDetailResp> res = productspu.spuDetail(productsTO.getProSkuSpuId());
        ProductSpuDetailResp pro =  (ProductSpuDetailResp)res.getData();

        productsTO.setSpuState(pro.getSpuState());
        productsTO.setProSpuSpuPic(pro.getSpuPic());
        productsTO.setSpuTitle(pro.getSpuTitle());


    }


}
