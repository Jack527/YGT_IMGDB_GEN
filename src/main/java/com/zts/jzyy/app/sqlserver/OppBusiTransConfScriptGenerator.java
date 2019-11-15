package com.zts.jzyy.app.sqlserver;

import java.util.Arrays;
public class OppBusiTransConfScriptGenerator extends AbstractScriptGenerator {

    public OppBusiTransConfScriptGenerator() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB";
        this.fileName = "OPP_BUSI_TRANS_CONF.sql";
        this.tableName = "OPP_BUSI_TRANS_CONF";
        this.parms = "CLASS_NAME,SEQU,DESCRIPTION,REDO,STATUS";
        this.paramList = Arrays.asList(parms.split(","));
    }

}
