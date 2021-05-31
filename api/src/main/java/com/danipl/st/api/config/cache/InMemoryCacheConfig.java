package com.danipl.st.api.config.cache;

import com.danipl.st.api.constant.ProfileConstant;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Configuration
@EnableCaching
@Profile(value = {ProfileConstant.TEST, ProfileConstant.LOCAL})
public class InMemoryCacheConfig {
}
