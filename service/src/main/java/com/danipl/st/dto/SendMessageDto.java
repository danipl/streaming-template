package com.danipl.st.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageDto {

    @Positive(message = "userToId has to be positive.")
    private int userToId;
    @NotBlank(message = "text is mandatory")
    private String text;

}
