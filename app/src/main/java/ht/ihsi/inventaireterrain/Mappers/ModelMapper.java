package ht.ihsi.inventaireterrain.Mappers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ht.ihsi.inventaireterrain.Backend.Batiment;
import ht.ihsi.inventaireterrain.Backend.CodeSDE;
import ht.ihsi.inventaireterrain.Backend.Commune;
import ht.ihsi.inventaireterrain.Backend.Departement;
import ht.ihsi.inventaireterrain.Backend.Inventaire;
import ht.ihsi.inventaireterrain.Backend.Logement;
import ht.ihsi.inventaireterrain.Backend.LogementDao;
import ht.ihsi.inventaireterrain.Backend.Personnel;
import ht.ihsi.inventaireterrain.Backend.PersonnelDao;
import ht.ihsi.inventaireterrain.Backend.Vqse;
import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Managers.CURecordMngrImpl;
import ht.ihsi.inventaireterrain.Managers.QueryRecordMngr;
import ht.ihsi.inventaireterrain.Managers.QueryRecordMngrImpl;
import ht.ihsi.inventaireterrain.Models.BatimentModel;
import ht.ihsi.inventaireterrain.Models.CodeSDEModel;
import ht.ihsi.inventaireterrain.Models.CommuneModel;
import ht.ihsi.inventaireterrain.Models.DepartementModel;
import ht.ihsi.inventaireterrain.Models.InventaireModel;
import ht.ihsi.inventaireterrain.Models.KeyValueModel;
import ht.ihsi.inventaireterrain.Models.LogementModel;
import ht.ihsi.inventaireterrain.Models.PersonnelModel;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.Models.VqseModel;
import ht.ihsi.inventaireterrain.Utilities.Shared_Preferences;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;
import ht.ihsi.inventaireterrain.Utilities.Tools;
import ht.ihsi.inventaireterrain.Utilities.TypeSafeConversion;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public class ModelMapper {
    //region Map To BaseModel

    public static KeyValueModel MapTo(String key, String value) {
        KeyValueModel kvm = new KeyValueModel(key, value);
        return kvm;
    }


    public static PersonnelModel MapTo(Personnel entity) {
        PersonnelModel m = new PersonnelModel();
        m.setPersId(entity.getPersId());
        m.setSdeId(entity.getSdeId());
        m.setNom(entity.getNom());
        m.setPrenom(entity.getPrenom());
        m.setSexe(entity.getSexe());
        m.setNomUtilisateur(entity.getNomUtilisateur());
        m.setMotDePasse(entity.getMotDePasse());
        m.setEmail(entity.getEmail());
        m.setEstActif(entity.getEstActif());
        m.setProfileId(entity.getProfileId());
        return m;
    }

    public static Personnel MapTo(PersonnelModel entity) {
        Personnel m = new Personnel();
        //m.setPersId(entity.getPersId());
        m.setSdeId(entity.getSdeId());
        m.setNom(entity.getNom());
        m.setPrenom(entity.getPrenom());
        m.setSexe(entity.getSexe());
        m.setNomUtilisateur(entity.getNomUtilisateur());
        m.setMotDePasse(entity.getMotDePasse());
        m.setEmail(entity.getEmail());
        m.setEstActif(entity.getEstActif());
        m.setProfileId(entity.getProfileId());
        return m;
    }

    public static DepartementModel MapTo(Departement entity){
        DepartementModel m = new DepartementModel();
        m.setDeptId(entity.getDeptId());
        m.setDeptNom(entity.getDeptNom());
        return m;
    }

    public static CommuneModel MapTo(Commune entity) {
        CommuneModel m = new CommuneModel();
        m.setComId(entity.getComId());
        m.setComNom(entity.getComNom());
        m.setDeptId(entity.getDeptId());
        return m;
    }

     public static VqseModel MapTo(Vqse entity) {
         VqseModel m = new VqseModel();
         m.setVqseId(entity.getVqseId());
         m.setVqseNom(entity.getVqseNom());
         m.setComId(entity.getComId());
         return m;
     }

    /*public static PaysModel MapTo(Pays entitiy){
        m.setcodePays(entity.getcodePays());
        m.setnomPays(entity.getnomPays());
    }*/

    //endregion

    //region Map To Entity

    //endregion

    //

    public static List<CommuneModel> MapToCommune(List<Commune> coms) {
        List<CommuneModel> result = new ArrayList<>() ;
        if(coms!=null && coms.size()>0) {
            for (Commune com : coms) {
                CommuneModel r = new CommuneModel();
                r.setComId(com.getComId());
                r.setComNom(com.getComNom());
                r.setDeptId(com.getDeptId());
                result.add(r);
            }
        }
        return result;
    }

    public static List<VqseModel> MapToVqse(List<Vqse> vqses) {
        List<VqseModel> result = new ArrayList<>() ;
        if(vqses!=null && vqses.size()>0) {
            for (Vqse com : vqses) {
                VqseModel r = new VqseModel();
                r.setVqseId(com.getVqseId());
                r.setVqseNom(com.getVqseNom());
                r.setComId(com.getComId());
                result.add(r);
            }
        }
        return result;
    }
    public static List<CodeSDEModel> MapToCodeSDE(List<CodeSDE> codeSDEList) {
        List<CodeSDEModel> result = new ArrayList<>() ;
        if(codeSDEList!=null && codeSDEList.size()>0) {
            for (CodeSDE codeSDE : codeSDEList) {
                CodeSDEModel r = new CodeSDEModel();
                r.setNumeroOrdre(codeSDE.getNumeroOrdre());
                r.setCodeSDE(codeSDE.getCodeSDE());
                r.setVqseId(codeSDE.getVqseId());
                r.setComId(codeSDE.getComId());
                r.setDeptId(codeSDE.getDeptId());
                r.setMilieu(codeSDE.getMilieu());
                result.add(r);
            }
        }
        return result;
    }

    public static Batiment MapTo(BatimentModel entity) {
        Batiment m = new Batiment();
        //m.setIdBatiment(entity.getIdBatiment());
        m.setInventaireId(entity.getInventaireId());
        m.setNoBatiment(entity.getNoBatiment());
        m.setCodeSDE(entity.getCodeSDE());
        m.setNumOrdreSDE(entity.getNumOrdreSDE());
        m.setTypeInventaire(entity.getTypeInventaire());
        m.setAdrBat_HabitationAncienNom(entity.getAdrBat_HabitationAncienNom());
        m.setAdrBat_HabitationNouveauNom(entity.getAdrBat_HabitationNouveauNom());
        m.setAdrBat_LocaliteAncienNom(entity.getAdrBat_LocaliteAncienNom());
        m.setAdrBat_LocaliteNouveauNom(entity.getAdrBat_LocaliteNouveauNom());
        m.setLongitude(entity.getLongitude());
        m.setLatitude(entity.getLatitude());
        m.setTypeBatiment(entity.getTypeBatiment());
        m.setEtatActuel(entity.getEtatActuel());
        m.setNbrEtage(entity.getNbrEtage());
        m.setUsageBatiment(entity.getUsageBatiment());
        m.setNbrLogement(entity.getNbrLogement());
        m.setDepartementId(entity.getDepartementId());
        m.setCommuneId(entity.getCommuneId());
        m.setVqseId(entity.getVqseId());
        m.setNomInstitution(entity.getNomInstitution());
        m.setPhoneInstitution(entity.getPhoneInstitution());
        m.setRemarques(entity.getRemarques());

        m.setIsValidated(entity.getIsValidated());
        m.setIsSynchroToAppSup(entity.getIsSynchroToAppSup());
        m.setIsSynchroToCentrale(entity.getIsSynchroToCentrale());
        m.setDateDebutCollecte(entity.getDateDebutCollecte());
        m.setDateFinCollecte(entity.getDateFinCollecte());
        return m;
    }

    public static BatimentModel MapTo(Batiment entity) {
        BatimentModel m = new BatimentModel();
        m.setIdBatiment(entity.getIdBatiment());
        m.setNoBatiment(entity.getNoBatiment());
        m.setInventaireId(entity.getInventaireId());
        m.setCodeSDE(entity.getCodeSDE());
        m.setNumOrdreSDE(entity.getNumOrdreSDE());
        m.setTypeInventaire(entity.getTypeInventaire());
        m.setAdrBat_HabitationAncienNom(entity.getAdrBat_HabitationAncienNom());
        m.setAdrBat_HabitationNouveauNom(entity.getAdrBat_HabitationNouveauNom());
        m.setAdrBat_LocaliteAncienNom(entity.getAdrBat_LocaliteAncienNom());
        m.setAdrBat_LocaliteNouveauNom(entity.getAdrBat_LocaliteNouveauNom());
        m.setLongitude(entity.getLongitude());
        m.setLatitude(entity.getLatitude());
        m.setTypeBatiment(entity.getTypeBatiment());
        m.setEtatActuel(entity.getEtatActuel());
        m.setNbrEtage(entity.getNbrEtage());
        m.setUsageBatiment(entity.getUsageBatiment());
        m.setNbrLogement(entity.getNbrLogement());
        m.setDepartementId(entity.getDepartementId());
        m.setCommuneId(entity.getCommuneId());
        m.setVqseId(entity.getVqseId());
        m.setNomInstitution(entity.getNomInstitution());
        m.setPhoneInstitution(entity.getPhoneInstitution());
        /**/
        m.setRemarques(entity.getRemarques());

        m.setIsValidated(entity.getIsValidated());
        m.setIsSynchroToAppSup(entity.getIsSynchroToAppSup());
        m.setIsSynchroToCentrale(entity.getIsSynchroToCentrale());
        m.setDateDebutCollecte(entity.getDateDebutCollecte());
        m.setDateFinCollecte(entity.getDateFinCollecte());
        return m;
    }


     public static List<LogementModel> MapToLogementModel(List<Logement> loges) {
        List<LogementModel> result = new ArrayList<LogementModel>();
        for (Logement log : loges) {
            result.add(MapToLogementModel(log));
        }
        return result;
    }

    public static Logement MapTo(LogementModel logement) {
        Logement log = new Logement();
        //log.setIdLogement(logement.getIdLogement());
        log.setBatimentId(logement.getBatimentId());
        log.setNumeroLogement(logement.getNumeroLogement());
        log.setNomCompletChefMenage(logement.getNomCompletChefMenage());
        log.setPhoneChefMenage(logement.getPhoneChefMenage());
        log.setNbrHommeVivant(logement.getNbrHommeVivant());
        log.setNbrFemmeVivant(logement.getNbrFemmeVivant());
        log.setRemarques(logement.getRemarques());
        return log;
    }

    public static LogementModel MapToLogementModel(Logement logement) {
        LogementModel log = new LogementModel();
        log.setIdLogement(logement.getIdLogement());
        log.setBatimentId(logement.getBatimentId());
        log.setNumeroLogement(logement.getNumeroLogement());
        log.setNomCompletChefMenage(logement.getNomCompletChefMenage());
        log.setPhoneChefMenage(logement.getPhoneChefMenage());
        log.setNbrHommeVivant(logement.getNbrHommeVivant());
        log.setNbrFemmeVivant(logement.getNbrFemmeVivant());
        log.setRemarques(logement.getRemarques());
        return log;
    }

    //region List<RowDataListModel>
    public static  List<RowDataListModel> MapToRowsInventaire(Context context, List<Inventaire> inventaireList) {
        List<RowDataListModel> result=new ArrayList<>() ;
        if(inventaireList!=null && inventaireList.size()>0) {
            QueryRecordMngr queryRecordMngr = new QueryRecordMngrImpl(context);
            for (Inventaire inv : inventaireList) {
                RowDataListModel r = new RowDataListModel();
                r.setId(inv.getIdInventaire());
                r.setTitle("SDE:" + inv.getCodeSDE());
                DepartementModel dept = null;
                CommuneModel com = null;
                VqseModel vqse  = null;

                if( inv.getDepartementId() != null) { dept = queryRecordMngr.GetDepartementById(inv.getDepartementId()); }
                if( inv.getCommuneId() != null) { com = queryRecordMngr.GetCommuneById(inv.getCommuneId()); }
                if( inv.getVqseId() != null){ vqse = queryRecordMngr.GetVqseById(inv.getVqseId()); }

                String regionZone = "";
                if( dept != null ){
                    regionZone = dept.getDeptNom();
                }
                if( com != null ){
                    if( !regionZone.equalsIgnoreCase("") ){
                        regionZone += " / " + com.getComNom();
                    }else{
                        regionZone = com.getComNom();
                    }
                }
                if( vqse != null ){
                    if( !regionZone.equalsIgnoreCase("") ){
                        regionZone += " / " + vqse.getVqseNom();
                    }else{
                        regionZone = vqse.getVqseNom();
                    }
                }
                String typeInventaire ="<br /> ";
                if( inv.getTypeInventaire() != null ) {
                    if (inv.getTypeInventaire().equalsIgnoreCase("" + Constant.FORM_URBAIN)) {
                        typeInventaire = "<br /> <b>Type SDE:</b>  Formulaire Urbain /";
                    } else if (inv.getTypeInventaire().equalsIgnoreCase("" + Constant.FORM_RURAL) && inv.getTypeInventaire() != null) {
                        typeInventaire = "<br /> <b>Type SDE:</b>  Formulaire Rural / ";
                    }
                }
                int nbrBatimentSave = queryRecordMngr.countBatimentByIdInventaire(inv.getIdInventaire());
                String desc = "<b>Région/Zone:</b> " + regionZone
                        + "" + typeInventaire
                        + " <b>Nbr Batiment:</b> " + nbrBatimentSave;
                r.setDesc(desc);

                r.setIsComplete(true);
                r.setIsModuleMenu(true);
                r.setModel(ModelMapper.MapToInventaireModel(inv));
                result.add(r);
            }
        }else{
            RowDataListModel r = new RowDataListModel();
            r.setTitle("AUCUN ELELEMENT TROUVE!!!");
            r.setDesc("Non apllicable");
            r.setIsEmpty(true);
            result.add(r);
        }
        return result;
    }

    public static  List<RowDataListModel> MapToRowsBatiment(Context context, List<Batiment> bats) {
        List<RowDataListModel> result=new ArrayList<>() ;
        if(bats!=null && bats.size()>0) {
            QueryRecordMngr queryRecordMngr = new QueryRecordMngrImpl(context);
            for (Batiment bat : bats) {
                RowDataListModel r = new RowDataListModel();
                r.setId(bat.getIdBatiment());
                r.setTitle("Batiment-" + bat.getNoBatiment());
                String EtatBatiment = Tools.getStringEtatBatiment(context, bat.getEtatActuel(), Constant.DATA_ETAT_BATIMENT);
                int nbrLogementSave = queryRecordMngr.countLogementByIdBatiment(bat.getIdBatiment());
                String desc = "<b>Adresse:</b> " + bat.getAdrBat_HabitationAncienNom()
                        + "<br /> <b>Etat:</b> " + EtatBatiment;

                r.setIsComplete(false);
                if(nbrLogementSave == bat.getNbrLogement()){
                    r.setIsComplete(true);
                }
                r.setIsModuleMenu(true);
                if( TypeSafeConversion.TranslateStringToInterger(bat.getUsageBatiment()) < Constant.COMMERCE_3 ){
                    desc += "<br /> <b>Logement:</b> " + nbrLogementSave +"<b>/</b>" + bat.getNbrLogement();
                }else{
                    r.setIsComplete(true);
                    r.setIsModuleMenu(false);
                    desc += "<br /> <b>Nom Inst.:</b> " + bat.getNomInstitution() +( !bat.getPhoneInstitution().equalsIgnoreCase("") ?"<br /><b> Tél.: </b>"+ bat.getPhoneInstitution():"" );
                }
                r.setDesc(desc);

                r.setModel(ModelMapper.MapTo(bat));
                result.add(r);
            }
        }else{
            RowDataListModel r = new RowDataListModel();
            r.setTitle("AUCUN ELELEMENT TROUVE!!!");
            r.setDesc("Non apllicable");
            r.setIsEmpty(true);
            result.add(r);
        }
        return result;
    }

    public static List<RowDataListModel> MapToRows(Shared_Preferences SPref, List<Personnel> personnelList) {
        List<RowDataListModel> result=new ArrayList<>() ;
        if(personnelList != null && personnelList.size() > 0) {
            for (Personnel personnel : personnelList) {
                RowDataListModel r = new RowDataListModel();
                r.setId(personnel.getPersId());
                r.setTitle("" + personnel.getPrenom() + " " + personnel.getNom());
                String desc = "<b>Compte:</b> " + personnel.getNomUtilisateur()
                        + " <br /><b>Profil:</b> " + Tools.getString_Reponse("" + personnel.getProfileId(), PersonnelDao.Properties.ProfileId.columnName)
                        + " | <b>Statut :</b> " + Tools.getString_Reponse((personnel.getEstActif() ? "1" : "0"), PersonnelDao.Properties.EstActif.columnName);
                r.setDesc(desc);

                r.setIsComplete(true);
                r.setIsModuleMenu(false);
                r.setModel(ModelMapper.MapTo(personnel));

                if (SPref != null) {
                    if (SPref.getProfileId() == Constant.COMPTE_ASTIC) {
                        result.add(r);
                    } else {
                        if (SPref.getPersId() == personnel.getPersId()) {
                            result.add(r);
                        } else if (personnel.getProfileId() != 1 && personnel.getProfileId() != 2) {
                            result.add(r);
                        }
                    }
                }
            }
        }else{
        }
        return result;
    }
    public static List<RowDataListModel> MapToRowsLogement(List<Logement> loges, BatimentModel batimentModel){
        List<RowDataListModel> result=new ArrayList<>() ;
        if(loges!=null && loges.size()>0) {
            for (Logement log : loges) {
                RowDataListModel r = new RowDataListModel();
                r.setId(log.getIdLogement());
                String desc ="";
                if( batimentModel != null ){
                    if( TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) < Constant.COMMERCE_3 ){
                        r.setTitle("Logement " + log.getNumeroLogement());
                        desc = "<b>Chef Ménage: </b> " + log.getNomCompletChefMenage() + "<b> | Tél.: </b>"+ log.getPhoneChefMenage()+""
                                + "<br /> <b>Nbr Homme: </b>" + log.getNbrHommeVivant() + " <b> | </b> <b>Nbr Femme: </b>" + log.getNbrFemmeVivant();

                    }else{
                        r.setTitle("Institution: " + log.getNomCompletChefMenage());
                        desc =  "<b> Tél.: </b>"+ log.getPhoneChefMenage() + ( !log.getPhoneChefMenage().equalsIgnoreCase("") ?"<br /><b> Remarques.: </b>"+ log.getPhoneChefMenage():"" );
                    }
                }
                r.setDesc(desc);
                r.setIsComplete(true);
                r.setIsModuleMenu(true);
                r.setModel(MapToLogementModel(log));
                result.add(r);
            }
        }else{
            result = null;
        }
        return result;
    }

    //endregion

    public static Inventaire MapTo(InventaireModel entity) {
        Inventaire inventaire = new Inventaire();
        inventaire.setTypeInventaire(entity.getTypeInventaire());
        inventaire.setDepartementId(entity.getDepartementId());
        inventaire.setCommuneId(entity.getCommuneId());
        inventaire.setVqseId(entity.getVqseId());
        inventaire.setCodeSDE(entity.getCodeSDE());
        inventaire.setNomEtPrenomCartographe(entity.getNomEtPrenomCartographe());
        inventaire.setNomEtPrenomSuperviseur(entity.getNomEtPrenomSuperviseur());
        inventaire.setNePlusAfficherCetteFenetre(entity.getNePlusAfficherCetteFenetre());
        inventaire.setIsValidated(entity.getIsValidated());
        inventaire.setIsSynchroToAppSup(entity.getIsSynchroToAppSup());
        inventaire.setIsSynchroToCentrale(entity.getIsSynchroToCentrale());
        inventaire.setDateDebutCollecte(entity.getDateDebutCollecte());
        inventaire.setDateFinCollecte(entity.getDateFinCollecte());
        return inventaire;
    }

    public static InventaireModel MapToInventaireModel(Inventaire entity) {
        InventaireModel inventaire = new InventaireModel();
        inventaire.setIdInventaire(entity.getIdInventaire());
        inventaire.setTypeInventaire(entity.getTypeInventaire());
        inventaire.setDepartementId(entity.getDepartementId());
        inventaire.setCommuneId(entity.getCommuneId());
        inventaire.setVqseId(entity.getVqseId());
        inventaire.setCodeSDE(entity.getCodeSDE());
        inventaire.setNomEtPrenomCartographe(entity.getNomEtPrenomCartographe());
        inventaire.setNomEtPrenomSuperviseur(entity.getNomEtPrenomSuperviseur());
        inventaire.setNePlusAfficherCetteFenetre(entity.getNePlusAfficherCetteFenetre());
        inventaire.setIsValidated(entity.getIsValidated());
        inventaire.setIsSynchroToAppSup(entity.getIsSynchroToAppSup());
        inventaire.setIsSynchroToCentrale(entity.getIsSynchroToCentrale());
        inventaire.setDateDebutCollecte(entity.getDateDebutCollecte());
        inventaire.setDateFinCollecte(entity.getDateFinCollecte());
        return inventaire;
    }
    //
    public static Shared_Preferences MapToPreferences(Context context, PersonnelModel entity) {
        Shared_Preferences sharedPreferences = new Shared_Preferences(context);
        sharedPreferences.setPersId((long) entity.getPersId());
        sharedPreferences.setSdeId(entity.getSdeId());
        sharedPreferences.setNom(entity.getNom());
        sharedPreferences.setPrenom(entity.getPrenom());
        sharedPreferences.setPrenomEtNom();
        sharedPreferences.setSexe(entity.getSexe());
        sharedPreferences.setNomUtilisateur(entity.getNomUtilisateur());
        sharedPreferences.setMotDePasse(entity.getMotDePasse());
        sharedPreferences.setEmail(entity.getEmail());
        sharedPreferences.setEstActif(entity.getEstActif());
        sharedPreferences.setProfileId(entity.getProfileId());

        sharedPreferences.setIsConnected(entity.getIsConnected());
        sharedPreferences.setprefLastLogin("");
        return sharedPreferences;
    }//

    public static Shared_Preferences MapToPreferences(Context context, InventaireModel entity) {
        Shared_Preferences sharedPreferences = new Shared_Preferences(context);
        sharedPreferences.setPref_IdInventaire(entity.getIdInventaire());
        sharedPreferences.setPref_TypeInventaire(entity.getTypeInventaire());
        sharedPreferences.setPref_Departement(entity.getDepartementId());
        sharedPreferences.setPref_Commune(entity.getCommuneId());
        sharedPreferences.setPref_Vqse(entity.getVqseId());
        sharedPreferences.setPref_CodeSDE(entity.getCodeSDE());
        //sharedPreferences.setPref_NumeroOdreSDE(entity.getNumeroOdreSDE());
        sharedPreferences.setPref_NomEtPrenomCartographe(entity.getNomEtPrenomCartographe());
        sharedPreferences.setPref_NomEtPrenomSuperviseur(entity.getNomEtPrenomSuperviseur());
        sharedPreferences.setPref_NePlusAfficherCetteFenetre(entity.getNePlusAfficherCetteFenetre());
        sharedPreferences.setIsConfigured(entity.getIsConfigured());

        return sharedPreferences;
    }//


}// END CLASS
//
