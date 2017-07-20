package ht.ihsi.inventaireterrain.Models;

/**
 * Created by JFDuverseau on 7/11/2016.
 */
public class BatimentModel  extends BaseModel {

    private Long IdBatiment;
    private Long InventaireId;
    private Integer NoBatiment;
    private String CodeSDE;
    private String NumOrdreSDE;
    private String TypeInventaire;
    private String AdrBat_HabitationAncienNom;
    private String AdrBat_HabitationNouveauNom;
    private String AdrBat_LocaliteAncienNom;
    private String AdrBat_LocaliteNouveauNom;
    private String Longitude;
    private String Latitude;
    private String TypeBatiment;
    private String EtatActuel;
    private Integer NbrEtage;
    private String UsageBatiment;
    private Integer NbrLogement;
    private String DepartementId;
    private String communeId;
    private String VqseId;
    private String NomInstitution;
    private String PhoneInstitution;
    private String Remarques;
    private Boolean isValidated;
    private Boolean isSynchroToAppSup;
    private Boolean isSynchroToCentrale;
    private String dateDebutCollecte;
    private String dateFinCollecte;

    public BatimentModel() {
    }

    public Long getIdBatiment() {
        return IdBatiment;
    }

    public void setIdBatiment(Long IdBatiment) {
        this.IdBatiment = IdBatiment;
    }

    public Long getInventaireId() {
        return InventaireId;
    }

    public void setInventaireId(Long InventaireId) {
        this.InventaireId = InventaireId;
    }

    public Integer getNoBatiment() {
        return NoBatiment;
    }

    public void setNoBatiment(Integer NoBatiment) {
        this.NoBatiment = NoBatiment;
    }

    public String getCodeSDE() {
        return CodeSDE;
    }

    public void setCodeSDE(String CodeSDE) {
        this.CodeSDE = CodeSDE;
    }

    public String getNumOrdreSDE() {
        return NumOrdreSDE;
    }

    public void setNumOrdreSDE(String NumOrdreSDE) {
        this.NumOrdreSDE = NumOrdreSDE;
    }

    public String getTypeInventaire() {
        return TypeInventaire;
    }

    public void setTypeInventaire(String TypeInventaire) {
        this.TypeInventaire = TypeInventaire;
    }

    public String getAdrBat_HabitationAncienNom() {
        return AdrBat_HabitationAncienNom;
    }

    public void setAdrBat_HabitationAncienNom(String AdrBat_HabitationAncienNom) {
        this.AdrBat_HabitationAncienNom = AdrBat_HabitationAncienNom;
    }

    public String getAdrBat_HabitationNouveauNom() {
        return AdrBat_HabitationNouveauNom;
    }

    public void setAdrBat_HabitationNouveauNom(String AdrBat_HabitationNouveauNom) {
        this.AdrBat_HabitationNouveauNom = AdrBat_HabitationNouveauNom;
    }

    public String getAdrBat_LocaliteAncienNom() {
        return AdrBat_LocaliteAncienNom;
    }

    public void setAdrBat_LocaliteAncienNom(String AdrBat_LocaliteAncienNom) {
        this.AdrBat_LocaliteAncienNom = AdrBat_LocaliteAncienNom;
    }

    public String getAdrBat_LocaliteNouveauNom() {
        return AdrBat_LocaliteNouveauNom;
    }

    public void setAdrBat_LocaliteNouveauNom(String AdrBat_LocaliteNouveauNom) {
        this.AdrBat_LocaliteNouveauNom = AdrBat_LocaliteNouveauNom;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getTypeBatiment() {
        return TypeBatiment;
    }

    public void setTypeBatiment(String TypeBatiment) {
        this.TypeBatiment = TypeBatiment;
    }

    public String getEtatActuel() {
        return EtatActuel;
    }

    public void setEtatActuel(String EtatActuel) {
        this.EtatActuel = EtatActuel;
    }

    public Integer getNbrEtage() {
        return NbrEtage;
    }

    public void setNbrEtage(Integer NbrEtage) {
        this.NbrEtage = NbrEtage;
    }

    public String getUsageBatiment() {
        return UsageBatiment;
    }

    public void setUsageBatiment(String UsageBatiment) {
        this.UsageBatiment = UsageBatiment;
    }

    public Integer getNbrLogement() {
        return NbrLogement;
    }

    public void setNbrLogement(Integer NbrLogement) {
        this.NbrLogement = NbrLogement;
    }

    public String getDepartementId() {
        return DepartementId;
    }

    public void setDepartementId(String DepartementId) {
        this.DepartementId = DepartementId;
    }

    public String getCommuneId() {
        return communeId;
    }

    public void setCommuneId(String communeId) {
        this.communeId = communeId;
    }

    public String getVqseId() {
        return VqseId;
    }

    public void setVqseId(String VqseId) {
        this.VqseId = VqseId;
    }

    public String getNomInstitution() {
        return NomInstitution;
    }

    public void setNomInstitution(String NomInstitution) {
        this.NomInstitution = NomInstitution;
    }

    public String getPhoneInstitution() {
        return PhoneInstitution;
    }

    public void setPhoneInstitution(String PhoneInstitution) {
        this.PhoneInstitution = PhoneInstitution;
    }

    public String getRemarques() {
        return Remarques;
    }

    public void setRemarques(String Remarques) {
        this.Remarques = Remarques;
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
}
