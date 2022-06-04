package com.codingwasabi.howtodo.acceptance;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.CaseFormat;

@Component
@ActiveProfiles("test")
public class DatabaseCleaner implements InitializingBean {
	private List<String> tableName;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		tableName = entityManager.getMetamodel()
								 .getEntities()
								 .stream()
								 .filter(e -> e.getJavaType()
											   .getAnnotation(Entity.class) != null)
								 .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
								 .collect(Collectors.toList());
	}

	@Transactional
	public void execute() {
		entityManager.flush();
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE")
					 .executeUpdate();

		tableName.forEach(table -> {
			entityManager.createNativeQuery("TRUNCATE TABLE " + table)
						 .executeUpdate();
			entityManager.createNativeQuery("ALTER TABLE " + table + " ALTER COLUMN id RESTART WITH 1")
						 .executeUpdate();
		});

		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE")
					 .executeUpdate();
	}
}
