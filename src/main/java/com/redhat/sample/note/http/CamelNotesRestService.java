package com.redhat.sample.note.http;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;

public class CamelNotesRestService extends EndpointRouteBuilder {
  @Override
  public void configure() {
    JacksonDataFormat dataFormat = new JacksonDataFormat();
    dataFormat.setAutoDiscoverObjectMapper(true);

    restConfiguration()
        .component("platform-http")
        .bindingMode(RestBindingMode.json);

    rest("/camel/sample").get().to("direct:sample");
    from("direct:sample").transform().constant("Hi there");

    rest("/camel/notes")
        .get()
        .produces("application/json")
        .to("bean:notesService?method=getAll");

    rest("/camel/notes/{id}")
        .delete()
        .to("bean:notesService?method=deleteNote(${header.id})");


    from(platformHttp("/camel/notes?httpMethodRestrict=POST"))
        .bean("notesService", "addNote")
        .marshal(dataFormat)
        .to("log:info");
  }
}
