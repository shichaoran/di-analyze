package com.vd.canary.data;

import com.vd.canary.service.annotations.CanaryCloudApplication;
import com.vd.canary.service.constants.ClusterConstant;
import com.vd.canary.service.launch.CanaryApplication;


//@CanaryCloudApplication(clusterName = ClusterConstant.OBMP, applicationName = "canary-dataanalyze",nacosNamespaceId="13c80def-b092"
//                                                                                                                     + "-4d3f-b662"
//        + "-64e2ecdf2d6c")
@CanaryCloudApplication(clusterName = ClusterConstant.OBMP, applicationName = "canary-dataanalyze")
public class DataAnalyzeApplication {
    //System.setProperty("es.set.netty.runtime.available.processors","false");
    public static void main(String[] args) {
        CanaryApplication.run(DataAnalyzeApplication.class, args);
    }

}

