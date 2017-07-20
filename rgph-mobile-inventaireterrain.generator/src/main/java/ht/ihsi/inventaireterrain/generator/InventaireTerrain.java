package ht.ihsi.inventaireterrain.generator;

import de.greenrobot.daogenerator.Entity;

public class InventaireTerrain {

    public static void createPersonnelEntity(Entity entity){
        entity.addLongProperty("persId").columnName("persId").primaryKey().autoincrement();
        entity.addStringProperty("sdeId").columnName("sdeId");
        entity.addStringProperty("nom").columnName("nom");
        entity.addStringProperty("prenom").columnName("prenom");
        entity.addStringProperty("sexe").columnName("sexe");
        entity.addStringProperty("nomUtilisateur").columnName("nomUtilisateur");
        entity.addStringProperty("motDePasse").columnName("motDePasse");
        entity.addStringProperty("email").columnName("email");
        entity.addBooleanProperty("estActif").columnName("estActif");
        entity.addIntProperty("ProfileId").columnName("ProfileId");
    }

    public static void createInventaireEntity(Entity entity){
        entity.addLongProperty("IdInventaire").columnName("IdInventaire").primaryKey().autoincrement();
        entity.addStringProperty("TypeInventaire").columnName("TypeInventaire"); // Rural=1 ou Urbain=2
        entity.addStringProperty("DepartementId").columnName("DepartementId");
        entity.addStringProperty("CommuneId").columnName("CommuneId");
        entity.addStringProperty("VqseId").columnName("VqseId");
        entity.addStringProperty("CodeSDE").columnName("CodeSDE");
        entity.addStringProperty("NomEtPrenomCartographe").columnName("NomEtPrenomCartographe");
        entity.addStringProperty("NomEtPrenomSuperviseur").columnName("NomEtPrenomSuperviseur");
        entity.addBooleanProperty("NePlusAfficherCetteFenetre").columnName("NePlusAfficherCetteFenetre");
        entity.addBooleanProperty("isValidated").columnName("isValidated");
        entity.addBooleanProperty("isSynchroToAppSup").columnName("isSynchroToAppSup");
        entity.addBooleanProperty("isSynchroToCentrale").columnName("isSynchroToCentrale");
        entity.addStringProperty("dateDebutCollecte").columnName("dateDebutCollecte");
        entity.addStringProperty("dateFinCollecte").columnName("dateFinCollecte");
    }

    public static void createBatimentEntity(Entity entity){
        entity.addLongProperty("IdBatiment").columnName("IdBatiment").primaryKey().autoincrement();
        entity.addLongProperty("InventaireId").columnName("InventaireId");
        entity.addIntProperty("NoBatiment").columnName("NoBatiment");
        entity.addStringProperty("CodeSDE").columnName("CodeSDE");
        entity.addStringProperty("NumOrdreSDE").columnName("NumOrdreSDE");
        entity.addStringProperty("TypeInventaire").columnName("TypeInventaire"); // Rural ou Urbain
        entity.addStringProperty("AdrBat_HabitationAncienNom").columnName("AdrBat_HabitationAncienNom");
        entity.addStringProperty("AdrBat_HabitationNouveauNom").columnName("AdrBat_HabitationNouveauNom");
        entity.addStringProperty("AdrBat_LocaliteAncienNom").columnName("AdrBat_LocaliteAncienNom");
        entity.addStringProperty("AdrBat_LocaliteNouveauNom").columnName("AdrBat_LocaliteNouveauNom");
        entity.addStringProperty("Longitude").columnName("Longitude");
        entity.addStringProperty("Latitude").columnName("Latitude");
        entity.addStringProperty("TypeBatiment").columnName("TypeBatiment");
        entity.addStringProperty("EtatActuel").columnName("EtatActuel");
        entity.addIntProperty("NbrEtage").columnName("NbrEtage");
        entity.addStringProperty("UsageBatiment").columnName("UsageBatiment");
        entity.addIntProperty("NbrLogement").columnName("NbrLogement");
        entity.addStringProperty("DepartementId").columnName("DepartementId");
        entity.addStringProperty("communeId").columnName("communeId");
        entity.addStringProperty("VqseId").columnName("VqseId");
        entity.addStringProperty("NomInstitution").columnName("NomInstitution");
        entity.addStringProperty("PhoneInstitution").columnName("PhoneInstitution");
        entity.addStringProperty("Remarques").columnName("Remarques");
        entity.addBooleanProperty("isValidated").columnName("isValidated");
        entity.addBooleanProperty("isSynchroToAppSup").columnName("isSynchroToAppSup");
        entity.addBooleanProperty("isSynchroToCentrale").columnName("isSynchroToCentrale");
        entity.addStringProperty("dateDebutCollecte").columnName("dateDebutCollecte");
        entity.addStringProperty("dateFinCollecte").columnName("dateFinCollecte");
    }

    public static void createLogementEntity(Entity entity){
        entity.addLongProperty("IdLogement").columnName("IdLogement").primaryKey().autoincrement();
        entity.addLongProperty("BatimentId").columnName("BatimentId");
        entity.addIntProperty("NumeroLogement").columnName("NumeroLogement");
        entity.addStringProperty("NomCompletChefMenage").columnName("NomCompletChefMenage");
        entity.addStringProperty("PhoneChefMenage").columnName("PhoneChefMenage");
        entity.addIntProperty("NbrHommeVivant").columnName("NbrHommeVivant");
        entity.addIntProperty("NbrFemmeVivant").columnName("NbrFemmeVivant");
        entity.addStringProperty("Remarques").columnName("Remarques");
    }

    public static void createDepartementEntity(Entity entity){
        entity.addStringProperty("DeptId").columnName("DeptId").unique().notNull();
        entity.addStringProperty("DeptNom").columnName("DeptNom");
    }

    public static void createCommuneEntity(Entity entity){
        entity.addStringProperty("ComId").columnName("ComId").unique().notNull();
        entity.addStringProperty("ComNom").columnName("ComNom");
        entity.addStringProperty("DeptId").columnName("DeptId").notNull();
    }

    public static void createVqseEntity(Entity entity){
        entity.addStringProperty("VqseId").columnName("VqseId").unique().notNull();
        entity.addStringProperty("VqseNom").columnName("VqseNom");
        entity.addStringProperty("ComId").columnName("ComId").notNull();
    }

    public static void createCodeSDEEntity(Entity entity){
        entity.addStringProperty("NumeroOrdre").columnName("NumeroOrdre").unique().notNull();
        entity.addStringProperty("CodeSDE").columnName("CodeSDE").notNull();
        entity.addStringProperty("DeptId").columnName("DeptId");
        entity.addStringProperty("ComId").columnName("ComId");
        entity.addStringProperty("VqseId").columnName("VqseId").notNull();
        entity.addIntProperty("Milieu").columnName("Milieu");
    }

}
