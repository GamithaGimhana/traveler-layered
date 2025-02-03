package lk.ijse.gdse.traveler.dao.custom.impl;

import lk.ijse.gdse.traveler.dao.SqlUtil;
import lk.ijse.gdse.traveler.dao.custom.GuideLanguagesDAO;
import lk.ijse.gdse.traveler.entity.GuideLanguages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuideLanguagesDAOImpl implements GuideLanguagesDAO {
    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(GuideLanguages guideLanguages) throws SQLException {
        return SqlUtil.execute(
                "insert into guide_languages values (?,?)",
                guideLanguages.getGuideId(),
                guideLanguages.getLangId()
        );
    }

    @Override
    public ArrayList<GuideLanguages> getAll() throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from guide_languages");

        ArrayList<GuideLanguages> guideLanguages = new ArrayList<>();

        while (rst.next()) {
            GuideLanguages guideLanguage = new GuideLanguages(
                    rst.getString(1),
                    rst.getString(2)
            );
            guideLanguages.add(guideLanguage);
        }
        return guideLanguages;
    }

    @Override
    public boolean update(GuideLanguages guideLanguages) throws SQLException {
        return SqlUtil.execute(
                "update guide_languages set guide_id=? where language_id=?",
                guideLanguages.getGuideId(),
                guideLanguages.getLangId()
        );
    }

    @Override
    public boolean delete(String guideLangId) throws SQLException {
        return SqlUtil.execute("delete from guide_languages where language_id=?", guideLangId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SqlUtil.execute("select language_id from guide_languages");

        ArrayList<String> guideLanguageIds = new ArrayList<>();

        while (rst.next()) {
            guideLanguageIds.add(rst.getString(1));
        }

        return guideLanguageIds;
    }

    @Override
    public GuideLanguages findById(String selectedGuideLanguageId) throws SQLException {
        ResultSet rst = SqlUtil.execute("select * from guide_languages where language_id=?", selectedGuideLanguageId);

        if (rst.next()) {
            return new GuideLanguages(
                    rst.getString(1),
                    rst.getString(2)
            );
        }
        return null;
    }
}
