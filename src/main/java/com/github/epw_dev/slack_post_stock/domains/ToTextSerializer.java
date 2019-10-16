package com.github.epw_dev.slack_post_stock.domains;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ToTextSerializer extends StdSerializer<HasIdValue> {

  ToTextSerializer() {
    this(null);
  }

  ToTextSerializer(Class<HasIdValue> cls) {
    super(cls);
  }

  @Override
  public void serialize(HasIdValue value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeString(value.asText());
  }
}
