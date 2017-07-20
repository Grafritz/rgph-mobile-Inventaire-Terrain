package ht.ihsi.inventaireterrain.Models;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public class LogementModel extends BaseModel  {
    private Long IdLogement;
    private Long BatimentId;
    private Integer NumeroLogement;
    private String NomCompletChefMenage;
    private String PhoneChefMenage;
    private Integer NbrHommeVivant;
    private Integer NbrFemmeVivant;
    private String Remarques;

    public Long getIdLogement() {
        return IdLogement;
    }

    public void setIdLogement(Long IdLogement) {
        this.IdLogement = IdLogement;
    }

    public Long getBatimentId() {
        return BatimentId;
    }

    public void setBatimentId(Long BatimentId) {
        this.BatimentId = BatimentId;
    }

    public Integer getNumeroLogement() {
        return NumeroLogement;
    }

    public void setNumeroLogement(Integer NumeroLogement) {
        this.NumeroLogement = NumeroLogement;
    }

    public String getNomCompletChefMenage() {
        return NomCompletChefMenage;
    }

    public void setNomCompletChefMenage(String NomCompletChefMenage) {
        this.NomCompletChefMenage = NomCompletChefMenage;
    }

    public String getPhoneChefMenage() {
        return PhoneChefMenage;
    }

    public void setPhoneChefMenage(String PhoneChefMenage) {
        this.PhoneChefMenage = PhoneChefMenage;
    }

    public Integer getNbrHommeVivant() {
        return NbrHommeVivant;
    }

    public void setNbrHommeVivant(Integer NbrHommeVivant) {
        this.NbrHommeVivant = NbrHommeVivant;
    }

    public Integer getNbrFemmeVivant() {
        return NbrFemmeVivant;
    }

    public void setNbrFemmeVivant(Integer NbrFemmeVivant) {
        this.NbrFemmeVivant = NbrFemmeVivant;
    }

    public String getRemarques() {
        return Remarques;
    }

    public void setRemarques(String Remarques) {
        this.Remarques = Remarques;
    }
}
