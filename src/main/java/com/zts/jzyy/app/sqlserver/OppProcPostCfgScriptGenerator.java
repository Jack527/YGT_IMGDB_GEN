package com.zts.jzyy.app.sqlserver;
import java.util.Arrays;
import java.util.List;

public class OppProcPostCfgScriptGenerator extends AbstractScriptGenerator {
    private static final List<String> PARAMS = Arrays.asList("PROCDEF_KEY", "RES_ID", "POST_ID", "ORG_SCOPE", "REMARK", "UPD_TIMESTAMP");

    public OppProcPostCfgScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "15.OPP_PROC_POST_CFG.sql";
        this.tableName = "OPP_PROC_POST_CFG";
        this.parms = "PROCDEF_KEY,RES_ID,POST_ID,ORG_SCOPE,REMARK,UPD_TIMESTAMP";
        this.paramList = Arrays.asList(parms.split(","));
    }
}
