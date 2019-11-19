package com.zts.jzyy.app.sqlserver;

import java.util.Arrays;

public class OppPreSaveGroupsScriptGenerator extends PerSaveScriptGenerator {

    public OppPreSaveGroupsScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "OPP_PRE_SAVE_GROUPS.sql";
        this.tableName = "OPP_PRE_SAVE_GROUPS";
        this.parms = "BUSI_CODE,USER_TYPE,GROUP_ID,GROUP_TITLE,GROUP_SEQ,BUSI_PLAT, GROUP_TIPS";
        this.paramList = Arrays.asList(parms.split(","));
    }
}
