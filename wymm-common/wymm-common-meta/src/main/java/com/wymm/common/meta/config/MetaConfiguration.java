package com.wymm.common.meta.config;

import com.wymm.common.meta.aspect.MetaAspect;
import com.wymm.common.meta.bind.MetaBind;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MetaConfiguration {
    
    @Bean
    public MetaAspect metaAspect(List<MetaBind> metaBindList) {
        return new MetaAspect(metaBindList);
    }
    
}
