package tikape.runko.domain;

public class Lanka {

    private Integer lankaid;
    private String nimi;
    private Integer alueid;
    private Integer viestiMaara;
    private String aika;

    public Lanka(Integer id, String nimi, Integer alueid, Integer viestiMaara, String aika) {
        this.lankaid = id;
        this.nimi = nimi;
        this.alueid = alueid;
        this.viestiMaara = viestiMaara;
        this.aika = aika;
    }
    public Lanka(String nimi, Integer alueid) {
        this.lankaid = null;
        this.nimi = nimi;
        this.alueid = alueid;
        this.viestiMaara = 0;
        this.aika = null;
    }
    public Integer getId() {
        return lankaid;
    }

    public void setId(Integer id) {
        this.lankaid = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Integer getAlueId() {
        return alueid;
    }

    public void setAlueId(Integer id) {
        this.alueid = alueid;
    }
    
        public Integer getViestiMaara() {
        return viestiMaara;
    }
        public String getAika() {
        return aika;
    }



}
