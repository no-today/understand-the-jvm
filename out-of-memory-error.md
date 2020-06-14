# OOM 异常(OutOfMemoryError)

## Java 堆溢出

- 出现标志: java.lang.OutOfMemoryError: Java heap space
- 解决方法:
    - 先通过 `jmap -dump:format=b,file=app.dump [PID]` 导出内存快照，确认内存中的对象是否是必要的，分清楚是出
    现了内存泄露还是内存溢出
    - 如果是内存泄露，通过工具查看泄露对象到 CG Root 的引用链，定位出泄露的位置
    - 如果不存在泄露，检查虚拟机参数(-Xmx 和 -Xms)是否可以调大，检查代码中是否有哪些对象的生命周期过长，尝试减少程序
    运行期的内存消耗
- 虚拟机参数:
    - `-XX:HeapDumpOnOutOfMemoryError`: 让虚拟机中出现内存异常时 Dump 出当时的内存转储快照用于事后分析
    
## Java 虚拟机栈和本地方法栈溢出

- 单线程下，栈桢过大、虚拟机容量过小都不会导致 OutOfMemoryError，只会导致 StackOverflowError(栈会比内存先爆掉)，
一般多线程才会出现 OutOfMemoryError，因为线程本身要占用内存
- 如果是多线程导致的 OutOfMemoryError，在不能减少线程数或更换 64 位虚拟机的情况，只能通过减少最大堆和减少栈容量来
换取更多的线程
    - 这个调节思路和 Java 堆出现 OOM 正好相反，Java 堆出现 OOM 要调大堆的大小，而栈出现 OOM 反而要调小(因为每个
    线程都占用了`-Xss`大小的栈内存，但是调的太小可能会出现 StackOverflowError)

## 方法区和运行时常量池溢出

- 测试思路: 产生大量的类去填满方法区
- 在经常动态生成大量 Class 的应用中，如 Spring 框架(使用 CGLib 字节码技术)，方法区溢出是一种常见的内存溢出，要特别
注意类的回收状况

## 直接内存溢出

- 出现特征: Heap Dump 文件中看不到明显异常，程序中直接或间接用了 NIO
- 虚拟机参数: `-XX:MaxDirectMemorySize`，如果不指定，则和 `-Xmx` 一样