package com.api.global.config.multimodule

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@PropertySource(
    value = [
        "classpath:application-common-\${spring.profiles.active}.yml",
//        "classpath:application-domain-\${spring.profiles.active}.yml",
        "classpath:application-persistence-\${spring.profiles.active}.yml",
//        "classpath:application-application-\${spring.profiles.active}.yml",
    ],
    factory = YamlPropertySourceFactory::class
)
@Configuration
class PropertySourceScanConfig
