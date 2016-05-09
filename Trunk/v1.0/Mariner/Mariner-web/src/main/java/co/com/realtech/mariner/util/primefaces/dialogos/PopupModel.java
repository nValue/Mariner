package co.com.realtech.mariner.util.primefaces.dialogos;

import java.io.Serializable;
/**
 * Componente de contencion de valores en session modal generico
 * 
 * @author  Andres Rivera
 * @version 1.0
 * @since   JDK1.6
 */
public class PopupModel implements Serializable{
    
    private String title;
    private String text;
    private String effect;
    private boolean modal=true;
    private boolean usaI18N=true;
    
    public PopupModel(){
      this.effect="";
      this.text="";
      this.title="";
    }
    
    public PopupModel(String title,String text){
       this.text=text;
       this.title=title;
    }
    
    public PopupModel(String title,String text,String effect,boolean modal){
       this.text=text;
       this.title=title;
       this.modal=modal;
       this.effect=effect;
    }
    
    public PopupModel(String title,String text,String effect,boolean modal,boolean usai18n){
       this.text=text;
       this.title=title;
       this.modal=modal;
       this.effect=effect;
       this.usaI18N=usai18n;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean isModal() {
        return modal;
    }
    public void setModal(boolean modal) {
        this.modal = modal;
    }

    public String getEffect() {
        return effect;
    }
    public void setEffect(String effect) {
        this.effect = effect;
    }
    public boolean isUsaI18N() {
        return usaI18N;
    }
    public void setUsaI18N(boolean usaI18N) {
        this.usaI18N = usaI18N;
    }
}