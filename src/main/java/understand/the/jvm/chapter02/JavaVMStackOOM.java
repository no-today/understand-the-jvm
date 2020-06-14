package understand.the.jvm.chapter02;

/**
 * VM Args: -Xss2M（这时候不妨设大些，请在32位系统下运行）
 */
public class JavaVMStackOOM {

    private void dontStop() {
        while (true) {
        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}

// Mac OS(10.15.4) 未出现异常 但会假死, 调大到每个线程占 2048M 有效果

// VM Args: -Xss2048M
// Error occurred during initialization of VM
// java.lang.OutOfMemoryError: unable to create new native thread
//         at java.lang.Thread.start0(Native Method)
//         at java.lang.Thread.start(Thread.java:717)
//         at java.lang.ref.Reference.<clinit>(Reference.java:232)