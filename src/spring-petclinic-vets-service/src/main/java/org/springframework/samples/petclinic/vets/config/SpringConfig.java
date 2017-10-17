package org.springframework.samples.petclinic.vets.config;

import org.springframework.samples.petclinic.vets.model.Specialty;
import org.springframework.samples.petclinic.vets.model.Vet;
import org.springframework.samples.vetclinic.vets.store.CacheJdbcVetStore;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpring;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	public IgniteCache<Long, Vet> getVetCache() {
		CacheConfiguration<Long, Vet> vetCache = new CacheConfiguration<>("VetCache");
		vetCache.setCacheMode(CacheMode.REPLICATED);
		vetCache.setIndexedTypes(Long.class, Vet.class);
		vetCache.setCacheStoreFactory(FactoryBuilder.factoryOf(CacheJdbcVetStore.class));
		vetCache.setReadThrough(true);
		vetCache.setWriteThrough(true);
		IgniteCache<Long, Vet> cache =ignite.getOrCreateCache(vetCache);
		cache.loadCache(null);
		return cache;
	}
}