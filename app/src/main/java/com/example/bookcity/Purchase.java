package com.example.bookcity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Purchase {
    protected String CardNumber;
    protected String ExpirationMonth;
    protected String ExpirationYear;
    protected String Cvv;
    protected String CardholderName;
    protected String PostalCode;
    protected String CountryCode;
    protected String MobileNumber;
    protected String Title;
    protected String Date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


    public Purchase(String cardNumber, String expirationMonth, String expirationYear, String cvv, String cardholderName, String postalCode, String countryCode, String mobileNumber, String title, String date) {
        CardNumber = cardNumber;
        ExpirationMonth = expirationMonth;
        ExpirationYear = expirationYear;
        Cvv = cvv;
        CardholderName = cardholderName;
        PostalCode = postalCode;
        CountryCode = countryCode;
        MobileNumber = mobileNumber;
        Title = title;
        Date = date;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public void setExpirationMonth(String expirationMonth) {
        ExpirationMonth = expirationMonth;
    }

    public void setExpirationYear(String expirationYear) {
        ExpirationYear = expirationYear;
    }

    public void setCvv(String cvv) {
        Cvv = cvv;
    }

    public void setCardholderName(String cardholderName) {
        CardholderName = cardholderName;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDate(String date) {
        Date = date;
    }

    // Returns the attributes of an event as a map
    // So that they can be saved to database
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("CardNumber", CardNumber);
        result.put("ExpirationMonth", ExpirationMonth);
        result.put("ExpirationYear", ExpirationYear);
        result.put("Cvv", Cvv);
        result.put("CardholderName", CardholderName);
        result.put("PostalCode", PostalCode);
        result.put("CountryCode", CountryCode);
        result.put("MobileNumber", MobileNumber);
        result.put("Title", Title);
        result.put("Date", Date);
        return result;
    }

}
