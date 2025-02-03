package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.LanguageDAO;
import lk.ijse.gdse.traveler.entity.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LanguageDAOImpl implements LanguageDAO {

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SqlUtil.execute("select language_id from languages order by language_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // last id
            String substring = lastId.substring(1); // extract numbers
            int i = Integer.parseInt(substring); // convert the numbers part to int
            int newIdIndex = i + 1; // increment
            return String.format("L%03d", newIdIndex); // return the new id in string
        }
        return "L001"; // return the default ID
    }

    @Override
    public boolean save(Language language) throws SQLException {
        return SqlUtil.execute(
                "insert into languages values (?,?)",
                language.getLangId(),
                language.getLanguage()
        );
    }

    @Override
    public ArrayList<Language> getAll() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from languages");

        ArrayList<Language> languages = new ArrayList<>();

        while (rst.next()) {
            Language language = new Language(
                    rst.getString(1),
                    rst.getString(2)
            );
            languages.add(language);
        }
        return languages;
    }

    @Override
    public boolean update(Language language) throws SQLException {
        return SqlUtil.execute(
                "update languages set language_name=? where language_id=?",
                language.getLanguage(),
                language.getLangId()
        );
    }

    @Override
    public boolean delete(String langId) throws SQLException {
        return SqlUtil.execute("delete from languages where language_id=?", langId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SqlUtil.execute("select language_id from languages");

        ArrayList<String> languageIds = new ArrayList<>();

        while (rst.next()) {
            languageIds.add(rst.getString(1));
        }

        return languageIds;
    }

    @Override
    public Language findById(String selectedLanguageId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from languages where language_id=?", selectedLanguageId);

        if (rst.next()) {
            return new Language(
                    rst.getString(1),
                    rst.getString(2)
            );
        }
        return null;
    }
}
