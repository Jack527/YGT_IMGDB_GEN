package com.zts.jzyy.app.sqlserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ImgCfg {

    public static String basePath = "E:\\xzx\\YGT_DB";

    public static String fileName = "14.OPP_IMG_BIZ_CFG.sql";

    public static void main(String[] args) throws Exception {
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

    }

    public static String generate(String oldBusiCode, String newBusiCode, JdbcTemplate jdbcTemplate) {

        String sql = "select IMG_CLS from OPP_IMG_BIZ_CFG where BUSI_CODE='" + oldBusiCode + "'";

        List<Map<String, Object>> imgCls = jdbcTemplate.queryForList(sql);

        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM OPP_BUSI_IMG_CONDITION WHERE IMG_CFG_SN IN (SELECT IMG_CFG_SN FROM OPP_IMG_BIZ_CFG WHERE BUSI_CODE='").append(newBusiCode).append("')\n");
        sb.append("DELETE FROM OPP_IMG_BIZ_CFG WHERE BUSI_CODE='").append(newBusiCode).append("';\n");
        for (Map<String, Object> map : imgCls) {
            String type = (String) map.get("IMG_CLS");

//			sb.append("DELETE FROM OPP_BUSI_IMG_CONDITION where IMG_CFG_SN IN "
//					+ "(select IMG_CFG_SN from OPP_IMG_BIZ_CFG where BUSI_CODE IN ('").append(newBusiCode)
//					.append("') and IMG_CLS IN ('").append(type).append("'))").append("\n");
//
//			sb.append("DELETE FROM OPP_IMG_BIZ_CFG where BUSI_CODE IN ('").append(newBusiCode)
//					.append("') and IMG_CLS IN ('").append(type).append("')").append("\n");

            sb.append("DECLARE @MAX_IMG_CFG_SN BIGINT").append("\n");

            sb.append("DECLARE @MAX_CONDITION_ID BIGINT").append("\n");

            sb.append("INSERT INTO SEQ_IMG_BIZ_CFG (SEQVAL) VALUES ('A')").append("\n");

            sb.append("INSERT INTO SEQ_BUSI_IMG_CONDITION (SEQVAL) VALUES ('A')").append("\n");

            sb.append("SELECT @MAX_IMG_CFG_SN = MAX(SEQID) FROM SEQ_IMG_BIZ_CFG ").append("\n");

            sb.append("SELECT @MAX_CONDITION_ID = MAX(SEQID) FROM SEQ_BUSI_IMG_CONDITION ").append("\n");

            sb.append("DELETE FROM SEQ_IMG_BIZ_CFG ").append("\n");

            sb.append("DELETE FROM SEQ_BUSI_IMG_CONDITION ").append("\n");

            sb.append(
                    "INSERT INTO OPP_IMG_BIZ_CFG(IMG_CFG_SN, BUSI_CODE, IMG_CLS, COLLECT_MUST, WATER_MARK, IMG_ORDER, REF_BUSI_TYPE) VALUES (@MAX_IMG_CFG_SN, '")
                    .append(newBusiCode).append("', '").append(type).append("', '1', NULL, 1, '')").append("\n");

            String subSql = "select CONDITION_VALUE from OPP_BUSI_IMG_CONDITION where "
                    + " IMG_CFG_SN IN (select IMG_CFG_SN from OPP_IMG_BIZ_CFG where BUSI_CODE IN ( ? ) and IMG_CLS IN ( ? ))";

            List<Map<String, Object>> conditions = jdbcTemplate.queryForList(subSql,
                    new Object[]{oldBusiCode, type});
            if (conditions == null || conditions.size() == 0) {
                sb.append(";");
            } else {
                for (Map<String, Object> con : conditions) {
                    String conStr = (String) con.get("CONDITION_VALUE");
                    sb.append(
                            "INSERT INTO OPP_BUSI_IMG_CONDITION(CONDITION_ID, ATOM_CODE, ATOM_PARAM, CONDITION_VALUE, IMG_CFG_SN, CONDITION_TYPE) VALUES ")
                            .append("(@MAX_CONDITION_ID, '', '', '").append(conStr).append("', @MAX_IMG_CFG_SN, '0')")
                            .append(";").append("\n");
                }
            }
        }
        sb.append("GO\n");

        return sb.toString();

    }

    public static void generateFile(String oldBusiCode, String newBusiCode, JdbcTemplate jdbcTemplate)
            throws Exception {

        String path = basePath + File.separator + newBusiCode + File.separator + fileName;

        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }
        file.getParentFile().mkdirs();
        file.createNewFile();
        String content = generate(oldBusiCode, newBusiCode, jdbcTemplate);

        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(content.getBytes());
            out.flush();
        } catch (Exception e) {
            if (out != null) {
                out.close();
            }
            throw e;
        }

        out.close();

    }

}
