package lab4.library;

import lab4.library.annotation.ToString;

import java.lang.reflect.Field;
import java.util.Objects;

public class ReflectionToString {

    public static String reflectionToString(Object o) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(o.getClass().getSimpleName()).append(": ");

        for (Field field: o.getClass().getDeclaredFields()) {

            if (field.isAnnotationPresent(ToString.class)) {

                field.setAccessible(true);

                try {
                    stringBuilder.append(Objects.toString(field.get(o))).append(", ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        return stringBuilder.toString();
    }
}
