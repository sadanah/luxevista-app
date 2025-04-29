package com.example.luxevista;

public class Booking {
    private String date;
    private String roomType;
    private String roomNumber;
    private String checkIn;
    private String checkOut;
    private int guestCount;

    public Booking(String date, String roomType, String roomNumber, String checkIn, String checkOut, int guestCount) {
        this.date = date;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestCount = guestCount;
    }

    public String getDate() {
        return date;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public int getGuestCount() {
        return guestCount;
    }
}

