package io.se7en.apigwtest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PongMessageBodyWorker implements MessageBodyReader<Pong>, MessageBodyWriter<Pong> {
  private final ObjectMapper mapper =
    JsonMapper
      .builder()
      .addModule(new ParameterNamesModule())
      .addModule(new Jdk8Module())
      .addModule(new JavaTimeModule())
      .build();

  @Override
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return type == Pong.class;
  }

  @Override
  public Pong readFrom(
    Class<Pong> type,
    Type genericType,
    Annotation[] annotations,
    MediaType mediaType,
    MultivaluedMap<String, String> httpHeaders,
    InputStream entityStream
  )
    throws IOException,
    WebApplicationException {
    return mapper.readValue(entityStream, Pong.class);
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return type == Pong.class;
  }

  @Override
  public void writeTo(
    Pong t,
    Class<?> type,
    Type genericType,
    Annotation[] annotations,
    MediaType mediaType,
    MultivaluedMap<String, Object> httpHeaders,
    OutputStream entityStream
  )
    throws IOException,
    WebApplicationException {
    mapper.writeValue(entityStream, t);
  }
}
