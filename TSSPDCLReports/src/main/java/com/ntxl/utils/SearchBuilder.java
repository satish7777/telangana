package com.ntxl.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.CollectionPath;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;


public class SearchBuilder {
	private List<SearchCriteria> params;

	public SearchBuilder() {
		params = new ArrayList<SearchCriteria>();
	}

	public SearchBuilder with(String key, String operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}
	
	public SearchBuilder with(String key, String operation, Object value, String joinStr) {
		params.add(new SearchCriteria(key, operation, value, joinStr));
		return this;
	}

	public <T> BooleanExpression build(PathBuilder<T> entityPath) {
		if (params.size() == 0) {
			return null;
		}

		List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
		for (SearchCriteria param : params) {
			BooleanExpression exp = getPredicate(param, entityPath);
			if (exp != null) {
				predicates.add(exp);
			}
		}

		BooleanExpression result = null;
		if(predicates.size() > 0) {
			result = predicates.get(0);
			for (int i = 1; i < predicates.size(); i++) {
				result = result.and(predicates.get(i));
			}
		}
		return result;
	}

	public <T> BooleanExpression getPredicate(SearchCriteria criteria, PathBuilder<T> entityPath) {
		if (criteria.getValue() != null)
			if (criteria.getOperation().equalsIgnoreCase("=")) {
				NumberPath<Long> path = entityPath.getNumber(criteria.getKey(), Long.class);
				long value = Long.parseLong(criteria.getValue().toString());
				return path.eq(value);
			} 
			else if(criteria.getOperation().equalsIgnoreCase("long")){
				NumberPath<Long> path = entityPath.getNumber(criteria.getKey(), Long.class);
				long value = Long.parseLong(criteria.getValue().toString());
				return path.eq(value);
			}
			else if (criteria.getOperation().equalsIgnoreCase(">")) {
				NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
				int value = Integer.parseInt(criteria.getValue().toString());
				return path.goe(value);
			} 
			else if (criteria.getOperation().equalsIgnoreCase("<")) {
				NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
				int value = Integer.parseInt(criteria.getValue().toString());
				return path.loe(value);
			} 
			else if (criteria.getOperation().equalsIgnoreCase(":")) {
				StringPath path = entityPath.getString(criteria.getKey());
				return path.equalsIgnoreCase(criteria.getValue().toString());
			} 
			else if (criteria.getOperation().equalsIgnoreCase("like")) {
				StringPath path = entityPath.getString(criteria.getKey());
				return path.like(criteria.getValue().toString());
			} 
			else if (criteria.getOperation().equalsIgnoreCase("date")) {
				DatePath<Date> path = entityPath.getDate(criteria.getKey(), Date.class);
				Date value = null;
				if(criteria.getValue() instanceof Date){
					value = (Date) criteria.getValue();
				}
				return path.eq(value);
			} else if (criteria.getOperation().equalsIgnoreCase("dategt")) {
				DatePath<Date> path = entityPath.getDate(criteria.getKey(), Date.class);
				Date value = null;
				if(criteria.getValue() instanceof Date){
					value = (Date) criteria.getValue();
				}
				return path.goe(value);
			} else if (criteria.getOperation().equalsIgnoreCase("datelt")) {
				DatePath<Date> path = entityPath.getDate(criteria.getKey(), Date.class);
				Date value = null;
				if(criteria.getValue() instanceof Date){
					value = (Date) criteria.getValue();
				}
				return path.loe(value);
			} 
		return null;
	}
}
