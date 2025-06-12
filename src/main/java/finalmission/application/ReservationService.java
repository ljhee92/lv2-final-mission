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

import java.util.NoSuchElementException;

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

    @Transactional
    public void cancelReservation(Long memberId, Long reservationId) {
        Member memberById = memberService.findMemberById(memberId);
        Reservation reservationById = findReservationById(reservationId);
        if (reservationById.isMine(memberById)) {
            reservationById.cancel();
        }
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservationById = findReservationById(reservationId);
        reservationById.cancel();
    }

    private Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 존재하지 않는 예약입니다."));
    }
}
