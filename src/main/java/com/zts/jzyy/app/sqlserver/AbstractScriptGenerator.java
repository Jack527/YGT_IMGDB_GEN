package com.zts.jzyy.app.sqlserver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public abstract class AbstractScriptGenerator implements ScriptGenerator {
    protected String basePath;
    protected String fileName;
    protected String tableName;
    protected String parms;
    protected List<String> paramList;

    public void generateScript() {
        // 创建Spring对象
        String config = "spring.xml";
        ApplicationContext app = new ClassPathXmlApplicationContext(config);
        // 创建template
        JdbcTemplate template = app.getBean("jdbcTemplate", JdbcTemplate.class);

        generateFile("Z0051", "V0057", template);

        generateFile("QS050", "V0058", template);

        generateFile("QS200", "V0052", template);

        generateFile("QS200", "V0050", template);

        generateFile("QS200", "V0051", template);

        generateFile("QS009", "V0059", template);

        generateFile("Z0160", "V0054", template);

        generateFile("QS052", "V0020", template);

        generateFile("Z0015", "V0060", template);

        generateFile("QS103", "V0061", template);

        generateFile("QS002", "V0006", template);

        generateFile("Z0042", "V0062", template);

        generateFile("Z0337", "V0063", template);

        generateFile("Z0115", "V0008", template);

        generateFile("QS005", "V0005", template);

        generateFile("QS113", "V0055", template);

        generateFile("QS007", "V0019", template);

        generateFile("Z0125", "V0064", template);

        generateFile("QS003", "V0009", template);

        generateFile("QS001A", "V0001", template);

        generateFile("QS660", "V0065", template);

        generateFile("QS310", "V0002", template);

        generateFile("Z0188", "V0053", template);

        generateFile("Z0043", "V0056", template);

        generateFile("QS011A", "V0017", template);

        generateFile("QS122", "V0018", template);

        generateFile("Z0300", "V0010", template);

        generateFile("Z0115", "V0013", template);

        generateFile("P0022", "V0066", template);

    }

    public String generate(String oldBusiCode, String newBusiCode, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM " + tableName + " WHERE BUSI_CODE='" + oldBusiCode + "'";
        List<Map<String, Object>> oldResultList = jdbcTemplate.queryForList(sql);

        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ").append(tableName).append(" WHERE BUSI_CODE='").append(newBusiCode).append("';\n");
        if (!CollectionUtils.isEmpty(oldResultList)) {
            for (Map<String, Object> oldResult : oldResultList) {
                sb.append("INSERT INTO ").append(tableName).append(" (BUSI_CODE,").append(parms).append(") VALUES ('").append(newBusiCode).append("',");
                for (String param : paramList) {
                    Object result = oldResult.get(param);
                    if (result instanceof String) {
                        result = StringUtils.isEmpty(result) ? "" : result;
                        sb.append("'").append(result).append("',");
                    } else if (result instanceof Integer) {
                        sb.append(result).append(",");
                    } else if (result instanceof Timestamp) {
                        result = result == null ? "" : result;
                        sb.append("'").append(result).append("',");
                    } else if (result instanceof BigDecimal) {
                        sb.append(result).append(",");
                    }
                }
                String str = sb.substring(0, sb.length() - 1);
                sb = new StringBuffer(str);
                sb.append(")\n");
            }
            sb.append("GO\n");
        }
        return sb.toString();
    }


    public void generateFile(String oldBusiCode, String newBusiCode, JdbcTemplate jdbcTemplate) {
        String path = basePath + File.separator + newBusiCode + File.separator + fileName;

        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = generate(oldBusiCode, newBusiCode, jdbcTemplate);

        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(content.getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
