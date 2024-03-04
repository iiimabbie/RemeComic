package tw.com.remecomic.forum.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostCollect;
import tw.com.remecomic.forum.model.bean.ForumPostCommentedPost;
import tw.com.remecomic.forum.model.bean.ForumPostForward;
import tw.com.remecomic.forum.model.bean.ForumPostLike;
import tw.com.remecomic.forum.model.bean.ForumPostPhoto;
import tw.com.remecomic.forum.model.bean.ForumPostTag;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.bean.ForumUserConnection;

@Data
@NoArgsConstructor
@Component
public class ForumPostDto {
	
	private Map<String,List<ForumUserDto>> connectMap;//show because how many following users like/forward?
	
	private Integer postId;
	
	private Integer postUserId;
	private String postUserName;
	private String postUserPhoto;
	
	private String postContent;
	
	private Integer groupId;
	private String groupName;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
	private Date postTime;
	
	private String publicStatus;
	
	private Map<Integer,String> postTagMap;
	private List<ForumPostPhoto> photos;
	
	private Integer likeAmount;	
	private Integer forwardAmount; //11/27 new
	private Integer commentAmount;
	
	private boolean loginUserLiked; //11/27 new
	private boolean loginUserForwarded; //11/27 new
	private boolean loginUserCollected; //11/27 new
	
	private Integer toCommentPostId;
	
	private List<ForumPostDto> parentPostDtoList;
	//private Integer[] commentPostIdList;
	
	
	public static ForumPostDto sendToFrontend(ForumPost post,boolean isComment,ForumUser user,boolean isFilterByFollow) {
		ForumPostDto postDto = new ForumPostDto();
		
		postDto.setPostId(post.getPostId());
		postDto.setPostUserId(post.getPostUser().getUserId());
		postDto.setPostUserName(post.getPostUser().getName());
		postDto.setPostUserPhoto(post.getPostUser().getUserPhoto());
		postDto.setPostContent(post.getPostContent());
		
		if(post.getGroup()!=null) {
			postDto.setGroupId(post.getGroup().getGroupId());
			postDto.setGroupName(post.getGroup().getGroupName());
		}
		postDto.setPostTime(post.getPostTime());
		postDto.setPublicStatus(post.getPublicStatus());
		
		if(user!=null) {
			List<ForumUser> likeList = post.getLikes().stream().map(ForumPostLike::getLikeFromUser).toList();
			List<ForumUser> forwardList = post.getForwards().stream().map(ForumPostForward::getForwardFromUser).toList();
			List<ForumUser> collectList = post.getCollects().stream().map(ForumPostCollect::getCollectFromUser).toList();
			postDto.setLoginUserLiked(likeList.contains(user)?true:false);
			postDto.setLoginUserForwarded(forwardList.contains(user)?true:false);
			postDto.setLoginUserCollected(collectList.contains(user)?true:false);
			if(isFilterByFollow) {
				
				List<ForumUser> followingList = user.getFollowingOrBlocking().stream()
						.filter(follow -> follow.getConnectionType().equals("follow"))
						.map(ForumUserConnection::getPassiveUser).toList();
				List<ForumUserDto> followingsLikeList = likeList.stream().filter(followingList::contains).map(ForumUserDto::sendToFrontendNoFollow).toList();
				List<ForumUserDto> followingsForwardList = forwardList.stream().filter(followingList::contains).map(ForumUserDto::sendToFrontendNoFollow).toList();
				Map<String,List<ForumUserDto>> map = new HashMap<>();
				map.put("like", followingsLikeList);
				map.put("forward", followingsForwardList);
				postDto.setConnectMap(map);
			}
		}
		
		// tag
		Map<Integer,String> tagsMap = new HashMap<>();
		postDto.setPostTagMap(tagsMap);
		if(post.getTags()!=null) {
			for(ForumPostTag tag : post.getTags()) {
				tagsMap.put(tag.getTag().getTagId(), tag.getTag().getTagName());
			}			
		}
		// photo
		if(post.getPhotos()!=null) {
			postDto.setPhotos(post.getPhotos().stream().toList());		
		}
		
		postDto.setLikeAmount(post.getLikes()!=null?post.getLikes().size():0);
		postDto.setForwardAmount(post.getForwards()!=null?post.getForwards().size():0);
		postDto.setCommentAmount(post.getComments()!=null?post.getComments().size():0);
		ForumPostCommentedPost toCommentPost = post.getToCommentPost();
		if(toCommentPost!=null) {
			postDto.setToCommentPostId(toCommentPost.getParentPost().getPostId());			
		}
		
		//Integer[] array = post.getComments().stream().map(ForumPostCommentedPost::getChildPost).map(ForumPost::getPostId).toArray(Integer[]::new);
		//postDto.setCommentPostIdList(array);

		//這個while一定要放最後，因為會覆蓋掉post
		if(!isComment) { //如果傳進來的是評論
			List<ForumPostDto> parentDtoList = new ArrayList<>();
			while(true) {
				ForumPostCommentedPost commentConnection = post.getToCommentPost();
				if(commentConnection==null) {
					System.out.println("ParentPostDtoList : xx");
					break;
				}else {
					ForumPost parentPost = commentConnection.getParentPost();
					if(parentPost==null) {
						System.out.println("ParentPostDtoList : xx");
						break;
					}else {
						ForumPostDto parentPostDto = ForumPostDto.sendToFrontend(parentPost,true,user,false);
						parentDtoList.add(parentPostDto);
						System.out.println("parentPost id : "+parentPost.getPostId()+"取代"+"post id : "+post.getPostId());
						post = parentPost;
					}
				}
			}
			postDto.setParentPostDtoList(parentDtoList);
		}
		
		return postDto;
	}
	
	
}
