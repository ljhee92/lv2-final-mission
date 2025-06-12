package finalmission.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.domain.Reservation;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,

        String email,

        String title,

        String author,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate rentalDate,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate returnDate
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getMember().getEmail(),
                reservation.getBookInformation().getTitle(),
                reservation.getBookInformation().getAuthor(),
                reservation.getReserveDate(),
                reservation.getReturnDate()
        );
    }
}
