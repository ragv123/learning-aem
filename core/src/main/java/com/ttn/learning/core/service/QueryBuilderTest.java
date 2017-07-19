package com.ttn.learning.core.service;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

public interface QueryBuilderTest {

	List<String> getPathByTitle(ResourceResolver resolver, String searchText);
}
