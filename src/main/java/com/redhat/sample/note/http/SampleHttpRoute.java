package com.redhat.sample.note.http;

import org.apache.camel.Exchange;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class SampleHttpRoute extends EndpointRouteBuilder {
  @Override
  public void configure() throws Exception {
    from(platformHttp("/camel-proxied/notes"))
      .removeHeader(Exchange.HTTP_PATH)
      .to("http://localhost:8080/notes?bridgeEndpoint=true")
      .to("log:info");
  }
}
