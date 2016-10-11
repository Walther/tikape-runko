package tikape.runko.domain;

public class Lanka {

    private Integer lankaid;
    private String nimi;
    private Integer alueid;

    public Lanka(Integer id, String nimi, Integer alueid) {
        this.lankaid = id;
        this.nimi = nimi;
        this.alueid = alueid;
    }
    public Lanka(String nimi, Integer alueid) {
        this.lankaid = null;
        this.nimi = nimi;
        this.alueid = alueid;
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

}
