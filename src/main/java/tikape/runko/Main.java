package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KayttajaDao;
import tikape.runko.database.LankaDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Lanka;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelu.db");
        database.init();

        KayttajaDao kayttajaDao = new KayttajaDao(database);
        AlueDao alueDao = new AlueDao(database);
        LankaDao lankaDao = new LankaDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

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

            return new ModelAndView(map, "lanka");
        }, new ThymeleafTemplateEngine());

        post("/uusialue", (req, res) -> {
            String nimi= req.queryParams("nimi");
            System.out.println("Vastaanotettiin" + nimi);
            Alue alue = new Alue(nimi);
            alueDao.save(alue);
            return nimi;
        });
        
        post("/uusilanka", (req, res) -> {
            String nimi= req.queryParams("nimi");
            Integer alueid = Integer.parseInt(req.queryParams("alueid"));
            System.out.println("Vastaanotettiin" + nimi);
            Lanka lanka = new Lanka(nimi, alueid);
            lankaDao.save(lanka);
            return nimi;
        });

    }
}
