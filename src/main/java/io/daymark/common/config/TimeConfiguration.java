package io.daymark.common.config;

import org.springframework.boot.autoconfigure.validation.ValidationConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class TimeConfiguration {

    @Bean
    public Clock applicationClock() {
        return Clock.systemUTC();
    }

    @Bean
    public ValidationConfigurationCustomizer utcValidationClockProvider(Clock applicationClock) {
        return validationConfiguration -> validationConfiguration.clockProvider(() -> applicationClock);
    }
}
