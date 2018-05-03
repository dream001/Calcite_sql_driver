package com.yonyou.optiq;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eigenbase.rel.RelNode;
import org.eigenbase.relopt.RelOptTable;
import org.eigenbase.relopt.RelOptTable.ToRelContext;
import org.eigenbase.reltype.RelDataType;
import org.eigenbase.reltype.RelDataTypeFactory;
import org.eigenbase.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.hydromatic.linq4j.Enumerator;
import net.hydromatic.linq4j.QueryProvider;
import net.hydromatic.linq4j.Queryable;
import net.hydromatic.optiq.SchemaPlus;
import net.hydromatic.optiq.TranslatableTable;
import net.hydromatic.optiq.impl.AbstractTableQueryable;
import net.hydromatic.optiq.impl.java.AbstractQueryableTable;
import net.hydromatic.optiq.rules.java.EnumerableConvention;
import net.hydromatic.optiq.rules.java.JavaRules;

/**
 * 
 * @ClassName: JavaBeanTable
 * @Description: TODO(类简要描述，必须以句号为结束)
 * @author caozq
 * @date 2018年4月27日
 * @param <E>
 */
public class JavaBeanTable<E> extends AbstractQueryableTable implements
    TranslatableTable {

  static final Logger logger = LoggerFactory.getLogger(JavaBeanTable.class);
  private List<E> javaBeanList;

  /**
   * Constructor
   * 
   * @param javaBeanList
   *          A JavaBean List
   */
  public JavaBeanTable(List<E> javaBeanList) {
    super(Object[].class);
    this.javaBeanList = javaBeanList;
  }

  public RelDataType getRowType(RelDataTypeFactory typeFactory) {
    List<String> names = new ArrayList<String>();
    List<RelDataType> types = new ArrayList<RelDataType>();
    if ((javaBeanList != null) && (javaBeanList.size() > 0)) {
      Class sample = javaBeanList.get(0).getClass();
      Method[] methods = sample.getMethods();
      for (Method method : methods) {
        if (JavaBeanInspector.checkMethodEligiblity(method)) {
          String name = method.getName().substring(3);
          Class type = method.getReturnType();
          names.add(name);
          types.add(typeFactory.createJavaType(type));
          logger.info("Added field name: " + name + " of type: "
              + type.getSimpleName());
        }
      }
    }
    return typeFactory.createStructType(Pair.zip(names, types));
  }

  public <T> Queryable<T> asQueryable(QueryProvider queryProvider,
      SchemaPlus schema, String tableName) {
    logger.info("Got query request for: " + tableName);
    return new AbstractTableQueryable<T>(queryProvider, schema, this, tableName) {
      public Enumerator<T> enumerator() {
        // noinspection unchecked
        try {
          JavaBeanEnumerator enumerator = new JavaBeanEnumerator(javaBeanList);
          return (Enumerator<T>) enumerator;
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    };
  }

  public RelNode toRel(ToRelContext context, RelOptTable relOptTable) {
    return new JavaRules.EnumerableTableAccessRel(context.getCluster(), context
        .getCluster().traitSetOf(EnumerableConvention.INSTANCE), relOptTable,
        (Class) getElementType());
  }

}
