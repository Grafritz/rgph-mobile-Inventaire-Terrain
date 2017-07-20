package ht.ihsi.inventaireterrain.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ht.ihsi.inventaireterrain.Constant.Constant;


/**
 * Created by JFDuverseau on 5/3/2016.
 */
public class Shared_Preferences
{

    //region ATTRIBUTS
    private Long persId;
    private String sdeId;
    private String nom;
    private String prenom;
    private String sexe;
    private String nomUtilisateur;
    private String motDePasse;
    private String email;
    private Boolean estActif;
    private Integer ProfileId;

    private long pref_IdInventaire;
    private String pref_TypeInventaire;
    private String pref_Departement;
    private String pref_Commune;
    private String pref_Vqse;
    private String pref_CodeSDE;
    private String pref_NumeroOdreSDE;
    private String pref_NomEtPrenomCartographe;
    private String pref_NomEtPrenomSuperviseur;
    private boolean pref_NePlusAfficherCetteFenetre;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean IsConnected;
    private boolean IsConfigured;
    private String pref_DefaultConfiguration;
    //endregion

    //region CONSTRUCTEURS
    public Shared_Preferences(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }
    //endregion

    //region GETTER & SETTER
    public Long getPersId() {
        persId = this.sharedPreferences.getLong(Constant.prefPersId, 0);
        return persId;
    }

    public void setPersId(Long persId) {
        this.persId = persId;
        editor.putLong(Constant.prefPersId, persId).commit();
    }

    public String getSdeId() {
        sdeId = this.sharedPreferences.getString(Constant.prefSdeId, "");
        return sdeId;
    }

    public void setSdeId(String sdeId) {
        this.sdeId = sdeId;
        editor.putString(Constant.prefSdeId, sdeId).commit();
    }

    public String getNom() {
        nom = this.sharedPreferences.getString(Constant.prefNom, "");
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        editor.putString(Constant.prefNom, nom).commit();
    }

    public String getPrenom() {
        prenom = this.sharedPreferences.getString(Constant.prefPrenom, "");
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
        editor.putString(Constant.prefPrenom, prenom).commit();
    }

    public String getPrenomEtNom() {
        return this.sharedPreferences.getString(Constant.prefPrenomEtNom, "");
    }

    public void setPrenomEtNom() {
        editor.putString(Constant.prefPrenomEtNom, this.prenom + "" + this.nom).commit();
    }

    public String getSexe() {
        sexe = this.sharedPreferences.getString(Constant.prefSexe, "");
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
        editor.putString(Constant.prefSexe, sexe).commit();
    }

    public String getNomUtilisateur() {
        nomUtilisateur = this.sharedPreferences.getString(Constant.prefNomUtilisateur, "");
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
        editor.putString(Constant.prefNomUtilisateur, nomUtilisateur).commit();
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        email = this.sharedPreferences.getString(Constant.prefEmail, "");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        editor.putString(Constant.prefEmail, email).commit();
    }

    public Boolean getEstActif() {
        estActif = this.sharedPreferences.getBoolean(Constant.prefEstActif, false);
        return estActif;
    }

    public void setEstActif(Boolean estActif) {
        this.estActif = estActif;
        editor.putBoolean(Constant.prefEstActif, estActif).commit();
    }

    public Integer getProfileId() {
        ProfileId = this.sharedPreferences.getInt(Constant.prefProfileId, 0);
        return ProfileId;
    }

    public void setProfileId(Integer profileId) {
        ProfileId = profileId;
        editor.putInt(Constant.prefProfileId, ProfileId).commit();
    }

    public Boolean getIsConnected() {
        return this.sharedPreferences.getBoolean(Constant.prefIsConnected, false);
    }

    public void setIsConnected(Boolean isConnected) {
        this.IsConnected = isConnected;
        editor.putBoolean(Constant.prefIsConnected, IsConnected).commit();
    }

    public String getprefLastLogin() {
        return this.sharedPreferences.getString(Constant.prefLastLogin, "");
    }

    public void setprefLastLogin(String prefLastLogin) {
        editor.putString(Constant.prefLastLogin, prefLastLogin).commit();
    }
//endregion

    //region GETTER & SETTER 2
    public Long getPref_IdInventaire() {
        pref_IdInventaire = this.sharedPreferences.getLong(Constant.pref_IdInventaire, 0);
        return pref_IdInventaire;
    }

    public void setPref_IdInventaire(Long pref_IdInventaire) {
        this.pref_IdInventaire = pref_IdInventaire;
        editor.putLong(Constant.pref_IdInventaire, pref_IdInventaire).commit();
    }

    public String getPref_TypeInventaire() {
        pref_TypeInventaire = this.sharedPreferences.getString(Constant.pref_TypeInventaire, "");
        return pref_TypeInventaire;
    }

