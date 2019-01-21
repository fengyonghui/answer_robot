package cn.mchina.demo.ide.idea.generator;

import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-7
 * Time: 下午5:33
 * To change this template use File | Settings | File Templates.
 */
public class MyBatisGeneratorTool {
    public static void main(String[] args) {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = false;
        String genCfg = "/generatorConfig.xml";
        File configFile = new File(MyBatisGeneratorTool.class.getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (org.mybatis.generator.exception.XMLParserException e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            myBatisGenerator.generate(null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(String warning : warnings){
            System.out.println(warning);
        }
    }
}
