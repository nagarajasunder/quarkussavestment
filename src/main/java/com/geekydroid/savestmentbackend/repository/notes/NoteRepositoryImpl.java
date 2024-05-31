package com.geekydroid.savestmentbackend.repository.notes;

import com.geekydroid.savestmentbackend.domain.notes.Note;
import com.geekydroid.savestmentbackend.domain.notes.NoteView;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static com.geekydroid.savestment.domain.db.Tables.SVMT_NOTES;

@ApplicationScoped
@Transactional
public class NoteRepositoryImpl implements NoteRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    DSLContext dslContext;

    @Override
    public Note create(Note note) {
        entityManager.persist(note);
        return note;
    }

    @Override
    public Note update(Note note) {
        return entityManager.merge(note);
    }

    @Override
    public void delete(Note note) {
        entityManager.remove(note);
    }

    @Override
    public Note getById(Long id) {
        return entityManager.createQuery("select n from Note n where n.id = ?1", Note.class)
                .setParameter(1, id)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<NoteView> getByUserId(String userId) {
        return dslContext.select(
                        SVMT_NOTES.ID.as("id"),
                        SVMT_NOTES.TITLE.as("title"),
                        SVMT_NOTES.DESCRIPTION.as("description"),
                        SVMT_NOTES.CREATED_BY.as("created_by"),
                        SVMT_NOTES.CREATED_AT.as("created_at"),
                        SVMT_NOTES.UPDATED_AT.as("updated_at")
                ).from(SVMT_NOTES)
                .where(SVMT_NOTES.CREATED_BY.eq(userId)).fetchInto(NoteView.class);
    }
}
