package finalmission.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private LocalDate reserveDate;

    private LocalDate returnDate;

    @Embedded
    private BookInformation bookInformation;

    private ReservationStatus status;

    public static Reservation create(
            Member member,
            LocalDate reserveDate,
            BookInformation bookInformation
    ) {
        return new Reservation(
                null,
                member,
                reserveDate,
                reserveDate.plusDays(7),
                bookInformation,
                ReservationStatus.RESERVED
        );
    }
}
