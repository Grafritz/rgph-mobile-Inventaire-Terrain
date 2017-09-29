package ht.ihsi.inventaireterrain.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Main {

    public static final String DESTINATION_PACKAGE_NAME="ht.ihsi.inventaireterrain.Backend";

    public static final String PERSONNEL_OBJECT="Personnel";
    public static final String TBL_PERSONNEL ="tbl_personnel";

    public static final String INVENTAIRE_OBJECT="Inventaire";
    public static final String TBL_INVENTAIRE_OBJECT="tbl_inventaire";

    public static final String BATIMENT_OBJECT="Batiment";
    public static final String TBL_BATIMENT_OBJECT="tbl_batiment";

    public static final String LOGEMENT_OBJECT="Logement";
    public static final String TBL_LOGEMENT_OBJECT="tbl_logement";

    public static final String DEPARTEMENT_OBJECT ="Departement";
    public static final String TBL_DEPARTEMENT ="tbl_departement";

    public static final String COMMUNE_OBJECT ="Commune";
    public static final String TBL_COMMUNE ="tbl_commune";

    public static final String VQSE_OBJECT ="Vqse";
    public static final String TBL_VQSE ="tbl_vqse";

    public static final String CODE_SDE_OBJECT ="CodeSDE";
    public static final String TBL_CODE_SDE ="tbl_codesde";



    public static void main(String args[]) throws Exception {

        Schema schema= new Schema(5, DESTINATION_PACKAGE_NAME);

        Entity personnel= schema.addEntity(PERSONNEL_OBJECT);
        personnel.setTableName(TBL_PERSONNEL);
        InventaireTerrain.createPersonnelEntity(personnel);

        Entity inventaire = schema.addEntity(INVENTAIRE_OBJECT);
        inventaire.setTableName(TBL_INVENTAIRE_OBJECT);
        InventaireTerrain.createInventaireEntity(inventaire);

        Entity bat = schema.addEntity(BATIMENT_OBJECT);
        bat.setTableName(TBL_BATIMENT_OBJECT);
        InventaireTerrain.createBatimentEntity(bat);

        Entity logement = schema.addEntity(LOGEMENT_OBJECT);
        logement.setTableName(TBL_LOGEMENT_OBJECT);
        InventaireTerrain.createLogementEntity(logement);

        Entity dept = schema.addEntity(DEPARTEMENT_OBJECT);
        dept.setTableName(TBL_DEPARTEMENT);
        InventaireTerrain.createDepartementEntity(dept);

        Entity commune = schema.addEntity(COMMUNE_OBJECT);
        commune.setTableName(TBL_COMMUNE);
        InventaireTerrain.createCommuneEntity(commune);

        Entity vqse = schema.addEntity(VQSE_OBJECT);
        vqse.setTableName(TBL_VQSE);
        InventaireTerrain.createVqseEntity(vqse);

        Entity codeSDE = schema.addEntity(CODE_SDE_OBJECT);
        codeSDE.setTableName(TBL_CODE_SDE);
        InventaireTerrain.createCodeSDEEntity(codeSDE);

        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }
}
