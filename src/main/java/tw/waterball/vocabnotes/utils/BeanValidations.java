package tw.waterball.vocabnotes.utils;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidations {
    private static Validator validator;

    public static Set<ConstraintViolation<Object>> validate(Object object) {
        if (validator == null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
        return validator.validate(object);
    }


    public static boolean isValid(Object object) {
        return validate(object).isEmpty();
    }
}
