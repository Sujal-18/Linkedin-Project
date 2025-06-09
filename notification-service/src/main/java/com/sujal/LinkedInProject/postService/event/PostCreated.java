package com.sujal.LinkedInProject.postService.event;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostCreated {
    private Long postOwnerUserId;
    private Long postId;
    private Long connectionUserId;
    private String content;
}
