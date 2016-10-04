package tikape.runko.domain;

public class Viesti {

    private Integer viestiid;
    private Integer kayttajaid;
    private String aika;
    private String viesti;
    private Integer lankaid;

    public Viesti(Integer id, String viesti, Integer lankaid, Integer kayttajaid, String aika) {
        this.viestiid = id;
        this.viesti = viesti;
        this.lankaid = lankaid;
        this.kayttajaid = kayttajaid;
        this.aika = aika;

    }

    public Integer getId() {
        return viestiid;
    }

    public void setId(Integer id) {
        this.viestiid = id;
    }

    public String getViesti() {
        return viesti;
    }

    public void setViesti(String nimi) {
        this.viesti = viesti;
    }

    public Integer getLankaId() {
        return lankaid;
    }

    public void setLankaId(Integer id) {
        this.lankaid = lankaid;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }
    
    public Integer getKayttajaId() {
        return kayttajaid;
    }

    public void setKayttajaId(Integer id) {
        this.kayttajaid = kayttajaid;
    }

}
