package finalmission.application;

import finalmission.domain.Book;
import finalmission.domain.BookInformation;
import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.presentation.request.ReservationCreateRequest;
import finalmission.presentation.response.ReservationCreateResponse;
import finalmission.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberService memberService;
    private final BookService bookService;

    public ReservationService(
            ReservationRepository reservationRepository,
            MemberService memberService,
            BookService bookService
    ) {
        this.reservationRepository = reservationRepository;
        this.memberService = memberService;
        this.bookService = bookService;
    }

    @Transactional
    public ReservationCreateResponse createReservation(ReservationCreateRequest request) {
        Member member = memberService.findMemberByEmail(request.email());
        Book bookById = bookService.findBookById(request.bookId());
        BookInformation bookInformation = BookInformation.from(bookById);
        Reservation reservationWithoutId = Reservation.create(member, request.reserveDate(), bookInformation);
        Reservation reservationWithId = reservationRepository.save(reservationWithoutId);
        return ReservationCreateResponse.from(reservationWithId);
    }
}
