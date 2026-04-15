package aho.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ResponseDate {
    private boolean result;
    private LocalDate currentDate;
}
