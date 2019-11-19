package com.zts.jzyy.app.sqlserver;

import java.util.Arrays;

public class OppPreSaveFieldsScriptGenerator extends PerSaveScriptGenerator{
    public OppPreSaveFieldsScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.fileName = "OPP_PRE_SAVE_FIELDS.sql";
        this.tableName = "OPP_PRE_SAVE_FIELDS";
        this.parms = "BUSI_CODE,FIELD_ID,FIELD_TITLE,FIELD_TYPE,FIELD_DICT,FIELD_REQ," +
                "FIELD_COLS,FIELD_REQUIRED,FIELD_PREC,FIELD_WIDTH,FIELD_SEQ,FIELD_GRP,VALID_TYPE," +
                "PLACE_HOLDER,DEFAULT_VALUE";
        this.paramList = Arrays.asList(parms.split(","));
    }
}
