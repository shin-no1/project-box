package io.github.haeun.petstats.domain.rfid_type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class RfidType {
    @Id
    private Integer id;

    @Column(length = 10, nullable = false)
    private String name;

}
