package com.geekydroid.savestmentbackend.resources.notes;

import com.geekydroid.savestmentbackend.domain.notes.Note;
import com.geekydroid.savestmentbackend.domain.notes.NoteRequest;
import com.geekydroid.savestmentbackend.domain.notes.NoteView;
import com.geekydroid.savestmentbackend.service.notes.NoteService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static com.geekydroid.savestmentbackend.utils.Constants.USER_ID_HEADER_PARAM_KEY;

@Path("/note")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NoteResource {

    @Inject
    NoteService noteService;

    @POST
    @Path("/create")
    public Response create(NoteRequest request, @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        request.setCreatedBy(userId);
        request.setUpdatedBy(userId);
        Note note = noteService.create(request);
        return Response.ok().entity(note).build();
    }

    @PUT
    @Path("/{noteId}/update")
    public Response update(@PathParam("noteId") Long noteId, NoteRequest request, @HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        request.setId(noteId);
        request.setCreatedBy(userId);
        request.setUpdatedBy(userId);
        Note note = noteService.update(request);
        return Response.ok().entity(note).build();
    }

    @DELETE
    @Path("/{noteId}/delete")
    public Response delete(@PathParam("noteId") Long noteId) {
        noteService.delete(noteId);
        return Response.ok().build();
    }

    @GET
    @Path("/fetch")
    public List<NoteView> getByUserId(@HeaderParam(USER_ID_HEADER_PARAM_KEY) String userId) {
        return noteService.getByUserId(userId);
    }

    @GET
    @Path("/{noteId}/fetch")
    public NoteView getById(@PathParam("noteId") Long noteId) {
        return noteService.getById(noteId);
    }

}
