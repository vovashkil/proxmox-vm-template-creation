package com.project.booking.Constants;

public enum Destinations {

    AMSTERDAM("Amsterdam"), ATLANTA("Atlanta"), AUSTIN("Austin");
//    Baltimore, Boston, Buffalo,
//    Charlotte, Cincinnati, Cleveland, Columbus,
//    Dallas, Dayton, Denver, Des_Moines,
//    Grand_Rapids,
//    Hartford, Houston,
//    Indianapolis,
//    Jacksonville,
//    Las_Vegas, Los_Angeles, Louisville,
//    Memphis, Mexico_City, Miami, Milwaukee, Minneapolis, Morrisville,
//    Nashville, New_Orleans, Norfolk,
//    Oklahoma_City, Omaha,
//    Philadelphia, Phoenix, Pittsburgh, Portland,
//    Richmond,
//    Salt_Lake_City, San_Antonio, San_Diego, San_Francisco, San_Jose,
//    Santa_Ana, Seattle, Saint_Louis,
//    Tampa;

    private final String name;

    Destinations(String destination) {
        name = destination;
    }

    public String getName() {
        return name;
    }

}
