package mf.bm443.depo.models;

public class DepolarimModel {
    private String DepoAdi, DepoAdresi, DepoBuyuklugu;


    public DepolarimModel() {
        //Boş Cons.
    }

    public DepolarimModel(String depoAdi, String depoAdresi, String depoBuyuklugu) {
        DepoAdi = depoAdi;
        DepoAdresi = depoAdresi;
        DepoBuyuklugu = depoBuyuklugu;

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

    public String getDepoBuyuklugu() {
        return DepoBuyuklugu;
    }

    public void setDepoBuyuklugu(String depoBuyuklugu) {
        DepoBuyuklugu = depoBuyuklugu;
    }


}

