package com.geekydroid.savestmentbackend.repository.notes;

import com.geekydroid.savestmentbackend.domain.notes.Note;
import com.geekydroid.savestmentbackend.domain.notes.NoteView;

import java.util.List;

public interface NoteRepository {

    Note create(Note note);

    Note update(Note note);

    void delete(Note note);

    Note getById(Long id);

    List<NoteView> getByUserId(String userId);
}
