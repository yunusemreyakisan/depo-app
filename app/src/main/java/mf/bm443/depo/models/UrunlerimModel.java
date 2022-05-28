package mf.bm443.depo.models;

public class UrunlerimModel {

    private String urunAdi, urunDeposu, urunMiktar, key, eklenmeTarihi;


    public UrunlerimModel() {
        //Boş Cons.
    }



    public UrunlerimModel(String urunAdi, String urunDeposu, String urunMiktar, String key, String eklenmeTarihi) {
        this.urunAdi = urunAdi;
        this.urunDeposu = urunDeposu;
        this.urunMiktar = urunMiktar;
        this.key = key;
        this.eklenmeTarihi = eklenmeTarihi;
    }

    public String getEklenmeTarihi() {
        return eklenmeTarihi;
    }

    public void setEklenmeTarihi(String eklenmeTarihi) {
        this.eklenmeTarihi = eklenmeTarihi;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getUrunMiktar() {
        return urunMiktar;
    }

    public void setUrunMiktar(String urunMiktar) {
        this.urunMiktar = urunMiktar;
    }

}
