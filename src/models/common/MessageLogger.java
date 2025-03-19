package models.common;

/**
 * Клас за логване на съобщения в конзолата.
 * Осигурява статични методи за извеждане на съобщения от различен тип.
 */
public class MessageLogger {

    public static void log(String message){
        System.out.println(message);
    }

    public static void log(double val){
        System.out.println(val);
    }
}
