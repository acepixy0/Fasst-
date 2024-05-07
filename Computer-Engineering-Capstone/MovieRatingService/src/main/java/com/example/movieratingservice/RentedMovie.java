package com.example.movieratingservice;
public class RentedMovie {
    private String user_id;
    private String movie_id;
    private String rental_date;
    private String return_date;
    private String rented_price;
    private String status;

    // Default constructor
    public RentedMovie() {}

    // Getters and setters
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getRental_date() {
        return rental_date;
    }

    public void setRental_date(String rental_date) {
        this.rental_date = rental_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getRented_price() {
        return rented_price;
    }

    public void setRented_price(String rented_price) {
        this.rented_price = rented_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RentedMovie{" +
                "user_id='" + user_id + '\'' +
                ", movie_id='" + movie_id + '\'' +
                ", rental_date='" + rental_date + '\'' +
                ", return_date='" + return_date + '\'' +
                ", rented_price='" + rented_price + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
