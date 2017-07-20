package ht.ihsi.inventaireterrain.Backend;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import ht.ihsi.inventaireterrain.Backend.Batiment;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "tbl_batiment".
*/
public class BatimentDao extends AbstractDao<Batiment, Long> {

    public static final String TABLENAME = "tbl_batiment";

    /**
     * Properties of entity Batiment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property IdBatiment = new Property(0, Long.class, "IdBatiment", true, "IdBatiment");
        public final static Property InventaireId = new Property(1, Long.class, "InventaireId", false, "InventaireId");
        public final static Property NoBatiment = new Property(2, Integer.class, "NoBatiment", false, "NoBatiment");
        public final static Property CodeSDE = new Property(3, String.class, "CodeSDE", false, "CodeSDE");
        public final static Property NumOrdreSDE = new Property(4, String.class, "NumOrdreSDE", false, "NumOrdreSDE");
        public final static Property TypeInventaire = new Property(5, String.class, "TypeInventaire", false, "TypeInventaire");
        public final static Property AdrBat_HabitationAncienNom = new Property(6, String.class, "AdrBat_HabitationAncienNom", false, "AdrBat_HabitationAncienNom");
        public final static Property AdrBat_HabitationNouveauNom = new Property(7, String.class, "AdrBat_HabitationNouveauNom", false, "AdrBat_HabitationNouveauNom");
        public final static Property AdrBat_LocaliteAncienNom = new Property(8, String.class, "AdrBat_LocaliteAncienNom", false, "AdrBat_LocaliteAncienNom");
        public final static Property AdrBat_LocaliteNouveauNom = new Property(9, String.class, "AdrBat_LocaliteNouveauNom", false, "AdrBat_LocaliteNouveauNom");
        public final static Property Longitude = new Property(10, String.class, "Longitude", false, "Longitude");
        public final static Property Latitude = new Property(11, String.class, "Latitude", false, "Latitude");
        public final static Property TypeBatiment = new Property(12, String.class, "TypeBatiment", false, "TypeBatiment");
        public final static Property EtatActuel = new Property(13, String.class, "EtatActuel", false, "EtatActuel");
        public final static Property NbrEtage = new Property(14, Integer.class, "NbrEtage", false, "NbrEtage");
        public final static Property UsageBatiment = new Property(15, String.class, "UsageBatiment", false, "UsageBatiment");
        public final static Property NbrLogement = new Property(16, Integer.class, "NbrLogement", false, "NbrLogement");
        public final static Property DepartementId = new Property(17, String.class, "DepartementId", false, "DepartementId");
        public final static Property CommuneId = new Property(18, String.class, "communeId", false, "communeId");
        public final static Property VqseId = new Property(19, String.class, "VqseId", false, "VqseId");
        public final static Property NomInstitution = new Property(20, String.class, "NomInstitution", false, "NomInstitution");
        public final static Property PhoneInstitution = new Property(21, String.class, "PhoneInstitution", false, "PhoneInstitution");
        public final static Property Remarques = new Property(22, String.class, "Remarques", false, "Remarques");
        public final static Property IsValidated = new Property(23, Boolean.class, "isValidated", false, "isValidated");
        public final static Property IsSynchroToAppSup = new Property(24, Boolean.class, "isSynchroToAppSup", false, "isSynchroToAppSup");
        public final static Property IsSynchroToCentrale = new Property(25, Boolean.class, "isSynchroToCentrale", false, "isSynchroToCentrale");
        public final static Property DateDebutCollecte = new Property(26, String.class, "dateDebutCollecte", false, "dateDebutCollecte");
        public final static Property DateFinCollecte = new Property(27, String.class, "dateFinCollecte", false, "dateFinCollecte");
    };


    public BatimentDao(DaoConfig config) {
        super(config);
    }
    
    public BatimentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"tbl_batiment\" (" + //
                "\"IdBatiment\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: IdBatiment
                "\"InventaireId\" INTEGER," + // 1: InventaireId
                "\"NoBatiment\" INTEGER," + // 2: NoBatiment
                "\"CodeSDE\" TEXT," + // 3: CodeSDE
                "\"NumOrdreSDE\" TEXT," + // 4: NumOrdreSDE
                "\"TypeInventaire\" TEXT," + // 5: TypeInventaire
                "\"AdrBat_HabitationAncienNom\" TEXT," + // 6: AdrBat_HabitationAncienNom
                "\"AdrBat_HabitationNouveauNom\" TEXT," + // 7: AdrBat_HabitationNouveauNom
                "\"AdrBat_LocaliteAncienNom\" TEXT," + // 8: AdrBat_LocaliteAncienNom
                "\"AdrBat_LocaliteNouveauNom\" TEXT," + // 9: AdrBat_LocaliteNouveauNom
                "\"Longitude\" TEXT," + // 10: Longitude
                "\"Latitude\" TEXT," + // 11: Latitude
                "\"TypeBatiment\" TEXT," + // 12: TypeBatiment
                "\"EtatActuel\" TEXT," + // 13: EtatActuel
                "\"NbrEtage\" INTEGER," + // 14: NbrEtage
                "\"UsageBatiment\" TEXT," + // 15: UsageBatiment
                "\"NbrLogement\" INTEGER," + // 16: NbrLogement
                "\"DepartementId\" TEXT," + // 17: DepartementId
                "\"communeId\" TEXT," + // 18: communeId
                "\"VqseId\" TEXT," + // 19: VqseId
                "\"NomInstitution\" TEXT," + // 20: NomInstitution
                "\"PhoneInstitution\" TEXT," + // 21: PhoneInstitution
                "\"Remarques\" TEXT," + // 22: Remarques
                "\"isValidated\" INTEGER," + // 23: isValidated
                "\"isSynchroToAppSup\" INTEGER," + // 24: isSynchroToAppSup
                "\"isSynchroToCentrale\" INTEGER," + // 25: isSynchroToCentrale
                "\"dateDebutCollecte\" TEXT," + // 26: dateDebutCollecte
                "\"dateFinCollecte\" TEXT);"); // 27: dateFinCollecte
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"tbl_batiment\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Batiment entity) {
        stmt.clearBindings();
 
        Long IdBatiment = entity.getIdBatiment();
        if (IdBatiment != null) {
            stmt.bindLong(1, IdBatiment);
        }
 
        Long InventaireId = entity.getInventaireId();
        if (InventaireId != null) {
            stmt.bindLong(2, InventaireId);
        }
 
        Integer NoBatiment = entity.getNoBatiment();
        if (NoBatiment != null) {
            stmt.bindLong(3, NoBatiment);
        }
 
        String CodeSDE = entity.getCodeSDE();
        if (CodeSDE != null) {
            stmt.bindString(4, CodeSDE);
        }
 
        String NumOrdreSDE = entity.getNumOrdreSDE();
        if (NumOrdreSDE != null) {
            stmt.bindString(5, NumOrdreSDE);
        }
 
        String TypeInventaire = entity.getTypeInventaire();
        if (TypeInventaire != null) {
            stmt.bindString(6, TypeInventaire);
        }
 
        String AdrBat_HabitationAncienNom = entity.getAdrBat_HabitationAncienNom();
        if (AdrBat_HabitationAncienNom != null) {
            stmt.bindString(7, AdrBat_HabitationAncienNom);
        }
 
        String AdrBat_HabitationNouveauNom = entity.getAdrBat_HabitationNouveauNom();
        if (AdrBat_HabitationNouveauNom != null) {
            stmt.bindString(8, AdrBat_HabitationNouveauNom);
        }
 
        String AdrBat_LocaliteAncienNom = entity.getAdrBat_LocaliteAncienNom();
        if (AdrBat_LocaliteAncienNom != null) {
            stmt.bindString(9, AdrBat_LocaliteAncienNom);
        }
 
        String AdrBat_LocaliteNouveauNom = entity.getAdrBat_LocaliteNouveauNom();
        if (AdrBat_LocaliteNouveauNom != null) {
            stmt.bindString(10, AdrBat_LocaliteNouveauNom);
        }
 
        String Longitude = entity.getLongitude();
        if (Longitude != null) {
            stmt.bindString(11, Longitude);
        }
 
        String Latitude = entity.getLatitude();
        if (Latitude != null) {
            stmt.bindString(12, Latitude);
        }
 
        String TypeBatiment = entity.getTypeBatiment();
        if (TypeBatiment != null) {
            stmt.bindString(13, TypeBatiment);
        }
 
        String EtatActuel = entity.getEtatActuel();
        if (EtatActuel != null) {
            stmt.bindString(14, EtatActuel);
        }
 
        Integer NbrEtage = entity.getNbrEtage();
        if (NbrEtage != null) {
            stmt.bindLong(15, NbrEtage);
        }
 
        String UsageBatiment = entity.getUsageBatiment();
        if (UsageBatiment != null) {
            stmt.bindString(16, UsageBatiment);
        }
 
        Integer NbrLogement = entity.getNbrLogement();
        if (NbrLogement != null) {
            stmt.bindLong(17, NbrLogement);
        }
 
        String DepartementId = entity.getDepartementId();
        if (DepartementId != null) {
            stmt.bindString(18, DepartementId);
        }
 
        String communeId = entity.getCommuneId();
        if (communeId != null) {
            stmt.bindString(19, communeId);
        }
 
        String VqseId = entity.getVqseId();
        if (VqseId != null) {
            stmt.bindString(20, VqseId);
        }
 
        String NomInstitution = entity.getNomInstitution();
        if (NomInstitution != null) {
            stmt.bindString(21, NomInstitution);
        }
 
        String PhoneInstitution = entity.getPhoneInstitution();
        if (PhoneInstitution != null) {
            stmt.bindString(22, PhoneInstitution);
        }
 
        String Remarques = entity.getRemarques();
        if (Remarques != null) {
            stmt.bindString(23, Remarques);
        }
 
        Boolean isValidated = entity.getIsValidated();
        if (isValidated != null) {
            stmt.bindLong(24, isValidated ? 1L: 0L);
        }
 
        Boolean isSynchroToAppSup = entity.getIsSynchroToAppSup();
        if (isSynchroToAppSup != null) {
            stmt.bindLong(25, isSynchroToAppSup ? 1L: 0L);
        }
 
        Boolean isSynchroToCentrale = entity.getIsSynchroToCentrale();
        if (isSynchroToCentrale != null) {
            stmt.bindLong(26, isSynchroToCentrale ? 1L: 0L);
        }
 
        String dateDebutCollecte = entity.getDateDebutCollecte();
        if (dateDebutCollecte != null) {
            stmt.bindString(27, dateDebutCollecte);
        }
 
        String dateFinCollecte = entity.getDateFinCollecte();
        if (dateFinCollecte != null) {
            stmt.bindString(28, dateFinCollecte);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Batiment readEntity(Cursor cursor, int offset) {
        Batiment entity = new Batiment( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // IdBatiment
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // InventaireId
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // NoBatiment
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // CodeSDE
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // NumOrdreSDE
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // TypeInventaire
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // AdrBat_HabitationAncienNom
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // AdrBat_HabitationNouveauNom
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // AdrBat_LocaliteAncienNom
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // AdrBat_LocaliteNouveauNom
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // Longitude
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // Latitude
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // TypeBatiment
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // EtatActuel
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // NbrEtage
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // UsageBatiment
            cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16), // NbrLogement
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // DepartementId
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // communeId
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // VqseId
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // NomInstitution
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // PhoneInstitution
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // Remarques
            cursor.isNull(offset + 23) ? null : cursor.getShort(offset + 23) != 0, // isValidated
            cursor.isNull(offset + 24) ? null : cursor.getShort(offset + 24) != 0, // isSynchroToAppSup
            cursor.isNull(offset + 25) ? null : cursor.getShort(offset + 25) != 0, // isSynchroToCentrale
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // dateDebutCollecte
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27) // dateFinCollecte
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Batiment entity, int offset) {
        entity.setIdBatiment(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setInventaireId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setNoBatiment(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setCodeSDE(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNumOrdreSDE(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTypeInventaire(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAdrBat_HabitationAncienNom(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAdrBat_HabitationNouveauNom(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAdrBat_LocaliteAncienNom(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAdrBat_LocaliteNouveauNom(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setLongitude(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setLatitude(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setTypeBatiment(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setEtatActuel(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setNbrEtage(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setUsageBatiment(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setNbrLogement(cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16));
        entity.setDepartementId(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setCommuneId(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setVqseId(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setNomInstitution(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setPhoneInstitution(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setRemarques(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setIsValidated(cursor.isNull(offset + 23) ? null : cursor.getShort(offset + 23) != 0);
        entity.setIsSynchroToAppSup(cursor.isNull(offset + 24) ? null : cursor.getShort(offset + 24) != 0);
        entity.setIsSynchroToCentrale(cursor.isNull(offset + 25) ? null : cursor.getShort(offset + 25) != 0);
        entity.setDateDebutCollecte(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setDateFinCollecte(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Batiment entity, long rowId) {
        entity.setIdBatiment(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Batiment entity) {
        if(entity != null) {
            return entity.getIdBatiment();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
