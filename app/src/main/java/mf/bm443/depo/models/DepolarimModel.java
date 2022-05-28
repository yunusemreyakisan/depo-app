package mf.bm443.depo.models;

public class DepolarimModel {
    private String DepoAdi, DepoAdresi, DepoBuyuklugu, EklenmeTarihi;


    public DepolarimModel() {
        //Boş Cons.
    }



    public DepolarimModel(String depoAdi, String depoAdresi, String depoBuyuklugu, String eklenmeTarihi) {
        DepoAdi = depoAdi;
        DepoAdresi = depoAdresi;
        DepoBuyuklugu = depoBuyuklugu;
        EklenmeTarihi = eklenmeTarihi;

    }

    public String getEklenmeTarihi() {
        return EklenmeTarihi;
    }

    public void setEklenmeTarihi(String eklenmeTarihi) {
        EklenmeTarihi = eklenmeTarihi;
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

