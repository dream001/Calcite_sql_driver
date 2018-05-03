package com.yonyou.calcite;


import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.linq4j.tree.Types;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.TableFunction;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.schema.impl.TableFunctionImpl;

import com.yonyou.util.ProUtils;

public class CalciteObjectQuery {

    public static final Method MAZE_METHOD =
            Types.lookupMethod(MazeTable.class, "generate", int.class, int.class, int.class);
    public static final Method SOLVE_METHOD =
            Types.lookupMethod(MazeTable.class, "solve", int.class, int.class, int.class);


    public static String checkMazeTableFunction(Boolean solution, String maze)
            throws SQLException, ClassNotFoundException {

        Connection connection = DriverManager.getConnection(ProUtils.getProperty("calcite.url"));
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        SchemaPlus schema = rootSchema.add("s", new AbstractSchema());
        TableFunction table = TableFunctionImpl.create(MAZE_METHOD);
        schema.add("Maze", table);
        TableFunction table2 = TableFunctionImpl.create(SOLVE_METHOD);
        schema.add("Solve", table2);
        String sql;
        if (solution) {
            sql = "select *\n" + "from table(\"s\".\"Solve\"(5, 3, 1)) as t(s)";
        } else {
            sql = "select *\n" + "from table(\"s\".\"Maze\"(5, 3, 1)) as t(s)";
        }
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        StringBuilder b = new StringBuilder();
        while (resultSet.next()) {
            b.append(resultSet.getString(1)).append("\n");
        }
        return b.toString();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String maze = "";
        System.out.println(checkMazeTableFunction(false, maze));

        System.out.println("----------------------------------------------------------------");
        System.out.println(checkMazeTableFunction(true, maze));
    }
}
