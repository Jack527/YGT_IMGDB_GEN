package com.zts.jzyy.app.sqlserver;

import java.util.Arrays;

public class OppPreSaveModulesScriptGenerator extends PerSaveScriptGenerator {

    public OppPreSaveModulesScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "OPP_PRE_SAVE_MODULES.sql";
        this.tableName = "OPP_PRE_SAVE_MODULES";
        this.parms = "BUSI_CODE,USER_TYPE,GROUP_ID,MODULE_ID,MODULE_TITLE,MODULE_SEQ,MODULE_ADD,MODULE_CLEAN,MODULE_READ,MODULE_CONTROL,MODULE_DATA_TYPE,BUSI_PLAT";
        this.paramList = Arrays.asList(parms.split(","));
    }
}
