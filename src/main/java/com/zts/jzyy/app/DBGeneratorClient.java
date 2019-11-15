package com.zts.jzyy.app;

import com.zts.jzyy.app.sqlserver.*;

public class DBGeneratorClient {
    public static void main(String[] args) {
        ScriptGenerator generator = new OppProcPostCfgScriptGenerator();
        generator.generateScript();
    }

    private static void generate() {

    }
}
