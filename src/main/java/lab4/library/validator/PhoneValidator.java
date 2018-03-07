package lab4.library.validator;

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

        if (phoneNo.matches("\\d{10}")) {
            return true;
        } else {
            return false;
        }
    }
}