    public void setPref_TypeInventaire(String pref_TypeInventaire) {
        this.pref_TypeInventaire = pref_TypeInventaire;
        editor.putString(Constant.pref_TypeInventaire, pref_TypeInventaire).commit();
    }

    public String getPref_Departement() {
        pref_Departement = this.sharedPreferences.getString(Constant.pref_Departement, "");
        return pref_Departement;
    }

    public void setPref_Departement(String pref_Departement) {
        this.pref_Departement = pref_Departement;
        editor.putString(Constant.pref_Departement, pref_Departement).commit();
    }

    public String getPref_Commune() {
        pref_Commune = this.sharedPreferences.getString(Constant.pref_Commune, "");
        return pref_Commune;
    }

    public void setPref_Commune(String pref_Commune) {
        this.pref_Commune = pref_Commune;
        editor.putString(Constant.pref_Commune, pref_Commune).commit();
    }

    public String getPref_Vqse() {
        pref_Vqse = this.sharedPreferences.getString(Constant.pref_Vqse, "");
        return pref_Vqse;
    }

    public void setPref_Vqse(String pref_Vqse) {
        this.pref_Vqse = pref_Vqse;
        editor.putString(Constant.pref_Vqse, pref_Vqse).commit();
    }

    public String getPref_CodeSDE() {
        pref_CodeSDE = this.sharedPreferences.getString(Constant.pref_CodeSDE, "");
        return pref_CodeSDE;
    }

    public void setPref_CodeSDE(String pref_CodeSDE) {
        this.pref_CodeSDE = pref_CodeSDE;
        editor.putString(Constant.pref_CodeSDE, pref_CodeSDE).commit();
    }

    public String getPref_NumeroOdreSDE() {
        pref_NumeroOdreSDE = this.sharedPreferences.getString(Constant.pref_NumeroOdreSDE, "");
        return pref_NumeroOdreSDE;
    }

    public void setPref_NumeroOdreSDE(String pref_NumeroOdreSDE) {
        this.pref_NumeroOdreSDE = pref_NumeroOdreSDE;
        editor.putString(Constant.pref_CodeSDE, pref_NumeroOdreSDE).commit();
    }

    public String getPref_NomEtPrenomCartographe() {
        pref_NomEtPrenomCartographe = this.sharedPreferences.getString(Constant.pref_NomEtPrenomCartographe, "");
        return pref_NomEtPrenomCartographe;
    }

    public void setPref_NomEtPrenomCartographe(String pref_NomEtPrenomCartographe) {
        this.pref_NomEtPrenomCartographe = pref_NomEtPrenomCartographe;
        editor.putString(Constant.pref_NomEtPrenomCartographe, pref_NomEtPrenomCartographe).commit();
    }

    public String getPref_NomEtPrenomSuperviseur() {
        pref_NomEtPrenomSuperviseur = this.sharedPreferences.getString(Constant.pref_NomEtPrenomSuperviseur, "");
        return pref_NomEtPrenomSuperviseur;
    }

    public void setPref_NomEtPrenomSuperviseur(String pref_NomEtPrenomSuperviseur) {
        this.pref_NomEtPrenomSuperviseur = pref_NomEtPrenomSuperviseur;
        editor.putString(Constant.pref_NomEtPrenomSuperviseur, pref_NomEtPrenomSuperviseur).commit();
    }

    public boolean isPref_NePlusAfficherCetteFenetre() {
        pref_NePlusAfficherCetteFenetre = this.sharedPreferences.getBoolean(Constant.pref_NePlusAfficherCetteFenetre, false);
        return pref_NePlusAfficherCetteFenetre;
    }

    public void setPref_NePlusAfficherCetteFenetre(boolean pref_NePlusAfficherCetteFenetre) {
        this.pref_NePlusAfficherCetteFenetre = pref_NePlusAfficherCetteFenetre;
        editor.putBoolean(Constant.pref_NePlusAfficherCetteFenetre, pref_NePlusAfficherCetteFenetre).commit();
    }

    public boolean getIsConfigured() {
        IsConfigured = this.sharedPreferences.getBoolean(Constant.pref_IsConfigured, false);
        return IsConfigured;
    }

    public void setIsConfigured(boolean isConfigured) {
        IsConfigured = isConfigured;
        editor.putBoolean(Constant.pref_IsConfigured, IsConfigured).commit();
    }

    public String getDefaultConfiguration() {
        pref_DefaultConfiguration = this.sharedPreferences.getString(Constant.pref_DefaultConfiguration, "");
        return pref_DefaultConfiguration;
    }
    public void set_DefaultConfiguration(String pref_DefaultConfiguration) {
        this.pref_DefaultConfiguration = pref_DefaultConfiguration;
        editor.putString(Constant.pref_DefaultConfiguration, pref_DefaultConfiguration).commit();
    }
//endregion
}
