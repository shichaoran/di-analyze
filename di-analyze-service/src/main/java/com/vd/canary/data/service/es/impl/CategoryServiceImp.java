package com.vd.canary.data.service.es.impl;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.CategoryReq;
import com.vd.canary.data.api.response.es.CategoryRes;
import com.vd.canary.data.api.response.es.ESPageRes;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.api.response.es.vo.CategoryVO;
import com.vd.canary.data.api.response.es.vo.ProductsDetailRes;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.service.es.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author shichaoran
 * @Date 2020/4/14 17:20
 * @Version
 */
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private ProductESServiceImpl productESServiceImpl;

    @Override
    public ResponseBO<CategoryRes> categoryres(@Valid CategoryReq categoryReq) {
        ResponseBO<CategoryRes> res = new ResponseBO<CategoryRes>();
        CategoryRes categoryRes = new CategoryRes();
        ESPageRes esPageRes = productESServiceImpl.boolQueryByKeyword(categoryReq.getSkuIdList());
        List<Map<String, Object>> recordList = esPageRes.getRecordList();
        if (recordList != null && recordList.size() > 0) {
            String skuId = new String();
            CategoryVO categoryVO = new CategoryVO();
            Map<String, String> maplist = new HashMap<>();
            for (Map<String, Object> recordMap : recordList) {
                skuId.toString();
                categoryVO.setFOneCategoryCode(recordMap.get("fOneCategoryCode").toString());
                categoryVO.setFOneCategoryId();
                categoryVO.setFOneCategoryName();
                categoryVO.setFThreeCategoryCode();
                categoryVO.setFThreeCategoryId();
                categoryVO.setFThreeCategoryName();
                categoryVO.setFTwoCategoryCode();
                categoryVO.setFTwoCategoryId();
                categoryVO.setFTwoCategoryName();

            }
            }
        }
    }