package ru.diolloyd.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class Bookingdate {

        private String checkin;
        private String checkout;

        public static String getDateAsString(String date) {
                return (LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))).toString();
        }
}
