package com.esbnet.planner.trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esbnet.planner.participant.ParticipantCreateResponse;
import com.esbnet.planner.participant.ParticipantData;
import com.esbnet.planner.participant.ParticipantRequestPayload;
import com.esbnet.planner.participant.ParticipantService;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripRepository repository;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);

        this.repository.save(newTrip);
        this.participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {

        Optional<Trip> trip = this.repository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {

        Optional<Trip> trip = this.repository.findById(id);

        if (trip.isPresent()) {
            Trip updatedTrip = trip.get();
            updatedTrip.setDestination(payload.destination());
            updatedTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            updatedTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));

            this.repository.save(updatedTrip);

            return ResponseEntity.ok(updatedTrip);

        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Trip> confimeTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {

        Optional<Trip> trip = this.repository.findById(id);

        if (trip.isPresent()) {
            Trip updatedTrip = trip.get();
            updatedTrip.setIsConfirmed(true);

            this.repository.save(updatedTrip);
            this.participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(updatedTrip);

        }

        return ResponseEntity.notFound().build();
    }

    // Participant

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id) {
        List<ParticipantData> participantList = this.participantService.getAllParticipantsFromEvent(id);

        return ResponseEntity.ok(participantList);
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id,
            @RequestBody ParticipantRequestPayload payload) {

        Optional<Trip> trip = this.repository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ParticipantCreateResponse participantResponse = this.participantService
                    .registerParticipantToEvent(payload.email(), rawTrip);

            if (rawTrip.getIsConfirmed())
                this.participantService.triggerConfirmationEmailToParticipant(payload.email());

            return ResponseEntity.ok(participantResponse);
        }
        return ResponseEntity.notFound().build();

    }

}
