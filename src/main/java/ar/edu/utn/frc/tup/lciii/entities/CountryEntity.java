package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "countries")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String Name;

    @Column
    private String Code;

    @Column
    private Long Population;

    @Column
    private Double Area;

}
