package com.aerothinker.kms.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.aerothinker.kms.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.KmsInfo.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.KmsInfo.class.getName() + ".paraOthers", jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.VerifyRec.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaType.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaClass.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaCat.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaState.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaSource.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaProp.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaAttr.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaOther.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaOther.class.getName() + ".kmsInfos", jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaUser.class.getName() + ".paraRoles", jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaDep.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaRole.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaRole.class.getName() + ".paraNodes", jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaRole.class.getName() + ".paraUsers", jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaNode.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.ParaNode.class.getName() + ".paraRoles", jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.QueryCommon50.class.getName(), jcacheConfiguration);
            cm.createCache(com.aerothinker.kms.domain.QueryCommon10.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
