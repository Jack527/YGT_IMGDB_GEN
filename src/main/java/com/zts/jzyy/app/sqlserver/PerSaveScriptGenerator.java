package com.zts.jzyy.app.sqlserver;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class PerSaveScriptGenerator extends AbstractScriptGenerator {
    @Override
    public String generate(String oldBusiCode, String newBusiCode, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM " + tableName + " WHERE BUSI_CODE='" + newBusiCode + "'";
        List<Map<String, Object>> queryList = jdbcTemplate.queryForList(sql);

        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ").append(tableName).append(" WHERE BUSI_CODE='").append(newBusiCode).append("'").append("\n");
        if (!CollectionUtils.isEmpty(queryList)) {
            for (Map<String, Object> resultMap : queryList) {
                sb.append("INSERT INTO (").append(this.parms).append(") VALUES (");
                for (String s : paramList) {
                    Object result = resultMap.get(s);
                    if (result instanceof String) {
                        sb.append("'").append(result).append("',");
                    } else if (result instanceof Integer) {
                        sb.append(result).append(",");
                    } else if (null == result) {
                        sb.append("null").append(",");
                    }
                }
                String sa = sb.substring(0, sb.length() - 1);
                sb = new StringBuffer(sa);
                sb.append(")\n");
            }
        }
        sb.append("GO\n");
        return sb.toString();
    }
}
