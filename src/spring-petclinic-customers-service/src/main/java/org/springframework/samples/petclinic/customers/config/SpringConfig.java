package org.springframework.samples.petclinic.customers.config;

import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.PetType;
import org.springframework.samples.petclinic.customers.store.CacheJdbcOwnerStore;
import org.springframework.samples.petclinic.customers.store.CacheJdbcPetStore;
import org.springframework.samples.petclinic.customers.store.CacheJdbcPetTypeStore;

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
	public IgniteCache<Long, Owner> getOwnerCache() {
		CacheConfiguration<Long, Owner> ownerCache = new CacheConfiguration<>("OwnerCache");
		ownerCache.setCacheMode(CacheMode.REPLICATED);
		ownerCache.setIndexedTypes(Long.class, Owner.class);
		ownerCache.setCacheStoreFactory(FactoryBuilder.factoryOf(CacheJdbcOwnerStore.class));
		ownerCache.setReadThrough(true);
		ownerCache.setWriteThrough(true);
		IgniteCache<Long, Owner> cache =ignite.getOrCreateCache(ownerCache);
		cache.loadCache(null);
		return cache;
	}
	
	@Bean
	public IgniteCache<Long, Pet> getPetCache() {
		CacheConfiguration<Long, Pet> petCache = new CacheConfiguration<>("PetCache");
		petCache.setCacheMode(CacheMode.REPLICATED);
		petCache.setIndexedTypes(Long.class, Pet.class);
		petCache.setCacheStoreFactory(FactoryBuilder.factoryOf(CacheJdbcPetStore.class));
		petCache.setReadThrough(true);
		petCache.setWriteThrough(true);
		IgniteCache<Long, Pet> cache =ignite.getOrCreateCache(petCache);
		cache.loadCache(null);
		return cache;
	}
	
	@Bean
	public IgniteCache<Long, PetType> getPetTypeCache() {
		CacheConfiguration<Long, PetType> petTypeCache = new CacheConfiguration<>("PetTypeCache");
		petTypeCache.setCacheMode(CacheMode.REPLICATED);
		petTypeCache.setIndexedTypes(Long.class, PetType.class);
		petTypeCache.setCacheStoreFactory(FactoryBuilder.factoryOf(CacheJdbcPetTypeStore.class));
		petTypeCache.setReadThrough(true);
		petTypeCache.setWriteThrough(true);
		IgniteCache<Long, PetType> cache =ignite.getOrCreateCache(petTypeCache);
		cache.loadCache(null);
		return cache;
	}
}