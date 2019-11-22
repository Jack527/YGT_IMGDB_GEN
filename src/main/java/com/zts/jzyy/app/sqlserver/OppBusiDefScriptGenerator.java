package com.zts.jzyy.app.sqlserver;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OppBusiDefScriptGenerator extends AbstractScriptGenerator {
    public OppBusiDefScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "01.OPP_BUSI_DEF.sql";
        this.tableName = "OPP_BUSI_DEF";
        this.parms = "BUSI_CODE,BUSI_NAME,PROCDEF_KEY,PROCDEF_KEY_2,PRIO_LVL,BUSI_SCOPE,LAND_PATTERN,BATCH_YN,ENABLE_ALLDAY,HIST_URL,IMG_NODE,DEPENDENCY";
        this.paramList = Arrays.asList(parms.split(","));
    }

    @Override
    public String generate(String oldBusiCode, String newBusiCode, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM OPP_BUSI_DEF WHERE BUSI_CODE='" + newBusiCode + "'";
        List<Map<String, Object>> queryList = jdbcTemplate.queryForList(sql);

        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM OPP_BUSI_DEF WHERE BUSI_CODE='").append(newBusiCode).append("'\n");
        if (!CollectionUtils.isEmpty(queryList)) {
            for (Map<String, Object> map : queryList) {
                sb.append("INSERT INTO ").append(this.tableName).append(" (").append(this.parms).append(") VALUES (");
                for (String param : paramList) {
                    Object result = map.get(param);
                    if (result instanceof String) {
                        sb.append("'").append(result).append("',");
                    } else if (result instanceof Integer) {
                        sb.append(result).append(",");
                    } else if (null == result) {
                        sb.append("NULL").append(",");
                    }
                }
                String s = sb.substring(0, sb.length() - 1);
                sb = new StringBuffer(s);
                sb.append(")\n");
            }
        }
        sb.append("GO\n");
        return sb.toString();
    }
}
