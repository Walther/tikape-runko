package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Lanka;

public class LankaDao implements Dao<Lanka, Integer> {

    private Database database;

    public LankaDao(Database database) {
        this.database = database;
    }

    @Override
    public Lanka findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT lanka.lankaid, nimi, alueid, count(viesti) AS viestiMaara, aika FROM Lanka LEFT JOIN Viesti ON Viesti.lankaID = lanka.lankaID WHERE lanka.lankaid = ? group by lanka.lankaID order by viesti.aika desc");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer lankaid = rs.getInt("lankaid");
        String nimi = rs.getString("nimi");
        Integer alueid = rs.getInt("alueid");
        Integer viestiMaara = rs.getInt("viestiMaara");
        String aika = rs.getString("aika");

        Lanka o = new Lanka(lankaid, nimi, alueid, viestiMaara, aika);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Lanka> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT lanka.lankaid, nimi, alueid, count(viesti) AS viestiMaara, aika FROM Lanka LEFT JOIN Viesti ON Viesti.lankaID = lanka.lankaID group by lanka.lankaID order by viesti.aika desc");

        ResultSet rs = stmt.executeQuery();
        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer lankaid = rs.getInt("lankaid");
            Integer alueid = rs.getInt("alueid");
            String nimi = rs.getString("nimi");
            Integer viestiMaara = rs.getInt("viestiMaara");
            String aika = rs.getString("aika");

            langat.add(new Lanka(lankaid, nimi, alueid, viestiMaara, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }
    
    public List<Lanka> findAllByAlue(Integer alueid) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(" SELECT DISTINCT lanka.lankaid, nimi, alueid, count(viesti) AS viestiMaara, aika FROM Lanka LEFT JOIN Viesti ON Viesti.lankaID = lanka.lankaID WHERE alueid = ? group by lanka.lankaID order by viesti.aika desc");
        stmt.setObject(1, alueid);
        
        ResultSet rs = stmt.executeQuery();
        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer lankaid = rs.getInt("lankaid");
            String nimi = rs.getString("nimi");
            Integer viestiMaara = rs.getInt("viestiMaara");
            String aika = rs.getString("aika");

            langat.add(new Lanka(lankaid, nimi, alueid, viestiMaara, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }
    
   
    public void save(Lanka lanka) throws SQLException {
        this.database.update("INSERT INTO LANKA (lankaid, nimi, alueid) VALUES (?, ?, ?)", lanka.getId(), lanka.getNimi(), lanka.getAlueId());
    }
    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
