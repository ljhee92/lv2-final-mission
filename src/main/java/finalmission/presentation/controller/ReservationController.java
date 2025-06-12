package finalmission.presentation.controller;

import finalmission.application.ReservationService;
import finalmission.presentation.AuthenticationPrincipal;
import finalmission.presentation.request.LoginMember;
import finalmission.presentation.request.ReservationCreateRequest;
import finalmission.presentation.response.ReservationCreateResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateResponse> createReservation(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestBody @Valid ReservationCreateRequest request
    ) {
        ReservationCreateResponse response = reservationService.createReservation(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(required = false) Long id
    ) {
        reservationService.cancelReservation(loginMember.id(), id);
        return ResponseEntity.noContent().build();
    }

}
