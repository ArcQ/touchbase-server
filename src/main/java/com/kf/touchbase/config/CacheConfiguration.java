package com.kf.touchbase.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import com.github.benmanes.caffeine.jcache.spi.CaffeineCachingProvider;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.util.JHipsterProperties;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;

import javax.cache.CacheManager;
import javax.inject.Singleton;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Factory
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration<Object, Object> caffeineConfiguration = new CaffeineConfiguration<>();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @Singleton
    public CacheManager cacheManager(ApplicationContext applicationContext) {
        CacheManager cacheManager = new CaffeineCachingProvider().getCacheManager(
                null, applicationContext.getClassLoader(), new Properties());
        customizeCacheManager(cacheManager);
        return cacheManager;
    }

    private void customizeCacheManager(CacheManager cm) {
        createCache(cm, com.kf.touchbase.repository.UserRepository.USERS_BY_LOGIN_CACHE);
        createCache(cm, com.kf.touchbase.repository.UserRepository.USERS_BY_EMAIL_CACHE);
        createCache(cm, UserRepository.USERS_BY_AUTH_KEY_CACHE);
        createCache(cm, com.kf.touchbase.domain.User.class.getName());
        createCache(cm, com.kf.touchbase.domain.Authority.class.getName());
        createCache(cm, com.kf.touchbase.domain.User.class.getName() + ".authorities");
        createCache(cm, com.kf.touchbase.domain.Base.class.getName());
        createCache(cm, com.kf.touchbase.domain.Base.class.getName() + ".chats");
        createCache(cm, com.kf.touchbase.domain.Base.class.getName() + ".members");
        createCache(cm, com.kf.touchbase.domain.Chat.class.getName());
        createCache(cm, com.kf.touchbase.domain.Mission.class.getName());
        createCache(cm, com.kf.touchbase.domain.BaseMember.class.getName());
        createCache(cm, com.kf.touchbase.domain.BaseMember.class.getName() + ".bases");
        createCache(cm, com.kf.touchbase.domain.BaseMember.class.getName() + ".members");
        createCache(cm, com.kf.touchbase.domain.BaseJoin.class.getName());
        // jhipster-needle-caffeine-add-entry
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
