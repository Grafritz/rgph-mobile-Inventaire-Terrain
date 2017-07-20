package ht.ihsi.inventaireterrain.Models;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public class PersonnelModel extends BaseModel{

    private long persId;
    private String sdeId;
    private String nom;
    private String prenom;
    private String sexe;
    private String nomUtilisateur;
    private String motDePasse;
    private String email;
    private boolean estActif;
    private int ProfileId;
    private Boolean IsConnected;

    public PersonnelModel(){
        this.persId=0;
        this.sdeId="";
        this.nom="";
        this.prenom="";
        this.sexe="M";
        this.nomUtilisateur="";
        this.motDePasse="ihsi2016";
        this.email="";
        this.estActif=true;
        this.ProfileId=3;
        this.IsConnected=false;
    }
    //region getters and setters
    public long getPersId() {
        return persId;
    }

    public void setPersId(long persId) {
        this.persId = persId;
    }

    public String getSdeId() {
        return sdeId;
    }

    public void setSdeId(String sdeId) {
        this.sdeId = sdeId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEstActif() {
        return estActif;
    }

    public void setEstActif(boolean estActif) {
        this.estActif = estActif;
    }

    public int getProfileId() {
        return ProfileId;
    }

    public void setProfileId(int profileId) {
        ProfileId = profileId;
    }

    public Boolean getIsConnected() {
        return IsConnected;
    }

    public void setIsConnected(Boolean isConnected) {
        IsConnected = isConnected;
    }
//endregion
}
