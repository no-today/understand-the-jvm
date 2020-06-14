package understand.the.jvm.chapter02;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建对象导致堆内存溢出
 * <p>
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {

    static class OOMObject {
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();

        while (true) {
            list.add(new OOMObject());
        }
    }
}

// IDEA-Profiler https://www.jetbrains.com/help/idea/analyze-hprof-memory-snapshots.html