package tikape.runko;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TimeZone;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KayttajaDao;
import tikape.runko.database.LankaDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Kayttaja;
import tikape.runko.domain.Lanka;
import tikape.runko.domain.Viesti;

public class Main {

    public static void main(String[] args) throws Exception {
		// asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

		// käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:keskustelu.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        } 

        Database database = new Database(jdbcOsoite);

        KayttajaDao kayttajaDao = new KayttajaDao(database);
        AlueDao alueDao = new AlueDao(database);
        LankaDao lankaDao = new LankaDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/kayttajat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kayttajat", kayttajaDao.findAll());

            return new ModelAndView(map, "kayttajat");
        }, new ThymeleafTemplateEngine());

        get("/kayttajat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kayttaja", kayttajaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "kayttaja");
        }, new ThymeleafTemplateEngine());

        get("/alueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());
            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());

        get("/alueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", alueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("langat", lankaDao.findAllByAlue(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/langat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("lanka", lankaDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestit", viestiDao.findAllByLanka(Integer.parseInt(req.params("id"))));
            map.put("kayttajat", kayttajaDao.findAll());

            return new ModelAndView(map, "lanka");
        }, new ThymeleafTemplateEngine());

        post("/uusialue", (req, res) -> {
            String nimi = req.queryParams("nimi");
            System.out.println("Vastaanotettiin" + nimi);
            Alue alue = new Alue(nimi);
            alueDao.save(alue);
            res.redirect("/alueet");
            return nimi;
        });

        post("/uusilanka", (req, res) -> {
            String nimi = req.queryParams("nimi");
            Integer alueid = Integer.parseInt(req.queryParams("alueid"));
            System.out.println("Vastaanotettiin nimi: " + nimi);
            System.out.println("Vastaanotettiin alueID: " + alueid);
            Lanka lanka = new Lanka(nimi, alueid);
            lankaDao.save(lanka);
            res.redirect("/alueet/" + alueid);
            return nimi;
        });

        post("/uusiviesti", (req, res) -> {
            String viestiteksti = req.queryParams("viesti");
            Integer lankaid = Integer.parseInt(req.queryParams("lankaid"));
            Integer kayttajaid = Integer.parseInt(req.queryParams("kayttajaid"));

            // Time handling, from http://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);
            String aika = df.format(new Date());

            Viesti viesti = new Viesti(viestiteksti, lankaid, kayttajaid, aika);
            viestiDao.save(viesti);
            
            res.redirect("/langat/" + lankaid);
            return viesti;
        });
        
        post("/uusikayttaja", (req, res) -> {
            String nimi = req.queryParams("nimi");
            System.out.println("Vastaanotettiin" + nimi);
            Kayttaja kayttaja = new Kayttaja(nimi);
            kayttajaDao.save(kayttaja);
            res.redirect("/kayttajat");
            return nimi;
        });
    }
}
