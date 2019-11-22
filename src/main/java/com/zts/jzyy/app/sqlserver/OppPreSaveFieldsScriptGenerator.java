package com.zts.jzyy.app.sqlserver;

import java.util.Arrays;

public class OppPreSaveFieldsScriptGenerator extends PerSaveScriptGenerator{
    public OppPreSaveFieldsScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "08.OPP_PRE_SAVE_FIELDS.sql";
        this.tableName = "OPP_PRE_SAVE_FIELDS";
        this.parms = "BUSI_CODE,USER_TYPE,GROUP_ID,MODULE_ID,FIELD_ID,FIELD_TITLE,FIELD_REQUIRED,FIELD_CONTROL,FIELD_DICT_FILTER,DEFAULT_VALUE,FIELD_SEQ,PLACE_HOLDER,BUSI_PLAT";
        this.paramList = Arrays.asList(parms.split(","));
    }
}
