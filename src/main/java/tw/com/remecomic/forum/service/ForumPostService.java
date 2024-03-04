package tw.com.remecomic.forum.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostCommentedPost;
import tw.com.remecomic.forum.model.bean.ForumPostLike;
import tw.com.remecomic.forum.model.bean.ForumPostPhoto;
import tw.com.remecomic.forum.model.bean.ForumPostReport;
import tw.com.remecomic.forum.model.bean.ForumPostTag;
import tw.com.remecomic.forum.model.bean.ForumReportVerify;
import tw.com.remecomic.forum.model.bean.ForumTag;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.bean.ForumUserConnection;
import tw.com.remecomic.forum.model.dao.ForumPostCommentedPostDao;
import tw.com.remecomic.forum.model.dao.ForumPostDao;
import tw.com.remecomic.forum.model.dao.ForumPostLikeDao;
import tw.com.remecomic.forum.model.dao.ForumPostPhotoDao;
import tw.com.remecomic.forum.model.dao.ForumPostReportDao;
import tw.com.remecomic.forum.model.dao.ForumPostTagDao;
import tw.com.remecomic.forum.model.dao.ForumReportVerifyDao;
import tw.com.remecomic.forum.model.dao.ForumTagDao;
import tw.com.remecomic.forum.model.dto.ForumPostDto;
import tw.com.remecomic.forum.model.dto.ForumReportForPostDto;
import tw.com.remecomic.forum.model.dto.ForumTagDto2;

@Service
@Transactional
public class ForumPostService {
	
	@Autowired
	private ForumPostDao postDao;
	@Autowired
	private ForumPostCommentedPostDao forumPostCommentedPostDao;
	@Autowired
	private ForumPostLikeDao likeDao;
	@Autowired
	private ForumTagDao tagDao;
	@Autowired
	private ForumPostPhotoDao photoDao;
	@Autowired
	private ForumPostTagDao postTagConnectionDao;
	@Autowired
	private ForumPostReportDao reportDao;
	@Autowired
	private ForumReportVerifyDao reportVerifyDao;
	
	
	
	
//////////////////////////// general function ///////////////////////////////
	
	//////////////// post list need to show /////////////////
	//////////////// post list can not show ( redis? ) /////////////////
	//PS not in : (1)user have reported、 (2)user have blocked
	//   sort by (1)new 、(2)hot(3hour、today、week、month)
	
	//////////////// clear middle table /////////////////
	public boolean deleteOldTagConnection(ForumPost post) {
		List<ForumPostTag> findByPost = postTagConnectionDao.findByPost(post);
		postTagConnectionDao.deleteAll(findByPost);
//		for(ForumPostTag postTag : findByPost) {
//			postTagConnectionDao.delete(postTag);
//		}
		List<ForumPostTag> check = postTagConnectionDao.findByPost(post);
		System.out.println("check="+check);
		if(check.isEmpty()) {
			System.out.println("成功刪除tag成功");
			return true;
		}return false;
	}
	public boolean deleteOldPhotoConnection(ForumPost post) {
		List<ForumPostPhoto> findByPost = photoDao.findByPost(post);
		photoDao.deleteAll(findByPost);
//		for(ForumPostPhoto postTag : findByPost) {
//			photoDao.delete(postTag);
//		}
		List<ForumPostPhoto> check = photoDao.findByPost(post);
		System.out.println("check="+check);
		if(check.isEmpty()) {
			System.out.println("成功刪除photo成功");
			return true;
		}return false;
	}

	//////////////// clear not use orphan(which can not delete by cascade) /////////////////
	public boolean deleteNoUseTag(){
		List<ForumTag> findNotUsedTag = tagDao.findNotUsedTag();
		if(findNotUsedTag!=null) {
			tagDao.deleteAll(findNotUsedTag);
			return true;
		}return false;
	}
	
//////////////////////////// show post ///////////////////////////////
	
