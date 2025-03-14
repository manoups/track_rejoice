package one.breece.track_rejoice.security.validation

import com.google.common.base.Joiner
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.passay.*

class PasswordConstraintValidator:ConstraintValidator<ValidPassword, String> {
    override fun isValid(password: String, context: ConstraintValidatorContext): Boolean {

        // @formatter:off
        val validator = PasswordValidator(listOf(
            LengthRule(8, 30),
            UppercaseCharacterRule(1),
            DigitCharacterRule(1),
            SpecialCharacterRule(1),
            NumericalSequenceRule(3, false),
            AlphabeticalSequenceRule(3, false),
            QwertySequenceRule(3, false),
            WhitespaceRule()))
        val result: RuleResult = validator.validate(PasswordData(password))
        if (result.isValid) {
            return true
        }
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation()
        return false
    }

}
