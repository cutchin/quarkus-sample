package com.redhat.sample.note.service;

import com.redhat.sample.note.model.Note;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.util.Collection;

@ApplicationScoped
@Named("notesService")
@RegisterForReflection
public class NotesService {

  @Transactional
  public Collection<Note> getAll() {
    return Note.listAll();
  }

  @Transactional
  public Note addNote(String noteText) {
    Note note = new Note();
    note.setText(noteText);
    note.persist();

    return note;
  }

  @Transactional
  public void deleteNote(@PathParam("id") Long id) {
    Note note = Note.findById(id);
    note.delete();
  }

  @Path("/search")
  public void searchNotes(@QueryParam("text") String searchString) {
    //
  }
}
