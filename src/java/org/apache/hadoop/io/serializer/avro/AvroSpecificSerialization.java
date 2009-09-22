/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.io.serializer.avro;

import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

/**
 * Serialization for Avro Specific classes. This serialization is to be used 
 * for classes generated by Avro's 'specific' compiler.
 */
@SuppressWarnings("unchecked")
public class AvroSpecificSerialization 
                          extends AvroSerialization<SpecificRecord>{

  @Override
  public boolean accept(Map<String, String> metadata) {
    if (getClass().getName().equals(metadata.get(SERIALIZATION_KEY))) {
      return true;
    }
    Class<?> c = getClassFromMetadata(metadata);
    return c == null ? false : SpecificRecord.class.isAssignableFrom(c);
  }

  @Override
  protected DatumReader getReader(Map<String, String> metadata) {
    try {
      Class<SpecificRecord> clazz = (Class<SpecificRecord>)
        getClassFromMetadata(metadata);
      return new SpecificDatumReader(clazz.newInstance().getSchema());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected Schema getSchema(SpecificRecord t, Map<String, String> metadata) {
    return t.getSchema();
  }

  @Override
  protected DatumWriter getWriter(Map<String, String> metadata) {
    return new SpecificDatumWriter();
  }

}