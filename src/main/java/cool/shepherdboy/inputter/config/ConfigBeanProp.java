package cool.shepherdboy.inputter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ShepherdBoy
 * @createTime 2023-02-21 11:26
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@ConfigurationProperties(prefix = "registry")
@PropertySource(value = "classpath:application.properties")
@Data
public class ConfigBeanProp {
    private String url;
}
