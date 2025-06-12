package finalmission.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ReservationTest {

    @Test
    void 예약을_취소하면_상태와_반납일자가_변경된다() {
        Member member = Member.createMember("member", "member1@email.con", "password");
        LocalDate reserveDate = LocalDate.now();
        Book book = Book.create("book", "author", LocalDate.of(2011, 1, 1), "description", "image", "isbn", 3);
        BookInformation bookInformation = BookInformation.from(book);
        Reservation reservation = Reservation.create(member, reserveDate, bookInformation);

        reservation.cancel();

        assertAll(
                () -> assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.CANCELED),
                () -> assertThat(reservation.getReturnDate()).isEqualTo(LocalDate.now())
        );
    }
}
