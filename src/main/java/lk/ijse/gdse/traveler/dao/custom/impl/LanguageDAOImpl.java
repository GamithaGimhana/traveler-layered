package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.LanguageDAO;
import lk.ijse.gdse.traveler.dto.LanguageDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LanguageDAOImpl implements LanguageDAO {
    public String getNextLangId() throws SQLException {
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

    public boolean saveLang(LanguageDTO languageDTO) throws SQLException {
        return SqlUtil.execute(
                "insert into languages values (?,?)",
                languageDTO.getLangId(),
                languageDTO.getLanguage()
        );
    }

    public ArrayList<LanguageDTO> getAllLanguages() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from languages");

        ArrayList<LanguageDTO> languagesDTOS = new ArrayList<>();

        while (rst.next()) {
            LanguageDTO languagesDTO = new LanguageDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
            languagesDTOS.add(languagesDTO);
        }
        return languagesDTOS;
    }

    public boolean updateLang(LanguageDTO languagesDTO) throws SQLException {
        return SqlUtil.execute(
                "update languages set language_name=? where language_id=?",
                languagesDTO.getLanguage(),
                languagesDTO.getLangId()
        );
    }

    public boolean deletLang(String langId) throws SQLException {
        return SqlUtil.execute("delete from languages where language_id=?", langId);
    }

    public ArrayList<String> getAllLangIds() throws SQLException {
        ResultSet rst = SqlUtil.execute("select language_id from languages");

        ArrayList<String> languageIds = new ArrayList<>();

        while (rst.next()) {
            languageIds.add(rst.getString(1));
        }

        return languageIds;
    }

    public LanguageDTO findById(String selectedLanguageId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from languages where language_id=?", selectedLanguageId);

        if (rst.next()) {
            return new LanguageDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
        }
        return null;
    }
}
