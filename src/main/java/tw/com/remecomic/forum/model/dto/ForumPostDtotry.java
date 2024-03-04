package tw.com.remecomic.forum.model.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ForumPostDtotry {
    private Integer postId;
    private Integer userId;
    private String postContent;
    private Integer groupId;
    private Date postTime;
    private Integer likeAmount;
    private Integer forwardAmount;
    private Integer commentAmount;
    private Integer totalInteractions;

    public ForumPostDtotry(Integer postId, Integer userId, String postContent, Integer groupId, Date postTime, Integer likeAmount, Integer forwardAmount, Integer commentAmount, Integer totalInteractions) {
        this.postId = postId;
        this.userId = userId;
        this.postContent = postContent;
        this.groupId = groupId;
        this.postTime = postTime;
        this.likeAmount = likeAmount;
        this.forwardAmount = forwardAmount;
        this.commentAmount = commentAmount;
        this.totalInteractions = totalInteractions;
    }
}