package com.wymm.common.meta;

import com.wymm.common.meta.config.MetaConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(MetaConfiguration.class)
@Configuration(proxyBeanMethods = false)
public class MetaAutoConfiguration {

}
