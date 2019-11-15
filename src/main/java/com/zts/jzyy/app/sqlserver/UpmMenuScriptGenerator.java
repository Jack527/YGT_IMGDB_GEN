package com.zts.jzyy.app.sqlserver;

import org.springframework.jdbc.core.JdbcTemplate;

public class UpmMenuScriptGenerator extends AbstractScriptGenerator {
    public String generate(String oldBusiCode, String newBusiCode, JdbcTemplate template) {
        String sql = "SELECT * FROM UPM_MENU WHERE BUSI_CODE'" + oldBusiCode + "'";

        return null;
    }
}
