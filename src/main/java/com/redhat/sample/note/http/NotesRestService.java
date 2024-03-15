package com.redhat.sample.note.http;

import com.redhat.sample.note.model.Note;
import com.redhat.sample.note.service.NotesService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;

@Path("/notes")
@Produces(MediaType.APPLICATION_JSON)
public class NotesRestService {

  @Inject
  NotesService notesService;

  @GET
  public Collection<Note> getAll() {
    return notesService.getAll();
  }

  @POST
  public Note addNote(String noteText) {
    return notesService.addNote(noteText);
  }

  @DELETE
  @Path("/{id}")
  public void deleteNote(@PathParam("id") Long id) {
    notesService.deleteNote(id);
  }
}
