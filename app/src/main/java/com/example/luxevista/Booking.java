package com.example.luxevista;

public class Booking {
    private int bookingId, guestCount;
    private String checkIn, checkOut, stayType, payProvider, roomNumber;
    private double payAmount;

    // Getters and setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }

    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }

    public int getGuestCount() { return guestCount; }
    public void setGuestCount(int guestCount) { this.guestCount = guestCount; }

    public String getStayType() { return stayType; }
    public void setStayType(String stayType) { this.stayType = stayType; }

    public double getPayAmount() { return payAmount; }
    public void setPayAmount(double payAmount) { this.payAmount = payAmount; }

    public String getPayProvider() { return payProvider; }
    public void setPayProvider(String payProvider) { this.payProvider = payProvider; }
}



