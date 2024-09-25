package com.dhruv.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record CustomerRequest(
        // this record will give you the getter methods
        /*
        also note  : No, Java records are immutable by design, which means they do not provide setter methods.
        Once you create an instance of a record, the fields cannot be modified.
        This is one of the key features of a record—it is intended to represent immutable data.
        * */

        String id,

        @NotNull(message = "Customer firstname is required")
        String firstname,

        @NotNull(message = "Customer lastname is required")
        String lastname,

        @NotNull(message = "Customer email is required")
        @Email(message = "invalid email address")
        String email,

        Address address

) {

}
