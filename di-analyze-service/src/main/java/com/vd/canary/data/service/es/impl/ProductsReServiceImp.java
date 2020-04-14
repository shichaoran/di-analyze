package com.vd.canary.data.service.es.impl;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ThreeCategoryReq;
import com.vd.canary.data.api.response.es.ESPageRes;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.service.es.ProductsReService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:57
 * @Version
 */
public class ProductsReServiceImp implements ProductsReService {
    @Autowired
    private ProductESServiceImpl productESServiceImpl;
    @Override
    public ResponseBO<ProductsRes> getProductByCategory(@Valid ThreeCategoryReq threeCategoryReq) {
        ResponseBO<ProductsRes> res = new ResponseBO<ProductsRes>();
        ProductsRes productsRes = new ProductsRes();
        ESPageRes esPageRes = productESServiceImpl.boolQueryByKeyword(threeCategoryReq.getPageNum(),threeCategoryReq.getPageSize(),threeCategoryReq);
        List<Map<String, Object>> recordList = esPageRes.getRecordList();


    }
}
