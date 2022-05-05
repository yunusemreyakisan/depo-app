package mf.bm443.depo;

public class DepolarimModel {
    private String DepoAdi, DepoAdresi;

    public DepolarimModel(String depoAdi, String depoAdresi) {
        DepoAdi = depoAdi;
        DepoAdresi = depoAdresi;
    }

    public String getDepoAdi() {
        return DepoAdi;
    }

    public void setDepoAdi(String depoAdi) {
        DepoAdi = depoAdi;
    }

    public String getDepoAdresi() {
        return DepoAdresi;
    }

    public void setDepoAdresi(String depoAdresi) {
        DepoAdresi = depoAdresi;
    }

    public DepolarimModel() {}


}

