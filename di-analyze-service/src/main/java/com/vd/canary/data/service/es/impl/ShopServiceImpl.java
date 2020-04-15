package com.vd.canary.data.service.es.impl;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.SearchShopReq;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.api.response.es.vo.ShopSearchVO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.helper.ESPageRes;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.service.es.ShopService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author shichaoran
 * @Date 2020/4/14 19:53
 * @Version
 */
@Service
public class ShopServiceImpl implements ShopService {
    public ShopESServiceImpl shopESService;

    @Override
    public ResponseBO<ShopRes> search(@Valid SearchShopReq shopSearchBO) {

        ResponseBO<ShopRes> res = new ResponseBO<ShopRes>();
        ShopRes shopRes = new ShopRes();
        ESPageRes esPageRes = shopESService.boolQueryByKeyword(shopSearchBO.getPageNum(), shopSearchBO.getPageSize(), shopSearchBO);
        List<Map<String, Object>> recordList = esPageRes.getRecordList();
        LocalDateTime localDateTime = LocalDateTime.of(2019,10,01,00,00,00);
        if (recordList != null && recordList.size() > 0) {
            List<ShopSearchVO> categoryIds = new ArrayList<>();
            List<ShopVo> brandIds = new ArrayList<>();

            Set set = new HashSet();
            boolean exhibitionJoined;
            String key;

            Map<String, String> brandMap = new HashMap<>();
            Map<String, String> categoryMap = new HashMap<>();
            for (Map<String, Object> recordMap : recordList) {
                String brandId = (String)recordMap.get("brandId");
                String brandName = (String)recordMap.get("brandName");
                brandMap.put(brandId,brandName);

                String categoryId = (String)recordMap.get("categoryId");
                String categoryName = (String)recordMap.get("categoryName");
                categoryMap.put(categoryId,categoryName);

                ShopSearchVO shopSearchVO = new ShopSearchVO();
                shopSearchVO.setBoothCode((List<String>) recordMap.get(brandIds));
                shopSearchVO.setBoothScheduledTime(localDateTime);
                shopSearchVO.setBusinessArea(recordMap.get("skuId").toString());
                shopSearchVO.setBusinessBrand((List<String>) recordMap.get(brandIds));
                shopSearchVO.setBusinessCategory(recordMap.get("skuId").toString());
                shopSearchVO.setClassify((HashSet<String>) set);
                shopSearchVO.setCustomerId(recordMap.get("skuId").toString());
                shopSearchVO.setId(recordMap.get("skuId").toString());
                shopSearchVO.setImageName(recordMap.get("skuId").toString());
                shopSearchVO.setImageOrder(recordMap.get("skuId").toString());
                shopSearchVO.setName(recordMap.get("skuId").toString());


                esPageRes.setRecordCount(shopRes.getTotal());
                categoryIds.add(shopSearchVO);

            }
            shopRes.setBrands(brandMap);
            shopRes.setCategories(categoryMap);
            shopRes.setTotal(esPageRes.getRecordCount());
        }
        res.setData(shopRes);
        return res;
    }
}
