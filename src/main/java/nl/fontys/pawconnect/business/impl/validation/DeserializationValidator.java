package nl.fontys.pawconnect.business.impl.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class DeserializationValidator {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(DeserializationValidator.class);


    public static <T> T validateAndDeserialize(String json, Class<T> clazz) throws Exception {
        // Deserialize the JSON string into the desired object
        if(json == null || json.isEmpty()) return null;
        T object = objectMapper.readValue(json, clazz);

        // Validate the object
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                errorMessages.append(violation.getMessage()).append(", ");
            }
            LOG.error(errorMessages.toString());
            throw new IllegalArgumentException("Validation failed: " + errorMessages);
        }

        return object;
    }
}
