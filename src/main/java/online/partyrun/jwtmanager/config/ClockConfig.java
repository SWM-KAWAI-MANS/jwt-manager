package online.partyrun.jwtmanager.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@Slf4j
@ConditionalOnMissingBean(Clock.class)
public class ClockConfig {

    @Bean
    public Clock clock() {
        log.info("Initializing Clock");
        return Clock.systemDefaultZone();
    }
}