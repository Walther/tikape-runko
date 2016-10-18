package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE viestiid = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer viestiid = rs.getInt("viestiid");
        String viesti = rs.getString("viesti");
        Integer lankaid = rs.getInt("lankaid");
        Integer kayttajaid = rs.getInt("kayttajaid");
        String aika = rs.getString("aika");
        
        Viesti o = new Viesti(viestiid, viesti, lankaid, kayttajaid, aika);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
        Integer viestiid = rs.getInt("viestiid");
        String viesti = rs.getString("viesti");
        Integer lankaid = rs.getInt("lankaid");
        Integer kayttajaid = rs.getInt("kayttajaid");
        String aika = rs.getString("aika");

            viestit.add(new Viesti(viestiid, viesti, lankaid, kayttajaid, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    public List<Viesti> findAllByLanka(Integer lankaid) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE lankaid = ?");
        stmt.setObject(1, lankaid);
        
        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
        Integer viestiid = rs.getInt("viestiid");
        String viesti = rs.getString("viesti");
        Integer kayttajaid = rs.getInt("kayttajaid");
        String aika = rs.getString("aika");

            viestit.add(new Viesti(viestiid, viesti, lankaid, kayttajaid, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    public void save(Viesti viesti) throws SQLException {
        this.database.update("INSERT INTO VIESTI(viestiid, viesti, lankaid, kayttajaid, aika) VALUES (?, ?, ?, ?, ?)", viesti.getId(), viesti.getViesti(), viesti.getLankaId(), viesti.getKayttajaId(), viesti.getAika());
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
