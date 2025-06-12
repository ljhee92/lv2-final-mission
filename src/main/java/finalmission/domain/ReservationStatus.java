package finalmission.domain;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    RESERVED("예약"),
    CANCELED("예약취소")
    ;

    private final String name;

    ReservationStatus(String name) {
        this.name = name;
    }
}
