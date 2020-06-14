package understand.the.jvm.chapter02;

public class RuntimeConstantPoolOOM_2 {

    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("av").toString();
        System.out.println(str2.intern() == str2);
    }
}

// JDK 7 以前 String 会放到永久代到字符串常量池中存储(栈), StringBuilder 则放在堆里