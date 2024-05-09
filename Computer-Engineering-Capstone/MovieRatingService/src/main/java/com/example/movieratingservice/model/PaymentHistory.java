package com.example.movieratingservice.model;
public class PaymentHistory {
    private String user_id;
    private String date;
    private Double total_paid;

    public PaymentHistory() {}

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotalPaid() {
        return total_paid;
    }

    public void setTotalPaid(Double total_paid) {
        this.total_paid = total_paid;
    }

    @Override
    public String toString() {
        return "PaymentHistory{" +
                "user_id='" + user_id + '\'' +
                ", date='" + date + '\'' +
                ", total_paid=" + total_paid +  // Adjust for Double
                '}';
    }
}
