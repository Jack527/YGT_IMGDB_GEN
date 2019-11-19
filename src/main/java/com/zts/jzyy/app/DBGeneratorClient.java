package com.zts.jzyy.app;

import com.zts.jzyy.app.sqlserver.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DBGeneratorClient {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        ScriptGenerator generator = new OppProcPostCfgScriptGenerator();
//        generator.generateScript();
        generate();
    }

    private static void generate() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String[] className = new String[]{"com.zts.jzyy.app.sqlserver.AscScriptGenerator","com.zts.jzyy.app.sqlserver.ImgScriptGenerator",
       "com.zts.jzyy.app.sqlserver.OppProcPostCfgScriptGenerator","com.zts.jzyy.app.sqlserver.OppBusiTransConfScriptGenerator" ,"com.zts.jzyy.app.sqlserver.OppBusiCommParamScriptGenerator",
        "com.zts.jzyy.app.sqlserver.OppBusiDefScriptGenerator","com.zts.jzyy.app.sqlserver.OppPreSaveGroupsScriptGenerator",
                "com.zts.jzyy.app.sqlserver.OppPreSaveFieldsScriptGenerator","com.zts.jzyy.app.sqlserver.OppPreSaveModulesScriptGenerator"};
        for (String s : className) {
            Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(s);
            Method generateScript = clazz.getMethod("generateScript");
            Object o = clazz.newInstance();
            generateScript.invoke(o);
        }
    }
}
