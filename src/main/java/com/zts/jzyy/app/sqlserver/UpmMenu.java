package com.zts.jzyy.app.sqlserver;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UpmMenu extends AbstractScriptGenerator {

    public UpmMenu() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "02.UPM_MENU.sql";
        this.parms = "MENU_ID,MENU_NAME,PAR_MENU,MENU_LVL,MENU_PUR,MENU_LINK,MENU_STA,MENU_ICO,MENU_PLAT,MENU_BPM_FORM,MENU_DLL,MENU_DLL_ENTRY,BUSI_CODE,NODE_MODE";
        this.paramList = Arrays.asList(parms.split(","));
    }

    @Override
    public String generate(String oldBusiCode, String newBusiCode, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM UPM_MENU WHERE BUSI_CODE='" + newBusiCode + "'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(maps)) {
            StringBuffer sb = new StringBuffer();
            Map<String, Object> resultMap = maps.get(0);
            Integer parMenu = (Integer) resultMap.get("PAR_MENU");
            String menuId = String.valueOf(resultMap.get("MENU_ID"));
            sb.append("DELETE FROM UPM_MENU WHERE MENU_ID='").append(menuId).append("'\n");
            sb.append("DECLARE @menuLevel VARCHAR(64)\n");
            sb.append("DECLARE @maxLen INT\n");
            sb.append("SELECT @menuLevel = ISNULL(MAX(MENU_LVL),'0') FROM dbo.UPM_MENU WHERE PAR_MENU='").append(parMenu).append("'\n");
            sb.append("SET @maxLen = LEN(@menuLevel)\n");
            sb.append("SELECT @menuLevel = RIGHT('00000000'+CAST((CAST(@menuLevel AS BIGINT)+1) AS VARCHAR(64)),8)\n");
            sb.append("SELECT @menuLevel\n");
            sb.append("INSERT INTO UPM_MENU(MENU_ID, MENU_NAME, PAR_MENU, MENU_LVL, MENU_PUR, MENU_LINK, MENU_STA, MENU_ICO, MENU_PLAT, MENU_BPM_FORM, MENU_DLL, MENU_DLL_ENTRY, BUSI_CODE, NODE_MODE) VALUES (");
            for (String param : paramList) {
                Object result = resultMap.get(param);
                if (param.equals("MENU_LVL")) {
                    sb.append("@menuLevel,");
                } else {
                    if (result instanceof String) {
                        sb.append("'").append(result).append("',");
                    } else if (result instanceof Integer) {
                        sb.append(result).append(",");
                    } else if (null == result) {
                        sb.append("NULL,");
                    } else if (result instanceof BigDecimal) {
                        sb.append(result).append(",");
                    }
                }
            }
            String substring = sb.substring(0, sb.length() - 1);
            sb = new StringBuffer(substring);
            sb.append(")\nGO\n\n");
            return sb.toString();
        }
        return "";
    }
}
