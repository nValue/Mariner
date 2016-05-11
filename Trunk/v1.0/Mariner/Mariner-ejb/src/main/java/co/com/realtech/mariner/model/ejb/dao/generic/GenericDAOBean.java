package co.com.realtech.mariner.model.ejb.dao.generic;

import co.com.realtech.mariner.util.excel.AliasToEntityOrderedMapResultTransformer;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * Controlador JPA Generico.
 *
 * @author Andres Rivera
 * @since JDK1.7
 * @version 1.0
 */
@Stateless(name = "GenericDAOBean")
public class GenericDAOBean implements GenericDAOBeanLocal {

    @PersistenceContext(unitName = "MarinerPU")
    private EntityManager entityManager;

    /**
     * Guardado generico.
     *
     * @param obj
     * @throws MarinerPersistanceException
     */
    @Override
    public void save(Object obj) throws MarinerPersistanceException {
        try {
            getEntityManager().persist(obj);
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
    }

    /**
     * Guardado retornando el id generado
     *
     * @param obj
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object saveReturningId(Object obj) throws MarinerPersistanceException {
        try {
            getEntityManager().persist(obj);
            getEntityManager().refresh(obj);
            return obj;
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
    }

    /**
     * Actualizacion del objeto.
     *
     * @param obj
     * @throws MarinerPersistanceException
     */
    @Override
    public void merge(Object obj) throws MarinerPersistanceException {
        try {
            getEntityManager().merge(obj);
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
    }

    /**
     * Proceso de actualizacion del objecto
     *
     * @param obj
     * @throws MarinerPersistanceException
     */
    @Override
    public void update(Object obj) throws MarinerPersistanceException {
        try {
            getEntityManager().refresh(obj);
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
    }

    /**
     * Borrado de objeto ingresado
     *
     * @param obj
     * @param pk
     * @throws MarinerPersistanceException
     */
    @Override
    public void delete(Object obj, Object pk) throws MarinerPersistanceException {
        try {
            Object objC = getEntityManager().find(obj.getClass(), pk);
            getEntityManager().remove(objC);
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
    }

    /**
     * Proceso de persistencia para varios registros
     *
     * @param objects
     * @throws MarinerPersistanceException
     */
    @Override
    public void persistBatch(List<? extends Object> objects) throws MarinerPersistanceException {
        try {
            for (Object o : objects) {
                getEntityManager().persist(o);
            }
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
    }

    /**
     * Proceso merge para varios registros
     *
     * @param objects
     * @throws MarinerPersistanceException
     */
    @Override
    public void mergeBatch(List<?> objects) throws MarinerPersistanceException {
        try {
            for (Object o : objects) {
                getEntityManager().merge(o);
            }
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
    }

    /**
     * Retorna el elemento asociado a la llave primaria.
     *
     * @param clazz
     * @param pk
     * @return
     * @throws co.com.realtech.mariner.util.exceptions.MarinerPersistanceException
     
     */
    @Override
    public Object findByID(Class clazz, Object pk) throws MarinerPersistanceException {
        Object obj = null;
        try {
            obj = getEntityManager().find(clazz, pk);
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return obj;
    }

    /**
     * Retorna el elemento asociado a la clase y con la columna igual al objecto
     * ingresado.
     *
     * @param clazz
     * @param column
     * @param object
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object findByColumn(Class clazz, String column, Object object) throws MarinerPersistanceException {
        Object obj = null;
        try {
            Query q = getEntityManager().createQuery("from " + clazz.getName() + " as p where p." + column + "=:valorQuery");
            q.setParameter("valorQuery", object);
            q.setMaxResults(1);
            obj = q.getSingleResult();
        } catch (NoResultException e) {
            obj = null;
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return obj;
    }

    /**
     * Retorna el elemento asociado a la clase y con la columna igual al objecto
     * ingresado con comparacion UPPER en los valores
     *
     * @param clazz
     * @param column
     * @param object
     * @param upperComparision
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object findByColumn(Class clazz, String column, Object object, Boolean upperComparision) throws MarinerPersistanceException {
        Object obj = null;
        try {
            Query q;

            if (!upperComparision) {
                q = getEntityManager().createQuery("from " + clazz.getName() + " as p where p." + column + "=:valorQuery");
            } else {
                q = getEntityManager().createQuery("from " + clazz.getName() + " as p where upper(p." + column + ")=upper(:valorQuery)");
            }
            q.setParameter("valorQuery", object);
            q.setMaxResults(1);
            obj = q.getSingleResult();
        } catch (NoResultException e) {
            obj = null;
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return obj;
    }

    /**
     * Retorna todos los registros asociados a la condicion ingresada.
     *
     * @param clazz
     * @param column
     * @param object
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object findAllByColumn(Class clazz, String column, Object object) throws MarinerPersistanceException {
        Object obj = null;
        try {
            Query q = getEntityManager().createQuery("from " + clazz.getName() + " as p where p." + column + "=:valorQuery");
            q.setParameter("valorQuery", object);
            obj = q.getResultList();
        } catch (NoResultException e) {
            obj = null;
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return obj;
    }

    /**
     * Retorna todos los registros asociados a la condicion ingresada.
     *
     * @param clazz
     * @param column
     * @param object
     * @param equals
     * @param order
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object findAllByColumn(Class clazz, String column, Object object, boolean equals, String order) throws MarinerPersistanceException {
        Object obj = null;
        try {
            String queryText = "from " + clazz.getName() + " as p where p." + column + (equals ? "=" : "!=") + ":valorQuery";

            if (order != null && (!order.equals(""))) {
                queryText += " order by p." + order;
            }
            Query q = getEntityManager().createQuery(queryText);
            q.setParameter("valorQuery", object);
            obj = q.getResultList();
        } catch (NoResultException e) {
            obj = null;
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return obj;
    }
    /**
     * Retorna todos los registros asociados a la condicion ingresada y con limite de registros.
     * @param clazz
     * @param column
     * @param object
     * @param equals
     * @param order
     * @param limit
     * @return
     * @throws MarinerPersistanceException 
     */
    public Object findAllByColumn(Class clazz, String column, Object object, boolean equals, String order,int limit) throws MarinerPersistanceException {
        Object obj = null;
        try {
            String queryText = "from " + clazz.getName() + " as p where p." + column + (equals ? "=" : "!=") + ":valorQuery";

            if (order != null && (!order.equals(""))) {
                queryText += " order by p." + order;
            }
            Query q = getEntityManager().createQuery(queryText);
            q.setParameter("valorQuery", object);
            q.setMaxResults(limit);
            obj = q.getResultList();
        } catch (NoResultException e) {
            obj = null;
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return obj;
    }

    /**
     * Retorna los elementos de la entidad asociados a la columna con
     * comparacion Like o Not Like
     *
     * @param clazz
     * @param column
     * @param isLike
     * @param order
     * @param likes
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object findAllByColumnLike(Class clazz, String column, boolean isLike, String order, String... likes) throws MarinerPersistanceException {
        Object obj = null;
        try {
            String queryText = "from " + clazz.getName() + " as p  " + (likes.length > 0 ? " where " : " ");

            for (String li : likes) {
                queryText += "upper(p." + column + (isLike ? ") like " : "not like") + " upper('%" + li + "%') and ";
            }

            if (likes.length > 0) {
                queryText = queryText.substring(0, queryText.length() - 4);
            }

            if (order != null && (!order.equals(""))) {
                queryText += " order by p." + order;
            }
            Query q = getEntityManager().createQuery(queryText);
            obj = q.getResultList();
        } catch (NoResultException e) {
            obj = null;
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return obj;
    }

    /**
     * Retorna los elementos de la entidad asociados a la columna con
     * comparacion Like o Not Like
     *
     * @param clazz
     * @param column
     * @param isLike
     * @param isAnd
     * @param order
     * @param likes
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object findAllByColumnLike(Class clazz, String column, boolean isLike, boolean isAnd, String order, List<? extends Object> likes) throws MarinerPersistanceException {
        Object obj = null;
        try {
            String queryText = "from " + clazz.getName() + " as p  " + (likes.isEmpty() ? "" : "where ");

            for (Object li : likes) {
                queryText += "upper(p." + column + (isLike ? ") like " : "not like") + " upper('%" + li + "%')  " + (isAnd ? " and " : " or  ");
            }

            if (!likes.isEmpty()) {
                queryText = queryText.substring(0, queryText.length() - 4);
            }

            if (order != null && (!order.equals(""))) {
                queryText += " order by p." + order;
            }
            Query q = getEntityManager().createQuery(queryText);
            obj = q.getResultList();
        } catch (NoResultException e) {
            obj = null;
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return obj;
    }

    /**
     * Retorna todos los elementos de la clase ingresada
     *
     * @param clazz
     * @param orderBy
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public List<? extends Object> loadAllForEntity(Class clazz, String orderBy) throws MarinerPersistanceException {
        List<? extends Object> objects = null;
        try {
            String sql = "from " + clazz.getName() + (orderBy != null ? " order by " + orderBy : "");
            Query q = getEntityManager().createQuery(sql);
            objects = q.getResultList();
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return objects;
    }

    /**
     * Retorna el siguiente valor asociado a la secuencia ingresada
     *
     * @param sqName
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public BigInteger loadNextValSQ(String sqName) throws MarinerPersistanceException {
        BigInteger val = null;
        try {
            Query q = getEntityManager().createNativeQuery("select nextval('" + sqName + "') seq");
            val = (BigInteger) q.getSingleResult();
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return val;
    }

    /**
     * Realiza el llamado dinamico a funciones en base de datos.
     *
     * @param fnName
     * @param parameters
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object callGenericFunction(String fnName, Object... parameters) throws MarinerPersistanceException {
        Object val = null;
        try {
            String sqlFnQuery = "SELECT " + fnName + (parameters.length > 0 ? " (" : " ");
            for (Object obj : parameters) {
                sqlFnQuery += "?,";
            }
            sqlFnQuery = sqlFnQuery.substring(0, sqlFnQuery.length() - 1);

            sqlFnQuery += (parameters.length > 0 ? " )" : " ") + " FROM DUAL";

            Query q = getEntityManager().createNativeQuery(sqlFnQuery);

            int paramCounter = 1;
            for (Object obj : parameters) {
                q.setParameter(paramCounter, obj);
                paramCounter++;
            }
            val = q.getSingleResult();
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return val;
    }
    /**
     * Ejecucion de procedimiento almacenado sin parametros de salida
     * @param pcName
     * @param parameters
     * @throws MarinerPersistanceException 
     */
    @Override
    public void callGenericProcedure(String pcName, Object... parameters) throws MarinerPersistanceException {
        try {
            String sqlFnQuery = "{ CALL " + pcName + (parameters.length > 0 ? " (" : " ");
            for (Object obj : parameters) {
                sqlFnQuery += "?,";
            }
            sqlFnQuery = sqlFnQuery.substring(0, sqlFnQuery.length() - 1);
            sqlFnQuery += (parameters.length > 0 ? " )" : " ") + " }";

            Query q = getEntityManager().createNativeQuery(sqlFnQuery);
            
            int paramCounter = 1;
            for (Object obj : parameters) {
                q.setParameter(paramCounter, obj);
                paramCounter++;
            }
            q.executeUpdate();
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
    }
    
    /**
     * Ejecución de Query Nativo, la sentencia ya debe tener todos los parametros
     * inicializados.
     *
     * @param query
     * @param singleResult
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object executeNativeQuery(String query, boolean singleResult) throws MarinerPersistanceException {
        Object val = null;
        try {
            String sql = query;
            Query q = getEntityManager().createNativeQuery(sql);
            if (singleResult) {
                val = q.getSingleResult();
            } else {
                val = q.getResultList();
            }
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return val;
    }
    
    /**
     * Obtiene los nombres de las columnas de un query nativo.
     * @param query
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<String> getColumnNamesFromQuery(String query) throws MarinerPersistanceException{
        List<String> columnas = new ArrayList<String>();
        try {
            Session session = entityManager.unwrap(Session.class);
            SQLQuery que = session.createSQLQuery(query);
            que.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
            que.setMaxResults(1);
            List<Map<String,Object>> aliasToValueMapList=que.list();
            if(aliasToValueMapList.isEmpty()){
                return columnas;
            }
            for (String valor : aliasToValueMapList.get(0).keySet()) {
                columnas.add(valor);
            }
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return columnas;
    }
    
    /**
     * Ejecucui de Query Nativo, la sentencia ya debe tener todos los parametros
     * inicializados.
     *
     * @param query
     * @param clazz
     * @param singleResult
     * @return
     * @throws MarinerPersistanceException
     */
    @Override
    public Object executeNativeQuery(String query,Class clazz, boolean singleResult) throws MarinerPersistanceException {
        Object val = null;
        try {
            String sql = query;
            Query q = getEntityManager().createNativeQuery(sql,clazz);
            if (singleResult) {
                val = q.getSingleResult();
            } else {
                val = q.getResultList();
            }
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return val;
    }

    /**
     * Extraccion de conexion JDBC del contexto JPA.
     
     * @throws MarinerPersistanceException
     * @return
     
     */
    @Override
    public Connection extractJDBCConnection() throws MarinerPersistanceException {
        Connection con = null;
        try {
            DataSource ds = (DataSource) getEntityManager().getEntityManagerFactory().getProperties().get("javax.persistence.jtaDataSource");
            con = ds.getConnection();
        } catch (SQLException e) {
            throw new MarinerPersistanceException(e);
        }
        return con;
    }

    /**
     * Retorna el EntityManager inyectado.
     *
     * @return
     */
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    /**
     * Obtiene un arreglo de objetos con los nombres de las columnas de una tabla en específico (Solo ORACLE)
     * @param tableName
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<Object> getColumnNamesFromTable(String tableName) throws MarinerPersistanceException{
        List<Object> columnNames = null;
        try {
            Query q = entityManager.createNativeQuery("SELECT column_name FROM user_tab_cols WHERE table_name = '" + tableName.toUpperCase() + "' ORDER BY column_id");
            columnNames = q.getResultList();
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return columnNames;
    }
    
    /**
     * Obtiene una lista de objetos de una entidad mediante la estrategia lazy, si se requiere ir a varias entidades a 
     * traer sus registros deben colocarse en entidades tantas como se quieran, si no se necesita el WHERE se puede dejar nulo
     * @param entidadLazy
     * @param column
     * @param object
     * @param entidades
     * @return 
     * @throws co.com.realtech.mariner.util.exceptions.MarinerPersistanceException 
     */
    @Override
    public Object getLoadLazyEntityWithDepth(String entidadLazy, String column, Object object, String... entidades) throws MarinerPersistanceException{
        List<Object> valores = null;
        try {
            String sql = "FROM " + entidadLazy + " ent ";
            for (String entidad : entidades) {
                sql = sql + " JOIN FETCH ent." + entidad  + " \n";
            }
            sql = sql + " WHERE 1 = 1 ";
            if(column != null){
                sql = sql + " AND ent." + column + " = " + object + "\n";
            }
            Query q = entityManager.createQuery(sql);
            valores = q.getResultList();
        } catch (Exception e) {
            throw new MarinerPersistanceException(e);
        }
        return valores;
    }
    
}
