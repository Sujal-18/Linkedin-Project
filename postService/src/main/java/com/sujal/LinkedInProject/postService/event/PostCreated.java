package com.sujal.LinkedInProject.postService.event;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class PostCreated {
    private Long postOwnerUserId;
    private Long postId;
    private Long connectionUserId;
    private String content;
}
