package sunghs.springexample.activeProfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.annotation.PostConstruct;

@Slf4j
@Getter
@ToString
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("sunghs")
public class ResourceConfiguration {

    private final String profile;

    private final int data;

    @PostConstruct
    public void broadcast() {
        log.info(this.toString());
    }
}