	public List<ForumPost> findAllPost() {
		List<ForumPost> postResult = postDao.findAll();
		return postResult;
	}
	
	
	public ForumPost findOnePostByPostId(Integer postId) {
		Optional<ForumPost> postResult = postDao.findById(postId);
		if(postResult.isPresent()) {
			return postResult.get();
		}
		return null;
	}
	//////////////// all (n+h) (show post) /////////////////
	//show page
	public PageImpl<ForumPostDto> getNewPost(Integer pageNumber,ForumUser user) {
		Pageable pgb = PageRequest.of(pageNumber-1,5, Sort.Direction.DESC,"postTime");
		String prublicString  = "public";
		Page<ForumPost> postPage = postDao.findByGroupIsNullAndPublicStatus(prublicString,pgb);
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	public PageImpl<ForumPostDto> getHotPostInTimePeriod(Integer pageNumber,Integer periodNumber,ForumUser user) {
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime past = now.minusDays(periodNumber);
		Date dateNow = Date.from(now.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Date datePast = Date.from(past.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Pageable pgb = PageRequest.of(pageNumber-1,10);
		
		Page<ForumPost> postPage = postDao.findHotPostInTimePeriod(datePast, dateNow, pgb);
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
//		return postPage;
	}
	public Page<Map<String, Object[]>> getHotPostInTimePeriodTryFast(Integer pageNumber,Integer periodNumber,ForumUser user) {
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime past = now.minusDays(periodNumber);
		Date dateNow = Date.from(now.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Date datePast = Date.from(past.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Pageable pgb = PageRequest.of(pageNumber-1,10);
		
		Page<Map<String, Object[]>> postPage = postDao.findHotPostInTimePeriodTryFast(datePast, dateNow, pgb);
//		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
		
//		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
		return postPage;
	}
	
	//////////////// tag (n+h) (show post) /////////////////
	//show page
	public PageImpl<ForumPostDto> getNewPostByTag(Integer pageNumber,ForumUser user,Integer tagId) {
		Pageable pgb = PageRequest.of(pageNumber-1,5, Sort.Direction.DESC,"postTime");
		Page<ForumPost> postPage = postDao.findByGroupIsNullByTag(tagId,pgb);
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	public PageImpl<ForumPostDto> getHotPostInTimePeriodByTag(Integer pageNumber,Integer periodNumber,ForumUser user,Integer tagId) {
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime past = now.minusDays(periodNumber);
		Date dateNow = Date.from(now.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Date datePast = Date.from(past.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Pageable pgb = PageRequest.of(pageNumber-1,10);
		
		Page<ForumPost> postPage = postDao.findHotPostInTimePeriodByTag(datePast, dateNow,tagId, pgb);
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
//		return postPage;
	}
	//////////////// filter by follows (n+h) (show post) /////////////////
	
	// post/comment 、 like 、 forward
	public PageImpl<ForumPostDto> getNewPostWithFollow(Integer pageNumber,ForumUser user) {
		
		List<Integer> following = new ArrayList<>();
		if(user.getFollowingOrBlocking()!=null && user.getFollowingOrBlocking().size()!=0) {
			following.addAll(user.getFollowingOrBlocking().stream()
					.filter(follow -> follow.getConnectionType().equals("follow"))
					.map(ForumUserConnection::getPassiveUser).map(ForumUser::getUserId).toList());
		}else {
			System.out.println("followingList is nothiong ");			
		}
		following.add(user.getUserId());
		
		Pageable pgb = PageRequest.of(pageNumber-1,10);
		Page<ForumPost> postPage = postDao.findNewPostWithFollowIn(following,pgb);
		
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	
	//////////////// filter by users owner and forward (n) (show post) /////////////////
	public PageImpl<ForumPostDto> getPostInProfileOwnerAndForward(Integer pageNumber,ForumUser loginUser,ForumUser profileUser) {
						
		Pageable pgb = PageRequest.of(pageNumber-1,5);
		Page<ForumPost> postPage = postDao.findByPostUserOwerAndForward(profileUser,pgb);
		
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,loginUser,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	
	//////////////// filter by users media (n) (show post) /////////////////
	public PageImpl<ForumPostDto> getPostInProfileOwnerHavePhoto(Integer pageNumber,ForumUser loginUser,ForumUser profileUser) {
		
		Pageable pgb = PageRequest.of(pageNumber-1,5,Sort.Direction.DESC,"postTime");
		Page<ForumPost> postPage = postDao.findByPostUserOwerHavePhoto(profileUser,pgb);
		
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,loginUser,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	
	//////////////// filter by users comment (n) (show post) /////////////////
	public PageImpl<ForumPostDto> getPostInProfileOwnerIsComment(Integer pageNumber,ForumUser loginUser,ForumUser profileUser) {
		
		Pageable pgb = PageRequest.of(pageNumber-1,5,Sort.Direction.DESC,"postTime");
		Page<ForumPost> postPage = postDao.findByPostUserOwerIsComment(profileUser,pgb);
		
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,loginUser,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	
	//////////////// filter by users like (n) (show post) /////////////////
	public PageImpl<ForumPostDto> getPostInProfileUserLike(Integer pageNumber,ForumUser loginUser,ForumUser profileUser) {
		
		Pageable pgb = PageRequest.of(pageNumber-1,5);
		Page<ForumPost> postPage = postDao.findByPostUserLike(profileUser,pgb);
		
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,loginUser,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	
	//////////////// filter by user collect (n) (show post) /////////////////
	public PageImpl<ForumPostDto> getPostInProfileUserCollect(Integer pageNumber,ForumUser loginUser,ForumUser profileUser) {
		
		Pageable pgb = PageRequest.of(pageNumber-1,5);
		Page<ForumPost> postPage = postDao.findByPostUserCollect(profileUser,pgb);
		
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,loginUser,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	////////////////filter by user private (n) (show post) /////////////////
	public PageImpl<ForumPostDto> getPostUserPrivate(Integer pageNumber,ForumUser loginUser) {
	
	Pageable pgb = PageRequest.of(pageNumber-1,5);
	Page<ForumPost> postPage = postDao.findByPostUserPrivate(loginUser,pgb);
	
	List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,loginUser,true)).toList();
	
	return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	
	//////////////// filter by have report (n+h) (show post) /////////////////
	
	public List<ForumReportForPostDto> getPostWithReport(Integer pageNumber,List<Integer> verifyIdList) {
		
		Pageable pgb = PageRequest.of(pageNumber-1,5);
//		Integer[] verifyIdArray = {1,2};
		List<ForumPost> posts = postDao.findByPostWithReport(verifyIdList);
		
		List<ForumReportForPostDto> postDtos = posts.stream().map(post->ForumReportForPostDto.sendToFrontend(post,verifyIdList)).toList();
		
		return postDtos;
	}
	public PageImpl<ForumReportForPostDto> getPostWithReportWithPage(Integer pageNumber,List<Integer> verifyIdList) {
		
		Pageable pgb = PageRequest.of(pageNumber-1,5);
//		Integer[] verifyIdArray = {1,2};
		Page<ForumPost> postPage = postDao.findByPostWithReportWithPage(verifyIdList,pgb);
		
		List<ForumReportForPostDto> list = postPage.getContent().stream().map(post->ForumReportForPostDto.sendToFrontend(post,verifyIdList)).toList();
		
		return new PageImpl<ForumReportForPostDto>(list,pgb,postPage.getTotalElements());
	}
	
	//////////////// filter by search (n+h) (show post) /////////////////
	
	public PageImpl<ForumPostDto> getNewPostBySearch(Integer pageNumber,ForumUser user,String searchString) {
		Pageable pgb = PageRequest.of(pageNumber-1,10, Sort.Direction.DESC,"postTime");
		Page<ForumPost> postPage = postDao.findByGroupIsNullBySearch(searchString,pgb);

		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
//		return postPage;
		System.out.println("total="+postPage.getTotalElements());
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	public PageImpl<ForumPostDto> getHotPostInTimePeriodBySearch(Integer pageNumber,Integer periodNumber,ForumUser user,String searchString) {
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime past = now.minusDays(periodNumber);
		Date dateNow = Date.from(now.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Date datePast = Date.from(past.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Pageable pgb = PageRequest.of(pageNumber-1,10);
		
		Page<ForumPost> postPage = postDao.findHotPostInTimePeriodBySearch(datePast, dateNow,searchString, pgb);
		System.out.println("total="+postPage.getTotalElements());
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
//		return postPage;
	}
	
	//////////////// filter by users in group (n+h) (show post) /////////////////
	public PageImpl<ForumPostDto> getNewPostInGroup(Integer pageNumber,ForumUser user,ForumGroup group) {
		Pageable pgb = PageRequest.of(pageNumber-1,5, Sort.Direction.DESC,"postTime");
		
		Page<ForumPost> postPage = postDao.findByGroup(group,pgb);
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	public PageImpl<ForumPostDto> getHotPostInTimePeriodInGroup(Integer pageNumber,Integer periodNumber,ForumUser user,Integer groupId) {
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime past = now.minusDays(periodNumber);
		Date dateNow = Date.from(now.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Date datePast = Date.from(past.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Pageable pgb = PageRequest.of(pageNumber-1,5);
		
		Page<ForumPost> postPage = postDao.findHotPostInTimePeriodByGroup(datePast, dateNow,groupId, pgb);
		List<ForumPostDto> list = postPage.getContent().stream().map(post->ForumPostDto.sendToFrontend(post,false,user,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}
	
	
	//read all
	public List<ForumPostDto> getAllPost() {
		List<ForumPost> allList = postDao.findAll();
		List<ForumPostDto> list = allList.stream().map(post->ForumPostDto.sendToFrontend(post,false,null,false)).toList();
		return list;
	}

	public List<ForumPostCommentedPost> getAllComment() {
		List<ForumPostCommentedPost> allList = forumPostCommentedPostDao.findAll();
		return allList;
	}
	public List<ForumPostLike> getAllLikes() {
		List<ForumPostLike> allList = likeDao.findAll();
		return allList;
	}

	
	///////////////////////////// show comments belong a post (show post)///////////////////////////////
	public PageImpl<ForumPostDto> getCommentByPost(Integer pageNumber,ForumUser loginUser,ForumPost post) {
		
		Pageable pgb = PageRequest.of(pageNumber-1,10);
		Page<ForumPost> postPage = forumPostCommentedPostDao.findCommentByPost(post,pgb);
//		return postPage;
		List<ForumPostDto> list = postPage.getContent().stream().map(pagePost->ForumPostDto.sendToFrontend(pagePost,true,loginUser,true)).toList();
		
		return new PageImpl<ForumPostDto>(list,pgb,postPage.getTotalElements());
	}



////////////////////////////add post //////////////////////////////
// to comment
// with tags
// with photos
	//add a post
	public ForumPostDto addOnePost(ForumPost toCommentPost,String postContent,ForumUser user,
			String[] tagArray,String[] photoArray,ForumGroup group,String publicStatus) {
		
		ForumPost newPost = new ForumPost(); 
		newPost.setPostContent(postContent);
		newPost.setPostUser(user);
		
		newPost.setPublicStatus(publicStatus);
		
		if(group!=null) {
			newPost.setGroup(group);
		}
		
		//先save這個new post!!! 再來考慮後續的comment、tag的有無、photot有無!
		ForumPost post = postDao.save(newPost);
		
		//處理標籤Array(tagArray)
		if(tagArray!=null) {
			makeTagInPost(newPost,tagArray,false);
		}
		//處理圖片Array(photoArray)
		if(photoArray!=null) {
			makePhotoInPost(newPost,photoArray);
		}
		
		//如果要新增的是某則貼文下的一則評論
		if(toCommentPost!=null) { 
			ForumPostCommentedPost commentConnection = new ForumPostCommentedPost(); 
			commentConnection.setParentPost(toCommentPost);
			commentConnection.setChildPost(newPost); 
			newPost.setToCommentPost(commentConnection);
			ForumPostCommentedPost saveConnection = forumPostCommentedPostDao.save(commentConnection);
		}
		return ForumPostDto.sendToFrontend(post,false,null,false);			
	}

///////////////////////////// update post //////////////////////////////
	//update a post  // by user 
	public ForumPostDto updateOnePost(Integer postId,String postContent,String[] tagArray,String[] photoArray) {
		Optional<ForumPost> postResult = postDao.findById(postId);
		if(postResult.isPresent()) {
			ForumPost post = postResult.get();
			post.setPostContent(postContent);
			//處理標籤Array(tagArray)
			//		1、先把原有的tag關係都清除
			boolean check1 = deleteOldTagConnection(post);
			if(check1) {
				////	2、建立新的tag關係
				if(tagArray!=null) {
					makeTagInPost(post,tagArray,true);
				}
				//		3、把沒有用到的tag刪除
				deleteNoUseTag();
			}else{
				System.out.println("沒有刪除tag成功");
			}
			
			//處理圖片Array(photoArray)
			//		1、先把原有的photo關係都清除
			boolean check2 = deleteOldPhotoConnection(post);
			if(check2) {
				////	2、建立新的photo關係
				if(photoArray!=null) {
					makePhotoInPost(post,photoArray);
				}
			}else {
				System.out.println("沒有刪除photo成功");
			}
			return ForumPostDto.sendToFrontend(post,false,null,false);
		}
		return null;
	}
	//update a post ( only public status )
	public ForumPostDto changePostPublicStatus(Integer postId,String publicStatusString) {
		
		Optional<ForumPost> postResult = postDao.findById(postId);
		if(postResult.isPresent()&&(publicStatusString.equals("private")||publicStatusString.equals("public"))) {
			ForumPost forumPost = postResult.get();
			ForumUser postUser = forumPost.getPostUser();
			forumPost.setPublicStatus(publicStatusString);
			 ForumPost check = postDao.findByPostIdAndPublicStatus(postId,publicStatusString);
			if(check!=null) {
				return ForumPostDto.sendToFrontend(forumPost, false, postUser, true);	
			}else{
				return null;						
			}
		}
		return null;
	}
	//update a post // by verify : post has already received 3 reports, so your post public status has become private
	public List<ForumReportForPostDto> hiddenPostsByVerify(List<Integer> postIdList,List<Integer> verifyIdList) {
		List<ForumReportVerify> verifyList = reportVerifyDao.findAllById(verifyIdList);
		List<ForumPost> posts = postDao.findAllById(postIdList);
		List<ForumPostReport> reports = reportDao.findByPostInAndReportVerifyIn(posts,verifyList);
		if(posts!=null&&reports!=null ) {
			posts.forEach(post->{
				post.setPublicStatus("private");
				System.out.println("post.getVerifyHidden()"+post.getVerifyHidden());
				post.setVerifyHidden(post.getVerifyHidden()+1);
				System.out.println("post.getVerifyHidden()"+post.getVerifyHidden());
			});
			reportDao.deleteAll(reports);
			List<ForumPostReport> check = reportDao.findByPostInAndReportVerifyIn(posts,verifyList);
			if(check!=null) {
				return null;
			}else{
				return posts.stream().map(post->ForumReportForPostDto.sendToFrontend(post,verifyIdList)).toList();										
			}
		}
		return null;
	}
///////////////////////////// delete post /////////////////////////////
	//delete a post
	public boolean deleteOnePostById(Integer postId) {
		Optional<ForumPost> optionalPost = postDao.findById(postId);
		if(optionalPost.isPresent()) {
			ForumPost post = optionalPost.get();
			postDao.delete(post);
			deleteNoUseTag();
			return true;
		}
		return false;
	}
	//delete post list // by verify : post has already received many reports,so your post has been deleted
	public boolean deletePostsByVerify(List<Integer> postIdList) {
		List<ForumPost> posts = postDao.findAllById(postIdList);
		if(posts!=null) {
			for(ForumPost post : posts) {
				ForumUser user = post.getPostUser();
				user.setDeletedPostsCount(user.getDeletedPostsCount()+1);
			}
			//這邊可能會導向notify的新增，通知使用者貼文被刪除
			postDao.deleteAll(posts);
			List<ForumPost> check = postDao.findAllById(postIdList);
			return check==null? true:false;
		}
		return false;
	}

/////////////////////////// post interactive //////////////////////////////

	public void makeTagInPost(ForumPost newPost,String[] tagArray,boolean hasExistPost) {
		
		List<ForumTag> existTags = tagDao.findByTagNameIn(tagArray);
		Collection<ForumPostTag> newtagsCollection = new ArrayList<>();
		List<String> existTagsStringList = new ArrayList<>();
		
		if(existTags!=null) { //如果該貼文中有已經存在DB的標籤
			existTagsStringList = existTags.stream().map(ForumTag::getTagName).toList();
		}else {
			existTags = new ArrayList<>();
		}
		
		//如果該貼文中有DB中還沒有的標籤，就需要先建立新tag(ForumTag)
		for(String newTagString : tagArray) {
			if(!existTagsStringList.contains(newTagString)) { //排除掉已經存在DB中的tag
				ForumTag newTag = new ForumTag();
				newTag.setTagName(newTagString);
				
				//原本的寫法
//				ForumTag saveTag = tagDao.save(newTag);
				//chatgpt 建議：
	            // 檢查是否已經存在相同 'tagId' 的記錄
	            ForumTag existingTag = tagDao.findByTagName(newTagString);
	            if (existingTag != null) {
	                newTag = existingTag; // 使用現有的標籤
	            } else {
	                newTag = tagDao.save(newTag); // 插入新的標籤
	            }
				existTags.add(newTag);
			}
		}
		for(ForumTag tag : existTags) { //為標籤跟貼文建立聯繫(ForumPostTag)
			ForumPostTag postTagConnection = new ForumPostTag();
			postTagConnection.setTag(tag);
			postTagConnection.setPost(newPost);
			if(!hasExistPost) {
				postDao.save(newPost);				
			}
			ForumPostTag saveConnection = postTagConnectionDao.save(postTagConnection);
			newtagsCollection.add(saveConnection);
		}
		
		newPost.setTags(newtagsCollection);
	}
	
	public void makePhotoInPost(ForumPost newPost,String[] photoArray) {
		List<ForumPostPhoto> photoList = new ArrayList<>();
		for(String photoUrl : photoArray) {
			ForumPostPhoto photo = new ForumPostPhoto();
			photo.setPhoto(photoUrl);
			photo.setPost(newPost);
			photoList.add(photo);
		}
		photoDao.saveAll(photoList);
	}
}
