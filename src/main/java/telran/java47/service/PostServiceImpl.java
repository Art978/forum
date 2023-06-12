package telran.java47.service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.filter.Filter;
import lombok.RequiredArgsConstructor;
import telran.java47.post.dao.PostRepository;
import telran.java47.post.dto.DatePeriodDto;
import telran.java47.post.dto.NewCommentDto;
import telran.java47.post.dto.NewPostDto;
import telran.java47.post.dto.PostDto;
import telran.java47.post.model.Comment;
import telran.java47.post.model.Post;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	final PostRepository postRepository;
	final ModelMapper modelMapper;

	@Override
	public PostDto addNewPost(String author, NewPostDto newPostDto) {
		Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author, newPostDto.getTags());
		postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto findPostById(String id) {
		Post post = postRepository.findById(id).orElse(null);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto removePost(String id) {
		Post post = postRepository.findById(id).orElse(null);
		postRepository.deleteById(id);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(String id, NewPostDto newPostDto) {
		Post post = postRepository.findById(id).orElse(null);
		if (post.getTitle() != null || post.getContent() != null || post.getTags() != null) {
			post.addTag(newPostDto.getTags().toString());
			post.setContent(newPostDto.getContent());
			post.setTitle(newPostDto.getTitle());
			postRepository.save(post);

		}
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
		Post post = postRepository.findById(id).orElse(null);
		Comment comment = new Comment(author, newCommentDto.getMessage());
		post.addComment(comment);
		postRepository.save(post);
		return modelMapper.map(post, PostDto.class);

	}

	@Override
	public void addLike(String id) {
		Post post = postRepository.findById(id).orElse(null);
		post.addLike();
		postRepository.save(post);
	}

	@Override
	public Iterable<PostDto> findPostsByAuthor(String author) {
		return postRepository.findPostsByAuthor(author)
				.map(a -> modelMapper.map(a, PostDto.class))
				.collect(Collectors.toList());
		
	}

	@Override
	public Iterable<PostDto> findPostsByTags(List<String> tags) {
		return postRepository.findPostsByTags(tags)
				.filter(p -> tags.containsAll(p.getTags()))
				.map(s -> modelMapper.map(s, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto) {
		return postRepository.findPostsByPeriod(datePeriodDto.getDateFrom(),datePeriodDto.getDateTo())
				.map(s -> modelMapper.map(s, PostDto.class))
				.collect(Collectors.toList());
	}

}
