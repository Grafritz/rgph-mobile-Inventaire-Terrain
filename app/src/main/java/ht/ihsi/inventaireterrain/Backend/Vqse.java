package ht.ihsi.inventaireterrain.Backend;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "tbl_vqse".
 */
public class Vqse {

    /** Not-null value. */
    private String VqseId;
    private String VqseNom;
    /** Not-null value. */
    private String ComId;

    public Vqse() {
    }

    public Vqse(String VqseId, String VqseNom, String ComId) {
        this.VqseId = VqseId;
        this.VqseNom = VqseNom;
        this.ComId = ComId;
    }

    /** Not-null value. */
    public String getVqseId() {
        return VqseId;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setVqseId(String VqseId) {
        this.VqseId = VqseId;
    }

    public String getVqseNom() {
        return VqseNom;
    }

    public void setVqseNom(String VqseNom) {
        this.VqseNom = VqseNom;
    }

    /** Not-null value. */
    public String getComId() {
        return ComId;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setComId(String ComId) {
        this.ComId = ComId;
    }

}
