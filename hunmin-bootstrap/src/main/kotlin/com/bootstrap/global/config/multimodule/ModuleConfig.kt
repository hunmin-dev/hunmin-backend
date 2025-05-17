package com.bootstrap.global.config.multimodule

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(
    basePackages = [
        "com.common",
        "com.domain",
        "com.application",
        "com.persistence",
        "com.adapter",
    ],
)
class ModuleConfig
