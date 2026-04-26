package com.mohammadbesharat.atlasmeat.appointment.domain;


import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    @Enumerated(EnumType.STRING)
    private ContactType contactPreference;
    @Enumerated(EnumType.STRING)
    private AnimalType animalType;
    private int animalCount;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    private Long checkoutId;
    private LocalDate scheduledDate;

    public Long getId() {return id;}
    public String getCustomerName() {return customerName;}
    public String getCustomerEmail() {return customerEmail;}
    public String getCustomerPhone() {return customerPhone;}
    public ContactType getContactPreference() {return contactPreference;}
    public AnimalType getAnimalType() {return animalType;}
    public int getAnimalCount() {return animalCount;}
    public AppointmentStatus getStatus() {return status;}
    public Long getCheckoutId() {return checkoutId;}
    public LocalDate getScheduledDate() {return scheduledDate;}

    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public void setCustomerEmail(String customerEmail) {this.customerEmail = customerEmail;}
    public void setCustomerPhone(String customerPhone) {this.customerPhone = customerPhone;}
    public void setContactPreference(ContactType contactPreference) {this.contactPreference = contactPreference;}
    public void setAnimalType(AnimalType animalType) {this.animalType = animalType;}
    public void setAnimalCount(int animalCount) {this.animalCount = animalCount;}
    public void setStatus(AppointmentStatus status) {this.status = status;}
    public void setCheckoutId(Long checkoutId) {this.checkoutId = checkoutId;}
    public void setScheduledDate(LocalDate scheduledDate) {this.scheduledDate = scheduledDate;}

}
