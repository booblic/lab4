package lab4.library;

import lab4.library.annotation.ToString;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author VYZH
 * @since 20.03.2018
 */
public class ReflectionToString {

    public static String toStringFromObject(Object o) {
        StringBuilder sb = new StringBuilder();
        for (Field f : o.getClass().getDeclaredFields()) {
            if (f.getAnnotation(ToString.class) != null) {
                f.setAccessible(true);
                try {
                    sb.append(",").append(Objects.toString(f.get(o)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
