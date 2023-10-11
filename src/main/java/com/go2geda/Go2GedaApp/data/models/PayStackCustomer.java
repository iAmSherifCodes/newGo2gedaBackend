package com.go2geda.Go2GedaApp.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PayStackCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;
    private Long driversId;
    private String email;
    private  String phone;
    private String first_name;
    private String last_name;
    private String integration;
    private String domain;
    private String customerCode;
    private String createdAt;
    private String updatedAt;
    private String metadata;
    private String risk_action;
    private String bvn;
    private String id;


//
//    {
//        "status": true,
//            "message": "Customers retrieved",
//            "data": [
//        {
//            "integration": 463433,
//                "first_name": null,
//                "last_name": null,
//                "email": "dom@gmail.com",
//                "phone": null,
//                "metadata": null,
//                "domain": "test",
//                "customer_code": "CUS_c6wqvwmvwopw4ms",
//                "risk_action": "default",
//                "id": 90758908,
//                "createdAt": "2022-08-15T13:46:39.000Z",
//                "updatedAt": "2022-08-15T13:46:39.000Z"
//        },
//        {
//            "integration": 463433,
//                "first_name": "Okiki",
//                "last_name": "Sample",
//                "email": "okiki@sample.com",
//                "phone": "09048829123",
//                "metadata": {},
//            "domain": "test",
//                "customer_code": "CUS_rki2ccocw7g8lsj",
//                "risk_action": "default",
//                "id": 90758301,
//                "createdAt": "2022-08-15T13:42:52.000Z",
//                "updatedAt": "2022-08-15T13:42:52.000Z"
//        },
//        {
//            "integration": 463433,
//                "first_name": "lukman",
//                "last_name": "calle",
//                "email": "lukman@calle.co",
//                "phone": "08922383034",
//                "metadata": {},
//            "domain": "test",
//                "customer_code": "CUS_hpxsz8c1if90quo",
//                "risk_action": "default",
//                "id": 90747194,
//                "createdAt": "2022-08-15T12:31:13.000Z",
//                "updatedAt": "2022-08-15T12:31:13.000Z"
//        }
//  ],
//        "meta": {
//        "next": "Y3VzdG9tZXI6OTAyMjU4MDk=",
//                "previous": null,
//                "perPage": 3
//    }
//    }
//

}

