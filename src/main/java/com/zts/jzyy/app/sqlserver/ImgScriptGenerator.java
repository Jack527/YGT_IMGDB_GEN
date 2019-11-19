package com.zts.jzyy.app.sqlserver;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * 业务影像脚本
 */
public class ImgScriptGenerator extends AbstractScriptGenerator {

    public ImgScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "OPP_BUSI_IMG_CONDITION.sql";
    }

    public String generate(String oldBusiCode, String newBusiCode, JdbcTemplate jdbcTemplate) {
        String sql = "select IMG_CLS from OPP_IMG_BIZ_CFG where BUSI_CODE='" + oldBusiCode + "'";

        List<Map<String, Object>> imgCls = jdbcTemplate.queryForList(sql);

        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM OPP_BUSI_IMG_CONDITION WHERE IMG_CFG_SN IN (SELECT IMG_CFG_SN FROM OPP_IMG_BIZ_CFG WHERE BUSI_CODE='").append(newBusiCode).append("')\n");
        sb.append("DELETE FROM OPP_IMG_BIZ_CFG WHERE BUSI_CODE='").append(newBusiCode).append("';\n");
        for (Map<String, Object> map : imgCls) {
            String type = (String) map.get("IMG_CLS");

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
                    .append(newBusiCode).append("', '").append(type).append("', '1', NULL, 1, '')");

            String subSql = "select CONDITION_VALUE from OPP_BUSI_IMG_CONDITION where "
                    + " IMG_CFG_SN IN (select IMG_CFG_SN from OPP_IMG_BIZ_CFG where BUSI_CODE IN ( ? ) and IMG_CLS IN ( ? ))";

            List<Map<String, Object>> conditions = jdbcTemplate.queryForList(subSql,
                    new Object[]{oldBusiCode, type});
            if (conditions == null || conditions.size() == 0) {
                sb.append(";\n");
            } else {
                for (Map<String, Object> con : conditions) {
                    String conStr = (String) con.get("CONDITION_VALUE");
                    sb.append("\n").append(
                            "INSERT INTO OPP_BUSI_IMG_CONDITION(CONDITION_ID, ATOM_CODE, ATOM_PARAM, CONDITION_VALUE, IMG_CFG_SN, CONDITION_TYPE) VALUES ")
                            .append("(@MAX_CONDITION_ID, '', '', '").append(conStr).append("', @MAX_IMG_CFG_SN, '0')")
                            .append(";").append("\n");
                }
            }
        }
        sb.append("GO\n");

        return sb.toString();

    }
}
