package domain;

import javax.xml.bind.ValidationException;

public enum LogTypeEnum {

    APPLICATION_LOG(1),
    DB_LOG(2);

    private final int data;

    LogTypeEnum(int data) {
        this.data = data;
    }

    public int toValue() {
        return data;
    }

    public static LogTypeEnum getEnumFromValue(int value) throws ValidationException {
        if (value == 1)
            return APPLICATION_LOG;
        else if (value == 2)
            return DB_LOG;
        else
            throw(new ValidationException("Missing LogType: " + value));
    }
}
