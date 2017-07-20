package ht.ihsi.inventaireterrain.Models;

/**
 * Created by JFDuverseau on 8/19/2016.
 */
public class CodeSDEModel {
    /** Not-null value. */
    private String NumeroOrdre;
    /** Not-null value. */
    private String CodeSDE;
    private String DeptId;
    private String ComId;
    /** Not-null value. */
    private String VqseId;
    private Integer Milieu;

    public CodeSDEModel() {
        this.NumeroOrdre = "";
        this.CodeSDE = "";
        this.DeptId = "";
        this.ComId = "";
        this.VqseId = "";
        this.Milieu = 0;
    }

    public CodeSDEModel(String NumeroOrdre, String CodeSDE, String DeptId, String ComId, String VqseId, Integer Milieu) {
        this.NumeroOrdre = NumeroOrdre;
        this.CodeSDE = CodeSDE;
        this.DeptId = DeptId;
        this.ComId = ComId;
        this.VqseId = VqseId;
        this.Milieu = Milieu;
    }

    /** Not-null value. */
    public String getNumeroOrdre() {
        return NumeroOrdre;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setNumeroOrdre(String NumeroOrdre) {
        this.NumeroOrdre = NumeroOrdre;
    }

    /** Not-null value. */
    public String getCodeSDE() {
        return CodeSDE;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCodeSDE(String CodeSDE) {
        this.CodeSDE = CodeSDE;
    }

    public String getDeptId() {
        return DeptId;
    }

    public void setDeptId(String DeptId) {
        this.DeptId = DeptId;
    }

    public String getComId() {
        return ComId;
    }

    public void setComId(String ComId) {
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

    public Integer getMilieu() {
        return Milieu;
    }

    public void setMilieu(Integer Milieu) {
        this.Milieu = Milieu;
    }

    @Override
    public String toString() {
        return this.CodeSDE.toString() + " (#Ordre:" + NumeroOrdre + ")";
    }
}
