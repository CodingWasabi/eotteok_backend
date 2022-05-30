package com.codingwasabi.howtodo.web.event.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codingwasabi.howtodo.web.event.api.dto.EventRequest;
import com.codingwasabi.howtodo.web.event.api.dto.EventResponse;
import com.codingwasabi.howtodo.web.event.entity.Event;
import com.codingwasabi.howtodo.web.event.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
	private static final String BUCKET_FOLDER_PATH = "eot/";

	private final EventRepository eventRepository;
	private final S3Service s3Service;

	public EventResponse.Image upload(MultipartFile image) {
		return new EventResponse.Image(s3Service.upload(getFileKey(image), image));
	}

	private String getFileKey(MultipartFile file) {
		return BUCKET_FOLDER_PATH + UUID.randomUUID() + "." + file.getName();
	}

	public EventResponse.Attend attend(EventRequest.Attend request) {
		if (eventRepository.existsByPhoneNumber(request.getPhoneNumber())) {
			return new EventResponse.Attend(2);
		}
		eventRepository.save(new Event(request.getName(), request.getPhoneNumber(), request.getImg()));
		return new EventResponse.Attend(1);
	}
}
