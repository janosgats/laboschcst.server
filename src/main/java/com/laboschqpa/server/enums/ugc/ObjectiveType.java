package com.laboschqpa.server.enums.ugc;

import com.laboschqpa.server.exceptions.NotImplementedException;

import java.util.Arrays;
import java.util.Optional;

public enum ObjectiveType {
    MAIN_OBJECTIVE(Integer.parseInt(ObjectiveTypeValues.MAIN_OBJECTIVE)),
    SWARM_TASK(Integer.parseInt(ObjectiveTypeValues.SWARM_TASK)),
    PRE_WEEK_TASK(Integer.parseInt(ObjectiveTypeValues.PRE_WEEK_TASK));

    private Integer value;

    ObjectiveType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static ObjectiveType fromValue(Integer value) {
        Optional<ObjectiveType> optional = Arrays.stream(ObjectiveType.values())
                .filter(en -> en.getValue().equals(value))
                .findFirst();

        if (optional.isEmpty())
            throw new NotImplementedException("Enum from this value is not implemented" + value);

        return optional.get();
    }
}
