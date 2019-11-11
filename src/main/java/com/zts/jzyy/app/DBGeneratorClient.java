package com.zts.jzyy.app;

import com.zts.jzyy.app.sqlserver.AscScriptGenerator;
import com.zts.jzyy.app.sqlserver.ScriptGenerator;

public class DBGeneratorClient {
    public static void main(String[] args) {
        ScriptGenerator generator = new AscScriptGenerator();
        generator.generateScript();
    }
}
