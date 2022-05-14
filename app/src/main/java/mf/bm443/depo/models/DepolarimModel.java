package mf.bm443.depo.models;

public class DepolarimModel {
    private String DepoAdi, DepoAdresi, DepoBuyuklugu, DepoUrunKategorisi;


    public DepolarimModel() {
        //Boş Cons.
    }

    public DepolarimModel(String depoAdi, String depoAdresi, String depoBuyuklugu, String depoUrunKategorisi) {
        DepoAdi = depoAdi;
        DepoAdresi = depoAdresi;
        DepoBuyuklugu = depoBuyuklugu;
        DepoUrunKategorisi = depoUrunKategorisi;
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

    public String getDepoUrunKategorisi() {
        return DepoUrunKategorisi;
    }

    public void setDepoUrunKategorisi(String depoUrunKategorisi) {
        DepoUrunKategorisi = depoUrunKategorisi;
    }

}

