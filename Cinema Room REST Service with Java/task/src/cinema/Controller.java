package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class Controller {

    private final List<Seat> seatList = createList();

    private static List<Seat> createList() {
        List<Seat> list = Collections.synchronizedList(new ArrayList());

        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (i <= 4) {
                    list.add(new Seat(i, j, 10, true));
                } else {
                    list.add(new Seat(i, j, 8, true));
                }
            }
        }
        return list;
    }


    @GetMapping("/seats")
    public Cinema getSeats() {
        return new Cinema(9, 9, seatList);
    }

    //statistics
    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String password) {
        int income = 0;
        int available = 0;
        int purchased = 0;
        Map<String, Integer> body = new LinkedHashMap<>();

        if ("super_secret".equals(password)) {
            //return stats
            for (Seat s : seatList) {
                if (s.isAvailable()) {
                    available++;
                } else {
                    income += s.getPrice();
                    purchased++;
                }
            }
            body.put("income", income);
            body.put("available", available);
            body.put("purchased", purchased);
            return ResponseEntity.ok().body(body);//200
        }

        return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);//401
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat) {
        Map<String, Object> body = new LinkedHashMap<>();
        if (seat == null) {
            return ResponseEntity.notFound().build();
        }
        //if seat is out of range
        if (!seatList.contains(seat)) {
            body.put("error", "The number of a row or a column is out of bounds!");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
            //return ResponseEntity.badRequest().body("{\"error\": \"The number of a row or a column is out of bounds!\"}");
        }
        for (Seat s : seatList) {
            if (s.equals(seat)) {
                if (s.isAvailable()) {//if the seat is available
                    s.setAvailable(false);
                    s.setUuid(UUID.randomUUID());
                    body.put("token", s.getUuid());
                    body.put("ticket", s);
                    return new ResponseEntity<>(body, HttpStatus.OK);
                    //return ResponseEntity.ok().body(s);
                } else { //if the seat is bought
                    body.put("error", "The ticket has been already purchased!");
                    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
                    //return ResponseEntity.badRequest().body("{\"error\": \"The ticket has been already purchased!\"}");
                }
            }
        }

        throw new RuntimeException();
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnSeat(@RequestBody Map<String, String> map) {
        for (Seat s : seatList) {
            if (s.getUuid() != null && map.containsValue(s.getUuid().toString())) {
                s.setUuid(UUID.randomUUID());//change token
                s.setAvailable(true);//ticket is available now
                return new ResponseEntity<>(Map.of("ticket", s), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

//    @ControllerAdvice
//    public class ControllerExceptionHandler {
//
//        @ExceptionHandler({TicketNotFoundException.class, FlightNotFoundException.class})
//        public ResponseEntity<Object> handleFlightAndTicketNotFound(RuntimeException e) {
//
//            Map<String, Object> body = new HashMap<>();
//            body.put("status", HttpStatus.NOT_FOUND.value());
//            body.put("message", e.getMessage());
//
//            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//        }
//    }

}