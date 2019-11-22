package com.zts.jzyy.app.sqlserver;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 业务准入脚本
 */
public class AscScriptGenerator extends AbstractScriptGenerator {

    public AscScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "04.OPP_BUSI_ACS_CFG.sql";
    }

    public String generate(String oldBusiCode, String newBusiCode, JdbcTemplate template) {
        String sql = "SELECT * FROM OPP_BUSI_ACS_CFG WHERE BUSI_CODE='" + oldBusiCode + "'";

        List<Map<String, Object>> oldResult = template.queryForList(sql);

        StringBuffer sb = new StringBuffer();
        if (CollectionUtils.isEmpty(oldResult)) {
            return "";
        }
        sb.append("DELETE FROM OPP_BUSI_ACS_CFG WHERE BUSI_CODE='").append(newBusiCode).append("';\n");
        for (Map<String, Object> map : oldResult) {
            String acsType = map.get("ACS_TYPE") == null ? "" : (String) map.get("ACS_TYPE"),
                    chkCond = map.get("CHK_COND") == null ? "" : (String) map.get("CHK_COND"),
                    resTrictType = map.get("RESTRICT_TYPE") == null ? "" : (String) map.get("RESTRICT_TYPE"),
                    menuPara = map.get("MENU_PARA") == null ? "" : (String) map.get("MENU_PARA"),
                    handleTip = map.get("HANDLE_TIP") == null ? "" : (String) map.get("HANDLE_TIP"),
                    busiScope = map.get("BUSI_SCOPE") == null ? "" : (String) map.get("BUSI_SCOPE");
            Timestamp updTimeStamp = (Timestamp) map.get("UPD_TIMESTAMP");
            String upTimeStr = "";
            if (null != updTimeStamp) {
                upTimeStr = updTimeStamp.toString();
            }
            int conId = (Integer) map.get("CON_ID");
            int menuId = (Integer) map.get("MENU_ID");
            sb.append("DECLARE @MAX_ACS_CFG_SN BIGINT").append("\n");
            sb.append("INSERT INTO SEQ_OPP_BUSI_ACS_CFG (SEQVAL) VALUES ('A')").append("\n");
            sb.append("SELECT @MAX_ACS_CFG_SN = MAX(SEQID) FROM SEQ_OPP_BUSI_ACS_CFG").append("\n");
            sb.append("DELETE FROM SEQ_OPP_BUSI_ACS_CFG").append("\n");
            sb.append("INSERT INTO OPP_BUSI_ACS_CFG(ACS_ID,ACS_TYPE,BUSI_CODE,CHK_COND,CON_ID,RESTRICT_TYPE,")
                    .append("MENU_ID,MENU_PARA,HANDLE_TIP,UPD_TIMESTAMP,BUSI_SCOPE) VALUES(").append("@MAX_ACS_CFG_SN,'")
                    .append(acsType).append("','").append(newBusiCode).append("','").append(chkCond).append("',").append(conId)
                    .append(",'").append(resTrictType).append("',").append(menuId).append(",'").append(menuPara).append("','")
                    .append(handleTip).append("','").append(upTimeStr).append("','").append(busiScope).append("')").append(";\n");
            sb.append("GO\n");
        }
        return sb.toString();
    }
}
