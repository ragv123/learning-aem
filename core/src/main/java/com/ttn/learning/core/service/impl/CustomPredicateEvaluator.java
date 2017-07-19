package com.ttn.learning.core.service.impl;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.Predicate;
import com.day.cq.search.eval.AbstractPredicateEvaluator;
import com.day.cq.search.eval.EvaluationContext;

@Component(factory = "com.day.cq.search.eval.PredicateEvaluator/customreplic")
public class CustomPredicateEvaluator extends AbstractPredicateEvaluator {

	public static final String PE_NAME = "customreplic";
	public static final String PN_LAST_REPLICATED_BY = "cq:lastReplicatedBy";
	public static final String PN_LAST_REPLICATED = "cq:lastReplicated";
	public static final String PN_LAST_REPLICATED_ACTION = "cq:lastReplicationAction";

	public static final String PREDICATE_BY = "by";
	public static final String PREDICATE_SINCE = "since";
	public static final String PREDICATE_SINCE_OP = " >= ";
	public static final String PREDICATE_ACTION = "action";
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getXPathExpression(Predicate predicate, EvaluationContext context) {
		log.error("predicate {}", predicate);
		String date = predicate.get(PREDICATE_SINCE);
		String user = predicate.get(PREDICATE_BY);
		String action = predicate.get(PREDICATE_ACTION);

		StringBuilder sb = new StringBuilder();

		if (date != null) {
			sb.append(PN_LAST_REPLICATED).append(PREDICATE_SINCE_OP);
			sb.append("xs:dateTime('").append(date).append("')");
		}
		if (user != null) {
			addAndOperator(sb);
			sb.append(PN_LAST_REPLICATED_BY);
			sb.append("='").append(user).append("'");
		}
		if (action != null) {
			addAndOperator(sb);
			sb.append(PN_LAST_REPLICATED_ACTION);
			sb.append("='").append(action).append("'");
		}
		String xpath = sb.toString();

		log.error("xpath **{}**", xpath);

		return xpath;
	}

	/**
	 * Add an and operator if the builder is not empty.
	 *
	 * @param sb
	 *            a {@link StringBuilder} containing the query under
	 *            construction
	 */
	private void addAndOperator(StringBuilder sb) {
		if (sb.length() != 0) {
			sb.append(" and ");
		}
	}
	
	@Override
	public boolean canXpath(Predicate predicate, EvaluationContext context) {
		return true;
	}

}
