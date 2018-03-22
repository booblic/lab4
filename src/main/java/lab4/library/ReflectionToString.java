package lab4.library;

import lab4.library.annotation.ToString;

import java.lang.reflect.Field;
import java.util.Objects;

public class ReflectionToString {
    public static String reflectionToString(Object o) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field: o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ToString.class)) {
                field.setAccessible(true);
                try {
                    stringBuilder.append(" / ").append(Objects.toString(field.get(o)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }
}
