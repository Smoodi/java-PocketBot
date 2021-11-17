package de.smoodi.pocketbot.util;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Config {

    private String botToken;
    private String botVersion;
    private String botPrefix;

    public boolean load(String filename) throws FileNotFoundException {

        File cfgFile = new File(filename);
        if(!cfgFile.exists()) return false;
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(cfgFile);

        Map<String, Object> obj = (Map<String, Object>) yaml.load(inputStream);

        botToken = (String) obj.get("botToken");
        botVersion = (String)  obj.get("botVersion");
        botPrefix = (String) obj.get("botPrefix");

        return true;

    }
    public String getBotToken() { return botToken; }

    public String getBotVersion() { return botVersion; }

    public String getBotPrefix() { return botPrefix; }
}
