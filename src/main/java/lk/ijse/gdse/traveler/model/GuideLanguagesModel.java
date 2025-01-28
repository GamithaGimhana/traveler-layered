package lk.ijse.gdse.traveler.model;

import lk.ijse.gdse.traveler.dto.GuideLanguagesDTO;
import lk.ijse.gdse.traveler.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuideLanguagesModel {
    public boolean saveGLang(GuideLanguagesDTO guideLanguagesDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into guide_languages values (?,?)",
                guideLanguagesDTO.getGuideId(),
                guideLanguagesDTO.getLangId()
        );
    }

    public ArrayList<GuideLanguagesDTO> getAllGLanguages() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from guide_languages");

        ArrayList<GuideLanguagesDTO> guideLanguagesDTOS = new ArrayList<>();

        while (rst.next()) {
            GuideLanguagesDTO guideLanguagesDTO = new GuideLanguagesDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
            guideLanguagesDTOS.add(guideLanguagesDTO);
        }
        return guideLanguagesDTOS;
    }

    public boolean updateGLang(GuideLanguagesDTO guideLanguagesDTO) throws SQLException {
        return CrudUtil.execute(
                "update guide_languages set guide_id=? where language_id=?",
                guideLanguagesDTO.getGuideId(),
                guideLanguagesDTO.getLangId()
        );
    }

    public boolean deleteGLang(String guideLangId) throws SQLException {
        return CrudUtil.execute("delete from guide_languages where language_id=?", guideLangId);
    }

    public ArrayList<String> getAllGLangIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select language_id from guide_languages");

        ArrayList<String> guideLanguageIds = new ArrayList<>();

        while (rst.next()) {
            guideLanguageIds.add(rst.getString(1));
        }

        return guideLanguageIds;
    }

    public GuideLanguagesDTO findById(String selectedGuideLanguageId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from guide_languages where language_id=?", selectedGuideLanguageId);

        if (rst.next()) {
            return new GuideLanguagesDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
        }
        return null;
    }
}
