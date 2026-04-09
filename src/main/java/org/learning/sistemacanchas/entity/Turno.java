package org.learning.sistemacanchas.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "tbl_turno")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false, nullable = false)
    private LocalDateTime creacion;
    @NonNull
    @Column(name = "fecha_turno", nullable = false)
    private LocalDateTime horario;
    @NonNull
    @UpdateTimestamp
    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;
    @NonNull
    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;
    @NonNull
    @Column(name = "apellido_cliente", nullable = false)
    private String apellidoCliente;
    @NonNull
    @Column(name = "celular_cliente", nullable = false)
    private String celularCliente;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_cancha", nullable = false)
    private Cancha cancha;
}
