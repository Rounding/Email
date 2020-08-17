package com.isoft.email.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveMessage {
    private String ip , port , url , activeCode , id ;
}
