package tikape.runko.domain;

public class Kayttaja {

    private Integer kayttajaid;
    private String nimi;

    public Kayttaja(Integer id, String nimi) {
        this.kayttajaid = id;
        this.nimi = nimi;
    }
    
    public Kayttaja(String nimi) {
        this.kayttajaid = null;
        this.nimi = nimi;
    }

    public Integer getId() {
        return kayttajaid;
    }

    public void setId(Integer id) {
        this.kayttajaid = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

}
