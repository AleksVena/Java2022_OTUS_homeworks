package kz.alseco.jdbc.mapper;

import kz.alseco.core.repository.DataTemplate;
import kz.alseco.core.repository.DataTemplateException;
import kz.alseco.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    private List getFieldValues(T object, List<Field> fields) {
        return fields.stream()
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Field is unavailable");
                    }
                })
                .collect(toList());
    }

    private T mapToObject(ResultSet resultSet) throws SQLException {
        List<Object> entityFields = new ArrayList<>();
        long idValue = resultSet.getLong(entityClassMetaData.getIdField().getName());
        entityFields.add(idValue);
        entityClassMetaData.getFieldsWithoutId().forEach(
                f -> {
                    try {
                        entityFields.add(resultSet.getObject(f.getName()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        try {
            return entityClassMetaData.getConstructor().newInstance(entityFields.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return mapToObject(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var list = new ArrayList<T>();
            try {
                while (rs.next()) {
                    list.add(mapToObject(rs));
                }
                return list;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public void update(Connection connection, T object) {
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), getFieldValues(object, entityClassMetaData.getAllFields()));
    }

    @Override
    public long insert(Connection connection, T object) {
        return dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(),
                getFieldValues(object, entityClassMetaData.getFieldsWithoutId())
        );
    }
}