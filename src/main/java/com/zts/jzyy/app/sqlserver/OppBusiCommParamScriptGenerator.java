package com.zts.jzyy.app.sqlserver;

import java.util.Arrays;

public class OppBusiCommParamScriptGenerator extends AbstractScriptGenerator {
    public OppBusiCommParamScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB";
        this.fileName = "OPP_BUSI_COMM_PARAM.sql";
        this.tableName = "OPP_BUSI_COMM_PARAM";
        this.parms = "BUS_TYPE,DATA_TYPE,MANA_TYPE,USER_TYPE,PARAM_CODE,PARAM_NAME,PARAM_VALUE,VALID_CHAR,VALID_LEN,VALID_DEC,MAX_VAL,MIN_VAL,REMARK";
        this.paramList = Arrays.asList(this.parms.split(","));
    }
}
