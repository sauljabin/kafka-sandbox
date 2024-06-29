/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package kafka.sandbox.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class TimerMetric extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -3286955059947589751L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TimerMetric\",\"namespace\":\"kafka.sandbox.avro\",\"fields\":[{\"name\":\"avg\",\"type\":\"double\"}],\"version\":\"1\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TimerMetric> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TimerMetric> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<TimerMetric> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<TimerMetric> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<TimerMetric> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this TimerMetric to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a TimerMetric from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a TimerMetric instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static TimerMetric fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private double avg;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TimerMetric() {}

  /**
   * All-args constructor.
   * @param avg The new value for avg
   */
  public TimerMetric(java.lang.Double avg) {
    this.avg = avg;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return avg;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: avg = (java.lang.Double)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'avg' field.
   * @return The value of the 'avg' field.
   */
  public double getAvg() {
    return avg;
  }


  /**
   * Sets the value of the 'avg' field.
   * @param value the value to set.
   */
  public void setAvg(double value) {
    this.avg = value;
  }

  /**
   * Creates a new TimerMetric RecordBuilder.
   * @return A new TimerMetric RecordBuilder
   */
  public static kafka.sandbox.avro.TimerMetric.Builder newBuilder() {
    return new kafka.sandbox.avro.TimerMetric.Builder();
  }

  /**
   * Creates a new TimerMetric RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TimerMetric RecordBuilder
   */
  public static kafka.sandbox.avro.TimerMetric.Builder newBuilder(kafka.sandbox.avro.TimerMetric.Builder other) {
    if (other == null) {
      return new kafka.sandbox.avro.TimerMetric.Builder();
    } else {
      return new kafka.sandbox.avro.TimerMetric.Builder(other);
    }
  }

  /**
   * Creates a new TimerMetric RecordBuilder by copying an existing TimerMetric instance.
   * @param other The existing instance to copy.
   * @return A new TimerMetric RecordBuilder
   */
  public static kafka.sandbox.avro.TimerMetric.Builder newBuilder(kafka.sandbox.avro.TimerMetric other) {
    if (other == null) {
      return new kafka.sandbox.avro.TimerMetric.Builder();
    } else {
      return new kafka.sandbox.avro.TimerMetric.Builder(other);
    }
  }

  /**
   * RecordBuilder for TimerMetric instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TimerMetric>
    implements org.apache.avro.data.RecordBuilder<TimerMetric> {

    private double avg;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(kafka.sandbox.avro.TimerMetric.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.avg)) {
        this.avg = data().deepCopy(fields()[0].schema(), other.avg);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing TimerMetric instance
     * @param other The existing instance to copy.
     */
    private Builder(kafka.sandbox.avro.TimerMetric other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.avg)) {
        this.avg = data().deepCopy(fields()[0].schema(), other.avg);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'avg' field.
      * @return The value.
      */
    public double getAvg() {
      return avg;
    }


    /**
      * Sets the value of the 'avg' field.
      * @param value The value of 'avg'.
      * @return This builder.
      */
    public kafka.sandbox.avro.TimerMetric.Builder setAvg(double value) {
      validate(fields()[0], value);
      this.avg = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'avg' field has been set.
      * @return True if the 'avg' field has been set, false otherwise.
      */
    public boolean hasAvg() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'avg' field.
      * @return This builder.
      */
    public kafka.sandbox.avro.TimerMetric.Builder clearAvg() {
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TimerMetric build() {
      try {
        TimerMetric record = new TimerMetric();
        record.avg = fieldSetFlags()[0] ? this.avg : (java.lang.Double) defaultValue(fields()[0]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TimerMetric>
    WRITER$ = (org.apache.avro.io.DatumWriter<TimerMetric>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TimerMetric>
    READER$ = (org.apache.avro.io.DatumReader<TimerMetric>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeDouble(this.avg);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.avg = in.readDouble();

    } else {
      for (int i = 0; i < 1; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.avg = in.readDouble();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










