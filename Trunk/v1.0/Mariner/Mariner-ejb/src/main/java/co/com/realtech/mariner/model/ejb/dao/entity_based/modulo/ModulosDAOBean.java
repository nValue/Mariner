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
            String query = "SELECT SYS_CONNECT_BY_PATH(MOD_ID, '-') PATH\n" +
                            "FROM mar_modulos WHERE MOD_ID IN(\n" +
                            "   SELECT RP.MOD_ID\n" +
                            "   FROM mar_roles_usuarios UR\n" +
                            "   INNER JOIN mar_roles_modulos RP ON UR.ROL_ID=RP.ROL_ID\n" +
                            "       WHERE UR.usu_id=:usuId)\n" +
                            "START WITH MOD_ID_PADRE IS NULL\n" +
                            "CONNECT BY PRIOR MOD_ID=MOD_ID_PADRE ORDER BY mod_orden";
            Query q = getEntityManager().createNativeQuery(query);
            q.setParameter("usuId", usuario.getUsuId());
            List<String> paths = (List<String>) q.getResultList();
            paths.stream().map((path) -> path.split("-")).forEach((elements) -> {
                for (String element : elements) {
                    if (!element.isEmpty()) {
                        MarModulos singleMod = getEntityManager().find(MarModulos.class, new BigDecimal(element));
                        modelTree.walkArray(modulos, singleMod, 0);
                    }
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
