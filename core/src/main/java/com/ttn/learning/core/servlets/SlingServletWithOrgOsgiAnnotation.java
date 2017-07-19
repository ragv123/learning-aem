package com.ttn.learning.core.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ttn.learning.core.service.QueryBuilderTest;

@Component(service = Servlet.class, property = { "sling.servlet.paths=/bin/learning/queryTest" })
public class SlingServletWithOrgOsgiAnnotation extends SlingAllMethodsServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Reference
	QueryBuilderTest queryBuilderTest;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException, ServletException {
		String searchText = request.getParameter("queryString");
		ResourceResolver resolver = request.getResourceResolver();
		List<String> list = queryBuilderTest.getResultByCustomPredicate(resolver);
		response.getWriter().write(list.toString());
	}

}
