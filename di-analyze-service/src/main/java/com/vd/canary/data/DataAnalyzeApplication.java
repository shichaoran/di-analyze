package com.vd.canary.data;

import com.vd.canary.service.annotations.CanaryCloudApplication;
import com.vd.canary.service.constants.ClusterConstant;
import com.vd.canary.service.launch.CanaryApplication;


@CanaryCloudApplication(clusterName = "DI", applicationName = "canary-dataanalyze")
public class DataAnalyzeApplication {

    public static void main(String[] args) {
        CanaryApplication.run(DataAnalyzeApplication.class, args);
    }

}

