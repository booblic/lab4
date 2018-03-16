package lab4.library.controller.validator;

import lab4.library.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone phone) {
    }

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {

        if (phoneNo == null) {
            return false;
        }

        if (phoneNo.matches("\\d{11}")) {
            return true;
        } else if (phoneNo.matches("\\(\\d{3}\\)\\d{6}")) {
            return true;
        } else if (phoneNo.matches("\\(\\d{3}\\)\\d{7}")) {
            return true;
        } else {
            return false;
        }
    }
}