package org.gatlin.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = "classpath*:conf/spring/spring-*.xml")
@ComponentScan(includeFilters = {@Filter(type = FilterType.REGEX, pattern = "org.gatlin")}, 
excludeFilters = {@Filter(type = FilterType.REGEX, pattern = "org.gatlin.web")})
public class Bootstrap {

}
