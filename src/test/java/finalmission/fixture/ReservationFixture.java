package finalmission.fixture;

import finalmission.domain.Book;
import finalmission.domain.BookInformation;
import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReservationFixture {

    @Autowired
    private MemberFixture memberFixture;

    @Autowired
    private BookFixture bookFixture;

    private final ReservationRepository reservationRepository;

    public ReservationFixture(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation1() {
        Member member1 = memberFixture.createMember1();
        Book book1 = bookFixture.createBook1();

        LocalDate reserveDate = LocalDate.now();
        BookInformation bookInformation = BookInformation.from(book1);

        return reservationRepository.save(Reservation.create(member1, reserveDate, bookInformation));
    }

    public Reservation createReservation2() {
        Member member2 = memberFixture.createMember2();
        Book book2 = bookFixture.createBook2();

        LocalDate reserveDate = LocalDate.now();
        BookInformation bookInformation = BookInformation.from(book2);

        return reservationRepository.save(Reservation.create(member2, reserveDate, bookInformation));
    }
}
