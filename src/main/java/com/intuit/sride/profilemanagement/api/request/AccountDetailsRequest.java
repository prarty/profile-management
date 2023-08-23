package com.intuit.sride.profilemanagement.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsRequest {

    @NotBlank(message = "Account Number can not be null or blank")
    String accountNumber;

    @NotBlank(message = "IFSC Code can not be null or blank")
    String ifscCode;

    @NotBlank(message = "Account Name can not be null or blank")
    String accountName;
}
