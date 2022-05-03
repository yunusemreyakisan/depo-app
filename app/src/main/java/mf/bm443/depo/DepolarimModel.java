package mf.bm443.depo;

public class DepolarimModel {
    private String DepoAdi, DepoAdresi, DepoBuyukluk, DepoKategori;

    public DepolarimModel(String depoAdi, String depoAdresi, String depoBuyukluk, String depoKategori) {
        DepoAdi = depoAdi;
        DepoAdresi = depoAdresi;
        DepoBuyukluk = depoBuyukluk;
        DepoKategori = depoKategori;
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

    public String getDepoBuyukluk() {
        return DepoBuyukluk;
    }

    public void setDepoBuyukluk(String depoBuyukluk) {
        DepoBuyukluk = depoBuyukluk;
    }

    public String getDepoKategori() {
        return DepoKategori;
    }

    public void setDepoKategori(String depoKategori) {
        DepoKategori = depoKategori;
    }

    public DepolarimModel() {}


}
