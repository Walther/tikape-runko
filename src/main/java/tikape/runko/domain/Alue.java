package tikape.runko.domain;

public class Alue {

    private Integer alueid;
    private String nimi;

    public Alue (Integer id, String nimi) {
        this.alueid = id;
        this.nimi = nimi;
    }
    public Alue (String nimi) {
        this.alueid = null;
        this.nimi = nimi;
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

}

