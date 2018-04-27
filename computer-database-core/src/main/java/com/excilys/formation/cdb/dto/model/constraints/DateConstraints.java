package com.excilys.formation.cdb.dto.model.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraints  {
    String date1();
    String date2();

    String message() default "Date constraints violated!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
