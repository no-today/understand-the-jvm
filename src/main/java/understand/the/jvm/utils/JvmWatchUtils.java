package understand.the.jvm.utils;

import java.lang.management.*;
import java.text.DecimalFormat;
import java.util.Arrays;

public class JvmWatchUtils {

    /**
     * 打印堆内存信息
     */
    public static void printHeapMemoryInfo() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        System.out.printf("%s/%s\n", format(memoryUsage.getUsed()), format(memoryUsage.getMax()));
    }

    /**
     * 打印非堆内存信息
     */
    public static void printNonHeapMemoryInfo() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage memoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        System.out.printf("%s/%s\n", format(memoryUsage.getUsed()), format(memoryUsage.getMax()));
    }

    /**
     * 打印内存池信息
     */
    public static void printMemoryPoolInfo() {
        for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
            // 内存分区名
            String name = memoryPoolMXBean.getName();
            // 内存管理器名称
            String[] memoryManagerNames = memoryPoolMXBean.getMemoryManagerNames();
            // 内存分区类型
            MemoryType type = memoryPoolMXBean.getType();
            // 内存使用情况
            MemoryUsage usage = memoryPoolMXBean.getUsage();
            // 内存使用峰值情况
            MemoryUsage peakUsage = memoryPoolMXBean.getPeakUsage();

            System.out.printf("%s:\n", name);
            System.out.printf("    managers: %s\n", Arrays.toString(memoryManagerNames));
            System.out.printf("    type: %s\n", type);
            System.out.printf("    usage: %s\n", usage);
            System.out.printf("    peakUsage: %s\n", peakUsage);
            System.out.println();
        }
    }

    /**
     * 1024 Byte = 1 KB
     * 1024 KB   = 1 MB
     * 1024 MB   = 1 GB
     * 1024 GB   = 1 TB
     *
     * @param bytes 字节数
     * @return format size
     */
    private static String format(long bytes) {
        StringBuilder sb = new StringBuilder();
        DecimalFormat format = new DecimalFormat("###.00");

        if (bytes >= 1024 * 1024 * 1024) {
            sb.append(format.format(bytes / (1024.0 * 1024.0 * 1024.0))).append("GB");
        } else if (bytes >= 1024 * 1024) {
            sb.append(format.format(bytes / (1024.0 * 1024.0))).append("MB");
        } else if (bytes >= 1024) {
            sb.append(format.format(bytes / 1024.0)).append("KB");
        } else {
            sb.append(bytes).append("B");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        printHeapMemoryInfo();
        printNonHeapMemoryInfo();
        printMemoryPoolInfo();
    }
}
