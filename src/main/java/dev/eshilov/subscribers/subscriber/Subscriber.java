package dev.eshilov.subscribers.subscriber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String number;

    @Column(columnDefinition = "enum('ACTIVE', 'BLOCKED')")
    @Enumerated(EnumType.STRING)
    private SubscriberStatus status;

    private long balance;

    private String pin;

    @Column(columnDefinition = "enum('USER', 'ADMIN')")
    @Enumerated(EnumType.STRING)
    private SubscriberRole role;
}
