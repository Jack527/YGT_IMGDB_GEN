package com.zts.jzyy.app.sqlserver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.swing.plaf.basic.BasicSliderUI;
import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * DELETE UPM_MENU WHERE MENU_ID='99990056'
 * GO
 * DECLARE @menuLevel VARCHAR(64)
 * DECLARE @maxLen INT
 * SELECT @menuLevel = ISNULL(MAX(MENU_LVL),'0') FROM dbo.UPM_MENU WHERE PAR_MENU = '10011871'
 * SET @maxLen = LEN(@menuLevel)
 * SELECT @menuLevel = RIGHT('00000000'+CAST((CAST(@menuLevel AS BIGINT)+1) AS VARCHAR(64)),8)
 * SELECT @menuLevel
 * insert into UPM_MENU(MENU_ID, MENU_NAME, PAR_MENU, MENU_LVL, MENU_PUR, MENU_LINK, MENU_STA, MENU_ICO, MENU_PLAT, MENU_BPM_FORM, MENU_DLL, MENU_DLL_ENTRY, BUSI_CODE, NODE_MODE) values (99990056, '融资专用账户指定于撤销', 10011871, @menuLevel, '1', 'apps/opp/uniform-credit/finance-trdacct-assign.html', '1', '', 0, '', '', '', '', '0')
 */
public class UpmMenuGenerator {

    private String params = "MENU_ID,MENU_NAME,PAR_MENU,MENU_LVL,MENU_PUR,MENU_LINK,MENU_STA,MENU_ICO,MENU_PLAT,MENU_BPM_FORM,MENU_DLL,MENU_DLL_ENTRY,BUSI_CODE,NODE_MODE";
    private List<String> paramList = Arrays.asList(params.split(","));
    private String basePath = "E:\\xzx\\YGT_DB2";
    private String fileName = "UPM_MENU.sql";

    private String generate(String menuId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM UPM_MENU WHERE MENU_ID=" + menuId;
        List<Map<String, Object>> queryList = jdbcTemplate.queryForList(sql);
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(queryList)) {
            Map resultMap = queryList.get(0);
            Integer parMenu = (Integer) resultMap.get("PAR_MENU");
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
        } else {
            return "";
        }
        return sb.toString();
    }

    public void generateFile() throws FileNotFoundException, UnsupportedEncodingException {
        // 创建Spring对象
        String config = "spring.xml";
        ApplicationContext app = new ClassPathXmlApplicationContext(config);
        // 创建template
        JdbcTemplate template = app.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "SELECT MENU_ID FROM UPM_MENU WHERE MENU_ID >= 99660000 AND MENU_ID < 99699999";
        List<Map<String, Object>> maps = template.queryForList(sql);
        for (Map<String, Object> map : maps) {
            generateFile(String.valueOf(map.get("MENU_ID")), template);
        }
    }

    public void generateFile(String menuId, JdbcTemplate jdbcTemplate) throws FileNotFoundException, UnsupportedEncodingException {
        String path = basePath + File.separator + fileName;

        File file = new File(path);

//        if (file.exists()) {
//            file.delete();
//        }
//        file.getParentFile().mkdirs();
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String content = generate(menuId, jdbcTemplate);

        OutputStream out = null;
        BufferedWriter writer = null;
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GB2312"));
        try {
//            out = new FileOutputStream(file, true);
//            out.write(content.getBytes());
//            out.flush();
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        UpmMenuGenerator upmMenuGenerator = new UpmMenuGenerator();
        upmMenuGenerator.generateFile();
    }
}
