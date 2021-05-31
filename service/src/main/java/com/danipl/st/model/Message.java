package com.danipl.st.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Table(name = "message")
@Entity(name = "Message")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = -7333940586255425088L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "text")
    private String text;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @OneToOne
    @JoinColumn(name = "user_from_id", referencedColumnName = "id")
    private User from;
    @OneToOne
    @JoinColumn(name = "user_to_id", referencedColumnName = "id")
    private User to;

}