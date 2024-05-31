package com.geekydroid.savestmentbackend.service.notes;

import com.geekydroid.savestmentbackend.domain.notes.Note;
import com.geekydroid.savestmentbackend.domain.notes.NoteRequest;
import com.geekydroid.savestmentbackend.domain.notes.NoteView;

import java.util.List;

public interface NoteService {

    Note create(NoteRequest request);

    Note update(NoteRequest request);

    void delete(Long noteId);

    List<NoteView> getByUserId(String userId);

    NoteView getById(Long noteId);
}
