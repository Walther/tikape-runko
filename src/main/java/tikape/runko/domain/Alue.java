package tikape.runko.domain;

public class Alue {

    private Integer alueid;
    private String nimi;
    private Integer viestiMaara;
    private String aika;

    public Alue(Integer id, String nimi, Integer viestiMaara, String aika) {
        this.alueid = id;
        this.nimi = nimi;
        this.viestiMaara = viestiMaara;
        this.aika = aika;
    }

    public Alue(String nimi) {
        this.alueid = null;
        this.nimi = nimi;
        this.viestiMaara = 0;
        this.aika = null;
    }

    public Integer getId() {
        return alueid;
    }

    public void setId(Integer id) {
        this.alueid = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Integer getViestiMaara() {
        return viestiMaara;
    }
    
    public String getAika() {
        return aika;
    }

}
