package co.com.realtech.mariner.model.ejb.dao.generic;

import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * <interface>
 * Controlador JPA Generico.
 *
 * @author Andres Rivera
 * @since JDK1.7
 * @version 1.0
 */
public interface GenericDAOBeanLocal {

    public void save(Object obj) throws MarinerPersistanceException;

    public Object saveReturningId(Object obj) throws MarinerPersistanceException;

    public void merge(Object obj) throws MarinerPersistanceException;

    public void update(Object obj) throws MarinerPersistanceException;

    public void delete(Object obj, Object pk) throws MarinerPersistanceException;

    public void persistBatch(List<? extends Object> objects) throws MarinerPersistanceException;

    public void mergeBatch(List<?> objects) throws MarinerPersistanceException;

    public Object findByID(Class clazz, Object pk) throws MarinerPersistanceException;

    public Object findByColumn(Class clazz, String column, Object object) throws MarinerPersistanceException;

    public Object findByColumn(Class clazz, String column, Object object, Boolean upperComparision) throws MarinerPersistanceException;

    public Object findAllByColumn(Class clazz, String column, Object object) throws MarinerPersistanceException;

    public Object findAllByColumn(Class clazz, String column, Object object, boolean equals, String order) throws MarinerPersistanceException;

    public Object findAllByColumnLike(Class clazz, String column, boolean isLike, String order, String... likes) throws MarinerPersistanceException;

    public Object findAllByColumnLike(Class clazz, String column, boolean isLike, boolean isAnd, String order, List<? extends Object> likes) throws MarinerPersistanceException;

    public List<? extends Object> loadAllForEntity(Class clazz, String orderBy) throws MarinerPersistanceException;

    public BigInteger loadNextValSQ(String sqName) throws MarinerPersistanceException;

    public Object callGenericFunction(String fnName, Object... parameters) throws MarinerPersistanceException;
    
    public void callGenericProcedure(String pcName, Object... parameters) throws MarinerPersistanceException;
    
    public Object executeNativeQuery(String query, boolean singleResult) throws MarinerPersistanceException;
    
    public Object executeNativeQuery(String query,Class clazz, boolean singleResult) throws MarinerPersistanceException;
    
    public List<String> getColumnNamesFromQuery(String query) throws MarinerPersistanceException;

    public Connection extractJDBCConnection() throws MarinerPersistanceException;

    public EntityManager getEntityManager();
    
    public List<Object> getColumnNamesFromTable(String tableName) throws MarinerPersistanceException;
    
    public Object getLoadLazyEntityWithDepth(String entidadLazy, String column, Object object, String... entidades) throws MarinerPersistanceException;

}
