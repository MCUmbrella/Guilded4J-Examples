package org.example.bot2;

import cn.hutool.core.date.DateUtil;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

/**
 * Used to collect and display status information.
 * Includes memory usage and cpu usage of the system and JVM.
 */
public class StatusUtil
{
    /**
     * Returns formatted status information.
     */
    public static String getStatusMessageText()
    {
        return "**==========SYSTEM STATUS==========**\n" +
                DateUtil.date() + "\n" +
                "**CPU usage:**\n" +
                "`System: " + getSysCpu() + "`\n" +
                "`JVM: " + getJVMCpu() + "`\n" +
                "**Memory:**\n" +
                "`System: " + getSysMem() + " / " + getSysMemMax() + " MB`\n" +
                "`JVM: " + getJVMMem() + " / " + getJVMMemMax() + " MB`\n" +
                "**=================================**";
    }

    static OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    static Runtime rt = Runtime.getRuntime();

    static String getSysMemMax()
    {
        return String.valueOf(os.getTotalPhysicalMemorySize() / 1024 / 1024);
    }

    static String getSysMem()
    {
        return String.valueOf((os.getTotalPhysicalMemorySize() - os.getFreePhysicalMemorySize()) / 1024 / 1024);
    }

    static String getJVMMemMax()
    {
        return String.valueOf(rt.maxMemory() / 1024 / 1024);
    }

    static String getJVMMem()
    {
        return String.valueOf((rt.totalMemory() - rt.freeMemory()) / 1024 / 1024);
    }

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
