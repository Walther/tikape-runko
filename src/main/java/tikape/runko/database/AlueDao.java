package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;

public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database database) {
        this.database = database;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT alue.alueID, alue.nimi as aluenimi, count(viesti) as  viestiMaara, aika  from alue left join Lanka on Lanka.alueID = alue.alueID left join Viesti on Viesti.lankaID = lanka.lankaID WHERE alue.alueid = ? group by alue.alueID order by aika desc;");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer alueid = rs.getInt("alueid");
        String nimi = rs.getString("aluenimi");
        Integer viestiMaara = rs.getInt("viestiMaara");
        String aika = rs.getString("aika");

        Alue o = new Alue(alueid, nimi, viestiMaara, aika);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Alue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT alue.alueID, alue.nimi as aluenimi, count(viesti) as  viestiMaara, aika  from alue left join Lanka on Lanka.alueID = alue.alueID left join Viesti on Viesti.lankaID = lanka.lankaID group by alue.alueID order by aika desc;");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer alueid = rs.getInt("alueid");
            String nimi = rs.getString("aluenimi");
            Integer viestiMaara = rs.getInt("viestiMaara");
            String aika = rs.getString("aika");

            alueet.add(new Alue(alueid, nimi, viestiMaara, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    public void save(Alue alue) throws SQLException {
        this.database.update("INSERT INTO ALUE (alueid, nimi) VALUES (?, ?)", alue.getId(), alue.getNimi());
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
