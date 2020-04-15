package com.vd.canary.data.common.kafka.consumer.impl;

import java.util.HashMap;

import com.vd.canary.data.common.kafka.consumer.impl.ObmpProduct.TestTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionFactory {

    private static final Logger logger = LoggerFactory.getLogger(FunctionFactory.class);

    private HashMap<String, Class> functionMap = defaultMapFactory();

    private static FunctionFactory factory;

    public static FunctionFactory instance(){
        if(factory==null){
            factory=new FunctionFactory();
        }
        return factory;
    }

    public Function createFunction(String  table) {
        Class classObj =  functionMap.get(table);
        if (classObj == null){
            classObj = (Class) functionMap.get(table);
            if(classObj==null){
                throw new RuntimeException(getClass() + " was unable to find an functionFactory named '" + table + "'.");
            }
        }
        Function function = null;
        try {
            function = (Function) classObj.newInstance();
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return function;
    }

    // 注册时一定要和数据库名.表名完全相同
    private HashMap<String,Class> defaultMapFactory() {
        HashMap<String,Class> map = new HashMap<>();
        map.put("obmp_product.product_sku", TestTable.class);
        map.put("obmp_product.sku_attribute_relations", TestTable.class);
        map.put("obmp_product.sku_selling_price", TestTable.class);
        map.put("obmp_product.sku_warehouse_relations", TestTable.class);
        map.put("obmp_product.store_product_relations", TestTable.class);
        map.put("obmp_customer.booth_business", TestTable.class);
        map.put("obmp_customer.store_info", TestTable.class);
        map.put("obmp_customer.store_media", TestTable.class);
        map.put("obmp_customer.customer_business_info", TestTable.class);
        map.put("obmp_customer.store_loop_banner", TestTable.class);
        map.put("obmp_customer.protocol_agreement", TestTable.class);
        return map;
    }

}
