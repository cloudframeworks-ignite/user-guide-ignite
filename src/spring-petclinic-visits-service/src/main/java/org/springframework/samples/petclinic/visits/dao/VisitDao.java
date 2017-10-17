package org.springframework.samples.petclinic.visits.dao;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.samples.petclinic.visits.model.Visit;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.List;
import java.util.ArrayList;

@Repository
public class VisitDao {
	
    @Autowired
    private final IgniteCache<Long, Visit> cache;

    @Autowired
    private final Ignite ignite;

    public VisitDao(IgniteCache<Long, Visit> cache, Ignite ignite) {
        this.cache = cache;
        this.ignite = ignite;
    }

    public Visit visitById(long id) {
        return cache.get(id);
    }
    
    public List<Visit> visitByPetId(int petId) {
    	List<Visit> rlist=new ArrayList<Visit>();
    	QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery(
                "select id, petId, date, description from Visit where petId="+petId));
    	//List<List<?>> res = cursor.getAll();
    	for (List<?> row : cursor){
    		rlist.add(new Visit(((Long)row.get(0)).intValue(),(Integer)row.get(1), (String)row.get(2),(String)row.get(3)));
    	}
        return rlist;
    }

	public long maxID() {
		long id=0;
		QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery(
				"select max(id) from Visit"));
		for (List<?> row : cursor) {
			id=(Long) row.get(0);
		}
		return id;
	}
    
    public void add(Visit visit) {
    	if(visit.id==null){
    		visit.id=maxID()+1;
		}
        cache.put(visit.id, visit);
    }
}
