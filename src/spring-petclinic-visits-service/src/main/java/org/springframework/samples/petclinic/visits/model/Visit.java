/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.visits.model;

import java.util.Date;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;

public class Visit implements Serializable {

	private static final AtomicLong ID_GEN = new AtomicLong();

	@QuerySqlField(index = true)
	public Long id;

	@QuerySqlField
	public Integer petId;

	@QuerySqlField
	public String date;

	@QuerySqlField
	public String description;

	public Visit(){
		
	}
	public Visit(int pet_id, String visit_date, String description) {
		id = ID_GEN.incrementAndGet();
		this.petId = pet_id;
		this.date = visit_date;
		this.description = description;
	}
	
	public Visit(int id, int pet_id, String visit_date, String description) {
		this.id = Long.valueOf(id);
		this.petId = pet_id;
		this.date = visit_date;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Visit [id=" + id + ", petId=" + petId + ", date="
				+ date + ", description=" + description + ']';
	}
}
