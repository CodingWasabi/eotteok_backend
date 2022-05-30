package com.codingwasabi.howtodo.web.event.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codingwasabi.howtodo.web.event.api.dto.EventRequest;
import com.codingwasabi.howtodo.web.event.api.dto.EventResponse;
import com.codingwasabi.howtodo.web.event.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EventController {
	private final EventService eventService;

	@PostMapping("/event/image")
	ResponseEntity<EventResponse.Image> postImage(@RequestParam("image") MultipartFile image) {
		return ResponseEntity.ok(eventService.upload(image));
	}

	@PostMapping("/event")
	ResponseEntity<EventResponse.Attend> attendEvent(@RequestBody EventRequest.Attend request) {
		return ResponseEntity.ok(eventService.attend(request));
	}
}