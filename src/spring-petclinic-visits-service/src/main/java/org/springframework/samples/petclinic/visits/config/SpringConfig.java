package org.springframework.samples.petclinic.visits.config;

import org.springframework.samples.petclinic.visits.model.Visit;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpring;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.visits.store.CacheJdbcVisitStore;
import javax.cache.configuration.FactoryBuilder;
import org.apache.ignite.IgniteCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.apache.ignite.cache.CacheMode;

@Configuration
@EnableIgniteRepositories
public class SpringConfig {

	private final Ignite ignite = Ignition.start("ignite.xml");

	@Bean
	@ConditionalOnClass(name = "org.apache.ignite.Ignite")
	public Ignite getIgnite() {
		return ignite;
	}

	@Bean
	public IgniteCache<Long, Visit> getVisitCache() {
		CacheConfiguration<Long, Visit> visitCache = new CacheConfiguration<>("VisitCache");
		visitCache.setCacheMode(CacheMode.REPLICATED);
		visitCache.setIndexedTypes(Long.class, Visit.class);
		visitCache.setCacheStoreFactory(FactoryBuilder.factoryOf(CacheJdbcVisitStore.class));
		visitCache.setReadThrough(true);
		visitCache.setWriteThrough(true);
		IgniteCache<Long, Visit> cache =ignite.getOrCreateCache(visitCache);
		cache.loadCache(null);
		return cache;
	}
}