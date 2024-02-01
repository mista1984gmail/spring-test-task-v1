package com.example.springtesttaskv1.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRequest {

    @NotNull
    private UUID uuidHouse;

    @NotNull
    private UUID uuidPerson;

}
