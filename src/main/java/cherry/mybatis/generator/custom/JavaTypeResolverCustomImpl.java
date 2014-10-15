/*
 * Copyright 2014 agwlvssainokuni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cherry.mybatis.generator.custom;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class JavaTypeResolverCustomImpl extends JavaTypeResolverDefaultImpl {

	private static final String PROP_USE_JODA_TIME = "useJodaTime";

	private static final String PROP_JAVA_TYPE_BY_COLUMN_NAME_PREFIX = "javaTypeByColumnName.";

	private Map<String, FullyQualifiedJavaType> javaTypeByColumnName = new HashMap<String, FullyQualifiedJavaType>();

	@Override
	public void addConfigurationProperties(Properties properties) {
		super.addConfigurationProperties(properties);

		if ("true".equalsIgnoreCase(properties.getProperty(PROP_USE_JODA_TIME))) {
			typeMap.put(Types.DATE, new JdbcTypeInformation("DATE",
					new FullyQualifiedJavaType(LocalDate.class.getName())));
			typeMap.put(Types.TIME, new JdbcTypeInformation("TIME",
					new FullyQualifiedJavaType(LocalTime.class.getName())));
			typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP",
					new FullyQualifiedJavaType(LocalDateTime.class.getName())));
		}

		for (String propName : properties.stringPropertyNames()) {
			if (propName.startsWith(PROP_JAVA_TYPE_BY_COLUMN_NAME_PREFIX)) {
				String columnName = propName
						.substring(PROP_JAVA_TYPE_BY_COLUMN_NAME_PREFIX
								.length());
				String propValue = properties.getProperty(propName);
				javaTypeByColumnName.put(columnName,
						new FullyQualifiedJavaType(propValue));
			}
		}
	}

	@Override
	public FullyQualifiedJavaType calculateJavaType(
			IntrospectedColumn introspectedColumn) {
		String columnName = introspectedColumn.getActualColumnName();
		if (javaTypeByColumnName.containsKey(columnName)) {
			return javaTypeByColumnName.get(columnName);
		}
		return super.calculateJavaType(introspectedColumn);
	}

}
