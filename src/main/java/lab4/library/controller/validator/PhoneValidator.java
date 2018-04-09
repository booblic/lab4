package lab4.library.controller.validator;

import lab4.library.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Class defines the logic to validate constraint phone number
 * @author Кирилл
 * @version 1.0
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone phone) {
    }

    /**
     * Method that verifies the validity of the phone number by comparing it with the templates
     * @param phoneNo - phone number
     * @param ctx - provides contextual data and operation when applying a given constraint validator
     * @return boolean
     */
    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {

        if (phoneNo == null) {
            return false;
        }

        if (phoneNo.matches("\\d{11}")) {
            return true;
        } else if (phoneNo.matches("\\(\\d{4}\\)\\d{6}")) {
            return true;
        } else if (phoneNo.matches("\\(\\d{3}\\)\\d{7}")) {
            return true;
        } else {
            return false;
        }
    }
}