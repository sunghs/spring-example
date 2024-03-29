package sunghs.springexample.activeProfiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.TestConstructor;

import java.util.Arrays;

/**
 * https://github.com/spring-projects/spring-boot/issues/19788
 */
@Slf4j
@TestBasedSpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ActiveProfilesTest {

    @Value("${spring.profiles.active:unknown}")
    private String active;

    private final ConfigurableEnvironment environment;

    private final ProfileConfiguration profileConfiguration;

    private final ResourceConfiguration resourceConfiguration;

    private final ProfileConditionalService profileConditionalService;

    @Test
    void test() {
        Arrays.stream(environment.getActiveProfiles()).forEach(profile -> log.info("env.getActiveProfiles() : {}", profile));
        log.info("env.get spring.profiles.active : {}", environment.getProperty("spring.profiles.active"));
        log.info("env.get spring.profiles.active[0] : {}", environment.getProperty("spring.profiles.active[0]"));
        log.info("spring.profiles.active value annotation : {}", active);
        log.info("profile configuration : {}", profileConfiguration.toString());
        log.info("resource configuration : {}", resourceConfiguration.toString());

        profileConditionalService.doSomething();
    }
}