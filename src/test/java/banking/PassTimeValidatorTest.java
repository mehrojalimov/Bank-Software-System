package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {
    CommandValidator commandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void passing_one_month_is_valid() {
        boolean actual = commandValidator.validate("pass 1");

        assertTrue(actual);
    }

    @Test
    void passing_zero_is_invalid() {
        boolean actual = commandValidator.validate("pass 0");

        assertFalse(actual);
    }

    @Test
    void passing_over_the_passtime_limit_is_invalid() {
        boolean actual = commandValidator.validate("pass 61");

        assertFalse(actual);
    }

    @Test
    void passing_negative_months_is_invalid() {
        boolean actual = commandValidator.validate("pass -2");

        assertFalse(actual);
    }

    @Test
    void passing_command_being_case_insensitive_is_valid() {
        boolean actual = commandValidator.validate("PaSs 5");

        assertTrue(actual);
    }

    @Test
    void passing_without_time_given_is_invalid() {
        boolean actual = commandValidator.validate("pass ");

        assertFalse(actual);
    }

    @Test
    void passing_just_the_number_is_invalid() {
        boolean actual = commandValidator.validate("1");

        assertFalse(actual);
    }

    @Test
    void pass_time_command_with_typo_is_invalid() {
        boolean actual = commandValidator.validate("pas 6");

        assertFalse(actual);
    }

    @Test
    void pass_time_command_changed_in_order_is_invalid() {
        boolean actual = commandValidator.validate("7 Pass");

        assertFalse(actual);
    }

    @Test
    void pass_time_command_with_an_extra_number_is_invalid() {
        boolean actual = commandValidator.validate("pass 7 7");

        assertFalse(actual);
    }

    @Test
    void pass_time_command_to_60_is_valid() {
        boolean actual = commandValidator.validate("pass 60");

        assertTrue(actual);
    }

    // Test@
}
