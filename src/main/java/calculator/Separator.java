package calculator;

import java.util.Arrays;

public interface Separator {

    PositiveNumbers split(InputText inputText);
    boolean match(String text);

    default PositiveNumber[] getPositiveNumberArray(String[] inputTexts) {
        return Arrays.stream(inputTexts)
                .map(PositiveNumber::new)
                .toArray(PositiveNumber[]::new);
    }

}
