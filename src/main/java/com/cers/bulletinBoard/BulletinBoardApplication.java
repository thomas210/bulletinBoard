package com.cers.bulletinBoard;

import com.cers.bulletinBoard.model.Notice;
import com.cers.bulletinBoard.repository.NoticeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.LongStream;

@SpringBootApplication
public class BulletinBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulletinBoardApplication.class, args);
	}

	@Bean
	CommandLineRunner init (NoticeRepository repository) {
		return args -> {
			repository.deleteAll();
			LongStream.range(1, 11)
					.mapToObj(i -> {
						Notice model = new Notice();
						model.setTitle("Notice" + i);
						model.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
						model.setPublicationDate(new Date());
						return  model;
					})
					.map(model -> repository.save(model))
					.forEach(System.out::println);
		};
	}
}
