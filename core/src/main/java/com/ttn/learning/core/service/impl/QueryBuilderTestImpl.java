package com.ttn.learning.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.ttn.learning.core.service.QueryBuilderTest;

@Component(immediate=true)
public class QueryBuilderTestImpl implements QueryBuilderTest{

	@Override
	public List<String> getPathByTitle(ResourceResolver resolver, String searchText) {
		QueryBuilder builder = resolver.adaptTo(QueryBuilder.class);
		Map<String , String> queryMap = new HashMap<String, String>();
		queryMap.put("path", "/content/geometrixx/en");
		queryMap.put("type", "cq:Page");
		queryMap.put("property", "jcr:content/jcr:title");
		queryMap.put("property.value", searchText);
		Query query = builder.createQuery(PredicateGroup.create(queryMap), resolver.adaptTo(Session.class));
		SearchResult result = query.getResult();
		List<String> list = new ArrayList<String>();
		for(Hit hit : result.getHits()){
			try {
				list.add(hit.getResource().getPath());
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public List<String> getResultByCustomPredicate(ResourceResolver resolver) {
		
		QueryBuilder builder = resolver.adaptTo(QueryBuilder.class);
		Map<String , String> queryMap = new HashMap<String, String>();
		/**
		 * replic.by=admin replic.since=2013-01-01T00:00:00.000+01:00
		 * replic.action=Activate
		 * 
		 */
		queryMap.put("path", "/content");
		//queryMap.put("type", "cq:Page");
		queryMap.put("customreplic.by", "admin");
		queryMap.put("customreplic.since","2013-01-01T00:00:00.000+01:00");
		queryMap.put("customreplic.action","Activate");
		Query query = builder.createQuery(PredicateGroup.create(queryMap), resolver.adaptTo(Session.class));
		SearchResult result = query.getResult();
		List<String> list = new ArrayList<String>();
		for(Hit hit : result.getHits()){
			try {
				list.add(hit.getResource().getPath());
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

}
