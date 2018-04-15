package lab4.library;

import lab4.library.annotation.ToString;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Class for reflection toString
 * @author Кирилл
 * @version 1.0
 */
public class ReflectionToString {

    /**
     * Method using reflection looks for the fields of the object annotated as @ToString and forms a string based on these fields
     * @param o - some object
     * @return object toString
     */
    public static String reflectionToString(Object o) {

        if (o == null) {
            return "it is null object";
        }

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
