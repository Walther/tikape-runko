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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka WHERE lankaid = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer lankaid = rs.getInt("lankaid");
        String nimi = rs.getString("nimi");
        Integer alueid = rs.getInt("alueid");

        Lanka o = new Lanka(lankaid, nimi, alueid);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Lanka> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka");

        ResultSet rs = stmt.executeQuery();
        List<Lanka> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer lankaid = rs.getInt("lankaid");
            Integer alueid = rs.getInt("alueid");
            String nimi = rs.getString("nimi");

            alueet.add(new Lanka(lankaid, nimi, alueid));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }
    
    public List<Lanka> findAllByAlue(Integer alueid) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Lanka WHERE alueid = ?");
        stmt.setObject(1, alueid);
        
        ResultSet rs = stmt.executeQuery();
        List<Lanka> langat = new ArrayList<>();
        while (rs.next()) {
            Integer lankaid = rs.getInt("lankaid");
            String nimi = rs.getString("nimi");

            langat.add(new Lanka(lankaid, nimi, alueid));
        }

        rs.close();
        stmt.close();
        connection.close();

        return langat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
