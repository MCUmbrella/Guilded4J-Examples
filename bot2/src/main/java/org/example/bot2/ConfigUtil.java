package org.example.bot2;

import java.io.*;
import java.util.Properties;
import java.util.UUID;

import static org.example.bot2.Main.*;

/**
 * Manage 'config.properties'. The file is stored and read under the working directory.
 */
public class ConfigUtil
{
    public static class ConfigCreationException extends RuntimeException
    {
        public ConfigCreationException(){super("Failed to create config file");}
    }

    public static class InvalidConfigException extends RuntimeException
    {
        public InvalidConfigException(){super("Config file check failed");}
    }

    public static void loadConfig() throws InvalidConfigException, ConfigCreationException
    {
        File configFile = new File("." + File.separator + "config.properties");

        // create config file if it doesn't exist
        if(!configFile.exists())
        {
            System.err.println("'config.properties' not found and a new one will be created.\nFill it with your token and channel ID, then restart the program.");
            try
            {
                // copy the example config to ./config.properties
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
                InputStream is = Main.class.getResourceAsStream("/config.properties");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                BufferedWriter bw = new BufferedWriter(new FileWriter(configFile));
                for(String line; (line = br.readLine()) != null; ) bw.write(line + "\n");
                bw.flush();
                bw.close();
                br.close();
                isr.close();
                is.close();
                System.exit(1);
            }
            catch(Exception e)
            {
                ConfigCreationException cce = new ConfigCreationException();
                cce.initCause(e);
                throw cce;
            }
        }

        // load and check config file
        try
        {
            Properties config = new Properties();
            config.load(new FileInputStream(configFile));
            token = config.getProperty("token");
            statusChannelId = config.getProperty("statusChannelId");
            statusMessageUpdateInterval = Integer.parseUnsignedInt(config.getProperty("statusMessageUpdateInterval"));
            printUpdateResults = Boolean.parseBoolean(config.getProperty("printUpdateResults"));
            // check if all required properties are set
            if(token == null || statusChannelId == null || token.isEmpty() || statusChannelId.isEmpty())
                throw new IllegalArgumentException("Null value detected");
            // test if channelId is a valid UUID
            UUID.fromString(statusChannelId);
            // be sure that the update interval is not too small
            if(statusMessageUpdateInterval < 5)
                throw new IllegalArgumentException("statusMessageUpdateInterval must >=5");
        }
        catch(Exception e)
        {
            InvalidConfigException ice = new InvalidConfigException();
            ice.initCause(e);
            throw ice;
        }
    }
}
