package org.example.bot2;

import cn.hutool.core.date.DateUtil;
import com.sun.management.OperatingSystemMXBean;
import vip.floatationdevice.guilded4j.object.Embed;
import vip.floatationdevice.guilded4j.object.misc.EmbedField;

import java.lang.management.ManagementFactory;
import java.util.Random;

/**
 * Used to collect and display status information.
 * Includes memory usage and cpu usage of the system and JVM.
 */
public class StatusUtil
{
    private static final Random r = new Random();

    /**
     * Returns a fancy embed with the system status.
     */
    public static Embed getStatusEmbed()
    {
        return new Embed()
                .setTitle(":information_source: SYSTEM STATUS")
                .setFields(
                        new EmbedField[]{
                                new EmbedField().setName("System CPU usage").setValue(getSysCpu()).setInline(true),
                                new EmbedField().setName("JVM CPU usage").setValue(getJVMCpu()).setInline(true),
                                new EmbedField().setName("System memory usage").setValue(getSysMem() + " / " + getSysMemMax() + " MB").setInline(true),
                                new EmbedField().setName("JVM memory usage").setValue(getJVMMem() + " / " + getJVMMemMax() + " MB").setInline(true)
                        }
                )
                .setColor(r.nextInt(0xFFFFFF))
                .setFooterText(DateUtil.now())
                .setAuthorName("Guilded4J System Status Bot")
                .setAuthorUrl("https://github.com/MCUmbrella/Guilded4J");
    }

    static OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    static Runtime rt = Runtime.getRuntime();

    static String getSysMemMax(){return String.valueOf(os.getTotalPhysicalMemorySize() / 1024 / 1024);}

    static String getSysMem(){return String.valueOf((os.getTotalPhysicalMemorySize() - os.getFreePhysicalMemorySize()) / 1024 / 1024);}

    static String getJVMMemMax(){return String.valueOf(rt.maxMemory() / 1024 / 1024);}

    static String getJVMMem(){return String.valueOf((rt.totalMemory() - rt.freeMemory()) / 1024 / 1024);}

    static String getSysCpu()
    {
        String l = String.valueOf(os.getSystemCpuLoad() * 100);
        return l.substring(0, Math.min(l.length(), 5)) + "%";
    }

    static String getJVMCpu()
    {
        String l = String.valueOf(os.getProcessCpuLoad() * 100);
        return l.substring(0, Math.min(l.length(), 5)) + "%";
    }
}
