package com.vd.canary.data.common.es.service;

import java.util.List;

import com.vd.canary.data.common.es.index.ProductsTO;

public interface ProductESService {

    // 新增商品信息
    void saveProduct(ProductsTO product);

    // 批量新增商品信息
    void batchAddProduct(List<ProductsTO> products);

    // 删除商品
    void deletedUserById(String id);

    //
    void updateProduct(ProductsTO product);

    List<ProductsTO> queryByProductName(String userName);

}
