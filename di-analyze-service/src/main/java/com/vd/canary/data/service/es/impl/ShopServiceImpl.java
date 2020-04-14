package com.vd.canary.data.service.es.impl;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ShopSearchReq;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.api.response.es.vo.ProductsDetailRes;
import com.vd.canary.data.api.response.es.vo.ShopSearchVO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.helper.ESPageRes;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.service.es.ShopService;

import javax.validation.Valid;
import java.util.*;

/**
 * @Author shichaoran
 * @Date 2020/4/14 19:53
 * @Version
 */
public class ShopServiceImpl implements ShopService {
    public ShopESServiceImpl shopESService;

    @Override
    public ResponseBO<ShopRes> search(@Valid ShopSearchReq shopSearchBO) {

        ResponseBO<ShopRes> res = new ResponseBO<ShopRes>();
        ShopRes shopRes = new ShopRes();
        ESPageRes esPageRes = shopESService.boolQueryByKeyword(shopSearchBO.getPageNum(), shopSearchBO.getPageSize(), shopSearchBO);
        List<Map<String, Object>> recordList = esPageRes.getRecordList();
        if (recordList != null && recordList.size() > 0) {
            List<ShopSearchVO> categoryIds = new ArrayList<>();
            List<ShopVo> brandIds = new ArrayList<>();
            Set set = new HashSet();
            boolean exhibitionJoined;
            String key;
            for (Map<String, Object> recordMap : recordList) {
                ShopSearchVO shopSearchVO = new ShopSearchVO();
                shopSearchVO.setBoothCode((List<String>) recordMap.get(brandIds));
                shopSearchVO.setBoothScheduledTime((Date) recordMap.get(brandIds));
                shopSearchVO.setBusinessArea(recordMap.get("skuId").toString());
                shopSearchVO.setBusinessBrand((List<String>) recordMap.get(brandIds));
                shopSearchVO.setBusinessCategory(recordMap.get("skuId").toString());
                shopSearchVO.setClassify((HashSet<String>) set);
                shopSearchVO.setCustomerId(recordMap.get("skuId").toString());
                shopSearchVO.setId(recordMap.get("skuId").toString());
                shopSearchVO.setImageName(recordMap.get("skuId").toString());
                shopSearchVO.setImageOrder(recordMap.get("skuId").toString());
                shopSearchVO.setName(recordMap.get("skuId").toString());
                categoryIds.add(shopSearchVO);
            }
            shopRes.setShopSeatchVOS(categoryIds);
        }
        res.setData(shopRes);
        return res;
    }
}
