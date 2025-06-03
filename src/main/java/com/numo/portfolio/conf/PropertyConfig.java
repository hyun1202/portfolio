package com.numo.portfolio.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertyConfig {
    @Value("${CLIENT_HOST}")
    private String clientHost;
}
