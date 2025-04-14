package com.api.global.config.multimodule

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(
    "com.common",
    "com.domain",
    "com.application",
    "com.persistence",
    "com.adapter"
)
class ModuleConfig {
}
