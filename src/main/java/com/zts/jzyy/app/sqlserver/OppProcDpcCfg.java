package com.zts.jzyy.app.sqlserver;


import java.util.Arrays;

public class OppProcDpcCfg extends AbstractScriptGenerator {

    public OppProcDpcCfg() {
        init();
    }

    private void init() {
        this.basePath = "E:\\xzx\\YGT_DB2";
        this.tableName = "OPP_PROC_DPC_CFG";
        this.fileName = "16.OPP_PROC_DPC_CFG.sql";
        this.parms = "BUSI_CODE,PROCDEF_KEY,RES_ID,OUT_ID,DPC_VALUE,REMARK,UPD_TIMESTAMP";
        this.paramList = Arrays.asList(parms.split(","));
    }
}
