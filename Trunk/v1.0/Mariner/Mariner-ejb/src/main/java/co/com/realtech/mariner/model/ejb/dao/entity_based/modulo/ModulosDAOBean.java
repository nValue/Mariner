/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.ejb.dao.entity_based.modulo;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarModulos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.tree.ModelTreeUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Bean de sessión encargado de traer toda la información concerniente a los
 * módulos de la aplicación
 *
 * @author fabianagudelo
 */
@Stateless
public class ModulosDAOBean extends GenericDAOBean implements ModulosDAOBeanLocal {

    /**
     * Obtiene los módulos asociados a un usuario.
     *
     * @param usuario
     * @return
     * @throws Exception
     */
    @Override
    public List<MarModulos> obtenerModulosDeUsuario(MarUsuarios usuario) throws Exception {
        List<MarModulos> modulos = new ArrayList<>();
        ModelTreeUtil modelTree = new ModelTreeUtil();
        try {
            String query = "WITH RECURSIVE tree \n"
                    + "AS \n"
                    + "(\n"
                    + "    SELECT \n"
                    + "        mod_id, mod_id_padre, mod_id||'' AS path, mod_orden \n"
                    + "    FROM mar_modulos WHERE mod_id_padre IS NULL\n"
                    + "    UNION\n"
                    + "    SELECT \n"
                    + "       f1.mod_id, f1.mod_id_padre, tree.path || '-' || f1.mod_id AS path,f1.mod_orden \n"
                    + "    FROM \n"
                    + "        tree \n"
                    + "    JOIN mar_modulos f1 ON f1.mod_id_padre = tree.mod_id\n"
                    + ") \n"
                    + "SELECT path FROM tree WHERE mod_id in (\n"
                    + "	SELECT m.mod_id FROM mar_modulos m \n"
                    + "    INNER JOIN mar_roles_modulos ro ON ro.mod_id=m.mod_id\n"
                    + "    INNER JOIN mar_roles r ON r.rol_id=ro.rol_id\n"
                    + "    INNER JOIN mar_roles_usuarios ru ON ru.rol_id=r.rol_id WHERE ru.usu_id=:usuId ORDER BY m.mod_nombre ASC\n"
                    + ") ORDER BY mod_orden NULLS LAST";
            Query q = getEntityManager().createNativeQuery(query);
            q.setParameter("usuId", usuario.getUsuId());
            List<String> paths = (List<String>) q.getResultList();

            paths.stream().map((path) -> path.split("-")).forEach((elements) -> {
                for (String element : elements) {
                    MarModulos singleMod = getEntityManager().find(MarModulos.class, new BigDecimal(element));
                    modelTree.walkArray(modulos, singleMod, 0);
                }
            });
        } catch (Exception e) {
            throw e;
        }
        return modulos;
    }

    /**
     * Retorna los modulos vinculados al modulo padre
     *
     * @param modulo
     * @return
     * @throws Exception
     */
    @Override
    public List<MarModulos> loadModulesByParent(MarModulos modulo) throws Exception {
        List<MarModulos> modulos = new ArrayList<>();
        try {
            String sql = "from MarModulos as m where m.modIdPadre";
            if (modulo == null) {
                sql += " is null";
            } else {
                sql += "=:modulo";
            }
            sql += " order by m.modOrden asc";
            Query q = getEntityManager().createQuery(sql);
            if (modulo != null) {
                q.setParameter("modulo", modulo);
            }
            modulos = (List<MarModulos>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return modulos;
    }

    /**
     * Retorna los modulos a los que puede ser vinculado un rol.
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<MarModulos> loadAccesibleModules() throws Exception {
        List<MarModulos> modulos = new ArrayList<>();
        try {
            Query q = getEntityManager().createQuery("from MarModulos as m where m.modUrl!='#' order by m.modNombre asc");
            modulos = (List<MarModulos>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return modulos;
    }
}
