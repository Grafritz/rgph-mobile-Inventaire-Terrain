package ht.ihsi.inventaireterrain.Models;

import ht.ihsi.inventaireterrain.Constant.Constant;

/**
 * Created by JFDuverseau on 7/14/2016.
 */
public class InventaireModel extends BaseModel {
    private Long IdInventaire;
    private String TypeInventaire;
    private String DepartementId;
    private String CommuneId;
    private String VqseId;
    private String CodeSDE;
    private String NumeroOdreSDE;
    private String NomEtPrenomCartographe;
    private String NomEtPrenomSuperviseur;
    private Boolean NePlusAfficherCetteFenetre;
    private Boolean isValidated;
    private Boolean isSynchroToAppSup;
    private Boolean isSynchroToCentrale;
    private String dateDebutCollecte;
    private String dateFinCollecte;

    private boolean IsConfigured;

    public Long getIdInventaire() {
        return IdInventaire;
    }

    public void setIdInventaire(Long IdInventaire) {
        this.IdInventaire = IdInventaire;
    }

    public String getTypeInventaire() {
        return TypeInventaire;
    }

    public void setTypeInventaire(String typeInventaire) {
        TypeInventaire = typeInventaire;
    }

    public String getDepartementId() {
        return DepartementId;
    }

    public void setDepartementId(String DepartementId) {
        this.DepartementId = DepartementId;
    }

    public String getCommuneId() {
        return CommuneId;
    }

    public void setCommuneId(String CommuneId) {
        this.CommuneId = CommuneId;
    }

    public String getVqseId() {
        return VqseId;
    }

    public void setVqseId(String VqseId) {
        this.VqseId = VqseId;
    }

    public String getCodeSDE() {
        return CodeSDE;
    }

    public void setCodeSDE(String CodeSDE) {
        this.CodeSDE = CodeSDE;
    }

    public String getNumeroOdreSDE() {
        return NumeroOdreSDE;
    }

    public void setNumeroOdreSDE(String NumeroOdreSDE) {
        this.NumeroOdreSDE = NumeroOdreSDE;
    }

    public String getNomEtPrenomCartographe() {
        return NomEtPrenomCartographe;
    }

    public void setNomEtPrenomCartographe(String NomEtPrenomCartographe) {
        this.NomEtPrenomCartographe = NomEtPrenomCartographe;
    }

    public String getNomEtPrenomSuperviseur() {
        return NomEtPrenomSuperviseur;
    }

    public void setNomEtPrenomSuperviseur(String NomEtPrenomSuperviseur) {
        this.NomEtPrenomSuperviseur = NomEtPrenomSuperviseur;
    }

    public Boolean getNePlusAfficherCetteFenetre() {
        return NePlusAfficherCetteFenetre;
    }

    public void setNePlusAfficherCetteFenetre(Boolean NePlusAfficherCetteFenetre) {
        this.NePlusAfficherCetteFenetre = NePlusAfficherCetteFenetre;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public Boolean getIsSynchroToAppSup() {
        return isSynchroToAppSup;
    }

    public void setIsSynchroToAppSup(Boolean isSynchroToAppSup) {
        this.isSynchroToAppSup = isSynchroToAppSup;
    }

    public Boolean getIsSynchroToCentrale() {
        return isSynchroToCentrale;
    }

    public void setIsSynchroToCentrale(Boolean isSynchroToCentrale) {
        this.isSynchroToCentrale = isSynchroToCentrale;
    }

    public String getDateDebutCollecte() {
        return dateDebutCollecte;
    }

    public void setDateDebutCollecte(String dateDebutCollecte) {
        this.dateDebutCollecte = dateDebutCollecte;
    }

    public String getDateFinCollecte() {
        return dateFinCollecte;
    }

    public void setDateFinCollecte(String dateFinCollecte) {
        this.dateFinCollecte = dateFinCollecte;
    }


    public boolean getIsConfigured() {
        return IsConfigured;
    }

    public void setIsConfigured(boolean isConfigured) {
        IsConfigured = isConfigured;
    }
}
