package com.tki.katalis.idempoten.config;

import com.tki.katalis.idempoten.model.MemoryIdempotentToken;
import com.tki.katalis.idempoten.properties.IdempotentProperties;
import com.tki.katalis.idempoten.util.IdempotentTokenUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass()
@EnableConfigurationProperties(IdempotentProperties.class)
@ConditionalOnProperty(prefix = "idempotent", value = "enable", havingValue = "true")
public class IdempotentAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(IdempotentAop.class)
    public IdempotentAop idempotentAop() {
        return new IdempotentAop();
    }

    @Bean
    @ConditionalOnMissingBean(IdempotentTokenUtils.class)
    public IdempotentTokenUtils tokenUtils() {
        return new IdempotentTokenUtils();
    }

    @Bean
    public IdempotentToken memoryIdempotentToken() {
        return new MemoryIdempotentToken();
    }

}
