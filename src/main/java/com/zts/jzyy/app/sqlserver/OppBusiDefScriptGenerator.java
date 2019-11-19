package com.zts.jzyy.app.sqlserver;
import java.util.Arrays;

public class OppBusiDefScriptGenerator extends AbstractScriptGenerator {
    public OppBusiDefScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "OPP_BUSI_DEF.sql";
        this.tableName = "OPP_BUSI_DEF";
        this.parms = "BUSI_NAME,PROCDEF_KEY,PROCDEF_KEY_2,PRIO_LVL,BUSI_SCOPE,LAND_PATTERN,BATCH_YN,ENABLE_ALLDAY,HIST_URL,IMG_NODE,DEPENDENCY";
        this.paramList = Arrays.asList(parms.split(","));
    }
}
