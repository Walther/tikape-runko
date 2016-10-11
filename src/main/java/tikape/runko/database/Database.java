package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }
    
    public int update(String updateQuery, Object... params) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();
        stmt.close();

        return changes;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE ALUE (alueID integer PRIMARY KEY, nimi varchar(50));");
        lista.add("CREATE TABLE LANKA (lankaID integer PRIMARY KEY, alueID integer NOT NULL, nimi varchar(50), FOREIGN KEY(alueID) REFERENCES ALUE(alueID));");
        lista.add("CREATE TABLE KAYTTAJA (kayttajaID integer PRIMARY KEY, nimi varchar(50));");
        lista.add("CREATE TABLE VIESTI (viestiID integer PRIMARY KEY, kayttajaID integer NOT NULL, lankaID integer NOT NULL, aika date, viesti varchar(300), FOREIGN KEY(kayttajaID) REFERENCES KAYTTAJA(kayttajaID), FOREIGN KEY(lankaID) REFERENCES LANKA(lankaID));");
        lista.add("INSERT INTO KAYTTAJA (kayttajaID, nimi) VALUES (1, 'Joonas');");
        lista.add("INSERT INTO KAYTTAJA (kayttajaID, nimi) VALUES (2, 'Mari');");
        lista.add("INSERT INTO ALUE (alueID, nimi) VALUES (1, 'Opiskelu');");
        lista.add("INSERT INTO ALUE (alueID, nimi) VALUES (2, 'Elämä');");
        lista.add("INSERT INTO LANKA (lankaID, nimi, alueID) VALUES (1, 'Tikape', 1);");
        lista.add("INSERT INTO VIESTI (viestiID, kayttajaID, lankaid, aika, viesti) VALUES (1, 1, 1, 2016-10-11, 'Osaamme ohjelmoida!');");
        
        return lista;
    }
}
