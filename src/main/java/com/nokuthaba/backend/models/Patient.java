package com.nokuthaba.backend.models;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Patient {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthDate;
    private String address;
    @Enumerated(EnumType.STRING)
    private RecordSyncStatus recordSyncStatus;


}