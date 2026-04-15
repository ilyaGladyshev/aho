package aho.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseDate {
    private boolean result;
    private LocalDate currentDate;
}
