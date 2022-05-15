package mf.bm443.depo.models;

public class UrunlerimModel {

    private String urunAdi, urunDeposu, urunKategori, urunMiktar;


    UrunlerimModel() {
        //Boş Cons.
    }

    public UrunlerimModel(String urunAdi, String urunDeposu, String urunKategori, String urunMiktar) {
        this.urunAdi = urunAdi;
        this.urunDeposu = urunDeposu;
        this.urunKategori = urunKategori;
        this.urunMiktar = urunMiktar;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getUrunDeposu() {
        return urunDeposu;
    }

    public void setUrunDeposu(String urunDeposu) {
        this.urunDeposu = urunDeposu;
    }

    public String getUrunKategori() {
        return urunKategori;
    }

    public void setUrunKategori(String urunKategori) {
        this.urunKategori = urunKategori;
    }

    public String getUrunMiktar() {
        return urunMiktar;
    }

    public void setUrunMiktar(String urunMiktar) {
        this.urunMiktar = urunMiktar;
    }

}
