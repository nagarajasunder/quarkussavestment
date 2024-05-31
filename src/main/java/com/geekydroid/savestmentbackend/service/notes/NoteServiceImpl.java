package com.geekydroid.savestmentbackend.service.notes;

import com.geekydroid.savestmentbackend.domain.notes.Note;
import com.geekydroid.savestmentbackend.domain.notes.NoteRequest;
import com.geekydroid.savestmentbackend.domain.notes.NoteView;
import com.geekydroid.savestmentbackend.repository.notes.NoteRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
@Transactional
public class NoteServiceImpl implements NoteService {


    @Inject
    NoteRepository repository;

    @Override
    public Note create(NoteRequest request) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setDescription(request.getDescription());
        note.setCreatedBy(request.getCreatedBy());
        note.setCreatedAt(Instant.now());
        note.setUpdatedBy(request.getUpdatedBy());
        note.setUpdatedAt(Instant.now());
        repository.create(note);
        return note;
    }

    @Override
    public Note update(NoteRequest request) {
        if (request.getId() == null) {
            throw new BadRequestException("Note id cannot be empty!");
        }
        Note existingNote = repository.getById(request.getId());
        if (existingNote == null) {
            throw new BadRequestException(String.format("Note with id %d doesn't exist", request.getId()));
        }
        existingNote.setTitle(request.getTitle());
        existingNote.setDescription(request.getDescription());
        existingNote.setUpdatedBy(request.getUpdatedBy());
        existingNote.setUpdatedAt(Instant.now());
        repository.update(existingNote);
        return existingNote;
    }

    @Override
    public void delete(Long noteId) {
        Note existingNote = repository.getById(noteId);
        if (existingNote == null) {
            throw new BadRequestException(String.format("Note with id %d doesn't exist", noteId));
        }
        repository.delete(existingNote);
    }

    @Override
    public NoteView getById(Long noteId) {
        Note existingNote = repository.getById(noteId);
        if (existingNote == null) {
            throw new BadRequestException(String.format("Note with id %d doesn't exist", noteId));
        }
        return new NoteView(existingNote.getId(), existingNote.getTitle(), existingNote.getDescription(), existingNote.getCreatedBy(), existingNote.getCreatedAt(), existingNote.getUpdatedAt());
    }

    @Override
    public List<NoteView> getByUserId(String userId) {
        return repository.getByUserId(userId);
    }
}
