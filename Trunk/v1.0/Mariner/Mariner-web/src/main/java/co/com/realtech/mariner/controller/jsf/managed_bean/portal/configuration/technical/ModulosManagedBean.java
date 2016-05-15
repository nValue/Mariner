package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.modulo.ModulosDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarModulos;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * ManagedBean encargado de la administracion de modulos y menus.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class ModulosManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "ModulosDAOBean")
    protected ModulosDAOBeanLocal modulosDAOBean;

    private TreeNode root;
    private TreeNode selectedNode;

    private MarModulos modulo;
    private MarModulos moduloSeleccion;
    private List<MarModulos> modulos;

    @Override
    public void init() {
        try {
            setModulos(modulosDAOBean.loadModulesByParent(null));
            if (!getModulos().isEmpty()) {
                setModulo(getModulos().get(0));
            }
            inicializarArbol();
        } catch (Exception e) {
            logger.error("Error inicializando ModulosManagedBean, causado por " + e, e);
        }
    }

    /**
     * Inicializar arbol.
     */
    public void inicializarArbol() {
        try {
            setRoot(new DefaultTreeNode());

            // llenamos con el primer nivel
            int counter = 0;
            for (MarModulos mod : getModulos()) {
                TreeNode node = new DefaultTreeNode(mod, root);
                if (counter == 0) {
                    node.setSelected(true);
                    node.setExpanded(true);

                    for (MarModulos mod2 : modulosDAOBean.loadModulesByParent(mod)) {
                        TreeNode node2 = new DefaultTreeNode(mod2, node);
                    }
                }
                counter++;
            }
        } catch (Exception e) {
            logger.error("Error inicializando Arbol en ModulosManagedBean, causado por " + e, e);
        }
    }

    /**
     * Evento expacion de nodo.
     *
     * @param event
     */
    public void onNodeExpand(NodeSelectEvent event) {
        try {
            MarModulos modSelect = (MarModulos) event.getTreeNode().getData();
            event.getTreeNode().getChildren().clear();
            for (MarModulos mod2 : modulosDAOBean.loadModulesByParent(modSelect)) {
                TreeNode node = new DefaultTreeNode(mod2, event.getTreeNode());
            }
            event.getTreeNode().setExpanded(true);
            setModulos(modulosDAOBean.loadModulesByParent(modSelect));
        } catch (Exception ex) {
            logger.error("Error Expandiendo Arbol en Modulos ManagedBean, causado por " + ex, ex);
        }
    }

    /**
     * Seleccion de modulo contexto
     */
    public void seleccionarModulo() {
        MarModulos modSelect = (MarModulos) getSelectedNode().getData();
        setModulo(modSelect);
    }

    /**
     * Seleccion de modulo de la tabla principal.
     */
    public void seleccionarModuloTabla() {
        setModulo(getModuloSeleccion());
    }

    /**
     * Agregar nuevo nodo Raiz.
     */
    public void nuevoNodoRaiz() {
        setModulo(new MarModulos());
    }

    /**
     * Nuevo Nodo Hijo.
     */
    public void nuevoNodo() {
        setModulo(new MarModulos());
        getModulo().setModIdPadre((MarModulos) getSelectedNode().getData());
    }

    /**
     * Refrescar estructuras.
     *
     * @param isRaiz
     * @param isContext
     */
    public void refrescarEstructuras(boolean isRaiz, boolean isContext) {
        try {
            if (isRaiz || isContext) {
                init();
            } else {
                MarModulos modSelect = (MarModulos) getSelectedNode().getData();
                setModulos(modulosDAOBean.loadModulesByParent(modSelect));

                getSelectedNode().getChildren().clear();
                for (MarModulos md : getModulos()) {
                    TreeNode node = new DefaultTreeNode(md, getSelectedNode());
                }
                getSelectedNode().setExpanded(true);
                setModuloSeleccion(getModulo());
            }
        } catch (Exception e) {
            logger.error("Error Refrescando estrucutras de Modulos, causado por " + e, e);
        }
    }

    /**
     * Borrado de Modulo seleccionado.
     */
    public void eliminarModulo() {
        try {
            genericDAOBean.delete(getModuloSeleccion(), getModuloSeleccion().getModId());
            refrescarEstructuras(false, false);
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Informacion de Modulo Eliminada Correctamente.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error.!", "No se puede eliminar el Modulo, por favor verifique que no contenga informacion asociada e intente de nuevo", true, false);
            logger.error("Error Eliminado modulo seleccionado, causado por " + e, e);
        }
    }

    /**
     * Borrado de modulo mediante metodo de Contexto
     */
    public void eliminarModuloContext() {
        try {
            MarModulos modulo = (MarModulos) getSelectedNode().getData();
            genericDAOBean.delete(modulo, modulo.getModId());
            refrescarEstructuras(modulo.getModIdPadre() == null, true);
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Informacion de Modulo Eliminada Correctamente.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error.!", "No se puede eliminar el Modulo, por favor verifique que no contenga informacion asociada e intente de nuevo", true, false);
            logger.error("Error Eliminado modulo seleccionado, causado por " + e, e);
        }
    }

    /**
     * Guardado de modulo en edicion.
     */
    public void guardarModulo() {
        try {
            auditSessionUtils.setAuditReflectedValues(getModulo());

            if (getModulo().getModId() == null) {
                genericDAOBean.save(getModulo());
            } else {
                genericDAOBean.merge(getModulo());
            }
            refrescarEstructuras(getModulo().getModIdPadre() == null, false);
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Informacion de Modulo almacenada Correctamente.", true, false);
            PrimeFacesContext.execute("PF('moduloDialog').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error.!", "Error interno intentando almacenar la informacion del modulo, causado por " + e, true, false);
            logger.error("Error Guardando Modulo, causado por " + e, e);
        }
    }

    public MarModulos getModulo() {
        return modulo;
    }

    public void setModulo(MarModulos modulo) {
        this.modulo = modulo;
    }

    public List<MarModulos> getModulos() {
        return modulos;
    }

    public void setModulos(List<MarModulos> modulos) {
        this.modulos = modulos;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public MarModulos getModuloSeleccion() {
        return moduloSeleccion;
    }

    public void setModuloSeleccion(MarModulos moduloSeleccion) {
        this.moduloSeleccion = moduloSeleccion;
    }

}
