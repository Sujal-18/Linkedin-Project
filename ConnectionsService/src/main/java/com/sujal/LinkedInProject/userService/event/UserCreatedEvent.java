package com.sujal.LinkedInProject.userService.event;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreatedEvent {

    private Long userId;
    private String name;
}
