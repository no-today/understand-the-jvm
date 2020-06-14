package understand.the.jvm.chapter02;

import java.util.HashSet;

/**
 * VM Args: -XX:PermSize=64M -XX:MaxPermSize=6M
 */
public class RuntimeConstantPoolOOM_1 {

    public static void main(String[] args) {
        // 使用 Set 保存着常量池的引用, 避免 Full GC 回收常量池行为
        HashSet<String> set = new HashSet<String>();
        // 在 short 范围内足以让 6MB 的 PermSize 产生 OOM 了
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}

// JDK7 之后已经没有永久代的概念了 常量池迁移到了堆内存
// OpenJDK 64-Bit Server VM warning: ignoring option PermSize=64M; support was removed in 8.0
// OpenJDK 64-Bit Server VM warning: ignoring option MaxPermSize=6M; support was removed in 8.0