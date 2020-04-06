package com.vd.canary.data.api.feign.es;

import com.sun.istack.NotNull;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.exception.BusinessException;
import com.vd.canary.data.api.request.es.ESEntityWordReq;
import com.vd.canary.data.api.response.es.ESEntityWordRes;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

import static com.vd.canary.core.constant.HttpResponseStatus.FEIGN_EXCEPTION;

@Slf4j
@Component
public class ESEntityWordFallbackFactory implements FallbackFactory<ESEntityWordServiceFeign> {

    @Override
    public ESEntityWordServiceFeign create(Throwable e) {
        return new ESEntityWordServiceFeign() {

            @Override
            public ResponseBO<ESEntityWordRes> get(@NotNull String id){
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }

            @Override
            public ResponseBO<ESEntityWordRes> getByKey(@NotNull String key){
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }

            @Override
            public ResponseBO<ESEntityWordRes> creatEntityWordByKey(@Valid ESEntityWordReq esEntityWordReq) {
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }


        };
    }
}
