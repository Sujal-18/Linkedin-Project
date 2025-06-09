package com.sujal.LinkedInProject.postService.event;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PostLiked {
    private Long ownerUserId;
    private Long postId;
    private Long likedByUserId;
}
