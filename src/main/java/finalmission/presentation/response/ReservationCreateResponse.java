package finalmission.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.domain.Reservation;

import java.time.LocalDate;

public record ReservationCreateResponse(
        Long id,

        String email,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate reserveDate,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate returnDate,

        Long bookId
) {

    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(
                reservation.getId(),
                reservation.getMember().getEmail(),
                reservation.getReserveDate(),
                reservation.getReturnDate(),
                reservation.getBookInformation().getBookId()
        );
    }
}
