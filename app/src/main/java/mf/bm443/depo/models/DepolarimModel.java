package mf.bm443.depo.models;

public class DepolarimModel {
    private String DepoAdi, DepoAdresi;

    public DepolarimModel() {
        //Boş Cons.
    }

    public DepolarimModel(String DepoAdi, String DepoAdresi) {
        this.DepoAdi = DepoAdi;
        this.DepoAdresi = DepoAdresi;
    }

    public String getDepoAdi() {
        return DepoAdi;
    }

    public void setDepoAdi(String depoAdi) {
        this.DepoAdi = depoAdi;
    }

    public String getDepoAdresi() {
        return DepoAdresi;
    }

    public void setDepoAdresi(String depoAdresi) {
        this.DepoAdresi = depoAdresi;
    }



}

