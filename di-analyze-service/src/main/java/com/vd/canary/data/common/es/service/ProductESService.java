package com.vd.canary.data.common.es.service;

import java.util.List;

public interface ProductESService {

    // 新增商品信息
    void saveProduct(Product product);

    // 批量新增商品信息
    void batchAddProduct(List<Product> products);

    // 删除商品
    void deletedUserById(String id);

    //
    void updateProduct(Product product);

    List<Product> queryByProductName(String userName);

}
