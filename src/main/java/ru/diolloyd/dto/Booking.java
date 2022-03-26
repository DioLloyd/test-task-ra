package ru.diolloyd.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class Booking {

    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private Bookingdate bookingdates;
    private String additionalneeds;

}
